package sit.int371.modride_service.beans;

import java.util.List;

import lombok.Data;

@Data
public class EventMemberBean {
    private Integer members_id;
    private Integer event_id;
    private Integer user_id;
    private String encrypt_id;
    private String role_name;
    private String role_check;
    private String faculty_name;
    private String branch_name;
    private String fullname;
    private String download_url;
    private String rate;
    private Integer total;
    private Integer status;
    private Integer seats;

    private Boolean isThisFriend;
    // list for friendship
    private List<FriendsBean> friendShip;
    private List<MutualFriendBean> mutualFriend;
    private Integer count_mutual;
}
