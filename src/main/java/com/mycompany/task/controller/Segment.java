package com.mycompany.task.controller;

import java.util.ArrayList;
import java.util.List;


public class Segment {
    
   public double x1, y1, x2, y2; //конечные точки сегмента
   public double delta = 30;     //точность расчета - 10 метров
   public Double k, m;           //параметры прямой, на которой лежит сегмент
   public Double _k, _m1, _m2;   //параметры перпендикулярной прямой   

   public List<RectanglePoint> shaders;
   
   public Segment(double x1, double y1, double x2, double y2){
       
       this.x1 = x1;
       this.y1 = y1;
       this.x2 = x2;
       this.y2 = y2;
    
       if (x1 == x2) {
           k = null; 
       } //вертикально-ориентированный сегмент
       else if (y1 == y2) {
           k = (double)0; m = y1;
       } //горизонтально ориентированный сегмент
       else 
       {
            k = (y2 - y1)/(x2 - x1);
            m = y1 - k * x1; 
       }
       //вычисление координат перпендикулярных прямых к концам сегмента
       if (k == null || k == 0) {
           if (k == null) {_k = (double)0; _m1 = y1; _m2 = y2; } //прямая вида y = _m1, y = _m2
           else {_k = null; } //прямая вида x = _m1, x = _m2
       }
       else 
       {
           _k = -1/k;
           _m1 = y1 - _k* x1;
           _m2 = y2 - _k *x2;
       }
   }
   
   public void buildRectPoint(){
       //построение точек обрамляющего прямоугольника
       shaders = new ArrayList<RectanglePoint>();
       if (_k == null || _k == 0){
           if (_k == null){ //вертикально ориентированный перпендикулярный отрезок
               shaders.add(new RectanglePoint(x1, y1 - delta));
               shaders.add(new RectanglePoint(x1, y1 + delta));
               shaders.add(new RectanglePoint(x2, y2 - delta));
               shaders.add(new RectanglePoint(x2, y2 + delta));
           }
           else if (_k == 0){ //горизонтально ориентированный перпендикулярный отрезок
               shaders.add(new RectanglePoint(x1 - delta, y1));
               shaders.add(new RectanglePoint(x1 + delta, y1));
               shaders.add(new RectanglePoint(x2 - delta, y2));
               shaders.add(new RectanglePoint(x2 + delta, y2));
           }
       }
       else 
       {
           //наклонная прямая
           ArrayList<RectanglePoint> oneSidePoints = getEndPoints(x1,y1);
           ArrayList<RectanglePoint> anotherSidePoints = getEndPoints(x2,y2);
           shaders.addAll(oneSidePoints);
           shaders.addAll(anotherSidePoints);
       }
   }
   
   private ArrayList<RectanglePoint> getEndPoints(double x_i, double y_i){
       double _a, _b, _c, _m;
       _m = (x_i == x1) ? _m1: _m2;      
       _a = (1 + _k*_k);
       _b = 2*(_k*(_m - y_i) - x_i);
       _c = x_i*x_i + (_m - y_i)*(_m - y_i) - delta*delta;
       double D = _b*_b - 4 * _a * _c;
       double x_rect1 = (-1*_b + (float)Math.sqrt((double)D)) / (2*_a);
       double y_rect1 = _k * x_rect1 + _m;
       double x_rect2 = (-1*_b - (float)Math.sqrt((double)D)) / (2*_a);
       double y_rect2 = _k * x_rect2 + _m;       
       ArrayList<RectanglePoint> endPoints = new ArrayList();
       endPoints.add(new RectanglePoint(x_rect1, y_rect1));
       endPoints.add(new RectanglePoint(x_rect2, y_rect2));
       return endPoints;        
    }
}