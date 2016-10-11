
package com.mycompany.task.controller;

import com.mycompany.task.mapper.MarshrutMapper;
import com.mycompany.task.model.AllRoutesResource;
import com.mycompany.task.model.BusReport;
import com.mycompany.task.model.History;
import com.mycompany.task.model.Route;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report_api")
public class ReportApiController {    
    @Autowired
    private MarshrutMapper segmentsMapper;
    
    //private List<Route> allRoutes;
    
    @RequestMapping(value = "/get_days_of_months", method = RequestMethod.GET)
    public List<Integer> daysOfMonth(@RequestParam("year")String year, @RequestParam("month")String month, 
            @RequestParam("vechicle_id")String vechicleId){
        return segmentsMapper.daysOfMonth(year, month, vechicleId);              
    }
    
    
    //анализ маршрута за сегодняшний день по указанному автобусу
    @RequestMapping(value = "/anylize", method = RequestMethod.GET)
    @ResponseBody
    public List<BusReport> anylize(){
        List<BusReport> busReports = new ArrayList<>(); //возвращаемый список отчетов
               
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        
        List<String> todayBuses = segmentsMapper.getTodayActiveBuses(sqlDate.toString());
        for(String todayBusID: todayBuses) {
            
            flushExcluded(); //очищаем булевские флажки
            BusReport todayReport = null; //отчет по текущему автобусу
            List<History> historyForDay =  segmentsMapper.getHistoryFields(sqlDate.toString(), todayBusID);//история движения
            List<String> routesIds = segmentsMapper.getRoutesForBus(todayBusID); //предписанные маршруты
            
            for(int i=0; i < historyForDay.size(); i++){

                String currentSectionId = historyForDay.get(i).getSectionID().toString();
                
                AllRoutesResource.allRoutes.forEach(route -> {
                    
                    if (!route.getExcluded()){
                        if (!route.getSectionsIds().contains(currentSectionId)){
                            route.setExcluded(Boolean.TRUE);
                        }
                    }
                });       
            }
            
            Iterator<Route> allRoutesIterator = AllRoutesResource.allRoutes.iterator();
            while(allRoutesIterator.hasNext()){
                Route route = allRoutesIterator.next();
                if (!route.getExcluded())
                {                    
                    todayReport = new BusReport(todayBusID, routesIds, route.getRouteId().toString());
                    break;
                }
            }
            if (todayReport !=null){
                busReports.add(todayReport);
                continue;
            }
            //на какой маршрут более всего похоже,если точного совпадения не найдено 
            for(int i=0; i < historyForDay.size(); i++){
                String currentSectionId = historyForDay.get(i).getSectionID().toString();
                AllRoutesResource.allRoutes.forEach(route -> {
                    //в текущем маршруте содержится выбранная секция
                    if (route.getSectionsIds().contains(currentSectionId)){
                        route.setCounter(route.getCounter() + 1);
                    }
                });        
            }
        
            AllRoutesResource.allRoutes.sort(routeCounterComparator);
            Integer checkingRoute = AllRoutesResource.allRoutes.get(0).getCounter();
            if (checkingRoute > 0)
                //высичтать процент совпадения по маршрутам
                todayReport = new BusReport(todayBusID, routesIds, "больше всего совпадений с маршрутом " + checkingRoute);
            if (todayReport == null)
                todayReport = new BusReport(todayBusID, routesIds, "в базе нет соотвествующего маршрута");
            
            busReports.add(todayReport);
        }
        return busReports;
    }
    
    
    //второй вариант алгоритма
    @RequestMapping(value = "/anylize2", method = RequestMethod.GET)
    @ResponseBody
    private List<BusReport> anylize2()
    {
        List<BusReport> busReports = new ArrayList<>(); //возвращаемый список отчетов
               
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        //String sqlDate = "2016-10-07";
        List<String> todayBuses = segmentsMapper.getTodayActiveBuses(sqlDate.toString());
        
        for(String todayBusID: todayBuses) 
        {          
            BusReport todayReport = null; //отчет по текущему автобусу
            List<History> historyForDay =  segmentsMapper.getHistoryFields(sqlDate.toString(), todayBusID);//история движения
            List<String> routesIds = segmentsMapper.getRoutesForBus(todayBusID); //предписанные маршруты
            List<Route> dublicateAllRoutes = new ArrayList();//рабочий список дубликатов маршрута
            dublicateAllRoutes.addAll(AllRoutesResource.allRoutes);
          
            int indexCrashSection = getCrashSection(dublicateAllRoutes, historyForDay,0);  
            if (indexCrashSection == -1){
                if (dublicateAllRoutes.size() == 1)
                    todayReport = new BusReport(todayBusID, routesIds, dublicateAllRoutes.get(0).getRouteId().toString());
                if (dublicateAllRoutes.size() > 1){
                    todayReport = new BusReport(todayBusID, routesIds, "мало данных.число секций за день " + historyForDay.size());
                }
            }
            else if (indexCrashSection > -1){
                //вторичный запуск
                dublicateAllRoutes = new ArrayList();
                dublicateAllRoutes.addAll(AllRoutesResource.allRoutes);
                indexCrashSection = getCrashSection(dublicateAllRoutes, historyForDay,indexCrashSection + 1);
                if (indexCrashSection == -1){
                    if (dublicateAllRoutes.size() == 1)
                        todayReport = new BusReport(todayBusID, routesIds, dublicateAllRoutes.get(0).getRouteId().toString());
                    if (dublicateAllRoutes.size() > 1){
                        todayReport = new BusReport(todayBusID, routesIds, "мало данных.число секций за день " + historyForDay.size());
                    }
                    /*else if (indexCrashSection > -1){
                       //третичный запуск
                    }*/
                }
            }

            if (todayReport == null)
            todayReport = new BusReport(todayBusID, routesIds, "cовпадающий маршрут не найден");
            //добавляем в список
            busReports.add(todayReport);
        }
        return busReports;
    }
    
    //процедурка будет возвращать индекс секции,в которой откинулись все маршруты
    
    private static Comparator<Route> routeCounterComparator = new Comparator<Route>(){
        @Override
        public int compare(Route o1, Route o2) {
            return o2.getCounter().compareTo(o1.getCounter());
        }   
    };
    
    private int getCrashSection(List<Route> dublicateAllRoutes, List<History> historyForDay, int startIndex){
        int indexCrash = -1;
        flushExcluded();
        for(int i= startIndex; i < historyForDay.size(); i++){
                
                String currentSectionId = historyForDay.get(i).getSectionID().toString(); //текущая сверяемая секция
                
                for(Route route: AllRoutesResource.allRoutes){
                     if (!route.getExcluded())
                     {
                        if (!route.getSectionsIds().contains(currentSectionId)){
                            route.setExcluded(Boolean.TRUE);
                            int pos = dublicateAllRoutes.indexOf(route);
                            if (pos > -1)
                            dublicateAllRoutes.remove(pos);
                        }
                        //искомый маршрут остался
                        if (dublicateAllRoutes.size() == 1){
                            break;
                        }
                        if (dublicateAllRoutes.isEmpty()){
                            indexCrash = i;
                            break;
                        }
                    }
                }                
        }
        return indexCrash;
    }
    
    private void flushExcluded(){
        AllRoutesResource.allRoutes.forEach((Route route) -> {
            route.setExcluded(Boolean.FALSE);
            route.setCounter(0);
        });
    }
}
