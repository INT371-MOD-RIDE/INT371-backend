package sit.int371.modride_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
//    private String id;
    private Integer bookingId;
    private String fileName;
    private String fileType;
    private byte[] data;
}
