package sit.int371.modride_service.beans;

import java.util.List;

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
    private String faculty_name;
    private String branch_name;
    private Integer vehicle_id;
    private String brand;
    private String model;
    private String vehicle_type;
    private String vehicle_color;
    private String license;
    private String car_img_path;
    private List<EventMemberBean> members;
}
