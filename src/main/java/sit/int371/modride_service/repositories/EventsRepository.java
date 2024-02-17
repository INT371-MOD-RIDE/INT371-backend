package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.annotations.Delete;

import sit.int371.modride_service.beans.ChatBean;
import sit.int371.modride_service.beans.DeniedRequestBean;
import sit.int371.modride_service.beans.EventDetailBean;
import sit.int371.modride_service.beans.EventMemberBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.RatingBean;
import sit.int371.modride_service.beans.UsersBean;
import sit.int371.modride_service.beans.VehiclesBean;
import sit.int371.modride_service.provider.EventsSqlProvider;

@Mapper
public interface EventsRepository {
      @Select({
                  // " SELECT event_id,user_id,event_name,event_detail, ",
                  // " start_point,dest_point,departure_time,seats,costs,create_date,update_date
                  // FROM events ",
                  // " where status = 1 ",
                  // " order by create_date desc ",
                  " SELECT e.event_id,e.user_id,e.event_name,e.event_detail, ",
                  " e.departure_time,e.seats,e.costs,e.status,e.create_date,e.update_date,u.fullname, ",
                  // " ,u.fullname,r.rating_point as rate,r.rating_amount as total,f.faculty_name,b.branch_name ",
                  " CASE WHEN ROUND(r.rating_point / r.rating_amount, 1) * 10 % 10 = 0 THEN ROUND(r.rating_point / r.rating_amount, 0) ",
                  " WHEN ROUND(r.rating_point / r.rating_amount, 1) * 10 % 10 > 0 THEN FORMAT(ROUND(r.rating_point / r.rating_amount, 1), 1) END as rate ",
                  " ,r.rating_amount as total,f.faculty_name,b.branch_name ",
                  " ,case when el.start_name is null then el.start_point else el.start_name end as start_point  ",
                  " ,case when el.dest_name is null then el.dest_point else el.dest_name end as dest_point ",
                  " ,uf.profile_img_name,uf.download_url ",
                  " FROM events e ",
                  " left join users u on e.user_id = u.user_id ",
                  " left join ratings r on e.user_id = r.user_id ",
                  " left join branches b on u.branch_id = b.branch_id ",
                  " left join faculties f on b.faculty_id = f.faculty_id ",
                  " left join event_location el on el.event_id = e.event_id ",
                  " left join users_files uf on uf.owner_id = u.user_id ",
                  // " where status = 1 ",
                  " order by create_date desc ",
      })

      public List<EventsBean> getAllEvents() throws Exception;

      @Select({
                  " SELECT e.event_id,e.user_id,e.event_name,e.event_detail, ",
                  " e.departure_time,e.seats,e.costs,e.create_date,e.update_date ",
                  " ,u.fullname,u.email,u.tel,u.other_contact,u.contact_info,f.faculty_name,b.branch_name ",
                  " ,v.brand,v.model,v.vehicle_type,v.vehicle_color,v.license_plate,v.vehicle_id,vf.vehicle_download ",
                  " ,el.start_point,el.dest_point,el.start_name,el.dest_name ",
                  " FROM events e ",
                  " left join users u on e.user_id = u.user_id ",
                  " left join branches b on u.branch_id = b.branch_id ",
                  " left join faculties f on b.faculty_id = f.faculty_id ",
                  " left join vehicles v on e.vehicle_id = v.vehicle_id ",
                  " left join vehicle_files vf on vf.vehicle_id = v.vehicle_id ",
                  " left join event_location el on el.event_id = e.event_id  ",
                  " where e.event_id = #{event_id} ",
      })

      public EventDetailBean getEventsById(HashMap<String, Object> event_id) throws Exception;

