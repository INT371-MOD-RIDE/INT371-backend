package sit.int371.modride_service.provider;

import org.apache.ibatis.jdbc.SQL;

import sit.int371.modride_service.beans.EventDetailBean;

public class EventsSqlProvider {
     public String updateEvents(EventDetailBean bean) {
        SQL sql = new SQL();
        sql.UPDATE("events");

        if (bean.getEvent_name() != null) {
            sql.SET("event_name = #{event_name}");
        }

        if (bean.getVehicle_id() != null) {
            sql.SET("vehicle_id = #{vehicle_id}");
        }

        if (bean.getEvent_detail()!= null) {
            sql.SET("event_detail = #{event_detail}");
        }
        if (bean.getStart_point()!= null) {
            sql.SET("start_point = #{start_point}");
        }
        if (bean.getDest_point()!= null) {
            sql.SET("dest_point = #{dest_point}");
        }
        if (bean.getDeparture_time()!= null) {
            sql.SET("departure_time = #{departure_time}");
        }
        if (bean.getSeats()!= null) {
            sql.SET("seats = #{seats}");
        }
        if (bean.getCosts()!= null) {
            sql.SET("costs = #{costs}");
        }
        
        // Add similar conditional statements for other fields

        sql.SET("update_date = sysdate()");
        sql.WHERE("event_id = #{event_id}");

        return sql.toString();
    }
}
