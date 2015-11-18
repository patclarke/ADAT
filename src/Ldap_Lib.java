import java.util.Hashtable;
//import java.util.Properties;
//import java.util.jar.Attributes;





import javax.naming.Context;
//import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
//import javax.naming.directory.Attribute;
//import javax.naming.directory.BasicAttribute;
//import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

    public class Ldap_Lib {  
    	//Connect to Classes
    	//final Core 							c 							= new Core();
    	ApplicationFunctions 					appfun              		= new ApplicationFunctions();
    	
    	
    	final public static String 				LDAP						= "ldap://"+ LogIn.LDAPServer;                                       //Core.get_prop("LDAP");				//"ldap://dc3.heartnhomehospice.com";						//"ldap://HomeServer.test.heartnhomehospice.com"
    	final public static String 				OU_DC                       = Core.get_prop("OU_DC");				//"OU=Heart n Home,DC=heartnhomehospice,DC=com"; 			//"OU=Heart n Home,DC=test,DC=heartnhomehospice,DC=com"
    	final public static String 				DOMAIN                      = Core.get_prop("DOMAIN");				//"DC=heartnhomehospice,DC=com"; 							//"DC=test,DC=heart
    	
    	

    	public static Hashtable<String, String> env 					= new Hashtable<String, String>();
    	
    		//connect to ldap
    		public static boolean connect_to_ldap(String PASS, String UsrCN){
    			Core.echo("This is the Usr CN "+ UsrCN);
               //Hashtable<String, String> env = new Hashtable<String, String>();
               env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
               env.put(Context.PROVIDER_URL, LDAP);     											//"ldap://HomeServer.test.heartnhomehospice.com"
               env.put(Context.SECURITY_AUTHENTICATION, "simple");
               //env.put(Context.SECURITY_PRINCIPAL,"CN=" + UsrCN + ",OU=Astaro," + DOMAIN ); 		// specify the username
               env.put(Context.SECURITY_PRINCIPAL,UsrCN);
               env.put(Context.SECURITY_CREDENTIALS,PASS);											// specify the password
               
               @SuppressWarnings("unused")
			DirContext ctx = null;  

	            try { 
	                // get a handle to an Initial DirContext  
	                ctx = new InitialDirContext(env);   
	            } catch (NamingException e) {  
	                return false;
	            } 
	            System.err.println("Established");
	            Main.PASS = PASS;
	            return true;
           }
    		
    		
           
           //check if user is already registered
           public static boolean is_username_exists(String username, String password){
        	   
        	   //do ldap query here and if results good then return true.
       		   //Hashtable<String, String> env = new Hashtable<String, String>();
               env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
               env.put(Context.PROVIDER_URL, LDAP);													//"ldap://HomeServer.test.heartnhomehospice.com"
               env.put(Context.SECURITY_AUTHENTICATION, "simple");
               env.put(Context.SECURITY_PRINCIPAL,"CN="+username+ "," + OU_DC); 					// specify the username
               env.put(Context.SECURITY_CREDENTIALS, password);										// specify the password
               
               try {
     		      // create the initial context
     		      @SuppressWarnings("unused")
				DirContext ctx = new InitialDirContext(env);
     		      return true;
     		      
     		    } catch (Exception e) {
     		      //otherwise return false on failure.	
     		      return false;
     		    } 
           }
           

    }  