      @Select({
                  // " select
                  // m.members_id,m.event_id,m.user_id,r.role_name,f.faculty_name,b.branch_name
                  // from members m ",
                  // " inner join users u on u.user_id = m.user_id ",
                  // " inner join user_role ur on ur.user_id = u.user_id ",
                  // " inner join roles r on r.role_id = ur.role_id ",
                  // " inner join branches b on u.branch_id = b.branch_id ",
                  // " inner join faculties f on b.faculty_id = f.faculty_id ",
                  // " where event_id = #{event_id} ",
                  " SELECT m.members_id,m.event_id,m.user_id,u.fullname,f.faculty_name,b.branch_name, ",
                  " CASE WHEN m.user_id = e.user_id THEN ra.rating_amount END AS total, ",
                  // " CASE WHEN m.user_id = e.user_id THEN ra.rating_point END AS rate, ",
                  " CASE WHEN m.user_id = e.user_id THEN ",
                  " CASE WHEN ROUND(ra.rating_point / ra.rating_amount, 1) * 10 % 10 = 0 THEN ROUND(ra.rating_point / ra.rating_amount, 0) ",
                  " WHEN ROUND(ra.rating_point / ra.rating_amount, 1) * 10 % 10 > 0 THEN FORMAT(ROUND(ra.rating_point / ra.rating_amount, 1), 1) ELSE NULL END END as rate, ",
                  // ROUND(ra.rating_point / ra.rating_amount,2)  END AS rate, ",
                  " CASE WHEN m.user_id = e.user_id THEN 'driver' ELSE 'passenger' END AS role_name, ",
                  " r.role_name as role_check ",
                  " ,uf.profile_img_name,uf.download_url ,m.status",
                  " FROM members m ",
                  " LEFT JOIN users u ON u.user_id = m.user_id ",
                  // " LEFT JOIN roles ur ON ur.user_id = u.user_id ",
                  " LEFT JOIN roles r ON r.role_id = u.role_id ",
                  " LEFT JOIN branches b ON u.branch_id = b.branch_id ",
                  " LEFT JOIN faculties f ON b.faculty_id = f.faculty_id ",
                  " LEFT JOIN events e ON m.user_id = e.user_id AND m.event_id = e.event_id ",
                  " LEFT JOIN ratings ra ON m.user_id = ra.user_id ",
                  " left join users_files uf on uf.owner_id = u.user_id ",
                  " WHERE m.event_id = #{event_id} ",
                  " And m.status = 1",
                  " OR m.event_id = #{event_id} ",
                  " And m.status = 4 "
      })
      public List<EventMemberBean> getEventMembers(HashMap<String, Object> event_id) throws Exception;

      @Insert({
                  " INSERT INTO events(user_id,vehicle_id,event_name,event_detail,departure_time,seats,costs,status,create_date,update_date,cost_type) ",
                  " VALUES(#{user_id},#{vehicle_id},#{event_name},#{event_detail},#{departure_time},#{seats},#{costs},1,sysdate(),sysdate(),#{cost_type})"
      })
      @Options(useGeneratedKeys = true, keyColumn = "event_id", keyProperty = "event_id")
      public void createEvents(EventDetailBean eventsBean) throws Exception;

      @Insert({
                  " insert into event_location(event_id,start_point,start_name,dest_point,dest_name) ",
                  " values(#{event_id},#{start_point},#{start_name},#{dest_point},#{dest_name}) ",
      })
      public void createEventLocation(EventDetailBean eventsBean) throws Exception;

      @Update({
                  " UPDATE events e ",
                  " JOIN event_location el on e.event_id = el.event_id ",
                  " set e.event_name = #{event_name}, ",
                  " e.vehicle_id = #{vehicle_id}, ",
                  " e.event_detail = #{event_detail}, ",
                  " e.departure_time = #{departure_time}, ",
                  " e.seats = #{seats}, ",
                  " e.costs = #{costs}, ",
                  " e.update_date = sysdate(), ",
                  " el.start_point = #{start_point} ",
                  " ,el.dest_point = #{dest_point} ",
                  " ,el.start_name = #{start_name} ",
                  " ,el.dest_name = #{dest_name} ",
                  " WHERE e.event_id = #{event_id} "
      })
      // @UpdateProvider(type = EventsSqlProvider.class, method = "updateEvents")
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

