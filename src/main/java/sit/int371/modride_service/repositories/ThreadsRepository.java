package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import sit.int371.modride_service.beans.EventMemberBean;
import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.ThreadsBean;

@Mapper
public interface ThreadsRepository {

        // สำหรับ driver 🚗 -----------------------------------
        // สำหรับ driver เมื่อเข้าดู "รายการกระทู้"
        @Select({
                        " select t.thread_id,u.user_id,u.fullname,uf.download_url ",
                        " ,f.faculty_name,b.branch_name ",
                        " ,count(f2.friend_id) as count_mutual ",
                        " ,case  ",
                        "         when exists( ",
                        "                 select * from friendships ",
                        "                 where user_id = #{my_id} and friend_id = u.user_id ",
                        "                 and friend_status = 'accepted' ",
                        "         ) then 1 ",
                        "         else 0 end as isThisFriend ",
                        " ,t.departure_time ",
                        " ,t.seats,t.cost_type,t.costs,t.status ",
                        " ,case when start_name is null then start_point else start_name end as start_point ",
                        " ,case when dest_name is null then dest_point else dest_name end as dest_point ",
                        " ,start_name,dest_name,distance,create_date from threads t ",
                        " left join users u on t.user_id = u.user_id ",
                        " LEFT JOIN branches b ON u.branch_id = b.branch_id ",
                        " LEFT JOIN faculties f ON b.faculty_id = f.faculty_id ",
                        " left join users_files uf on uf.owner_id = u.user_id ",
                        " left JOIN friendships f1 ON u.user_id = f1.friend_id AND f1.friend_status = 'accepted' ",
                        " LEFT JOIN friendships f2  ON f1.user_id = f2.friend_id AND f2.user_id = #{my_id} and f2.friend_status = 'accepted'  ",
                        " group by t.thread_id, uf.download_url ",
                        " order by create_date desc; ",
        })
        public List<ThreadsBean> getAllThreads(Integer my_id) throws Exception;

        @Select({
                        " SELECT u.user_id,u.fullname,f.faculty_name,b.branch_name,r.role_name as role_check, r.role_name ",
                        " ,uf.profile_img_name,uf.download_url ",
                        " ,count(f2.friend_id) as count_mutual ",
                        " ,case  ",
                        "         when exists( ",
                        "                 select * from friendships ",
                        "                 where user_id = #{my_id} and friend_id = u.user_id ",
                        "                 and friend_status = 'accepted' ",
                        "         ) then 1 ",
                        "         else 0 end as isThisFriend ",
                        " FROM users u  ",
                        " left join threads t on t.user_id = u.user_id ",
                        " LEFT JOIN roles r ON r.role_id = u.role_id ",
                        " LEFT JOIN branches b ON u.branch_id = b.branch_id ",
                        " LEFT JOIN faculties f ON b.faculty_id = f.faculty_id    ",
                        " left join users_files uf on uf.owner_id = u.user_id ",
                        " left JOIN friendships f1 ON u.user_id = f1.friend_id AND f1.friend_status = 'accepted' ",
                        " LEFT JOIN friendships f2  ON f1.user_id = f2.friend_id AND f2.user_id = #{my_id} and f2.friend_status = 'accepted'  ",
                        " WHERE t.thread_id = #{thread_id} ",
                        " group by uf.profile_img_name, uf.download_url; ",
        })
        public List<EventMemberBean> getThreadOwner(HashMap<String, Object> param) throws Exception;

        // สำหรับ passenger (thread) -----------------------------------
        @Select({
                        " select t.thread_id,et.event_id,t.user_id,t.thread_detail,t.departure_time ",
                        " ,t.seats,t.cost_type,t.costs,t.status ",
                        " ,case when t.start_name is null then t.start_point else t.start_name end as start_point ",
                        " ,case when t.dest_name is null then t.dest_point else t.dest_name end as dest_point ",
                        " ,t.start_name,t.dest_name,t.distance,t.create_date from threads t ",
                        " left join events_with_threads et on et.thread_id = t.thread_id ",
                        " where t.user_id = #{user_id} ",
        })
        public List<ThreadsBean> getPassengerEvent(Integer user_id) throws Exception;

        @Select({
                        " select * from threads ",
                        " where user_id = #{user_id} ",
        })
        public List<ThreadsBean> getPassengerEvent2(HashMap<String,Object> param) throws Exception;

        @Select({
                        " select t.thread_id,u.user_id,u.fullname,t.thread_detail,t.departure_time ",
                        " ,t.seats,t.cost_type,t.costs,t.status ",
                        " ,t.start_point ",
                        " ,t.dest_point ",
                        " ,t.start_name,t.dest_name,t.distance,t.create_date from threads t ",
                        " left join users u on u.user_id = t.user_id ",
                        " where t.thread_id = #{thread_id} ",
        })
        public ThreadsBean getPasEventDetail(Integer thread_id) throws Exception;

        // <user_id> is id of vehicle's owner
        // <thread_seat> is number of seats require from thread
        // +1 is number for vehicle's owner
        @Select({
                        " select * from vehicles v ",
                        " inner join licenses l on v.license_id = l.license_id ",
                        " inner join users u on u.user_id = l.user_id ",
                        " where u.user_id = #{user_id} and u.role_id != 1  ",
                        " and (#{thread_seat} + 1 <= v.seats) ",
        })
        public List<ThreadsBean> checkVehicleForThread(HashMap<String, Object> params) throws Exception;

        // status = 0(open status by default)
        @Insert({
                        " insert into threads (user_id,thread_detail,departure_time,seats ",
                        " ,status,costs,cost_type,start_point,start_name,dest_point ",
                        " ,dest_name,distance,create_date) ",
                        " values(#{user_id},#{thread_detail},#{departure_time},#{seats} ",
                        " ,0,#{costs},#{cost_type},#{start_point},#{start_name},#{dest_point} ",
                        " ,#{dest_name},#{distance},sysdate()) ",
        })
        @Options(useGeneratedKeys = true, keyColumn = "thread_id", keyProperty = "thread_id")
        public void createThread(ThreadsBean bean) throws Exception;

        @Delete("delete from threads where thread_id = #{thread_id}")
        public void deleteThread(Integer thread_id) throws Exception;

        @Delete("delete from events_with_threads where thread_id = #{thread_id}")
        public void deleteEventThread(Integer thread_id) throws Exception;
}
