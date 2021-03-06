package edu.npu.library.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.npu.library.domain.User;
import edu.npu.library.exception.DomainException;
import edu.npu.library.helper.LibraryConstants;

/**
 * Intercepter used to filter requests sent by user other than Student 
 * @author Neeta Vekairya
 *
 */
public class StudentInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(StudentInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		String context = request.getContextPath();
		
		logger.info("uri:" +request.getRequestURL().toString());
		
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
			if(user == null || !user.getUserRole().equals(LibraryConstants.STUDENT_USERROLE)){
				if(user == null) {
					session.invalidate();
					response.sendRedirect(context+"/");
				}else{
					throw new DomainException("You are not authorized to access this page.");
				}
				return false;
			}
		return true;
	}
	

}