      @Delete("DELETE FROM event_location WHERE event_id = #{event_id}")
      public void deleteLocation(HashMap<String, Object> params) throws Exception;

      @Insert({
                  " INSERT INTO members(event_id,user_id,status) ",
                  " VALUES(#{event_id},#{user_id},#{status}) "
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

      @Select({
                  " select * from events where status != 3 and user_id = #{user_id} "
      })
      public List<EventsBean> getEventNotClose(Integer user_id) throws Exception;

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
                  " select v.license_id,v.vehicle_id,v.brand,v.model ",
                  " ,v.vehicle_type,v.vehicle_color,v.seats,v.create_date,v.license_plate ",
                  " ,vf.vehicle_file_name,vf.vehicle_download ",
                  " from vehicles v ",
                  " inner join licenses l on v.license_id = l.license_id ",
                  " inner join vehicle_files vf on v.vehicle_id = vf.vehicle_id ",
                  " where l.user_id = #{user_id} ",
      })
      public List<VehiclesBean> getVehicles(HashMap<String, Object> params) throws Exception;

      // For chat
      @Select({
                  // " select
                  // e.event_id,e.event_name,e.status,e.user_id,u.fullname,u.profile_img_path,m.members_id,m.status
                  // as reqestStatus",
                  " select e.event_id,e.event_name,e.status,e.user_id,u.fullname,m.members_id,m.status as reqestStatus",
                  " from events e ",
                  " left join members m on e.event_id=m.event_id ",
                  " left join users u on m.user_id=u.user_id ",
                  " where u.user_id = #{user_id} ",
                  " and m.status != 3 ",
                  " order by e.create_date desc "
      })
      public List<ChatBean> getChatRoom(HashMap<String, Object> params) throws Exception;

      @Select({
                  // " select
                  // e.event_id,e.event_name,m.user_id,u.fullname,u.profile_img_path,e.user_id as
                  // owner,e.status ",
                  " select e.event_id,e.event_name,e.status,m.user_id,u.fullname,uf.download_url,e.user_id as owner,m.status as reqStatus,m.members_id  ",
                  // " CASE WHEN e.user_id = m.user_id THEN 'owner' END as owner ",
                  " from events e ",
                  " left join members m on e.event_id=m.event_id ",
                  " left join users u on m.user_id=u.user_id ",
                  " left join users_files uf on uf.owner_id = u.user_id ",
                  " where e.event_id = #{event_id} ",
                  " and (m.status = 1 or m.status = 4) ",
                  " order by e.create_date desc "
      })
      public List<ChatBean> getChatRoomMember(HashMap<String, Object> params) throws Exception;

      @Select({
                  " select count(members_id) ",
                  " from members ",
                  " where event_id= #{event_id} ",
                  " and status = 1 ",
      })
      public Integer getMemberCount(HashMap<String, Object> params) throws Exception;

      @Select({
                  " SELECT m.members_id,m.event_id,e.seats,m.user_id,u.fullname,f.faculty_name,b.branch_name, ",
                  " CASE WHEN m.user_id = e.user_id THEN ra.rating_amount END AS total, ",
                  " CASE WHEN m.user_id = e.user_id THEN ",
                  " CASE WHEN ROUND(ra.rating_point / ra.rating_amount, 1) * 10 % 10 = 0 THEN ROUND(ra.rating_point / ra.rating_amount, 0) ",
                  " WHEN ROUND(ra.rating_point / ra.rating_amount, 1) * 10 % 10 > 0 THEN FORMAT(ROUND(ra.rating_point / ra.rating_amount, 1), 1) ELSE NULL END END as rate, ",
                  " CASE WHEN m.user_id = e.user_id THEN 'driver' ELSE 'passenger' END AS role_name, ",
                  " r.role_name as role_check ",
                  " FROM members m ",
                  " LEFT JOIN users u ON u.user_id = m.user_id ",
                  // " LEFT JOIN roles ur ON ur.user_id = u.user_id ",
                  " LEFT JOIN roles r ON r.role_id = u.role_id ",
                  " LEFT JOIN branches b ON u.branch_id = b.branch_id ",
                  " LEFT JOIN faculties f ON b.faculty_id = f.faculty_id ",
                  " LEFT JOIN events e ON m.event_id = e.event_id ",
                  " LEFT JOIN ratings ra ON m.user_id = ra.user_id ",
                  " WHERE m.event_id = #{event_id} ",
                  " And m.status = 0 ",
      })
      public List<EventMemberBean> getRequestMembers(HashMap<String, Object> event_id) throws Exception;

