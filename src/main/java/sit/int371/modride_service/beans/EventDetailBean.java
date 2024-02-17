package sit.int371.modride_service.beans;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EventDetailBean {
    private Integer event_id;

    // @NotBlank
    // @NotNull
    private Integer user_id;

    @NotBlank(message = "Event name is required Blank")
    @NotNull(message = "Event name is required NULL")
    @NotEmpty(message = "Event name is required EMPTY")
    private String event_name;

    private String event_detail;

    @NotBlank(message = "Start Point is required")
    @NotNull(message = "Start Point is required")
    @NotEmpty(message = "Start Point is required")
    private String start_point;

    @NotBlank(message = "Dest Point is required")
    @NotNull(message = "Dest Point is required")
    @NotEmpty(message = "Dest Point is required")
    private String dest_point;

    @NotBlank(message = "Start Name is required")
    @NotNull(message = "Start Name is required")
    @NotEmpty(message = "Start Name is required")
    private String start_name;

    @NotBlank(message = "Dest Name is required")
    @NotNull(message = "Dest Name is required")
    @NotEmpty(message = "Dest Name is required")
    private String dest_name;

    @NotBlank(message = "Departure Time is required")
    @NotNull(message = "Departure Time is required")
    @NotEmpty(message = "Departure Time is required")
    private String departure_time;

    // @NotBlank
    // @NotNull
    private Integer seats;

    // @NotBlank
    // @NotNull
    private Integer costs;

    // @NotBlank
    // @NotNull
    private Integer cost_type;
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
    private Integer vehicle_id;
    private String brand;
    private String model;
    private String vehicle_type;
    private String vehicle_color;
    private String license_plate;
    private String vehicle_download;
    private List<EventMemberBean> members;

    // public void setEvent_name(String name) {
    //     this.event_name = name.trim();
    // }

}
