
package com.mycompany.task.controller;

import com.mycompany.task.mapper.BusVehicleMapper;
import com.mycompany.task.model.BusVehicle;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.mycompany.task.mapper.MarshrutMapper;
import com.mycompany.task.model.Route;

@Controller
public class MainController {
    
    @Autowired
    private MarshrutMapper sectionMapper;
    
    @RequestMapping(value = "/view_on_map", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map) {
        ModelAndView model = new ModelAndView("view_routes");
        //Section section = sectionMapper.getSectionGeoLocation(1);
        List<Route> marshruts = sectionMapper.getRoutes();
        map.addAttribute("marshruts", marshruts);  
        return model;
    }
}
