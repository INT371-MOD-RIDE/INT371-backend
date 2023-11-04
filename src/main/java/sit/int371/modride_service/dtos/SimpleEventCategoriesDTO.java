package sit.int371.modride_service.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleEventCategoriesDTO {
    private Integer eventCategoryId;
    private String eventCategoryName;
    private String eventCategoryDescription;
    private Integer eventDuration;
    private String catImg;
}
