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
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.UsersBean;

@Mapper
public interface EventsRepository {
    @Select({
            " SELECT event_id,user_id,event_name,event_detail, ",
            " start_point,dest_point,departure_time,seats,costs,create_date,update_date FROM events ",
            " where status = 1 ",
            " order by create_date desc ",
    })

    public List<EventsBean> getAllEvents() throws Exception;

    @Select({
            " SELECT e.event_id,e.user_id,e.event_name,e.event_detail, ",
            " e.start_point,e.dest_point,e.departure_time,e.seats,e.costs,e.create_date,e.update_date ",
            " ,concat(u.firstname, ' ', u.lastname) as fullname,u.tel,u.profile_img_path,f.fac_name,f.branch ",
            " ,v.brand,v.model,v.vehicle_type,v.vehicle_color,v.license,v.car_img_path ",
            " FROM events e ",
            " left join users u on e.user_id = u.user_id ",
            " left join faculties f on u.faculty_id = f.faculty_id ",
            " left join vehicles v on e.vehicle_id = v.vehicle_id ",
            " where event_id = #{event_id} "
    })

    public List<EventDetailBean> getEventsById(HashMap<String, Object> event_id) throws Exception;

    @Insert({
        " INSERT INTO events(user_id,event_name,event_detail,start_point,dest_point,departure_time,seats,costs,status,create_date,update_date) ",
        " VALUES(#{user_id},#{event_name},#{event_detail},#{start_point},#{dest_point},#{departure_time},#{seats},#{costs},1,sysdate(),sysdate())"
    })
    @Options(useGeneratedKeys = true, keyColumn = "event_id", keyProperty = "event_id")
    public void createEvents(EventsBean eventsBean) throws Exception;

    @Update({
        " UPDATE events SET ",
        " event_name = #{event_name}, ",
        " event_detail = #{event_detail}, ",
        " start_point = #{start_point}, ",
        " dest_point = #{dest_point}, ",
        " departure_time = #{departure_time}, ",
        " seats = #{seats}, ",
        " costs = #{costs}, ",
        " update_date = sysdate() ",
        " WHERE event_id = #{event_id} "
    })
    public void editEvents(HashMap<String, Object> params) throws Exception;

    @Update({
        " UPDATE events SET ",
        " status = #{status}, ",
        " update_date = sysdate() ",
        " WHERE event_id = #{event_id} "
    })
    public void editEventsStatus(HashMap<String, Object> params) throws Exception;

    @Delete("DELETE FROM events WHERE event_id = #{event_id}")
    public void deleteEvents(HashMap<String, Object> params) throws Exception;
}
