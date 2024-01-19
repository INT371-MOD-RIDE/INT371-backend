package sit.int371.modride_service.beans;

import java.util.List;

import lombok.*;

@Data
public class UsersBean {
    private Integer user_id;
    private Integer role_id;
    private String role_name;
    private String role_name_th;
    private Integer branch_id;
    private String email;
    // private String firstname;
    // private String lastname;
    private String fullname;
    private String tel;
    private String other_contact;
    private String contact_info;
    private String profile_img_path;
    private String faculty_name;
    private String branch_name;
    private Integer my_id;
    private Integer friend_id;
    private String friend_status;
    // private List<RolesBean> roles;
    // check is it friend-together
    // private Boolean isThisFriend;
    // list for friendship
    // private List<FriendsBean> friendShip;
    private List<MutualFriendBean> mutualFriend;

}
