
package com.mycompany.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController {
    
    @RequestMapping(value={"/report"}, method = RequestMethod.GET)
    public ModelAndView getReportPage(){
       return new ModelAndView("report");
    }
}
