
package com.mycompany.task.controller;

import com.mycompany.task.mapper.MarshrutMapper;
import com.mycompany.task.model.AllRoutesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {
    @Autowired
    private MarshrutMapper segmentsMapper;
    
    @RequestMapping(value={"/report"}, method = RequestMethod.GET)
    public ModelAndView getReportPage(){
        //сразу получаем все маршруты и храним в памяти
       AllRoutesResource.allRoutes = segmentsMapper.getAllRoutes();
       return new ModelAndView("report");
    }
}
