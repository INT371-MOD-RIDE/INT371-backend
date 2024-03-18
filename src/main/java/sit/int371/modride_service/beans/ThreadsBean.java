package sit.int371.modride_service.beans;

import lombok.Data;

@Data
public class ThreadsBean {
    private Integer thread_id;
    private Integer user_id;
    private String thread_detail;
    private Integer seats;
    private Integer costs;
    private Integer cost_type;
    private Integer status;
    private String start_point;
    private String start_name;
    private String dest_point;
    private String dest_name;
    private Integer distance;
    private String departure_time;
    private String create_date;
}
