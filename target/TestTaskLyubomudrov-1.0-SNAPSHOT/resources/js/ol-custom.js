        var map;
        //формирование векторного слоя маршрута
        function getVectorLayer(coordinates){
            var lineString = new ol.geom.LineString(coordinates); //создаём отрезок                
               lineString.transform('EPSG:4326', 'EPSG:3857'); 
            
            var feature = new ol.Feature({
                    geometry: lineString,
                    name: 'Line'
            }); 
            var styles = [];
            
            var lineStyle = new ol.style.Style({
                    stroke: new ol.style.Stroke({
                        color: '#f80000',
                        width: 3
                    })
            });
            styles.push(lineStyle);  
            //создаём в отдельном слое
            var source = new ol.source.Vector({
                features: [feature]
            });
            
            return new ol.layer.Vector({
                    source: source,
                    style: styleFunction
            });
        }
        
        
        var styleFunction = function(feature) {
            var geometry = feature.getGeometry();
            var styles = [
              // linestring
              new ol.style.Style({
                stroke: new ol.style.Stroke({
                  color: 'red',
                  width: 5
                })
              })
            ];
            //для каждого сегмента функция
            geometry.forEachSegment(function(start, end) {
                
            //var dx = (end[0] - start[0]);
            //var dy = (end[1] - start[1]);
            //var rotation = Math.atan2(dy, dx);
            //var middlePoint = new ol.geom.Point([(end[0] + start[0])/2,(end[1] + start[1])/2]);

            /*styles.push(new ol.style.Style({
              geometry: new ol.geom.Point(end),
              image: new ol.style.Icon({
                //src: 'http://openlayers.org/en/v3.18.2/examples/data/arrow.png',
                src: 'resources/image/arrow_right.png',
                anchor: [0.75, 0.5],
                rotateWithView: false,
                rotation: -rotation
              })
            }));*/ 
            styles.push(new ol.style.Style({
                    geometry: new ol.geom.Point(start),
                    image: new ol.style.Circle({
                        radius: 3,
                        stroke: new ol.style.Stroke({
                          width: 1,
                          color: 'blue'
                        }),
                        fill: new ol.style.Fill({
                          color: 'blue'
                        })
                    })
            }));
            });

        return styles;
      };
        
        $(document).ready(function(){
                //прорисовать сегменты все и для них прямоугольники
                /*var lineString = new ol.geom.LineString(coords); //создаём отрезок                
                lineString.transform('EPSG:4326', 'EPSG:3857'); 
                var point1 = [4369571.5633409, 6741118.302003121];
                var point2 = [4369583.6482891, 6741134.23839418];
                var point3 = [4370144.875037538,6740683.546686126];
                var point4 = [4370156.959296326,6740699.482168059];
                console.log(ol.proj.toLonLat(point1));
                console.log(ol.proj.toLonLat(point2));
                console.log(ol.proj.toLonLat(point3));
                console.log(ol.proj.toLonLat(point4));*/
                //прогрузка карты                
                map = new ol.Map({
                    layers: [
                      new ol.layer.Tile({
                        source: new ol.source.OSM()
                      })
                      //,vector
                    ],
                    target: 'map',
                    controls: ol.control.defaults({
                      attributionOptions:  ({
                        collapsible: false
                      })
                    }),
                    view: new ol.View({
                      center: [39.1841, 51.67202],
                      zoom: 15
                    })
                });
                var voronezhLon = 39.1841;
                var voronezhLat = 51.67202;
                map.getView().setCenter(ol.proj.transform([voronezhLon, voronezhLat], 'EPSG:4326', 'EPSG:3857'));
        });



