
package com.mycompany.task.model;

//структура отчета по автобусу

import java.util.List;

public class BusReport {
    //private String busId;  
    private BusInfo info;
    private List<String> prescribedRoutes; //предписанные маршруты
    
    private String todayResult;     //результат анализа
    
    public BusReport(BusInfo _info, List<String> _prescribedRoutes, String _todayResult){
        this.info = _info;
        this.prescribedRoutes = _prescribedRoutes;
        this.todayResult = _todayResult;
    }

    public BusInfo getInfo() {
        return info;
    }

    public void setInfo(BusInfo info) {
        this.info = info;
    }

    /*public String getBusId() {
        return busId;
    }*/

    public List<String> getPrescribedRoutes() {
        return prescribedRoutes;
    }

    public String getTodayResult() {
        return todayResult;
    }
    
}
