package sit.int371.modride_service.beans.licenses;
import lombok.Data;

@Data
public class LicenseApprovalStatusBean {
    private Integer license_id;
    private Integer approval_status;
    private String denied_detail;
    private String timestamp;
   
    
}