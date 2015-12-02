import java.util.Enumeration;
import java.util.Hashtable;

//import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
//import javax.naming.directory.Attribute;
//import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
//import javax.naming.directory.SearchControls;
//import javax.naming.directory.SearchResult;
import javax.swing.text.JTextComponent;


public class AD_Replace_Attribute {
	//Connect to Classes
	static Core 					c 								= new Core();
	static ApplicationFunctions 	appfun              			= new ApplicationFunctions();
	
	public static String NewOffice;
	public static String NewMGR;
	public static String NewDEPARTMENT;   	
	public static String NewGROUP; 		 	
	public static String NewLGONBAT;
	
	public static String PositionAttr;
	public static String NewDN;
	
	public static String ipPhoneVariable;
	public static String faxVariable;
	public static String edVariable;
	
	
		
	public static Object variable(String v){
		if(v.equals("cityVariable")){
			return Main.city;
		}else if(v.equals("stateVariable")){
			return Main.state;
		}else if(v.equals("zipVariable")){
			return Main.zip;
		}else if(v.equals("streetVariable")){
			return Main.sAddress;
		}else if(v.equals("titleVariable")){
			return Main.TITLE;
		}else if(v.equals("HomePVariable")){
			return Main.HPhone;	
		}else if(v.equals("mobileVariable")){
			return Main.CPhone;	
		}else if(v.equals("pagerVariable")){
			return Main.HCPhone;
		}else if(v.equals("allscriptsVariable")){
			return Main.allscriptsNumber;		
		}else if(v.equals("credentialsVariable")){
			return Main.credentials;	
		//}else if(v.equals("UAC")){
		//	return "userAccountControl";	
		}
		return null;
	}
	
	public static String convert_attributes_key(String v){
		if(v.equals("cityVariable")){
			return "l";
		}else if(v.equals("stateVariable")){
			return "st";
		}else if(v.equals("zipVariable")){
			return "postalCode";
		}else if(v.equals("streetVariable")){
			return "streetAddress";
		}else if(v.equals("titleVariable")){
			return "title";
		}else if(v.equals("HomePVariable")){
			return "homePhone";	
		}else if(v.equals("mobileVariable")){
			return "mobile";	
		}else if(v.equals("pagerVariable")){
			return "pager";		
		}else if(v.equals("allscriptsVariable")){
			return "employeeID";		
		}else if(v.equals("credentialsVariable")){
			return "description";		
		//}else if(v.equals("UAC")){
		//	return "userAccountControl";		
		}
		return "";
	}

	public static void main(String[] Change_Attr) {

		
	}

