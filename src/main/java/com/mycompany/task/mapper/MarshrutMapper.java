
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
    
    @Select("update vistar_marshrut.sectionsPolygons set points = #{points} where section_id= #{section_id} and part = #{part}")
    void updateIntoSectionsPolygon(@Param("section_id")Integer sectionId, @Param("part")Integer part, @Param("points")String points);
    
    @Select("select rect_points_xy from vistar_marshrut.sectionsRectangle where section_id=#{section_id}")    
    String getPoints(@Param("section_id")Integer sectionId);
    
    @Select("select points from vistar_marshrut.sectionsPolygons where section_id=#{section_id} and part=#{part}")
    String getPolygonPoints(@Param("section_id")Integer sectionId, @Param("part")Integer part);
    
    @Update("update vistar_marshrut.sectionsRectangle set rect_points_xy= #{rectangle} where section_id=#{section_id}")
    void updateIntoSections(@Param("section_id")Integer sectionId, @Param("rectangle")String rectangle); 
    
    @Select("select distinct dayofmonth(switchTime) from vistar_marshrut.history where busID=#{vechicle_id} and year(switchTime) = #{year} and month(switchTime) = #{month}")
    List<Integer> daysOfMonth(@Param("year")String year, @Param("month")String month, @Param("vechicle_id")String vechicleId);

    //года, в течение которых наблюдалась активность
    @Select("select distinct year(switchTime) from vistar_murshrut.history where busID=#{vechicle_id}")
    List<Integer> getYearsOfTrack(@Param("vechicle_id")String vechicleId);
    
    //все записи по устройству за указанный день
    @Select("select *, @row:=@row + 1 _row FROM vistar_marshrut.history,(select @row := 0) _row where  busID=#{bus_id}  and switchTime between #{date_str} and  date_add(#{date_str},INTERVAL 1 DAY) having _row % 3 = 0")
    List<String> getHistoryFields(@Param("date_str")String date_str, @Param("bus_id")String busId); //cоответствующую структуру данных
    
    //все маршруты устройства
    @Select("select routeID from vistar_marshrut.history where busID = #{bus_id}")
    List<String> getRoutesForBus(@Param("bus_id")String busId);
    
    
    //автобусы,у которых несколько маршрутов
    //"select busID, routeID from vistar_marshrut.busRoutes layer1 where 1 < (select count(*)  from vistar_marshrut.busRoutes layer2 where layer2.busID = layer1.busID) order by busID";
    
    //270 272 274 277 294 333 345 351 354 358 359 360 361 364 526 528 530 530 609 612 796 1177
    
}
