package prince.ldapTest;


import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.ldap.control.SortControlDirContextProcessor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AggregateDirContextProcessor;
//import org.springframework.security.userdetails.ldap.LdapUserDetailsImpl;



import static org.junit.Assert.*;

public class SpringLdapTest {
//	static ApplicationContext ctx = new FileSystemXmlApplicationContext(
//			new String[] { "WebContent/WEB-INF/spring/Spring_secure.xml", 
//					"WebContent/WEB-INF/spring/Spring_database.xml", 
//					"WebContent/WEB-INF/spring/Spring_admin.xml"});
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("Spring_secure.xml");
	private final Log log = LogFactory.getLog(this.getClass());
	
	static LdapTemplate ldapTemp = (LdapTemplate) ctx.getBean("ldapTemplate");
	
	static LdapUserDAO ldapUserDAO = (LdapUserDAO) ctx.getBean("LdapUserDAO");
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void springLdapTest() {
		String param = "Prince";
		String ldapQuery = "(&(objectCategory=person)(objectClass=user)" +
//								"(|" +
//									"(cn=*" + param +  "*)" +
//									//"(title=*"+ param + "*)" +
//									"(name=*"+ param + "*)" +
//									"(displayName=*"+ param + "*)" +
//									"(sAMAccountName="+ param + ")" +
//									"(sAMAccountName="+ param + ")" +
//								")" +
							")";
		List<Object> list = ldapTemp.search("", ldapQuery,
				new AttributesMapper() {
					public Object mapFromAttributes(Attributes attrs)
							throws NamingException {
						return attrs.get("cn").get();
					}
				});
		
		
		for(Object s : list){
			System.out.println(s);
		}
		System.out.println("===============================");
		System.out.println(list.size() );
		System.out.println("===============================");	
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void springLdapSortTest(){
		String param = "Prince";
		String ldapQuery = "(&(objectCategory=person)(objectClass=user)" +
//								"(|" +
//									"(cn=*" + param +  "*)" +
//									//"(title=*"+ param + "*)" +
//									"(name=*"+ param + "*)" +
//									"(displayName=*"+ param + "*)" +
//									"(sAMAccountName="+ param + ")" +
//									"(sAMAccountName="+ param + ")" +
//								")" +
							")";
//		List<Object> list = ldapTemp.search("", ldapQuery,
//				new AttributesMapper() {
//					public Object mapFromAttributes(Attributes attrs)
//							throws NamingException {
//						return attrs.get("cn").get();
//					}
//				});
		SortControlDirContextProcessor sortControl = new SortControlDirContextProcessor("cn");
		AggregateDirContextProcessor processor = new AggregateDirContextProcessor();	
		processor.addDirContextProcessor(sortControl);

		
		SearchControls searchControls = new SearchControls();
		searchControls.setReturningObjFlag(true);
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		//System.out.println("CountLimit" + searchControls.getCountLimit() );
		
		List<Object> list = ldapTemp.search("", ldapQuery, searchControls, 
				new AttributesMapper() {
				public Object mapFromAttributes(Attributes attrs)
						throws NamingException {
					return attrs.get("cn").get();
				}
			}, processor);
		
		
		for(Object s : list){
			System.out.println(s);
		}
		System.out.println("===============================");
		System.out.println(list.size() );
		System.out.println("===============================");	
	}
	
	@Test
	public void testSearchUser() {
		Long start = System.currentTimeMillis();
		List<LdapUser> list = ldapUserDAO.searchUser("administrator");
		assertNotNull(list);

		System.out.println("ldapUserSize: " + list.size());
		Long end = System.currentTimeMillis();
		System.out.println("Time cost: " + (end - start) );
		for (LdapUser user : list) {
			System.out.println(user);
		}
	}
}
