
package com.mycompany.task.model;

//структура отчета по автобусу

import java.util.List;

public class BusReport {
    private String busId;   
    private List<String> prescribedRoutes; //предписанные маршруты
    
    private String todayResult;     //результат анализа
    
    public BusReport(String _busId, List<String> _prescribedRoutes, String _todayResult){
        this.busId = _busId;
        this.prescribedRoutes = _prescribedRoutes;
        this.todayResult = _todayResult;
    }

    public String getBusId() {
        return busId;
    }

    public List<String> getPrescribedRoutes() {
        return prescribedRoutes;
    }

    public String getTodayResult() {
        return todayResult;
    }
    
}
