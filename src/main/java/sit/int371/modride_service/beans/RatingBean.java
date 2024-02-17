package sit.int371.modride_service.beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class RatingBean {
    private Integer event_id;
    private Integer member_id;
    private Integer rating_id;
    // @NotBlank
    // @NotNull
    private Integer user_id;
    
    // @NotBlank(message = "rating_point is required")
    // @NotNull(message = "rating_point is required")
    private Integer rating_point;
    private Integer rating_amount;
}
