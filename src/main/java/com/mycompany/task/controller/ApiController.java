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
    
    private  List<Section> sectionsArray;

    @RequestMapping(value = "/get_sections", method = RequestMethod.GET)
    public List<Section> getSections(@RequestParam("route_id") Integer routeId){
        String  segments = segmentsMapper.getSections(routeId);
        List<String> sectionsIdsArray = new ArrayList<String>();
        
        sectionsArray = new ArrayList<Section>();
        
        if (segments != null){
           sectionsIdsArray = Arrays.asList(segments.split("\\s+"));
           sectionsIdsArray.forEach(item-> {sectionsArray.add(segmentsMapper.getSectionGeoLocation(Integer.parseInt(item)));});
        }
        
        sectionsArray.forEach(item -> {item.getWrapperRectangle(segmentsMapper);});   
        return sectionsArray;
    }
    
    @RequestMapping(value = "/get_portion_of_section", method = RequestMethod.GET)
    public List<Section> getPortionOfSection(@RequestParam("from") Integer from){
        return segmentsMapper.getPortionOfSections(from);
    }
    
    @RequestMapping(value = "/save_points", method = RequestMethod.POST)
    public void SaveXY(@RequestParam("coordinates")String coordinates, @RequestParam("section_id")Integer sectionId){

        Segment segment1;
        Segment segment2;
        Segment segment3;
        
        String[] points = coordinates.split(" ");
        List<Double> lists = new ArrayList<Double>();
        
        for(int i = 0; i < points.length; i++){           
           String[] currentPoint = points[i].split(",");  
           lists.add(Double.parseDouble(currentPoint[0]));
           lists.add(Double.parseDouble(currentPoint[1]));           
        }
        segment1 = new Segment(lists.get(0), lists.get(1), lists.get(2), lists.get(3));
        segment2 = new Segment(lists.get(2), lists.get(3), lists.get(4), lists.get(5));
        segment3 = new Segment(lists.get(4), lists.get(5), lists.get(6), lists.get(7));
        
        segment1.buildRectPoint();
        List<RectanglePoint> shaders = segment1.shaders;
        String shadersStr = printShadersToString(shaders);
        segmentsMapper.updateIntoSectionsPolygon(sectionId, 1, shadersStr);
        
        segment2.buildRectPoint();
        shaders = segment2.shaders;
        shadersStr = printShadersToString(shaders);
        segmentsMapper.updateIntoSectionsPolygon(sectionId, 2, shadersStr);
        
        segment3.buildRectPoint();
        shaders = segment3.shaders;
        shadersStr = printShadersToString(shaders);  
        segmentsMapper.updateIntoSectionsPolygon(sectionId, 1, shadersStr);
        
        System.out.println("SECTION_ID" + sectionId);
    }
    
    private String printShadersToString(List<RectanglePoint> shaders){
        String shadersStr = "";         
        for(int i=0; i < shaders.size(); i++){
            shadersStr += Double.toString(shaders.get(i).x) + "," + Double.toString(shaders.get(i).y) + " ";
        }
        shadersStr = shadersStr.trim();
        return shadersStr;
    }
}
