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

        // Add similar conditional statements for other fields

        sql.SET("update_date = sysdate()");
        sql.WHERE("event_id = #{event_id}");

        return sql.toString();
    }
}
