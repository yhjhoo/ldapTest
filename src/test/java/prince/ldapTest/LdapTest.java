package prince.ldapTest;


import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;



import static org.junit.Assert.*;

public class LdapTest {
	private final Log log = LogFactory.getLog(this.getClass());
	
	@Test
	public void testLdap(){
		LdapTest test = new LdapTest();
		assertTrue(test._authLdap("local\\yhjhoo", "Password@123"));
	}
	
	private boolean _authLdap(String userName, String password){
		String ldapHost = "ldap://192.168.0.107:389"; // ldap host + port number
		
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");

		props.put(Context.SECURITY_AUTHENTICATION, "simple"); // use simple
																// authentication
																// mechanism
//		props.put(Context.SECURITY_PRINCIPAL, "gen-net1\\"+userName);
		props.put(Context.SECURITY_PRINCIPAL, ""+userName);
		props.put(Context.SECURITY_CREDENTIALS, "" + password);
		props.put(Context.PROVIDER_URL, ldapHost);
		try {
//			DirContext ctx = new InitialDirContext(props);
			new InitialDirContext(props);
			return true;
		} catch (NamingException e) {
			log.error("Failed Login, login userName:" + userName + ".");
		}
		
		return false;
	}
	
}
