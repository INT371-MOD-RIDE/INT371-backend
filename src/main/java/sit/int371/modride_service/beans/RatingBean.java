package sit.int371.modride_service.beans;

import lombok.Data;

@Data
public class RatingBean {
    private Integer rating_id;
    private Integer user_id;
    private Integer rating_point;
    private Integer rating_amount;
}
