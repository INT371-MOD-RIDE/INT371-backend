package sit.int371.modride_service.beans;

import lombok.Data;

@Data
public class ThreadsBean {
    private Integer thread_id;
    private Integer event_id;
    private Integer user_id;
    private String encrypt_id;
    private String fullname;
    private String faculty_name;
    private String branch_name;
    private String download_url;
    private Integer count_mutual;
    private Boolean isThisFriend;
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
