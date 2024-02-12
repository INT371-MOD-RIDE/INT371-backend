package sit.int371.modride_service.beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReportUserBean {
    private Integer report_id;
    private Integer report_to;
    private Integer reporter_id;
    private Integer report_type;
    private String description;
}
