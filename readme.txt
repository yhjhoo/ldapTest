To be able to use following feature, you have to obtain a SSL from AD server

Online reference to get the SSL cert
https://confluence.atlassian.com/display/CROWD/Configuring+an+SSL+Certificate+for+Microsoft+Active+Directory
http://social.technet.microsoft.com/wiki/contents/articles/2980.ldap-over-ssl-ldaps-certificate.aspx


1. disable user
2. enable user
3. reset password



import the cert to your java system
sudo keytool -import -keystore cacerts -file /Users/yhjhoo/Desktop/ldapOverSSL.cer