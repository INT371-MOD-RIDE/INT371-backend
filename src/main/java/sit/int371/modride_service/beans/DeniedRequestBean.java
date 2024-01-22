package sit.int371.modride_service.beans;

import java.util.List;

import lombok.Data;

@Data
public class DeniedRequestBean {
    private Integer event_id;
    private Integer user_id;
    // private String role_name;
    // private String role_check;
    private Integer role_id;
    private String faculty_name;
    private String branch_name;
    private String fullname;
    private String profile_img_path;
    private Integer rate;
    private Integer total;

    private Boolean isThisFriend;
    // list for friendship
    private List<FriendsBean> friendShip;
    private List<MutualFriendBean> mutualFriend;

    private String detail;
}
