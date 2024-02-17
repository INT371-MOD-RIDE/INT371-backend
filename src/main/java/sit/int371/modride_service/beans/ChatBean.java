package sit.int371.modride_service.beans;

import lombok.Data;

@Data
public class ChatBean {
    private Integer event_id;
    private String event_name;
    private Integer status;
    private Integer reqStatus;
    private Integer member_count;
    private Integer members_id;
    private Integer reqestStatus;
    private Integer user_id;
    private String fullname;
    private String profile_img_name;
    private String download_url;
    private Integer owner;
}
