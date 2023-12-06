package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import sit.int371.modride_service.beans.ChatBean;
import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.EventMemberBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.beans.VehiclesBean;

@Mapper
public interface EventsRepository {
    @Select({
            // " SELECT event_id,user_id,event_name,event_detail, ",
            // " start_point,dest_point,departure_time,seats,costs,create_date,update_date
            // FROM events ",
            // " where status = 1 ",
            // " order by create_date desc ",
            " SELECT e.event_id,e.user_id,e.event_name,e.event_detail, ",
            " e.start_point,e.dest_point,e.departure_time,e.seats,e.costs,e.create_date,e.update_date ",
            " ,concat(u.firstname, ' ', u.lastname) as fullname,u.profile_img_path,r.rate,r.total,f.faculty_name,b.branch_name ",
            " FROM events e ",
            " left join users u on e.user_id = u.user_id ",
            " left join ratings r on e.user_id = r.user_id ",
            " left join branches b on u.branch_id = b.branch_id ",
            " left join faculties f on b.faculty_id = f.faculty_id ",
            " where status = 1 ",
            " order by create_date desc ; "
    })

    public List<EventsBean> getAllEvents() throws Exception;

    @Select({
            " SELECT e.event_id,e.user_id,e.event_name,e.event_detail, ",
            " e.start_point,e.dest_point,e.departure_time,e.seats,e.costs,e.create_date,e.update_date ",
            " ,concat(u.firstname, ' ', u.lastname) as fullname,u.tel,u.profile_img_path,f.faculty_name,b.branch_name ",
            " ,v.brand,v.model,v.vehicle_type,v.vehicle_color,v.license,v.car_img_path,v.vehicle_id ",
            " FROM events e ",
            " left join users u on e.user_id = u.user_id ",
            " left join branches b on u.branch_id = b.branch_id ",
            " left join faculties f on b.faculty_id = f.faculty_id ",
            " left join vehicles v on e.vehicle_id = v.vehicle_id ",
            " where event_id = #{event_id} ",
    })

    public EventDetailBean getEventsById(HashMap<String, Object> event_id) throws Exception;

    @Select({
        //     " select m.members_id,m.event_id,m.user_id,r.role_name,f.faculty_name,b.branch_name from members m ",
        //     " inner join users u on u.user_id = m.user_id ",
        //     " inner join user_role ur on ur.user_id = u.user_id ",
        //     " inner join roles r on r.role_id = ur.role_id ",
        //     " inner join branches b on u.branch_id = b.branch_id ",
        //     " inner join faculties f on b.faculty_id = f.faculty_id ",
        //     " where event_id = #{event_id} ",
        " SELECT m.members_id,m.event_id,m.user_id,concat(u.firstname, ' ', u.lastname) as fullname,f.faculty_name,b.branch_name,u.profile_img_path, ",
        " CASE WHEN m.user_id = e.user_id THEN ra.total END AS total, ",
        " CASE WHEN m.user_id = e.user_id THEN ra.rate END AS rate, ",
        // " r.role_name",
        " CASE WHEN m.user_id = e.user_id THEN 'driver' ELSE 'passenger' END AS role_name ",
        " FROM members m ",
        " LEFT JOIN users u ON u.user_id = m.user_id ",
        // " LEFT JOIN roles ur ON ur.user_id = u.user_id ",
        " LEFT JOIN roles r ON r.role_id = u.role_id ",
        " LEFT JOIN branches b ON u.branch_id = b.branch_id ",
        " LEFT JOIN faculties f ON b.faculty_id = f.faculty_id ",
        " LEFT JOIN events e ON m.user_id = e.user_id AND m.event_id = e.event_id ",
        " LEFT JOIN ratings ra ON m.user_id = ra.user_id ",
        " WHERE m.event_id = #{event_id} ",
    })
    public List<EventMemberBean> getEventMembers(HashMap<String, Object> event_id) throws Exception;

    @Insert({
            " INSERT INTO events(user_id,vehicle_id,event_name,event_detail,start_point,dest_point,departure_time,seats,costs,status,create_date,update_date) ",
            " VALUES(#{user_id},#{vehicle_id},#{event_name},#{event_detail},#{start_point},#{dest_point},#{departure_time},#{seats},#{costs},1,sysdate(),sysdate())"
    })
    @Options(useGeneratedKeys = true, keyColumn = "event_id", keyProperty = "event_id")
    public void createEvents(EventDetailBean eventsBean) throws Exception;

    @Update({
            " UPDATE events SET ",
            " event_name = #{event_name}, ",
            " vehicle_id = #{vehicle_id}, ",
            " event_detail = #{event_detail}, ",
            " start_point = #{start_point}, ",
            " dest_point = #{dest_point}, ",
            " departure_time = #{departure_time}, ",
            " seats = #{seats}, ",
            " costs = #{costs}, ",
            " update_date = sysdate() ",
            " WHERE event_id = #{event_id} "
    })
    public void editEvents(EventDetailBean bean) throws Exception;

    @Update({
            " UPDATE events SET ",
            " status = #{status}, ",
            " update_date = sysdate() ",
            " WHERE event_id = #{event_id} "
    })
    public void editEventsStatus(HashMap<String, Object> params) throws Exception;

    @Delete("DELETE FROM events WHERE event_id = #{event_id}")
    public void deleteEvents(HashMap<String, Object> params) throws Exception;

    @Insert({
            " INSERT INTO members(event_id,user_id) ",
            " VALUES(#{event_id},#{user_id}) "
    })
    @Options(useGeneratedKeys = true, keyColumn = "members_id", keyProperty = "members_id")
    public void joinEvent(HashMap<String, Object> params) throws Exception;

    @Select({
        "select members_id from members where event_id = #{event_id}"
    })
    public List<Integer> getMembersId(HashMap<String, Object> params) throws Exception;

    @Delete("DELETE FROM members WHERE members_id = #{members_id}")
    public void deleteMembers(HashMap<String, Object> params) throws Exception;

    @Select({
        "select seats from events where event_id = #{event_id}"
    })
    public Integer getSeats(HashMap<String, Object> params) throws Exception;

    @Update({
        " UPDATE events SET ",
        " seats = #{join_seat}, ",
        " update_date = sysdate() ",
        " WHERE event_id = #{event_id} "
    })
    public void editSeats(HashMap<String, Object> params) throws Exception;

    @Select({
        "select count(members_id) from members ",
       " where event_id = #{event_id} ",
       " and user_id = #{user_id} "
    })
    public Integer checkDuplicateMember(HashMap<String, Object> params) throws Exception;

    @Select({
        " select vehicle_id,concat(brand,' ',model) as car_name,license,car_img_path ",
       " from vehicles ",
       " where user_id = #{user_id} "
    })
    public List<VehiclesBean> getVehicles(HashMap<String, Object> params) throws Exception;

    //For chat
    @Select({
        " select e.event_id,e.event_name,m.user_id,concat(u.firstname,' ',u.lastname) as fullname,u.profile_img_path ",
        " from events e ",
        " left join members m on e.event_id=m.event_id ",
        " left join users u on m.user_id=u.user_id ",
        " where u.user_id = #{user_id} ",
        " order by e.create_date desc "
    })
    public List<ChatBean> getChatRoom(HashMap<String, Object> params) throws Exception;
}
