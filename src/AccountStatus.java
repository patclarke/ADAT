import java.io.IOException;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;


public class AccountStatus {
	
	
	
	//Check if account is locked
	public void CheckStat(String DC) throws IOException, InterruptedException, NamingException {
		//66048 & 66080 = Good
		//66050 & 66082 = Bad
		DirContext ctx = new InitialDirContext(Ldap_Lib.env);
	    SearchControls ctls = new SearchControls();
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		      
		//search location for samAccount      
		NamingEnumeration answer = ctx.search(ApplicationFunctions.OU_DC,"(&(objectCategory=Person)(objectClass=User)(sAMAccountName=" + "patc" +"))", ctls);
		SearchResult sr = (SearchResult)answer.next();
		Attributes answer2 = ctx.getAttributes(sr.getName() + "," + ApplicationFunctions.OU_DC);
		
		user_attrib.put("UAC",     	answer2.get("userAccountControl").get().toString());
		 if 
		 
	}
	
	
	

}
