package sit.int371.modride_service.beans;

import org.hibernate.mapping.List;

import lombok.*;

@Data
public class BranchesBean {
    private Integer branch_id;
    private Integer faculty_id;
    private String branch_name;
}
