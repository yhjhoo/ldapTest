package prince.ldapTest;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
//import org.springframework.security.GrantedAuthority;
//import org.springframework.security.GrantedAuthorityImpl;



public class LdapAuthoritiesPopulator extends DefaultLdapAuthoritiesPopulator{
	
	//private UserDAO userDAO;

	public LdapAuthoritiesPopulator(ContextSource contextSource,
			String groupSearchBase) {
		super(contextSource, groupSearchBase);
	}

	@Override
	protected Set<GrantedAuthority> getAdditionalRoles(
			DirContextOperations user, String username) {
		// TODO Auto-generated method stub
		return super.getAdditionalRoles(user, username);
	}

	/*
	@Override
	protected Set<GrantedAuthority> getAdditionalRoles(DirContextOperations dco, String username) {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		String name = dco.getObjectAttribute("name")==null? "" : dco.getObjectAttribute("name").toString();
		String displayName = dco.getObjectAttribute("displayName")==null? "" : dco.getObjectAttribute("displayName").toString();
		String givenName = dco.getObjectAttribute("givenName")==null? "" : dco.getObjectAttribute("givenName").toString();
		String familyName = dco.getObjectAttribute("sn")==null? "" : dco.getObjectAttribute("sn").toString();
		String email = dco.getObjectAttribute("mail")==null? "" : dco.getObjectAttribute("mail").toString();
		String title = dco.getObjectAttribute("title")==null? "" : dco.getObjectAttribute("title").toString();
		String dn = dco.getObjectAttribute("distinguishedName")==null? "" : dco.getObjectAttribute("distinguishedName").toString();
		String loginId = dco.getObjectAttribute("sAMAccountName").toString();
		User user;
		//user = userDAO.getUserByLdapDN(dn) ;//solve different environment problem
		user = userDAO.getUserByLoginID(loginId) ;
		if(user!=null){
			List<Groups> groups = userDAO.getGroupsByUserID(user.getUserId() );
			user.setCreatedBy(loginId);
			user.setModifiedBy(loginId);
			user.setLdapDn(dn);
			user.setFullName(displayName);
			user.setGivenName(givenName);
			user.setFamilyName(familyName);
			user.setEmail(email);
			user.setDesignation(title);
			user.setLastLoginOn(new Date() );
			userDAO.update(user);
			_addRole(authorities, groups);
		}else {
			user = new User();
			user.setUserLoginId(loginId);
			user.setCreatedBy(loginId);
			user.setModifiedBy(loginId);
			user.setLdapDn(dn);
			user.setFullName(displayName);
			user.setGivenName(givenName);
			user.setFamilyName(familyName);
			user.setEmail(email);
			user.setDesignation(title);
			userDAO.add(user);
		}
		
//		authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
		return authorities;
	}*/

	/*
	private Set<GrantedAuthority> _addRole(Set<GrantedAuthority> authorities, List<Groups> groups) {
		for(Groups g : groups){
			if(g!=null){
//				Module m = g.getModule();
//				if(m!=null && !CommonUtils.isNullOrEmpty(m.getName()) ){
					authorities.add(new GrantedAuthorityImpl("ROLE_" + g.getGroupName().toUpperCase() ) );
//				}
			}
		}
		
		return authorities;
	}*/

	/*
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}*/

}
