
package com.mycompany.task.model;

import java.util.List;


public class Route {
    private Integer routeId;
    private String routeName;
    
    private String sections; //строка секций через пробел
    
    private Boolean excluded = false; //исключен ли из списка возможных маршрутов? 

    public Boolean getExcluded() {
        return excluded;
    }

    public void setExcluded(Boolean excluded) {
        this.excluded = excluded;
    }

    public String getSectionsIds() {
        return sections;
    }
    
    public void setSectionsIds(String sectionsIds) {
        this.sections = sectionsIds;
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
