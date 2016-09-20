
package com.mycompany.task.model;


public class Bus {
   private String vechicleCode = "";
   private Integer busId;
   private Integer routeId;
   private String routeName;

    public String getVechicleCode() {
        return vechicleCode;
    }

    public void setVechicleCode(String vechicleCode) {
        this.vechicleCode = vechicleCode;
    }

    public Integer getBusId() {
        return busId;
    }

    public void setBusId(Integer busId) {
        this.busId = busId;
    }

    public Integer getRouteId() {
        return routeId;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
   
   
}
