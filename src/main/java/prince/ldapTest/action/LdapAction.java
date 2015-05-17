package prince.ldapTest.action;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.*;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import prince.ldapTest.EmailUtils;
import prince.ldapTest.LdapUser;
import prince.ldapTest.LdapUserDAO;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("json-default")
@Namespace("/ldap")
public class LdapAction extends ActionSupport implements ServletRequestAware {
	private final Log log = LogFactory.getLog(this.getClass());
	
	private static final long serialVersionUID = 2156655858735306357L;
	private List<String> list = new ArrayList<String>();
	
	private String email;
	private String loginId;
	private String token;
	
	private boolean matchesUser = false;
	
	private String errorMsg;
	
	//default 10 minutes
//	private long tokenExpWithin = 10 * 60 * 1000;
	private int tokenRetries;
	private boolean isTokenMatches = false;
	
	private String newPassword;
	
	@Autowired
	private LdapUserDAO ldapUserDAO;
	private HttpServletRequest request;
	
	@Action(value="getUser", results = {
		    @Result(name="success", type="json")
		})
	@JSON(serialize=false)
	public String getUser(){
		if(StringUtils.isBlank(loginId) || StringUtils.isBlank(email)){
			return SUCCESS;
		}
		
		LdapUser user = ldapUserDAO.getUserByLoginId(loginId);
		if( user!=null && email.equalsIgnoreCase(user.getMail()) ){
			matchesUser = true;
		}
		
		if(matchesUser){
			generateToken();
		}
		
		return SUCCESS;
	}
	
	public void generateToken(){
		String token = RandomStringUtils.random(6, true, true);
		getSession().setAttribute("token", token);
		getSession().setAttribute("failedTryCount", 0);
		
		EmailUtils eu = new EmailUtils();
		try {
			eu.sendEmail(email, token);
		} catch (Exception e) {
			log.error(e, e);
			errorMsg = "We are unable to send email to you!";
		}
	}
	
	@Action(value="matchToken", results = {
		    @Result(name="success", type="json")
		})
	@JSON(serialize=false)
	public String matchToken(){
		String savedToken = (String) getSession().getAttribute("token");
		tokenRetries = (int) getSession().getAttribute("failedTryCount");
		if(tokenRetries > 10){
			errorMsg = "Token expired!";
			return SUCCESS;
		}
		
		if(StringUtils.isNotBlank(token)){
			if(token.equalsIgnoreCase(savedToken)){
				isTokenMatches = true;
			}else{
				errorMsg = "You have inputed a invalid token! ";
				tokenRetries++;
			}
		}else{
			errorMsg = "Empty Token!";
		}
		
		return SUCCESS;
	}
	
	
	@Action(value="resetPassword", results = {
		    @Result(name="success", type="json")
		})
	public String resetPassword(){
		getUser();
		if(matchesUser){
			matchToken();
			if(isTokenMatches){
				String userName = ldapUserDAO.getUserByLoginId(loginId).getName();
				ldapUserDAO.enableUser(userName);
				if(ldapUserDAO.changePassword(userName, newPassword) ){
					
				}else{
					errorMsg = "We couldn't change your password for the time being, please retry later or contact admin!";
				}
			}
		}
		
		return SUCCESS;
	}
	
	
	@Action(value="enableUser", results = {
		    @Result(name="success", type="json")
		})
	public String enableUser(){
		getUser();
		if(matchesUser){
			matchToken();
			if(isTokenMatches){
				String userName = ldapUserDAO.getUserByLoginId(loginId).getName();
				ldapUserDAO.enableUser(userName);
			}
		}
		
		return SUCCESS;
	}
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		
	}
	
	@JSON(serialize=false)
	public HttpSession getSession() {
		return request.getSession();
	}

//	@JSON(serialize=false)
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public boolean getMatchesUser() {
		return this.matchesUser;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public int getTokenRetries() {
		return tokenRetries;
	}

	public boolean isTokenMatches() {
		return isTokenMatches;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
