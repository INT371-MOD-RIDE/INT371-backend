package sit.int371.modride_service.beans;

import lombok.Data;

@Data
public class EventDetailBean {
    private Integer event_id;
    private Integer user_id;
    private String event_name;
    private String event_detail;
    private String start_point;
    private String dest_point;
    private String departure_time;
    private Integer seats;
    private Integer costs;
    // private Integer status;
    private String create_date;
    private String update_date;
    private String fullname;
    private String tel;
    private String profile_img_path;
    private String fac_name;
    private String branch;
    private String brand;
    private String model;
    private String vehicle_type;
    private String vehicle_color;
    private String license;
    private String car_img_path;
}
