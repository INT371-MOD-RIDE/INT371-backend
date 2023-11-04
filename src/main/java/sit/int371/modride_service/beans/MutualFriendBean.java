package sit.int371.modride_service.beans;

import lombok.*;

@Data
public class MutualFriendBean {
    // ถ้าไม่ได้ตรวจพบ mutual friend หรือ ไม่ได้เป็นเพื่อนกันกับ driver
    private String email;
    private String firstname;
    private String lastname;
}
