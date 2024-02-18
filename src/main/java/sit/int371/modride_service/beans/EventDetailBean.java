package sit.int371.modride_service.beans;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class EventDetailBean {
    private Integer event_id;

    // @NotBlank
    // @NotNull
    @NotNull(message = "User id cannot be null or blank.")
    private Integer user_id;

    @Size(max = 100, message = "The maximum of event_name length is 100.")
    @NotBlank(message = "Event name cannot be null or blank.")
    @NotNull(message = "Event name cannot be null or blank.")
    @NotEmpty(message = "Event name cannot be null or blank.")
    private String event_name;

    private String event_detail;

    @Size(max = 200, message = "The maximum of start_point length is 200.")
    @NotBlank(message = "Start Point cannot be null or blank.")
    @NotNull(message = "Start Point cannot be null or blank.")
    @NotEmpty(message = "Start Point cannot be null or blank.")
    private String start_point;

    @Size(max = 200, message = "The maximum of dest_point length is 200.")
    @NotBlank(message = "Dest Point cannot be null or blank.")
    @NotNull(message = "Dest Point cannot be null or blank.")
    @NotEmpty(message = "Dest Point cannot be null or blank.")
    private String dest_point;

    // @NotBlank(message = "Start Name cannot be null or blank.")
    // @NotNull(message = "Start Name cannot be null or blank.")
    // @NotEmpty(message = "Start Name cannot be null or blank.")
    private String start_name;

    // @NotBlank(message = "Dest Name cannot be null or blank.")
    // @NotNull(message = "Dest Name cannot be null or blank.")
    // @NotEmpty(message = "Dest Name cannot be null or blank.")
    private String dest_name;

    @NotBlank(message = "Departure time cannot be null or blank.")
    @NotNull(message = "Departure time cannot be null or blank.")
    @NotEmpty(message = "Departure time cannot be null or blank.")
    private String departure_time;

    @NotNull(message = "Seats cannot be null or blank.")
    private Integer seats;

    @NotNull(message = "Costs cannot be null or blank.")
    private Integer costs;

    // @NotBlank
    // @NotNull
    @NotNull(message = "Cost Type cannot be null or blank.")
    private Integer cost_type;

    @NotNull(message = "Vehicle id cannot be null or blank.")
    private Integer vehicle_id;

    // status จะถูก set อยู่แล้ว ไม่ต้องดัก
    private Integer status;
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
    private String brand;
    private String model;
    private String vehicle_type;
    private String vehicle_color;
    private String license_plate;
    private String vehicle_download;
    private List<EventMemberBean> members;

    // public void setEvent_name(String name) {
    // this.event_name = name.trim();
    // }

}
