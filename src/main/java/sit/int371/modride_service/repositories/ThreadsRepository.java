package sit.int371.modride_service.repositories;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import sit.int371.modride_service.beans.EventsBean;
import sit.int371.modride_service.beans.ThreadsBean;

@Mapper
public interface ThreadsRepository {

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
                        " ,case when start_name is null then start_point else start_name end as start_point ",
                        " ,case when dest_name is null then dest_point else dest_name end as dest_point ",
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
