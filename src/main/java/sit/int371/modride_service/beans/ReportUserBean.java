package sit.int371.modride_service.beans;

import lombok.Data;

@Data
public class ReportUserBean {
    private Integer report_id;
    private Integer report_to;
    private Integer reporter_id;
    private Integer report_type;
    private String description;
}
