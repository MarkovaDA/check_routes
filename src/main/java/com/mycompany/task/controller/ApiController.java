package com.mycompany.task.controller;

import com.mycompany.task.mapper.MarshrutMapper;
import com.mycompany.task.model.Section;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private MarshrutMapper segmentsMapper;
    

    @RequestMapping(value = "/get_sections", method = RequestMethod.GET)
    public List<Section> getSections(@RequestParam("route_id") Integer routeId){
        String  segments = segmentsMapper.getSections(routeId);
        List<String> sectionsIdsArray = new ArrayList<String>();
        List<Section> sectionsArray = new ArrayList<Section>();
        if (segments != null){
           sectionsIdsArray = Arrays.asList(segments.split("\\s+"));
           sectionsIdsArray.forEach(item-> {sectionsArray.add(segmentsMapper.getSectionGeoLocation(Integer.parseInt(item)));});
        }
        return sectionsArray;
    }
    
    @RequestMapping(value = "/get_portion_of_section", method = RequestMethod.GET)
    public List<Section> getPortionOfSection(@RequestParam("from") Integer from){
        return segmentsMapper.getPortionOfSections(from);
    }
    
    @RequestMapping(value = "/save_points", method = RequestMethod.POST)
    public void SaveXY(@RequestParam("coordinates")String coordinates, @RequestParam("section_id")Integer sectionId){
        segmentsMapper.insertIntoSectionsRectangle(sectionId, coordinates);
    }
}
