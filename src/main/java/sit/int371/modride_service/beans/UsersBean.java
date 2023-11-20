package sit.int371.modride_service.beans;

import java.util.List;

import lombok.*;

@Data
public class UsersBean {
    private Integer user_id;
    private Integer branch_id;
    private String email;
    private String firstname;
    private String lastname;
    private String fullname;
    private String tel;
    private String profile_img_path;
    private String faculty_name;
    private String branch_name;
    private Integer my_id;
    private Integer friend_id;
    private String friend_status;
    private List<RolesBean> roles;


}
