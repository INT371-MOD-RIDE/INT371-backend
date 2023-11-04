package sit.int371.modride_service.dtos;

import lombok.*;
import sit.int371.modride_service.entities.EventCategory;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Data
public class EventDTO {
    private Integer id;
    private String bookingName;
    private String bookingEmail;
    private LocalDateTime eventStartTime;
    private Integer eventDuration;
    private String eventNotes;
    private Integer eventCategoryId;
    private String eventCategoryName;
}
