
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
            var vector;
            $(document).ready(function(){
                $('#all_routes').change(function(){
                    var routeId = parseInt($(this).val());
                    $.ajax({
                        type: "GET",
                        url: 'http://localhost:8080/check_routes/api/get_sections?route_id=' + routeId      
                    }).done(function(data) {
                        //console.log(data[0].lat1);
                        var points = new Array();                       
                        for(var i=0; i < data.length; i++){
                            //точки сегмента
                            var point1 = new Array(); var point2 = new Array(); var point3 = new Array(); var point4 = new Array();
                            point1.push(data[i].lon1);
                            point1.push(data[i].lat1);
                            point2.push(data[i].lon2);
                            point2.push(data[i].lat2);
                            point3.push(data[i].lon3);
                            point3.push(data[i].lat3);
                            point4.push(data[i].lon4);
                            point4.push(data[i].lat4);
                            points.push(point1); points.push(point2); points.push(point3); points.push(point4);
                        }
                        map.removeLayer(vector);                      
                        vector = getVectorLayer(points);
                        map.addLayer(vector);                       
                    });
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
        <div id="map" class="map">         
        </div>
    </body>
</html>
