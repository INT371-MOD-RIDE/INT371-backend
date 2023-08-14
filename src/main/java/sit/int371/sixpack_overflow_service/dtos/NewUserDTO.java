package sit.int371.sixpack_overflow_service.dtos;

import lombok.*;
import sit.int371.sixpack_overflow_service.services.PasswordService;

import java.time.Instant;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Data

public class NewUserDTO {
        private String name;
        private String email;
        private String role;
        private String password;

}
