package sit.int371.sixpack_overflow_service.beans;
import org.springframework.web.context.request.RequestContextHolder;

import lombok.Data;

@Data
public class APIResponseBean {
	
	private String response_ref = RequestContextHolder.currentRequestAttributes().getSessionId();
	private String response_datetime;
	private String response_code = "20000";
	private String response_desc = "success";
	private Object data;
	// private Paginate paginate = new Paginate();
	


}