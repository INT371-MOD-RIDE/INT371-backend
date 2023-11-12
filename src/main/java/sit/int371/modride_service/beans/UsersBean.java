package sit.int371.modride_service.beans;

import lombok.*;

@Data
public class UsersBean {
    private Integer user_id;
    private Integer faculty_id;
    private String email;
    private String firstname;
    private String lastname;
    private String tel;
    private String profile_img_path;
    private String fac_name;
    private String branch;

}
