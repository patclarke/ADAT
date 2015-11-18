import java.awt.Color;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.swing.JOptionPane;


public class ApplicationFunctions {
	//Connect to Classes
	static Core 							c 							= new Core();
	
	public static String 					FAX							= null;
	public static String 					IPPHONE 					= null;
	public static String 					GROUP						= null;
	public static String 					LGONBAT						= null;
	public static String 					MGRvar						= null;
	public static String 					MGR							= null;
	public static String 					DEPARTMENT					= null;
	public static String 					ED							= null;
    public static String 					new_sam_name 				= null; 
    public static String 					new_email 					= null;
	
	final public static String 				OU_DC                       = Core.get_prop("OU_DC");				//"OU=Heart n Home,DC=heartnhomehospice,DC=com"; 			//"OU=Heart n Home,DC=test,DC=heartnhomehospice,DC=com"
	final public static String 				DOMAIN                      = Core.get_prop("DOMAIN");				//"DC=heartnhomehospice,DC=com"; 							//"DC=test,DC=heartnhomehospice,DC=com"
	final public static String 				EMAIL						= Core.get_prop("EMAIL");
	
	private static char 					Last_Initial;
	private static char 					Last_Initial_2;
	
	
	
	public static void homeOffice(){
	 String	Required = Main.homeOffice.getSelectedItem().toString();
	 FAX 				= Core.get_prop(Required.toUpperCase() + "_FAX");
	 IPPHONE 			= Core.get_prop(Required.toUpperCase() + "_IP_PHONE");
	 ED 				= Core.get_prop(Required.toUpperCase() + "_ED");
	 
	Core.echo("FAX is " + FAX);
	Core.echo("IPPHONE is " + IPPHONE);
	Core.echo("ED is " + ED);
	 
	}	
	
	
	public void position() throws NamingException{
		 String	PositionAttr = Main.position.getSelectedItem().toString();
		 Core.echo("Position " + PositionAttr.toString());
		 DEPARTMENT 			= Core.get_prop(PositionAttr.toUpperCase() + "_DEPARTMENT");
		 GROUP  				= Core.get_prop(PositionAttr.toUpperCase() + "_GROUP");
		 LGONBAT				= Core.get_prop(PositionAttr.toUpperCase() + "_BAT");
		 MGRvar					= Core.get_prop(PositionAttr.toUpperCase() + "_MGR");
		 Core.echo("Department " + DEPARTMENT );
		 Core.echo("GROUP " + GROUP );
		 Core.echo("LGONBAT " + LGONBAT );
		 Core.echo("MGR " + MGRvar);
		 
		//Search for MGR distinguishedName
		DirContext ctx = new InitialDirContext(Ldap_Lib.env);
	    SearchControls ctls = new SearchControls();
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		      
	    
		//search samAccount  
	    if (!MGRvar.equals("ED") && !MGRvar.equals(null)){
	    	Core.echo("MGR is "+ Core.get_prop(PositionAttr.toUpperCase() + "_MGR"));
	    	String LookUpMGR = Core.get_prop(PositionAttr.toUpperCase() + "_MGR");
	    	Core.echo("MGR is " + LookUpMGR);
	    	@SuppressWarnings("rawtypes")
			NamingEnumeration answer = ctx.search(OU_DC,"(&(objectCategory=Person)(objectClass=User)(sAMAccountName=" + LookUpMGR +"))", ctls);
	    	
	    	SearchResult sr = (SearchResult)answer.next();
		    System.out.println(">>>" + sr.getName());
		    
		    DirContext ctx2 = new InitialDirContext(Ldap_Lib.env);  
		    Attributes answer2 = ctx.getAttributes(sr.getName() + "," + OU_DC);
		    
		    System.out.println("distinguishedName: " + answer2.get("distinguishedName").get());
		    MGR			= "" + answer2.get("distinguishedName").get();
		    
		    
	    }else if ( MGRvar.equals("ED") && !MGRvar.equals(null)){
	    	Core.echo("this is the answer " + Core.get_prop(PositionAttr.toUpperCase() + "_MGR"));
	    	Core.echo("ED: " + ED);
	    	MGR 		 = "CN=" + ED + ",OU=Management,OU=Heart n Home," + DOMAIN;
	    }		
	}	
	
	
	public void create() throws NamingException{
   		Core.echo("Inside ActionEvent for create button");
		DirContext ctx = new InitialDirContext(Ldap_Lib.env);

   		String First_Name      		= Main.FName.getText();
   		Core.echo(First_Name);
   		String Last_Name       		= Main.LName.getText();
   		Core.echo(Last_Name);
   		String Street 		 		= Main.sAddress.getText();
   		Core.echo(Street);
   		String State 			 	= Main.state.getText();
   		Core.echo(State);
   		String ZIP 			 		= Main.zip.getText();
   		Core.echo(ZIP);
   		String City 			 	= Main.city.getText();
   		Core.echo(City);
   		String Office 		 		= (String) Main.homeOffice.getSelectedItem();
   		Core.echo(Office);
   		String Title 			 	= Main.TITLE.getText();
   		Core.echo(Title);
   			
   		Core.echo("Befor Char LName");
   		
   		if(!Main.LName.getText().equals("")){
            Last_Initial = Main.LName.getText().charAt(0);
   		}
   		if(!Main.LName.getText().equals("")){
   			Last_Initial_2 = Main.LName.getText().charAt(1);
   		} 
   		   
   		Core.echo("Test:" + First_Name.toLowerCase() + Character.toLowerCase(Last_Initial) + Character.toLowerCase(Last_Initial_2) );
   			
   				
   		 try { 
   			Core.echo("Inside create button try");

   			// get text from text box and set as a verbal 
   			if(First_Name.equals("") || Last_Name.equals("") || Street.equals("") || 
   			State.equals("") || ZIP.equals("") || City.equals("") || Title.equals("")){
   				throw new NamingException("Please fill all required fields.");
   			}
   			
   			Core.echo("After throw new NamingException");		   			

   
   			// entry's DN 
   			String entryDN = "CN=" + First_Name + " " + Last_Name + ",OU=" + GROUP + "," + OU_DC;  	
  
			Core.echo("The IP Phone is:"+IPPHONE);
	       
	       // entry's attributes  
	       Attribute givenName = new BasicAttribute("givenName", First_Name) ;
	       Attribute cn = new BasicAttribute("cn", First_Name + " " + Last_Name);  
	       Attribute displayName = new BasicAttribute("displayName", First_Name + " " + Last_Name);
           Attribute sn = new BasicAttribute("sn", Last_Name);    
           Attribute st = new BasicAttribute("st", State.toUpperCase());
           Attribute streetAddress = new BasicAttribute("streetAddress", Street);
           Attribute postalCode = new BasicAttribute("postalCode", ZIP);
           Attribute l = new BasicAttribute("l", City);
           Attribute facsimileTelephoneNumber = new BasicAttribute("facsimileTelephoneNumber", FAX);
           Attribute physicalDeliveryOfficeName = new BasicAttribute("physicalDeliveryOfficeName", Office);
           Attribute ipPhone = new BasicAttribute("ipPhone", IPPHONE + " x" + Main.IPext.getText());
           Attribute scriptPath = new BasicAttribute("scriptPath", LGONBAT);
           Attribute title = new BasicAttribute("title", Title);
           Attribute department = new BasicAttribute("department", DEPARTMENT);
           Attribute company = new BasicAttribute("company", "Heart 'n Home Hospice & Palliative Care, LLC");
           Attribute sAMAccountName = null;
           Attribute mail = null; 
           Attribute manager = new BasicAttribute("manager", MGR);
           Attribute userPrincipalName = null;  
           Attribute userAccountControl = new BasicAttribute("userAccountControl", "66048");
           Attribute oc = new BasicAttribute("objectClass");  
	        		 oc.add("top");  
	        		 oc.add("person");  
	        		 oc.add("organizationalPerson");  
	        		 oc.add("User");

	       
	       Core.echo("checking for SAM account...");
	       
	       
	       //Check to see if SAM account exist
	       String look 		= First_Name.toLowerCase() + Character.toLowerCase(Last_Initial);
	       String look2 	= First_Name.toLowerCase() + Character.toLowerCase(Last_Initial) + Character.toLowerCase(Last_Initial_2);
	    	  if (Core.SamSearch(look) == true){
	    		  Core.echo("SAM found!!!!! So lets try somthing else!"); 
	    		  
	    		  //Check to see if SAM account with last name 2 initials exist
	    		  if (Core.SamSearch(look2) == true){
	    			  Core.echo("SAM found!!!!! This is not going to work!");
	    			  throw new NamingException();
	    		  }else if (Core.SamSearch(look2) == false){
	    			  Core.echo("SAM  NOT found this time. So lets do this!");
		    		  new_sam_name 				= look2;
			 		  new_email    				= new_sam_name + EMAIL;

			 		  int result = JOptionPane.showConfirmDialog(null,
			 				 "SAM Account: " + new_sam_name + " will be created. Would you like to continue?");
			 		  if (result == JOptionPane.NO_OPTION) throw new NamingException();
	    			  
	    		  }
	    		  
	    	  }else if (Core.SamSearch(look) == false){
	    		  Core.echo("SAM  NOT! found... So lets do this!"); 
	    		  new_sam_name 				= look;
		 		  new_email    				= new_sam_name + EMAIL;

		 		  int result = JOptionPane.showConfirmDialog(null,
		 				 "SAM Account: " + new_sam_name + " will be created. Would you like to continue?");
		 		  if (result == JOptionPane.NO_OPTION) throw new NamingException();
	    	  }
	    	  
	    //Final compiled name and email.
	 	sAMAccountName 		= new BasicAttribute("sAMAccountName", new_sam_name);
	 	mail           		= new BasicAttribute("mail", new_email);
	 	userPrincipalName 	= new BasicAttribute("userPrincipalName", new_email);	  
	    	  
		   //Check to see if SAM account exist

		   Core.echo("Outside SAM account check");
		   Core.echo("Manager;" + MGR); 
		   Core.echo("Fax number;" + FAX); 
	       
	       //Build the entry  
	       BasicAttributes entry = new BasicAttributes();    
	       //Mandatory entries 
	       entry.put(cn); 
	       entry.put(givenName); 
	       entry.put(displayName);
	       entry.put(sn);  
	       entry.put(mail);
	       entry.put(st);
	       entry.put(streetAddress);
	       entry.put(postalCode);
	       entry.put(l);										//City
	       entry.put(oc);  
	       entry.put(facsimileTelephoneNumber); 
           entry.put(ipPhone);
           entry.put(physicalDeliveryOfficeName); 
           entry.put(scriptPath); 
           entry.put(title);
           entry.put(department);
           entry.put(company);
           entry.put(sAMAccountName);
           entry.put(manager);
           entry.put(userPrincipalName);
	           
           //Optional Entries
           //Home Phone Check
           if(!Main.HPhone.getText().equals("   -   -    ")){
        	   Attribute homePhone = new BasicAttribute("homePhone", Main.HPhone.getText());  
        	   entry.put(homePhone); 
   		   }

            //Home Cell Check
            if(!Main.HCPhone.getText().equals("   -   -    ")){
   				Attribute pager = new BasicAttribute("pager", Main.HCPhone.getText());  
   				entry.put(pager); 
   		   	}
            
            //Work Cell Check
            if(!Main.CPhone.getText().equals("   -   -    ")){
   				Attribute mobile = new BasicAttribute("mobile", Main.CPhone.getText());  
   				entry.put(mobile); 
   		   	}
            
            //AllScripts Check
            if(!Main.allscriptsNumber.getText().equals("")){
   				Attribute employeeID = new BasicAttribute("employeeID", Main.allscriptsNumber.getText());  
   				entry.put(employeeID); 
   		   	}
            
            //Credentials Check
            if(!Main.credentials.getText().equals("")){
   				Attribute description = new BasicAttribute("description", Main.credentials.getText());  
   				entry.put(description); 
   		   	}	        
            
            // Add the entry  
            ctx.createSubcontext(entryDN, entry); 
            
            
            //Set lblCONSOLE text and color upon success
            Core.echo( "AddUser: added entry " + entryDN + ".");
            Main.lblCONSOLE.setForeground(Color.green);
            Main.lblCONSOLE.setText(" " + sAMAccountName);

   		 	} catch (NamingException e) { 
   		 		String input = e.getMessage();
   		 		System.err.println("Error adding entry." + e.getMessage()); 
   		 		Main.lblCONSOLE.setForeground(Color.red);
   		 		Main.lblCONSOLE.setText(e.getMessage());	
            	            
            if(input.contains("LDAP: error code 68")){
            	Main.lblCONSOLE.setForeground(Color.red);
            	Main.lblCONSOLE.setText("LDAP all ready exists");
            	 
        	}
    		if(input.contains("LDAP: error code 19")){
    	 		Core.echo("LDAP CONSTRAINT VIOLATION"); 
    	 		Main.lblCONSOLE.setForeground(Color.red);
    	 		Main.lblCONSOLE.setText("LDAP constraint violation");
        	}
    		
        }
		
	}
	
	
	

}
