package sit.int371.modride_service.dtos;

import lombok.*;
import sit.int371.modride_service.EnumRole;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String role;
//    private OffsetDateTime createdOn;
//    private OffsetDateTime updatedOn;
    private Instant createdOn;
    private Instant updatedOn;
}
