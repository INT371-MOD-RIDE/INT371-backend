package sit.int371.modride_service.beans;


import java.util.List;
import sit.int371.modride_service.beans.FriendsBean;


import lombok.*;

@Data
public class EventsBean {
    private Integer event_id;
    private Integer user_id;
    private String event_name;
    private String event_detail;
    private String start_point;
    private String dest_point;
    private String departure_time;
    private Integer vehicle_id;
    private Integer seats;
    private Integer costs;
    // private Integer status;
    private String create_date;
    private String update_date;
    // check is it friend-together
    private Boolean isThisFriend;
    // list for friendship
    private List<FriendsBean> friendShip;
    private List<MutualFriendBean> mutualFriend;

}