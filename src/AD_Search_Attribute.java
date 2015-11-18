
//import java.text.ParseException;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;












import javax.imageio.ImageIO;
//import javax.imageio.ImageIO;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
//import javax.swing.JFormattedTextField;
//import javax.swing.text.MaskFormatter;
//import javax.swing.ImageIcon;
//import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class AD_Search_Attribute {
	//public static Attributes answer2;
	
	static Core 					c 								= new Core();
	static ApplicationFunctions 	appfun              			= new ApplicationFunctions();
	
	public static Object 			cityVariable;
	public static Object 			stateVariable;
	public static Object 			zipVariable;
	public static Object 			streetVariable;
	public static Object 			titleVariable;
	
	
	//public static Object NewOffice;
	public static String 			ipPhoneVariable;
	public static Object 			faxVariable;
	public static Object 			edVariable;
	

	public static Object			HomePVariable;
	public static Object			pagerVariable;
	public static Object			mobileVariable;
	public static Object			allscriptsVariable;
	public static Object			credentialsVariable;
	public static Object			ipPhoneExtVariable;
	
	public static Object            UAC;
	
	public static Object 			HomeP;
	private static Object 			IpPhoneCheck;
	private static Object 			FaxCheck;
	public static Object 			OfficeCheck;
	
	
	public static BufferedImage 	bImageFromConvert 							= null;
	
	public static String 			POSITION;
	
	public static String 			LookUp;
	
	public static String 			OldDN;
	public static String 			NewDN;
	public static String			OldOffice;
	public static String            OldMGR;
	public static String 			HCPhoneString;
	public static String 			IpPhoneExt;
	public static String 			AllscriptsNumber;
	
	
	public static Object 			distinguishedName;
	
	
	public static Object 			CN;
	
	//public static Attributes  		answer2;
	
	@SuppressWarnings("rawtypes")
	public static Hashtable			user_attrib = new Hashtable();
	@SuppressWarnings("rawtypes")
	public static Hashtable 		user_attributes;
	
	

	public static void searchAtr(String LookUp) throws NamingException{
		//String LookUp = Main.AccountName.getText();
		//LookUp = Main.AccountName.getText();
		Core.echo("" + LookUp);
		Core.echo("" + LookUp + " 1");
	
			
		DirContext ctx = new InitialDirContext(Ldap_Lib.env);
	    SearchControls ctls = new SearchControls();
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		      
		//search location for samAccount      
		NamingEnumeration answer = ctx.search(appfun.OU_DC,"(&(objectCategory=Person)(objectClass=User)(sAMAccountName=" + LookUp +"))", ctls);
		//NamingEnumeration<SearchResult> Test = ctx.search(appfun.OU_DC,"(&(objectClass=computer)", ctls);
		 
		if (!answer.hasMore()) {
			throw new NamingException("SAMAccount does not exsist");
		}else if (answer.hasMore()) {
			SearchResult sr = (SearchResult)answer.next();
		    System.out.println(">>>" + sr.getName());

		    Attributes answer2 = ctx.getAttributes(sr.getName() + "," + ApplicationFunctions.OU_DC);

		    System.out.println("sn: " + answer2.get("sn").get());
		    System.out.println("cn: " + answer2.get("cn").get());
		    System.out.println("given name: " + answer2.get("givenName").get());
		    System.out.println("distinguishedName: " + answer2.get("distinguishedName").get());
		    System.out.println("display name: " + answer2.get("displayName").get());
		    System.out.println("street address: " + answer2.get("streetAddress").get());
		    System.out.println("zip: " + answer2.get("postalCode").get());
		    //System.out.println("mamager: " + answer2.get("manager").get().toString());
		    System.out.println("principal name: " + answer2.get("userPrincipalName").get());
		    System.out.println("sAMAccountName: " + answer2.get("sAMAccountName").get());
		    IpPhoneExt = answer2.get("ipPhone").get().toString();
		    System.out.println("Extension: " + IpPhoneExt.substring(IpPhoneExt.lastIndexOf("x") + 1));
		    System.out.println("distinguishedName: " + answer2.get("distinguishedName").get());
		    distinguishedName = answer2.get("distinguishedName").get();
		    
	         
		    
		    
		   // System.out.println("Lets Try and get the OU's " + Test);
	       
	       //Photo stuff WIP
	       String thumbnail = answer2.get("thumbnailPhoto").get().toString();
	       byte[] photo = (byte[])answer2.get("thumbnailPhoto").get();
	       
	       InputStream in = new ByteArrayInputStream(photo);
			//BufferedImage bImageFromConvert = null;
			try {
				bImageFromConvert = ImageIO.read(in);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			Main.lblthumbnail.setIcon(new ImageIcon(bImageFromConvert));
			
	             
	       
	       //Required Checks 
	       FaxCheck			= (answer2.get("facsimileTelephoneNumber").get());
	       IpPhoneCheck		= (answer2.get("ipPhone").get());
	       OfficeCheck		= (answer2.get("physicalDeliveryOfficeName").get());
	       Core.echo("fax " + FaxCheck);
	       Core.echo("ip phone " + IpPhoneCheck);
	        
	        
	       Core.echo("Global fax" + Core.get_prop("GLOBAL_FAX"));
	       Core.echo("Global ip phone:" + Core.get_prop("GLOBAL_IP_PHONE"));
		}		
	}
			
 	public static void setAtr(String LookUp) throws NamingException{
		DirContext ctx = new InitialDirContext(Ldap_Lib.env);
	    SearchControls ctls = new SearchControls();
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		      
		//search location for samAccount      
		NamingEnumeration answer = ctx.search(appfun.OU_DC,"(&(objectCategory=Person)(objectClass=User)(sAMAccountName=" + LookUp +"))", ctls);
		if (!answer.hasMore()) {
			throw new NamingException("SAMAccount does not exsist");
		}else if (answer.hasMore()) {
			
			SearchResult sr = (SearchResult)answer.next();
		    System.out.println(">>>" + sr.getName());
	
		    Attributes answer2 = ctx.getAttributes(sr.getName() + "," + ApplicationFunctions.OU_DC);
	 		
 
	       //Get Users Position/Organizational Unit
	       Object Orginization  = (answer2.get("distinguishedName").get());
	       Orginization 	= Orginization.toString().substring(Orginization.toString().indexOf("OU=") + 3); 
	       Orginization 	= Orginization.toString().substring(0, Orginization.toString().indexOf(","));
	       System.out.println("here... "+Orginization);
	       Main.position.setSelectedItem(Orginization);
	       POSITION = (String) Orginization;
	       Core.echo("position: " + POSITION );
	       
	       
	    
	       
	       OldDN 	= answer2.get("distinguishedName").get().toString();
	       
	       if (answer2.get("manager") != null){
		       //OldDN 	= answer2.get("distinguishedName").get().toString();
		       OldMGR 	= answer2.get("manager").get().toString();
	       }
	 
	       
	       if (FaxCheck.equals(Core.get_prop(OfficeCheck.toString().toUpperCase() + "_FAX"))) {
	    	   Core.echo("inside fax and office check");
		       Main.homeOffice.setSelectedItem(OfficeCheck.toString());	
		   }
    
	        OldOffice = Main.homeOffice.getSelectedItem().toString();
	        Core.echo("Old Office = " + OldOffice);

	        //Store into hashtable instead
	       user_attrib.put("streetVariable",    	answer2.get("streetAddress").get().toString());
	      
	       user_attrib.put("cityVariable",      	answer2.get("l").get().toString());
	       
	       user_attrib.put("zipVariable",       	answer2.get("postalCode").get().toString());
	      
	       user_attrib.put("stateVariable",     	answer2.get("st").get().toString());
	
	       user_attrib.put("titleVariable",     	answer2.get("title").get().toString());
	      

	        //Check Account Lockout Status
	        user_attrib.put("UAC",     				answer2.get("userAccountControl").get().toString());    															//Get users Status 
	        Core.echo("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + answer2.get("userAccountControl").get().toString());
	        if (answer2.get("userAccountControl").get().toString().equals("66082") || answer2.get("userAccountControl").get().toString().equals("66050")){				//if status = 66082 or 66050 account is locked
	        	Core.echo("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Account is Locked");
	        	Main.lblDisabled.setVisible(true);
	        	Main.StatuscomboBox.setVisible(true);
	        	Main.StatuscomboBox.setSelectedItem("Disabled");
	        	Main.lblAccountStatus.setVisible(true);
	        }else if (answer2.get("userAccountControl").get().toString().equals("66080") || answer2.get("userAccountControl").get().toString().equals("66048")){		//if status = 66080 or 66048 account is locked
	        	Core.echo("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ Account is Enabled");
	        	Main.lblEnabled.setVisible(true);
	        	Main.StatuscomboBox.setVisible(true);
	        	Main.StatuscomboBox.setSelectedItem("Enabled");
	        	Main.lblAccountStatus.setVisible(true);
	        }
	       
	       user_attributes = user_attrib;
	      
	       
	       //Required options Stored into textBox
	       Main.LName.setText("" + answer2.get("sn").get());
	       
	       Main.FName.setText("" + answer2.get("givenName").get());
	       
	       Main.sAddress.setText("" + answer2.get("streetAddress").get());
	       
	       Main.city.setText("" + answer2.get("l").get());
	       
	       Main.zip.setText("" + answer2.get("postalCode").get());
	       
	       Main.state.setText("" + answer2.get("st").get());
	
	       Main.TITLE.setText("" + answer2.get("title").get());

	       
	       //None-Required options Stored into textBox & hashtable
	       if (answer2.get("homePhone") != null){
	    	   Core.echo("inside homePhone search");
	    	   user_attrib.put("HomePVariable", 	answer2.get("homePhone").get().toString());
	    	   String getcell = answer2.get("homePhone").get().toString();
	    	   String number = answer2.get("homePhone").get().toString().replaceAll("\\s","");
	    	   
	    	   if (!answer2.get("homePhone").get().toString().contains("-")){
	    		   Core.echo(getcell.format("%s-%s-%s", number.substring(0, 3), number.substring(3, 6), number.substring(6, 10)));
		   	   	   Main.HPhone.setText(getcell.format("%s-%s-%s", number.substring(0, 3), number.substring(3, 6), number.substring(6, 10)));
	    	   }else if (answer2.get("homePhone").get().toString().contains("-")){
	    		   Core.echo(getcell = answer2.get("homePhone").get().toString());
	    		   Main.HPhone.setText(answer2.get("homePhone").get().toString());
	    	   }
   	    
	       }else if (answer2.get("homePhone") == null){
	    	   Main.HPhone.setText("");
	    	   //user_attrib.put("homePhoneVariable", 	"");	
	       }
	       if (answer2.get("pager") != null){
	    	   Core.echo("inside pager search");
	    	   user_attrib.put("pagerVariable", 	answer2.get("pager").get().toString());
	    	   String getcell = answer2.get("pager").get().toString();
	    	   String number = answer2.get("pager").get().toString().replaceAll("\\s","");
	    	   
	    	   if (!answer2.get("pager").get().toString().contains("-")){
	    		   Core.echo(getcell.format("%s-%s-%s", number.substring(0, 3), number.substring(3, 6), number.substring(6, 10)));
		   	   	   Main.HCPhone.setText(getcell.format("%s-%s-%s", number.substring(0, 3), number.substring(3, 6), number.substring(6, 10)));
	    	   }else if (answer2.get("pager").get().toString().contains("-")){
	    		   Core.echo(getcell = answer2.get("pager").get().toString());
	    		   Main.HCPhone.setText(answer2.get("pager").get().toString());
	    	   }
   	    
	       }else if (answer2.get("pager") == null){
	    	   Main.HCPhone.setText("");
	    	   //user_attrib.put("pagerVariable", 	"");	
	       }
	       
	    
	       
	       
	       
	       if (answer2.get("mobile") != null){
	    	   Core.echo("inside mobile search");
	    	   user_attrib.put("mobileVariable",	answer2.get("mobile").get().toString());
	    	   String getcell = answer2.get("mobile").get().toString();
	    	   String number = answer2.get("mobile").get().toString().replaceAll("\\s","");
	    	   
	    	   if (!answer2.get("mobile").get().toString().contains("-")){
	    		   Core.echo(getcell.format("%s-%s-%s", number.substring(0, 3), number.substring(3, 6), number.substring(6, 10)));
		   	   	   Main.CPhone.setText(getcell.format("%s-%s-%s", number.substring(0, 3), number.substring(3, 6), number.substring(6, 10)));
	    	   }else if (answer2.get("mobile").get().toString().contains("-")){
	    		   Core.echo(getcell = answer2.get("mobile").get().toString());
	    		   Main.CPhone.setText(answer2.get("mobile").get().toString());
	    	   }
   	    
	       }else if (answer2.get("mobile") == null){
	    	   Main.CPhone.setText("");
	    	   //user_attrib.put("mobileVariable", 	"");	
	       }
	       if (answer2.get("description") != null){
		   		Main.credentials.setText("" + answer2.get("description").get());
		   		user_attrib.put("credentialsVariable",	answer2.get("description").get().toString());
	       }else if (answer2.get("description") == null){
	    	   Main.credentials.setText("");
	    	   user_attrib.put("credentialsVariable", 	""); 
	       }
	       if (answer2.get("employeeID") != null){
		   		Main.allscriptsNumber.setText("" + answer2.get("employeeID").get());
		   		user_attrib.put("allscriptsVariable",  answer2.get("employeeID").get().toString());
	       }else if (answer2.get("employeeID") == null){
	    	   	Main.allscriptsNumber.setText("");
	    	   	user_attrib.put("allscriptsVariable", 	"");	
	       } 
	       if (IpPhoneExt.substring(IpPhoneExt.lastIndexOf("x") + 1) != null){
		   		Main.IPext.setText("" + IpPhoneExt.substring(IpPhoneExt.lastIndexOf("x") + 1));
		   		//user_attrib.put("ipPhoneExtVariable", "208-452-2662 x911");
	       }else if (IpPhoneExt.substring(IpPhoneExt.lastIndexOf("x") + 1) == null){
	    	   Main.IPext.setText("");
	    	   user_attrib.put("ipPhoneExtVariable", 	"");	
	       }
	       

	       
Core.echo("!!!!!!!!!!!!!!!!!!!  " + Core.get_prop(Main.homeOffice.getSelectedItem().toString().toUpperCase() + "_IP_PHONE") + " x" + IpPhoneExt.substring(IpPhoneExt.lastIndexOf("x") + 1));
	       
	        //Turn off create button
	        Main.btnCreate.setVisible(false);
	        //Turn on update button
	        Main.btnUpDate.setVisible(true);
	        
	        //Turn off editing FName text box
	        Main.FName.setEditable(false);
	        //Turn off editing LName text box
	        Main.LName.setEditable(false);
	
	        
	        
	        
	        
	        
		   	}
 	}
}	

	


