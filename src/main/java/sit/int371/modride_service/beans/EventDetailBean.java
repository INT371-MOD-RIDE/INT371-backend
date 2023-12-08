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
    private String email;
    private String tel;
    private String other_contact;
    private String contact_info;
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

    public void setEvent_name(String event_name) {
        this.event_name = trimIfNotNull(event_name);
    }
    public void setStart_point(String start_point) {
        this.start_point = trimIfNotNull(start_point);
    }
    public void setDest_point(String dest_point) {
        this.dest_point = trimIfNotNull(dest_point);
    }
    public void setDeparture_time(String departure_time) {
        this.departure_time = trimIfNotNull(departure_time);
    }
    public void setBrand(String brand) {
        this.brand = trimIfNotNull(brand);
    }
    public void setModel(String model) {
        this.model = trimIfNotNull(model);
    }
    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = trimIfNotNull(vehicle_type);
    }
    public void setVehicle_color(String vehicle_color) {
        this.vehicle_color = trimIfNotNull(vehicle_color);
    }
    public void setLicense(String license) {
        this.license = trimIfNotNull(license);
    }
    // public void setCar_img_path(String car_img_path) {
    //     this.car_img_path = trimIfNotNull(car_img_path);
    // }



    private String trimIfNotNull(String value) {
        return (value != null) ? value.trim() : null;
    }
}
