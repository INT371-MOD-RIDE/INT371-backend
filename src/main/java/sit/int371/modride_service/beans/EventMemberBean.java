package sit.int371.modride_service.beans;

import lombok.Data;

@Data
public class EventMemberBean {
    private Integer members_id;
    private Integer event_id;
    private Integer user_id;
    private String role_name;
    private String faculty_name;
    private String branch_name;
}
