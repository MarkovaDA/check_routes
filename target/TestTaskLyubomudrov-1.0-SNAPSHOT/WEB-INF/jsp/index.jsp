
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js">                 
        </script>
        
        <link rel="stylesheet" href="http://openlayers.org/en/v3.18.2/css/ol.css" type="text/css">
       <!--<script src="http://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,URL"></script>-->
        <script src="http://openlayers.org/en/v3.18.2/build/ol.js"></script>
        <script src="<c:url value="resources/js/ol-custom.js"/>"></script>
        
        <script>
            
            var vector;//слой, на который наносятся маршруты
            var vectors = new Array();//группа слоев
            $(document).ready(function(){
                $('#all_routes').change(function(){
                    var point1, point2, point3, point4;                   
                    var firstPartOfSegments, secondPartsOfSegments,thirdPartsOfSegments;
                    
                    var routeId = parseInt($(this).val());
                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8080/check_routes/api/get_sections?route_id=' + routeId      
                    }).done(function(data) 
                    {   console.log(data);
                        for(var i=0; i < data.length; i++)
                        {
                                point1 = new Array(); 
                                point2 = new Array(); 
                                point3 = new Array(); 
                                point4 = new Array();
                                
                                firstPartOfSegments = new Array();
                                secondPartsOfSegments = new Array();
                                thirdPartsOfSegments = new Array();
                                                              
                                point1.push(data[i].lon1);
                                point1.push(data[i].lat1);
                                point2.push(data[i].lon2);
                                point2.push(data[i].lat2);
                                point3.push(data[i].lon3);
                                point3.push(data[i].lat3);
                                point4.push(data[i].lon4);
                                point4.push(data[i].lat4);   
                                //генерируем отрезки сегментов
                                firstPartOfSegments.push(point1); firstPartOfSegments.push(point2);
                                secondPartsOfSegments.push(point2); secondPartsOfSegments.push(point3);
                                thirdPartsOfSegments.push(point3); thirdPartsOfSegments.push(point4); 
                                
                                addNewFeature(firstPartOfSegments);
                                addNewFeature(secondPartsOfSegments);
                                addNewFeature(thirdPartsOfSegments); 
                                
                                /*for(var j=0; j < data[i].rectpoints.length; j++){
                                   var currentPoint = new Array(); //представление текущей точки
                                   var x = data[i].rectpoints[j].x;
                                   var y = data[i].rectpoints[j].y;
                                   currentPoint.push(x); currentPoint.push(y);
                                   currentPoint = ol.proj.toLonLat(currentPoint);
                                   addNewRectFeature(currentPoint); 
                                }*/
                                var polygon = new Array();
                                for(var j=0; j < data[i].rectpoints1.length; j++){                                   
                                   var currentPoint = new Array(); //представление текущей точки
                                   var x = data[i].rectpoints1[j].x;
                                   var y = data[i].rectpoints1[j].y;
                                   currentPoint.push(x); currentPoint.push(y);
                                   currentPoint = ol.proj.toLonLat(currentPoint);
                                   polygon.push(currentPoint);
                                   addNewRectFeature(currentPoint); 
                                }
                                //addNewRectFeature(polygon);
                                console.log(i);
                                
                                /*for(var i=0; i < data[i].rectpoints2.length; i++){
                                   var currentPoint = new Array(); //представление текущей точки
                                   var x = data[i].rectpoints1[i].x;
                                   var y = data[i].rectpoints1[i].y;
                                   currentPoint.push(x); currentPoint.push(y);
                                   currentPoint = ol.proj.toLonLat(currentPoint);
                                   console.log(currentPoint);
                                   addNewRectFeature(currentPoint); 
                                }
                                
                                for(var i=0; i < data[i].rectpoints3.length; i++){
                                   var currentPoint = new Array(); //представление текущей точки
                                   var x = data[i].rectpoints1[i].x;
                                   var y = data[i].rectpoints1[i].y;
                                   currentPoint.push(x); currentPoint.push(y);
                                   currentPoint = ol.proj.toLonLat(currentPoint);
                                   console.log(currentPoint);
                                   addNewRectFeature(currentPoint); 
                                }*/
                                
                                /*var x1 = data[i].rectpoints[0].x;
                                var y1 = data[i].rectpoints[0].y;
                                var x2 = data[i].rectpoints[1].x;
                                var y2 = data[i].rectpoints[1].y;
                                var x3 = data[i].rectpoints[2].x;
                                var y3 = data[i].rectpoints[2].y;
                                var x4 = data[i].rectpoints[3].x;
                                var y4 = data[i].rectpoints[3].y;                                                                                             
                                var points = new Array();    //набор точек                               
                                points.push(x1); points.push(y1);  
                                points = ol.proj.toLonLat(points); //1-ая точка
                                addNewRectFeature(points);                                                               
                                points = new Array();                                
                                points.push(x2); points.push(y2);
                                points = ol.proj.toLonLat(points); //2-ая точка
                                addNewRectFeature(points);                                                                
                                points = new Array();                               
                                points.push(x3); points.push(y3);
                                points = ol.proj.toLonLat(points); //3-ая точка
                                addNewRectFeature(points);//прорисовываем                                                                
                                points = new Array();
                                points.push(x4); points.push(y4);
                                points = ol.proj.toLonLat(points); //4 точка
                                addNewRectFeature(points);*/
                        }
                        map.removeLayer(layerVector);
                        layerVector.setStyle(styleFunction);
                        rectLayerVector.setStyle(rectStyle);
                        map.addLayer(layerVector); 
                        map.addLayer(rectLayerVector);
                    });
                });
                $('#launch').click(function(){
                    for(var i=0; i< 3093; i+=100) {
                         $.get("http://localhost:8080/check_routes/api/get_portion_of_section?from="+i).
                         done(function(data)
                        {
                             for(var j=0; j < data.length; j++){
                                  var strOfPoints = "";
                                  var point1 = new Array(), point2 = new Array(), point3 = new Array(), point4 = new Array();
                                  //точки секции 
                                  point1.push(data[j].lon1); point1.push(data[j].lat1); //первая точка                                  
                                  point2.push(data[j].lon2); point2.push(data[j].lat2);
                                  point3.push(data[j].lon3); point3.push(data[j].lat3);
                                  point4.push(data[j].lon4); point4.push(data[j].lat4); //крайняя точка
                                  
                                  strOfPoints += ol.proj.fromLonLat(point1).toString();
                                  strOfPoints += " " + ol.proj.fromLonLat(point2).toString();
                                  strOfPoints += " " + ol.proj.fromLonLat(point3).toString();
                                  strOfPoints += " " + ol.proj.fromLonLat(point4).toString();                                 
                                  //console.log(strOfPoints);
                                  $.post("http://localhost:8080/check_routes/api/save_points",{coordinates:strOfPoints, section_id: data[j].sectionID}, function(data) {       
                                  });
                              }
                         });
                    }
                });
            });
        </script>
        <style>
            .map{
                width: 100%;
                height: 100%;
            }
        </style>
    </head>
    <body>                 
        <h1>Hello World!</h1>
        <br>
        <p>Select routes:</p>
        
        <!-- -->
        <select id="all_routes">
            <c:forEach items = "${marshruts}" var="marshrut">
                <option value="${marshrut.routeId}">                
                       ${marshrut.routeName}
                </option>
            </c:forEach>
        </select>
        <br><br>
        <button id="launch">запустить скрипт</button>
        <br>
        <div id="map" class="map">         
        </div>
    </body>
</html>