      @Update({
                  " UPDATE members SET ",
                  " status = #{status}, ",
                  " detail = if( status = 2,#{detail},null) ",
                  " WHERE members_id = #{members_id} "
      })
      public void responseRequest(HashMap<String, Object> params) throws Exception;

      @Select({
                  // " SELECT
                  // e.event_id,e.user_id,u.fullname,u.role_id,f.faculty_name,b.branch_name,u.profile_img_path,
                  // ",
                  " SELECT e.event_id,e.user_id,u.fullname,u.role_id,f.faculty_name,b.branch_name, ",
                  // " ra.rating_amount as total,ROUND(ra.rating_point / ra.rating_amount,2) as rate,m.detail ",
                  " ra.rating_amount as total,CASE WHEN ROUND(ra.rating_point / ra.rating_amount, 1) * 10 % 10 = 0 THEN ROUND(ra.rating_point / ra.rating_amount, 0) ",
                  " WHEN ROUND(ra.rating_point / ra.rating_amount, 1) * 10 % 10 > 0 THEN FORMAT(ROUND(ra.rating_point / ra.rating_amount, 1), 1) END as rate,m.detail  ",
                  " FROM members m ",
                  " LEFT JOIN events e ON e.event_id = m.event_id ",
                  " LEFT JOIN users u ON e.user_id = u.user_id ",
                  " LEFT JOIN branches b ON u.branch_id = b.branch_id ",
                  " LEFT JOIN faculties f ON b.faculty_id = f.faculty_id ",
                  " LEFT JOIN ratings ra ON u.user_id = ra.user_id ",
                  " WHERE m.members_id = #{members_id} ",
      })
      public List<DeniedRequestBean> getDeniedDetail(HashMap<String, Object> params) throws Exception;

      @Select({
                  " select e.user_id,u.fullname,u.role_id,e.event_id,e.event_name,e.status,ra.rating_id ",
                  " from events e ",
                  " left join users u on e.user_id = u.user_id ",
                  " left join ratings ra on e.user_id = ra.user_id ",
                  " where e.event_id = #{event_id} ",
      })
      public HashMap<String, Object> getEventDriver(HashMap<String, Object> params) throws Exception;

      @Insert({
                  " INSERT INTO ratings(user_id,rating_point,rating_amount) ",
                  " VALUES(#{user_id},#{rating_point},#{rating_amount}) "
      })
      @Options(useGeneratedKeys = true, keyColumn = "rating_id", keyProperty = "rating_id")
      public void ratingDriver(RatingBean bean) throws Exception;

      @Select({
                  " select count(rating_id) ",
                  " from ratings ",
                  " where user_id = #{user_id} "
      })
      public Integer findRating(RatingBean bean) throws Exception;

      @Select({
                  " select rating_point,rating_amount ",
                  " from ratings ",
                  " where user_id = #{user_id} "
      })
      public HashMap<String, Object> getRating(RatingBean bean) throws Exception;

      @Update({
                  " UPDATE ratings SET ",
                  " rating_point = #{rating_point}, ",
                  " rating_amount = #{rating_amount} ",
                  " WHERE user_id = #{user_id} "
      })
      public void updateRating(RatingBean bean) throws Exception;

      @Select({
            "select members_id from members where event_id = #{event_id} and user_id = #{user_id}"
      })
      public Integer getMemberId(HashMap<String, Object> params) throws Exception;

      @Select({
            "select status from members where event_id = #{event_id} and user_id = #{user_id}"
      })
      public Integer getMemberStatus(HashMap<String, Object> params) throws Exception;
}