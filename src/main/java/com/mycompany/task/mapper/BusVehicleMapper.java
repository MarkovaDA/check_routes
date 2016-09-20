
package com.mycompany.task.mapper;

import com.mycompany.task.model.BusVehicle;
import org.apache.ibatis.annotations.Select;

/**
 *
 * @author darya
 */
public interface BusVehicleMapper {
    
   
    
   
    @Select("select * from vistar_marshrut.busVehicle limit 1")
    BusVehicle getEntity();
}
