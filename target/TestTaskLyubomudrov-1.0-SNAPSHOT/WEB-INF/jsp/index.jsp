
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
        
        <script>
        $(document).ready(function(){
                //var coords = [[-65.65, 10.10], [13, 18]];//координаты конца отрезка
                //[39.2525834845, 51.6725098659], [39.257733628, 51.6700877978]
                var coords = [
                    [39.25252920403162, 51.6724654754469], 
                    [39.252637764968384,51.67255425630958],
                    [39.25778790537183, 51.670132188050786],
                    [39.25767935062816, 51.670043407505744],
                    [39.25252920403162, 51.6724654754469]
                ]; 
                var lineString = new ol.geom.LineString(coords); //создаём отрезок                
                lineString.transform('EPSG:4326', 'EPSG:3857'); //хз зачем надо
                //либо сделать вручную описанный полигон и занести его точки в базу
                //либо сделать ориентацию по точкам
                var point1 = [4369571.5633409, 6741118.302003121];
                var point2 = [4369583.6482891, 6741134.23839418];
                var point3 = [4370144.875037538,6740683.546686126];
                var point4 = [4370156.959296326,6740699.482168059];
                console.log(ol.proj.toLonLat(point1));
                console.log(ol.proj.toLonLat(point2));
                console.log(ol.proj.toLonLat(point3));
                console.log(ol.proj.toLonLat(point4));
                
                var feature = new ol.Feature({
                    geometry: lineString,
                    name: 'Line'
                });
                //стиль линии
                var lineStyle = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: '#f80000',
                        width: 5
                    })
                });
                //создаём в отдельном слое
                var source = new ol.source.Vector({
                    features: [feature]
                });
                
                var vector = new ol.layer.Vector({
                    source: source,
                    style: [lineStyle]
                });
                
                $('#all_routes').change(function(){
                    var routeId = parseInt($(this).val());
                    console.log(routeId);
                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8080/check_routes/api/get_sections?route_id=' + routeId      
                    }).done(function(data) {
                        console.log(data);
                    });
                });
                //прогрузка карты                
                var map = new ol.Map({
                    layers: [
                      new ol.layer.Tile({
                        source: new ol.source.OSM()
                      }),
                      vector
                    ],
                    target: 'map',
                    controls: ol.control.defaults({
                      attributionOptions:  ({
                        collapsible: false
                      })
                    }),
                    view: new ol.View({
                      center: [39.1841, 51.67202],
                      zoom: 12
                    })
                });
                var voronezhLon = 39.1841;
                var voronezhLat = 51.67202;
                map.getView().setCenter(ol.proj.transform([voronezhLon, voronezhLat], 'EPSG:4326', 'EPSG:3857'));
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
        <div id="map" class="map"></div>
    </body>
</html>
