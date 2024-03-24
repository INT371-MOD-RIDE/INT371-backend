package sit.int371.modride_service.controllers;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import sit.int371.modride_service.beans.APIResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController implements Serializable {
	public static final String AES_ALGORITHM = "AES";
	public static final String SECRET_KEY = "MoDRideAdmiN.Un2";

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final String MSG_401 = "Unauthorized.";
	public static final String MSG_404 = "Data not found.";

	public static final String METHOD_GET = "get";
	public static final String METHOD_POST = "post";
	public static final String METHOD_PUT = "put";
	public static final String METHOD_DELETE = "delete";

	// Custom http-status number
	public static final Integer UnprocessableContentStatus = 422;

	protected APIResponseBean checkException(Exception e, APIResponseBean res) {
		if (e.getMessage().equals(MSG_404)) {
			res.setResponse_code(404);
			res.setResponse_desc(e.getMessage());
		} else if (e.getMessage().equals(MSG_401)) {
			res.setResponse_code(401);
			res.setResponse_desc(e.getMessage());
		} else if (e.getMessage().contains("is required.") || e.getMessage().contains("should not be")) {
			res.setResponse_code(400);
			res.setResponse_desc(e.getMessage());
		} else if (e.getMessage().contains("Duplicate")) {
			res.setResponse_code(409);
			if (e.getCause() != null) {
				res.setResponse_desc("Duplicated data, " + e.getCause().getMessage() + ".");
			} else {
				res.setResponse_desc(e.getMessage());
			}
		} else {
			logger.info("Got an exception. {}", e.getMessage());
			res.setResponse_code(50000);
			res.setResponse_desc(e.getMessage());
			// res.setResponse_desc("API error please contact administrator.");
		}
		return res;
	}

}
