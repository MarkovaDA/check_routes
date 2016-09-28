
package com.mycompany.task.mapper;
import com.mycompany.task.model.Route;
import com.mycompany.task.model.Section;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface MarshrutMapper {
    
    //получаем строку сегментов через пробел по route_id
    @Select("select distinct vistar_marshrut.routes.sections as SECTIONS FROM vistar_marshrut.gpsBusesRoutes join" + 
    " vistar_marshrut.routes" + 
    " on vistar_marshrut.gpsBusesRoutes.routeId = vistar_marshrut.routes.routeID" + 
    " where vistar_marshrut.gpsBusesRoutes.routeId = #{routeId}")
    String getSections(Integer routeId);
    
    @Select("select distinct routeID as routeId, routeName FROM vistar_marshrut.gpsBusesRoutes")
    List<Route> getRoutes();
    
    @Select("select sectionID, lat1, lon1, lat2, lon2, lat3, lon3, lat4, lon4 from vistar_marshrut.sections where sectionID = #{sectionId};")
    Section getSectionGeoLocation(Integer sectionId);
    
  
    @Select("select * from vistar_marshrut.sections limit #{from},100")
    List<Section> getPortionOfSections(Integer from);
    
    @Insert("insert into vistar_marshrut.sectionsRectangle (section_id, rect_points_xy, koeff_xy) values (#{sectionId},#{rectangle},'no')")
    void insertIntoSectionsRectangle(@Param("sectionId")Integer sectionId, @Param("rectangle")String rectangle);
    
    @Insert("insert into vistar_marshrut.sectionsPolygons (points,section_id,part) values (#{points},#{section_id},#{part})")          
    void insertIntoSectionsPolygon(@Param("points")String points, @Param("section_id")Integer sectionId, @Param("part")Integer part);
    
    @Select("select rect_points_xy from vistar_marshrut.sectionsRectangle where section_id=#{section_id}")    
    String getPoints(@Param("section_id")Integer sectionId);
    
    @Select("select points from vistar_marshrut.sectionsPolygons where section_id=#{section_id} and part=#{part}")
    String getPolygonPoints(@Param("section_id")Integer sectionId, @Param("part")Integer part);
    
    @Update("update vistar_marshrut.sectionsRectangle set rect_points_xy= #{rectangle} where section_id=#{section_id}")
    void updateIntoSections(@Param("section_id")Integer sectionId, @Param("rectangle")String rectangle); 
    
    //MyBatis + SpringMVC
    
}
