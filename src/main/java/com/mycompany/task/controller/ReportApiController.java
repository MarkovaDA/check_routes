
package com.mycompany.task.controller;

import com.mycompany.task.mapper.MarshrutMapper;
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
    
    @RequestMapping(value = "/get_days_of_months", method = RequestMethod.GET)
    public List<Integer> daysOfMonth(@RequestParam("year")String year, @RequestParam("month")String month, 
            @RequestParam("vechicle_id")String vechicleId){
        return segmentsMapper.daysOfMonth(year, month, vechicleId);              
    }
    //отчет по дням?
}
