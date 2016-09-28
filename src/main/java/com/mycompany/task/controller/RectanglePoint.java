package com.mycompany.task.controller;

enum PointType {
    OUTER, INNER
}

public class RectanglePoint {
    public double x, y;
    
    public PointType type = PointType.INNER;
    
    public RectanglePoint(double _x, double _y){
        this.x = _x;
        this.y = _y;
    }
}
