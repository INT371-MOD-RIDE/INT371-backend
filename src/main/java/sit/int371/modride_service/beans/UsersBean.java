package sit.int371.modride_service.beans;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Data
public class UsersBean {
    private Integer user_id;
    private String encrypt_id;
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
    private String profile_img_name;
    private String download_url;
    // 🧑‍🤝‍🧑exclusive for otherUserProfile
    private Integer check_friend;
    private Integer count_friend;
    private Integer count_travel;
    private Integer count_mutual;
    private List<UsersBean> friendList;
    // for driver
    private Boolean disablePost;
    private Integer check_thread;

}
