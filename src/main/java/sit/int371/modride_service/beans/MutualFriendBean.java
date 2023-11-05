package sit.int371.modride_service.beans;

import lombok.*;

@Data
public class MutualFriendBean {
    // ถ้าตรวจพบ mutual friend และ ไม่ได้เป็นเพื่อนกันกับ driver
    private Integer user_id;
    private String email;
    private String firstname;
    private String lastname;
}
