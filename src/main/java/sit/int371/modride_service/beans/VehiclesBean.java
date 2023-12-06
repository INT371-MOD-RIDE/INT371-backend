package sit.int371.modride_service.beans;
import java.util.List;
import lombok.Data;

@Data
public class VehiclesBean {
    private Integer vehicle_id;
    private Integer user_id;
    private String brand;
    private String model;
    private String vehicle_type;
    private String vehicle_color;
    private String car_name;
    private String car_img_path;
    private String license;
}
