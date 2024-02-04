package sit.int371.modride_service.beans;
import java.util.List;
import lombok.Data;

@Data
public class VehiclesBean {
    private Integer vehicle_id;
    // private Integer user_id;
    private Integer license_id;
    private String brand;
    private String model;
    private String vehicle_type;
    private String vehicle_color;
    // private String car_name;
    private Integer seats;
    private String license_plate;
    private String create_date;
    private String vehicle_file_name;
    private String vehicle_download;
}
