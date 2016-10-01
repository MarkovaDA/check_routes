
package com.mycompany.task.model;

import com.mycompany.task.mapper.MarshrutMapper;
import java.util.ArrayList;
import java.util.List;



public class Section {
    private double lon1;
    private double lat1;
    private double lon2;
    private double lat2;
    private double lon3;
    private double lat3;
    private double lon4;
    private double lat4;
    private Integer sectionID;


    private List<Point> rectpoints; //точки прямоугольника   
    private List<Point> rectpoints1; //точки первой подсекции
    private List<Point> rectpoints2; //точки второй подсекции
    private List<Point> rectpoints3; //точки третьей подсекции

    public List<Point> getRectpoints1() {
        return rectpoints1;
    }

    public void setRectpoints1(List<Point> rectpoints1) {
        this.rectpoints1 = rectpoints1;
    }

    public List<Point> getRectpoints2() {
        return rectpoints2;
    }

    public void setRectpoints2(List<Point> rectpoints2) {
        this.rectpoints2 = rectpoints2;
    }

    public List<Point> getRectpoints3() {
        return rectpoints3;
    }

    public void setRectpoints3(List<Point> rectpoints3) {
        this.rectpoints3 = rectpoints3;
    }
        
    public List<Point> getRectpoints() {
        return rectpoints;
    }

    public void setRectpoints(List<Point> rectpoints) {
        this.rectpoints = rectpoints;
    }
    //заполнение списка точек обрамляющего прямоугольника
    public void getWrapperRectangle(MarshrutMapper marshrut){
        rectpoints = new ArrayList<Point>(); 
        rectpoints1 = new ArrayList<Point>();
        rectpoints2 = new ArrayList<Point>();
        rectpoints3 = new ArrayList<Point>();
        
        String rectPoints1 = marshrut.getPolygonPoints(sectionID, 1);
        String rectPoints2 = marshrut.getPolygonPoints(sectionID, 2);
        String rectPoints3 = marshrut.getPolygonPoints(sectionID, 3);
        
        String coordinates = marshrut.getPoints(sectionID);
        rectpoints = addPoints(coordinates);
        rectpoints1 = addPoints(rectPoints1);
        rectpoints2 = addPoints(rectPoints2);
        rectpoints3 = addPoints(rectPoints3);
        /*String[] points = coordinates.split(" ");       
        for(int i = 0; i < points.length; i++){
            
            String[] decarts = points[i].split(","); 
            Point point = new Point();
            point.x = Double.parseDouble(decarts[0]);
            point.y = Double.parseDouble(decarts[1]);
            rectpoints.add(point);
        }*/
    }
    
    //парсим строку координат точек в список
    private List<Point> addPoints(String coordinates){
        String[] points = coordinates.split(" ");
        List<Point> pointsList = new ArrayList<Point>();       
        for(int i = 0; i < points.length; i++){           
            String[] decarts = points[i].split(","); 
            Point point = new Point();
            point.x = Double.parseDouble(decarts[0]);
            point.y = Double.parseDouble(decarts[1]);
            pointsList.add(point);
        }
        return pointsList;
    }
    
    public Integer getSectionID() {
        return sectionID;
    }

    public void setSectionID(Integer sectionID) {
        this.sectionID = sectionID;
    }
    public double getLon4() {
        return lon4;
    }

    public void setLon4(double lon4) {
        this.lon4 = lon4;
    }

    public double getLat4() {
        return lat4;
    }

    public void setLat4(double lat4) {
        this.lat4 = lat4;
    }
    
   

    public double getLon1() {
        return lon1;
    }

    public double getLon3() {
        return lon3;
    }

    public void setLon3(double lon3) {
        this.lon3 = lon3;
    }

    public double getLat3() {
        return lat3;
    }

    public void setLat3(double lat3) {
        this.lat3 = lat3;
    }
    
    public void setLon1(double lon1) {
        this.lon1 = lon1;
    }

    public double getLat1() {
        return lat1;
    }

    public void setLat1(double lat1) {
        this.lat1 = lat1;
    }

    public double getLon2() {
        return lon2;
    }

    public void setLon2(double lon2) {
        this.lon2 = lon2;
    }

    public double getLat2() {
        return lat2;
    }

    public void setLat2(double lat2) {
        this.lat2 = lat2;
    }
   
}
