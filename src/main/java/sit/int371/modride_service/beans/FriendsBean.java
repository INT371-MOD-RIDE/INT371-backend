package sit.int371.modride_service.beans;

import org.hibernate.mapping.List;

import lombok.*;

@Data
public class FriendsBean {
    private Integer user_id;
    private Integer friend_id;
    private String friend_status;
    

}
