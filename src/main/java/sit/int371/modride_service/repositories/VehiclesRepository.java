package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.EventMemberBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.beans.VehiclesBean;

@Mapper
public interface VehiclesRepository {
    @Insert({
        " insert into vehicles (user_id,brand,model,vehicle_type,vehicle_color,license,car_img_path) ",
        " values (#{user_id},#{brand},#{model},#{vehicle_type},#{vehicle_color},#{license},#{car_img_path}) "
    })
    @Options(useGeneratedKeys = true, keyColumn = "vehicle_id", keyProperty = "vehicle_id")
    // public void createVehicles(VehiclesBean vehiclesBean) throws Exception;
    public void createVehicles(HashMap<String, Object> vehiclesBean) throws Exception;
    @Update({
        " update vehicles set brand = #{brand},model = #{model},vehicle_type = #{vehicle_type},vehicle_color = #{vehicle_color},license = #{license},car_img_path = #{car_img_path} ",
        " where vehicle_id = #{vehicle_id} "
    })
    public void editVehicles(HashMap<String, Object> vehiclesBean) throws Exception;
}
