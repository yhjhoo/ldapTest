package prince.ldapTest;


import java.util.List;

public class LdapUser implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8417790852826346170L;
	private String loginId;//user login name = sAMAccountName
	private String dn;//user distinguishedName
	private String name;//user login name
	private String displayName;
	private String title;
	private String mail;
	private String description;
	private String memberof;
	private String givenName;
	
	//private List<Groups> groupList;
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMemberof() {
		return memberof;
	}
	public void setMemberof(String memberof) {
		this.memberof = memberof;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
//	public List<Groups> getGroupList() {
//		return groupList;
//	}
//	public void setGroupList(List<Groups> groupList) {
//		this.groupList = groupList;
//	}
	@Override
	public String toString() {
		return "LdapUser [userName=" + loginId + ", dn=" + dn + ", name="
				+ name + ", displayName=" + displayName + ", title=" + title
				+ ", mail=" + mail + ", description=" + description
				+ ", memberof=" + memberof + "]";
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	
	
}
