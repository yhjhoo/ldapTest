package prince.ldapTest;


import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
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
		assertTrue(test._authLdap("yhjhoo@local", "Password@123"));
	}
	
	private boolean _authLdap(String userName, String password){
		String ldapHost = "ldaps://ad.local.prince.me:636"; // ldap host + port number
//		String ldapHost = "ldap://ad.local.prince.me:389";
		
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");

		props.put(Context.SECURITY_AUTHENTICATION, "simple"); // use simple
																// authentication
																// mechanism
		props.put(Context.SECURITY_PRINCIPAL, ""+userName);
		props.put(Context.SECURITY_CREDENTIALS, "" + password);
		props.put(Context.PROVIDER_URL, ldapHost);
		
		props.put(Context.SECURITY_PROTOCOL, "ssl");
		
		System.setProperty("javax.net.ssl.trustStore", "/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre/lib/security/cacerts");
		try {
			DirContext ctx = new InitialDirContext(props);
			NamingEnumeration<NameClassPair> list = ctx.list("CN=Users,DC=local,DC=prince,DC=me");
			while(list.hasMore()){
				NameClassPair ncp=(NameClassPair)list.next();
			    System.out.println(ncp.getName());
			}
			
			return true;
		} catch (NamingException e) {
			log.error("Failed Login, login userName:" + userName + ".", e);
		}
		
		return false;
	}
	
}
