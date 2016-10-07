package com.mycompany.task.model;

import java.util.Date;


public class History {
    
    private Integer sectionID;   
    private Date    switchTime;

    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }

    public Date getSwitchTime() {
        return switchTime;
    }

    public void setSwitchTime(Date switchTime) {
        this.switchTime = switchTime;
    }   
}
