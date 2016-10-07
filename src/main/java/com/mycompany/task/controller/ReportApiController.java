
package com.mycompany.task.controller;

import com.mycompany.task.mapper.MarshrutMapper;
import com.mycompany.task.model.History;
import com.mycompany.task.model.Route;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    
    private List<Route> allRoutes;
    
    @RequestMapping(value = "/get_days_of_months", method = RequestMethod.GET)
    public List<Integer> daysOfMonth(@RequestParam("year")String year, @RequestParam("month")String month, 
            @RequestParam("vechicle_id")String vechicleId){
        return segmentsMapper.daysOfMonth(year, month, vechicleId);              
    }
    
    
    //анализ маршрута за сегодняшний день
    @RequestMapping(value = "/anylize", method = RequestMethod.GET)
    public void anylize(@RequestParam("bus_id")String busId){
        allRoutes = segmentsMapper.getAllRoutes();
        java.sql.Date sqlDate = new java.sql.Date(new Date().getTime());
        List<History> historyForDay =  segmentsMapper.getHistoryFields(sqlDate.toString(), busId);
        //кол-во совпадений просчитать
        for(int i=0; i < historyForDay.size(); i++){
            
            String currentSectionId = historyForDay.get(i).getSectionID().toString();
            
            allRoutes.forEach(route -> {
                if (!route.getExcluded()){
                    if (!route.getSectionsIds().contains(currentSectionId))
                        route.setExcluded(Boolean.TRUE);
                }
            });       
        }
        System.out.println("МАРШРУТ, ПО КОТОРОМУ ЕЗДИЛ");
        allRoutes.forEach(route -> {
            if (!route.getExcluded())
                System.out.println(route.getRouteId());
        });   
        
        /*List<String> routesIds = segmentsMapper.getRoutesForBus(busId);               
        List<Route> routes =  new ArrayList<>();       
        String sectionStr = "";
        Route route;       
        for(int i=0; i < routesIds.size(); i++) {           
            route = new Route();
            Integer routeId = Integer.parseInt(routesIds.get(0));
            route.setRouteId(routeId);
            sectionStr = segmentsMapper.getSectionsStrByRouteId(routeId);
            route.setSectionsIds(sectionStr.split(" "));
        }*/
    }
}
