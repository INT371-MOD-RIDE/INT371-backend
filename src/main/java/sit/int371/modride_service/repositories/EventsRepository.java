package sit.int371.modride_service.repositories;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
