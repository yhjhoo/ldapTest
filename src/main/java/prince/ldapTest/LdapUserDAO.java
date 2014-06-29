package prince.ldapTest;


import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ldap.control.SortControlDirContextProcessor;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.AggregateDirContextProcessor;


public class LdapUserDAO {
	private LdapTemplate ldapTemp;
	private final Log log = LogFactory.getLog(this.getClass());
	
	public List<LdapUser> searchUser(String filter){
		String param = filter;
		String samFilter = param;
		
		if(!StringUtils.isNotBlank(samFilter) && !samFilter.equals("*")){
			samFilter = "*" + samFilter + "*";
		}
		
		String ldapQuery = "(&(objectCategory=person)(objectClass=user)" +
								"(|" +
									"(name=*" + param +  "*)" +
									"(displayName=*"+ param + "*)" +
									"(sAMAccountName="+ samFilter + ")" +
									//"(title=*"+ param + "*)" +
								")" +
							")";
		List<LdapUser> list = _wrapUser(ldapQuery);
		return list;
	}
	
	/**
	 * wrap ldap User attribute
	 * @param ldapQuery
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<LdapUser> _wrapUser(String ldapQuery){
		SortControlDirContextProcessor sortControl = new SortControlDirContextProcessor("cn");
		AggregateDirContextProcessor processor = new AggregateDirContextProcessor();	
		processor.addDirContextProcessor(sortControl);

		
		SearchControls searchControls = new SearchControls();
		searchControls.setReturningObjFlag(true);
		searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		List<LdapUser> list = ldapTemp.search("", ldapQuery,searchControls,
				new AttributesMapper() {
					public LdapUser mapFromAttributes(Attributes attrs)
							throws NamingException {
						LdapUser user = new LdapUser();
						user.setLoginId(_getLdapAttr(attrs, "sAMAccountName") );
						user.setDn(_getLdapAttr(attrs, "distinguishedName"));
						user.setName(_getLdapAttr(attrs, "name"));
						user.setDisplayName(_getLdapAttr(attrs, "displayName"));
						user.setMail(_getLdapAttr(attrs, "mail"));
						user.setTitle(_getLdapAttr(attrs, "title"));
						user.setDescription(_getLdapAttr(attrs, "description"));
						user.setMemberof(_getLdapAttr(attrs, "memberof"));
						user.setGivenName(_getLdapAttr(attrs, "givenName") );
						return user;
					}
				}, sortControl);
		return list;
	}
	
	private String _getLdapAttr(Attributes attrs, String attr){
		try {
			if(attrs.get(attr)!=null){
				return attrs.get(attr).get().toString();
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	public LdapUser getUserByLoginId(String userName) {
		String param = userName;
		if(userName.contains("\\")){
			userName = userName.substring(userName.indexOf("\\") +1 );
		}
		String ldapQuery = "(&(objectCategory=person)(objectClass=user)" +
								"(|" +
									"(sAMAccountName="+ param + ")" +
								")" +
							")";
		
		List<LdapUser> list = _wrapUser(ldapQuery);
		if(list!=null){
			if(list.size() == 1){
				return list.get(0);
			}else if(list.size() > 1){
				log.error("More than one user with the same login Id" + userName);
				return list.get(0);
			}
		}
		return null;
	}

	
	public LdapTemplate getLdapTemp() {
		return ldapTemp;
	}
	public void setLdapTemp(LdapTemplate ldapTemp) {
		this.ldapTemp = ldapTemp;
	}
}
