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
                        " SELECT u.fullname,f.faculty_name,b.branch_name,r.role_name as role_check ",
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
                        " select thread_id,user_id,thread_detail,departure_time ",
                        " ,seats,cost_type,costs,status ",
                        " ,case when start_name is null then start_point else start_name end as start_point ",
                        " ,case when dest_name is null then dest_point else dest_name end as dest_point ",
                        " ,start_name,dest_name,distance,create_date from threads ",
                        " where user_id = #{user_id} and status = 0 ",
        })
        public List<ThreadsBean> getPassengerEvent(Integer user_id) throws Exception;

        @Select({
                        " select thread_id,user_id,thread_detail,departure_time ",
                        " ,seats,cost_type,costs,status ",
                        " ,start_point ",
                        " ,dest_point ",
                        " ,start_name,dest_name,distance,create_date from threads ",
                        " where thread_id = #{thread_id} ",
        })
        public ThreadsBean getPasEventDetail(Integer thread_id) throws Exception;

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
}
