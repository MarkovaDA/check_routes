
package com.mycompany.task.mapper;
import com.mycompany.task.model.Route;
import com.mycompany.task.model.Section;
import java.util.List;
import org.apache.ibatis.annotations.Select;


public interface MarshrutMapper {
    
    //получаем строку сегментов через пробел по route_id
    @Select("select distinct vistar_marshrut.routes.sections as SECTIONS FROM vistar_marshrut.gpsBusesRoutes join" + 
    " vistar_marshrut.routes" + 
    " on vistar_marshrut.gpsBusesRoutes.routeId = vistar_marshrut.routes.routeID" + 
    " where vistar_marshrut.gpsBusesRoutes.routeId = #{routeId}")
    String getSections(Integer routeId);
    
    @Select("select distinct routeID as routeId, routeName FROM vistar_marshrut.gpsBusesRoutes")
    List<Route> getRoutes();
    
    @Select("select lat1, lon1, lat2, lon2 from vistar_marshrut.sections where sectionID = #{sectionId};")
    Section getSectionGeoLocation(Integer sectionId);
}