	private static void execute_update(String key, String new_val){
		Core.echo("position " + AD_Search_Attribute.POSITION);
		
		ModificationItem[] mods       = new ModificationItem[1];
		
		String fName    = Main.FName.getText();
		String lName    = Main.LName.getText();
		String Position = AD_Search_Attribute.POSITION;
		//String search   = "CN="+fName+" "+lName+",OU="+Position+","+ Ldap_Lib.OU_DC;
		String search 	= AD_Search_Attribute.distinguishedName.toString();
		

    	try {
		DirContext ctx = new InitialDirContext(Ldap_Lib.env);
			mods[0]    = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					     new BasicAttribute(key, new_val));
			
			ctx.modifyAttributes(search, mods);
    	} catch (NamingException e1) {
			e1.printStackTrace();
		}
	}

	
	//checks to see if something has changed. if so it envokes execute_update()
	@SuppressWarnings("rawtypes")
	public static void update_user_info() {
		 Core.echo("ADRA - inside update usr info");
		 ApplicationFunctions.homeOffice();
		 
		 NewOffice = Main.homeOffice.getSelectedItem().toString();
		 Hashtable user_attributes = AD_Search_Attribute.user_attributes;
		 Enumeration e = user_attributes.keys();
		 while (e.hasMoreElements()) {
		      String key = (String) e.nextElement();
		      
		      String old_input_recorded = user_attributes.get(key).toString();
		      String new_input_recorded = ((JTextComponent) variable(key)).getText();
		      String convert_key_to_ads = convert_attributes_key(key);
		      
		     if(new_input_recorded.equals("") || new_input_recorded.equals("   -   -    ")){
		    	 new_input_recorded = null;
		     }

		     execute_update("ipPhone", Core.get_prop(Main.homeOffice.getSelectedItem().toString().toUpperCase() + "_IP_PHONE") + " x" + Main.IPext.getText());
		     Core.echo("updating IP Phone with ext to: " + Core.get_prop(Main.homeOffice.getSelectedItem().toString().toUpperCase() + "_IP_PHONE") + " x" + Main.IPext.getText());
		     Core.echo("new_input_recorded =" + new_input_recorded);
		     
		     
		     
	    	 if (AD_Search_Attribute.OldOffice != NewOffice){
	    		 Core.echo("??????????????????????????? = " + NewOffice);
	    		 execute_update("ipPhone",						Core.get_prop(Main.homeOffice.getSelectedItem().toString().toUpperCase() + "_IP_PHONE") + " x" + Main.IPext.getText());
	    		 execute_update("facsimileTelephoneNumber",		ApplicationFunctions.FAX);
	    		 execute_update("physicalDeliveryOfficeName",	NewOffice);
	    	 }
		     

		     if(!old_input_recorded.equals(new_input_recorded)){
		    	 System.out.println(convert_key_to_ads + " to ->  " + new_input_recorded);
		    	 System.out.println("Hi pat --->" +convert_key_to_ads);
		    	 System.out.println("Hi pat2--->" +new_input_recorded);
		    	 System.out.println("Hi pat3--->" +old_input_recorded);
		    	 execute_update(convert_key_to_ads,new_input_recorded); 
		      } 
	    }	
		//update_user_position();
		System.gc();
    }	
	
	/*
	 public static void update_user_position() {
		PositionAttr  = Main.position.getSelectedItem().toString();
		//Position
		 NewDN = "CN=" + Main.FName.getText() + " " + Main.LName.getText() + ",OU=" + Core.get_prop(PositionAttr.toUpperCase() + "_GROUP").toString() + "," + ApplicationFunctions.OU_DC;
		 
		 
		 NewDEPARTMENT   		= Core.get_prop(PositionAttr.toUpperCase() + "_DEPARTMENT");
		 NewGROUP 		 		= Core.get_prop(PositionAttr.toUpperCase() + "_GROUP");
         NewLGONBAT 	 		= Core.get_prop(PositionAttr.toUpperCase() + "_BAT");

	         if (Core.get_prop(PositionAttr.toUpperCase() + "_MGR") != "ED"){
	        	 NewMGR 		 		= ApplicationFunctions.MGR;

	         }else if (Core.get_prop(PositionAttr.toUpperCase() + "_MGR") == "ED"){
	        	 NewMGR 		 		= "CN=" + Core.get_prop(PositionAttr.toUpperCase() + "_ED") + "," + ApplicationFunctions.OU_DC;
	         }
	 


	      if (AD_Search_Attribute.OldDN != NewDN){
	    	  Core.echo("Old DN= " + AD_Search_Attribute.OldDN);
	    	  Core.echo("New DN= " + NewDN);
	    	  	try{
	    	  		if (NewDEPARTMENT != null){
	    	  			execute_update("department", 		NewDEPARTMENT);
	    	  		}
	    	  		
	    	  		if (NewLGONBAT != null){
	    	  			execute_update("scriptPath", 		NewLGONBAT);
	    	  		}
	    	  		
	    	  		if (AD_Search_Attribute.OldMGR != NewMGR){
		    		   Core.echo("ED " + edVariable);
		    		   Core.echo("Old MGR " + AD_Search_Attribute.OldMGR);
		    		   Core.echo("NewMGR " + NewMGR);
		    		   if (NewMGR != null){
		    			   execute_update("manager", 		NewMGR);
		    		   }   
	    	  		}
	    	  		
	    		   DirContext ctx2 = new InitialDirContext(Ldap_Lib.env);
		    	   ctx2.rename(AD_Search_Attribute.OldDN,NewDN);
		    	   Core.echo("OldDN = " + AD_Search_Attribute.OldDN);
		    	   Core.echo("NewDN = " + NewDN);
		    	   Core.echo("testing " + ApplicationFunctions.GROUP);
		    	   Core.echo("New Department " + NewDEPARTMENT);
		    	   Core.echo("New Script " + NewLGONBAT);
		    	   Core.echo("New Manager " + NewMGR);	   
	    	   } catch (NamingException e1) {
	   			e1.printStackTrace();
		    	   Core.echo("OldDN = " + AD_Search_Attribute.OldDN);
		    	   Core.echo("NewDN = " + NewDN);
		    	   Core.echo("New Department " + NewDEPARTMENT);
		    	   Core.echo("New bat " + NewDEPARTMENT);
		    	   Core.echo("ED " + edVariable);
		    	   Core.echo("New MGR " + NewMGR);
	   			}
	       }
	 }  
	*/
	 public static void AccountStatus() {
		 Core.echo("Inside AccountStatus ");
		 if (Main.StatuscomboBox.getSelectedItem().toString().equals("Enabled")){
			 execute_update("userAccountControl", "66080");
			 Core.echo("Executing update to Disabled");
		 } else if (Main.StatuscomboBox.getSelectedItem().toString().equals("Disabled")){
			 execute_update("userAccountControl", "66082");
			 Core.echo("Executing update to Enabled");
		 }	 
		 
	 }
	    
}
