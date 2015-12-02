import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
//import javax.swing.ComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerConfigurationException;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;








import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.xml.sax.SAXException;



@SuppressWarnings("deprecation")
public class Core {
	public static Hashtable			user_Domain = new Hashtable<String, String>();
	public static int 				input;
	
	//static ApplicationFunctions     d                   = new ApplicationFunctions();
	

	//echo
	public static void echo(String s){
		System.out.println(s);
	}//end: echo
	
	
	//ping
	public static boolean ping(String ip) throws Exception{
		 Process p = Runtime.getRuntime().exec("ping -n 2 " + ip);
		 BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		 String line;
		 while ((line = reader.readLine()) != null) {
			  //System.out.println(line);
			  if (line.contains("Lost = 2")) {
				  JOptionPane.showMessageDialog(null,"WARNING! No Internet connection. Please check network settings and try again.");	  
				  return false;
			  } 

		 }
		 return true;
	}//end: ping
	
	
	//cmd 
	public static String cmd(String s) throws IOException, InterruptedException{
		
		String line = "";
		
		
		@SuppressWarnings("unused")
		Process p = Runtime.getRuntime().exec(s); 
		
		
		
		BufferedReader input =new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader error =new BufferedReader(new InputStreamReader(p.getErrorStream()));

		System.out.println("OUTPUT");
		while ((line = input.readLine()) != null){
			  System.out.println("LOOK HERE" + line);
			  //input.close();	
		return line;
		}
		return null;
		
	}//end: cmd
	
	
	
	public static void cmdHash(String s) throws IOException{
		Runtime runtime = Runtime.getRuntime();
		//Process process = runtime.exec("cmd /c nltest /dclist:heartnhome | findstr \"Site:\"");
		Process process = runtime.exec(s);
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		int i = 0;
		
		while ((line = br.readLine()) != null) {
			i++;
			
			
//		    String[] result = line.split("        ", 2);
//		    String first = result[0];
//		    String rest = result[1];
//		    System.out.println("First: " + first);
//		    System.out.println("Rest: " + rest);

			
			
			String[] stringArray = line.split("\\s+");

//			for (String str : stringArray)
//			{
//			    System.out.println(str);
//			}
//			
			
			
		    //System.out.println(line);
			//Store into hashtable instead
		    user_Domain.put("DC"+ i,  stringArray[1]);
//		    System.out.println(user_Domain);
		    System.out.println(stringArray[1]);
		}
	System.out.println("TESTING....." + user_Domain.get("DC1"));
	}
	
	
	
	
	//PopUp window
	public void popUp(String s1, String s2, String s3){
		//s1 = popup window tile 
		//s2 = label 1
		//s2 = label 2
		JFrame frame = new JFrame();
	    JPanel panel = new JPanel(new BorderLayout(5, 5));

	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel(s2, SwingConstants.LEFT));
	    label.add(new JLabel(s3, SwingConstants.LEFT));
	    panel.add(label, BorderLayout.WEST);

	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
	    panel.add(controls, BorderLayout.CENTER);

	    input = JOptionPane.showConfirmDialog(frame, panel, s1, JOptionPane.OK_CANCEL_OPTION);
	}//end:PopUp window
	
	
	
	
	
	
	//Authentication window
	public void authWindow() throws Exception{
		JFrame frame = new JFrame();
	    Hashtable<String, String> logininformation = new Hashtable<String, String>();
	    JPanel panel = new JPanel(new BorderLayout(5, 5));
	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel("User Name:", SwingConstants.RIGHT));
	    label.add(new JLabel("Password:", SwingConstants.RIGHT));
	    panel.add(label, BorderLayout.WEST);

	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
	    JTextField username = new JTextField();
	    controls.add(username);
	    JPasswordField password = new JPasswordField();
	    controls.add(password);
	    panel.add(controls, BorderLayout.CENTER);

	    input = JOptionPane.showConfirmDialog(frame, panel, "login", JOptionPane.OK_CANCEL_OPTION);

	    logininformation.put("user", username.getText());
	    logininformation.put("pass", new String(password.getPassword()));
	    
	    //Write to login.conf 
	    if (input == JOptionPane.OK_OPTION) {
		    PrintWriter writer = null;
			try {
				writer = new PrintWriter("C:/Profile setup/login.conf"); //"UTF-8"
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		    writer.println(username.getText());
		    writer.println(password.getPassword());
		    writer.close();
		
		    //d.doer1(); //start VPN2.bat
		    echo("\"C:\\Profile setup\\Support Scripts\\VPN2.bat\"");
	    }
	}//end:Authentication window
	
	
	//Search text for string and replace all instances of that string with another
	public void searchReaplace(String s1, String s2) throws IOException{
		//s1 is the current text in the file that will be replaced with the "textbox" string
		//s2 is the name of the label that you are searching for
	    JFrame frame = new JFrame();
	    JPanel panel = new JPanel(new BorderLayout(5, 5));

	    JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
	    label.add(new JLabel(s2, SwingConstants.RIGHT));
	    panel.add(label, BorderLayout.WEST);

	    JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
	    JTextField textbox = new JTextField();
	    controls.add(textbox);
	    panel.add(controls, BorderLayout.CENTER);

	    int input = JOptionPane.showConfirmDialog(frame, panel, "VPN Setup", JOptionPane.OK_CANCEL_OPTION);
	    String TXT = textbox.getText();
	    
	    if (input == JOptionPane.OK_OPTION) {
			Path path1 = Paths.get("C:/Profile setup/Support Scripts","VPN2.bat");
			Path path2 = Paths.get("C:/Profile setup/VPN/conf","NetOperators.bat");
			Charset charset = StandardCharsets.UTF_8;
	
			String content = new String(Files.readAllBytes(path1), charset);
			String content2 = new String(Files.readAllBytes(path2), charset);
			
			content = content.replaceAll(s1, TXT);
			content2 = content2.replaceAll(s1, TXT);
			Files.write(path1, content.getBytes(charset));
			Files.write(path2, content2.getBytes(charset));
	    }	
	}//end:searchReaplace
	

	//Timed task
	public boolean timeTask() {
	    final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleWithFixedDelay(new Runnable(){
	        public void run(){ 
	        		try {
						//ip.isIPReachable("192.168.254.3");
						//echo("Alpha reachable? true");
					} catch (Exception e) {
						//echo("Alpha reachable? false");
					}	
	        }
	    }, 0, 2, TimeUnit.SECONDS);
		return true;
	}//end: Timed task     
	
	/*
	//While loop 
	public static void loop() throws Exception {
		System.out.println("Inside loop");
		if (!ip.isIPReachableYN("192.168.254.3")){
			System.out.println("Alpha CANT be reached!");
			while(!ip.isIPReachableYN("192.168.254.3")){
				System.out.println("Alpha can be reached? " + ip.isIPReachableYN("192.168.254.3"));
				Main.rdbtnNewRadioButton.setSelected(false);
			}
		}
		
		if (ip.isIPReachableYN("192.168.254.3")){
			System.out.println("Alpha can be reached");
			while(ip.isIPReachableYN("192.168.254.3")){
				System.out.println("Alpha can be reached? " + ip.isIPReachableYN("192.168.254.3"));
				Main.rdbtnNewRadioButton.setSelected(true);
			}	
			loop();
		}	
	}
	*/
	
	public void sleep(int i){
		try {
		    Thread.sleep(i);                
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
	
		
	//Delete file
	public void delete(String s) throws IOException{
		File file = new File(s);
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}	
	}//end: Delete file 
	
	
	public static void copyfile(String s) {
		try {
			FileUtils.copyDirectory(
			        FileUtils.getFile("C:\\Users\\patc\\Documents\\Letters\\"),  // source
			        FileUtils.getFile("C:\\tempfolder\\Letters"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
	//Checck if a file exists 
	public boolean fileExists(String s1, String s2) {
		//s1 = Directory 
		//s2 = File
		boolean check = new File(s1, s2).exists();
		return check;
	}
	
	/*
	//Check if process exists 
	public boolean processBoolean(String s) throws Exception{
		if (ProcessMgmt.isProcessRunning(s)){	
		return true;
		}
	return false;	
	}
	*/
	
	//Send post to receive email
	public void sendPost(String s, String m) throws Exception {
		
		String USER_AGENT = "Mozilla/5.0";
		 
		String url = "https://ws.heartnhomehospice.com/android/send_email.php";
		 
		@SuppressWarnings("resource")
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
 
		// add header
		post.setHeader("User-Agent", USER_AGENT);
 
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("perm", "20150521"));
		urlParameters.add(new BasicNameValuePair("m", m));
		urlParameters.add(new BasicNameValuePair("s", s));
 
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
 
		HttpResponse response = client.execute(post);
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + post.getEntity());
		System.out.println("Response Code : " + 
                                    response.getStatusLine().getStatusCode());
 
		BufferedReader rd = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
 
		System.out.println(result.toString());
 
	}//end:Send post to receive email
	
	public void tryPost(String s, String m) {
		try {
			sendPost(s, m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//Email 
	public static void email(String user,String pass,String emailFrom, String emailTo, String subject, String body)throws IOException {


	    final String username = user;
	    final String password = pass;

	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.host", "mail.gohospice.com");
	    props.put("mail.smtp.port", "25");

	    Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	      });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(emailFrom));
	        message.setRecipients(Message.RecipientType.TO,
	            InternetAddress.parse(emailTo));
	        message.setSubject(subject);
	        message.setText(body);

	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	}//end: Email
	

	//Read XML properties file	
	public static String get_prop(String key) {
		
		try {
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream("c:/Profile setup/AD/conf/ADConfig.xml");
			
			try {
				props.loadFromXML(fis);
		        return props.getProperty(key);
				
				
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return "Could not find this value, check key?";
	}//end: Read XML properties file		
	
	
	
	public String[] get_prop_Hash(String key) {
		
		try {
			Properties props = new Properties();
			FileInputStream fis = new FileInputStream("c:/Profile setup/AD/conf/ADConfig.xml");
			
			try {
				props.loadFromXML(fis);
		        props.getProperty(key);
				
				
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		echo("Could not find this value, check key?");
		return null;
	}//end: Read XML properties file		



	@SuppressWarnings("unused")
	public static String[] fetchArrayFromPropFile(String key) {
		
		try {
			Properties propFile = new Properties();
			FileInputStream fis = new FileInputStream("c:/Profile setup/AD/conf/ADConfig.xml");
			try {
			propFile.loadFromXML(fis);
			//propFile.getProperty("XYZ").split(";");
			//System.out.println("test array " +propFile);
			
//			
//			  //get array split up by the semicolin
			  String a = propFile.getProperty(key);
			  String[] parts = a.split(Pattern.quote(";"));
//			  String parts1 = parts[0];
//			  String parts2 = parts[1];
//			  String parts3 = parts[2];
//			  String parts4 = parts[3];
//			  String parts5 = parts[4];
//			  String parts6 = parts[5];
//			  String parts7 = parts[6];
//			  String parts8 = parts[7];
//			  String parts9 = parts[8];
			 
			  
			  System.out.println("test a " +a);
			  System.out.println("test b " +parts1+" "+parts2+" "+parts3+" "+parts4+" "+parts5+" "+parts6+" "+parts7+" "+parts8+" "+parts9);
			  //create the two dimensional array with correct size
			  //String[][] array = new Stringparts[]];
			  System.out.println(parts.length);
			  //combine the arrays split by semicolin and comma 
			  for(int i = 0;i < parts.length;i++) {
			      System.out.println(parts[i]);
			      return parts;
			  }
			} catch (InvalidPropertiesFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		//return parts;
		return null;
	}
	
	

	
	
	
	
	//Create XML properties file
	public void set_prop(String osChoice) throws InvalidPropertiesFormatException, IOException {
		
	Properties props = new Properties();
	props.setProperty("OS", osChoice);
	File file = new File("c:/Profile setup/VPN/conf/VPNConfig.xml");
	FileOutputStream fileOut = new FileOutputStream(file);
	props.storeToXML(fileOut, "Operating System");
	fileOut.close();
	
	}//end: Create XML properties file
	
	public void userName (String username){
		System.getProperty("user.name");
	}//end:Create XML properties file 
	
	
	//Set look and feel to System
	public void windoLook(){
		 try {
			UIManager.setLookAndFeel(
				        UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}	
	}//end:Set look and feel to System		
	
	
	//LDAP SAMACCOUNT Search 
	public static boolean SamSearch(String LookUp) throws NamingException{
			//String LookUp = Main.AccountName.getText();
			Core.echo("" + LookUp);
			Core.echo("" + LookUp + " 1");
		
				
			DirContext ctx = new InitialDirContext(Ldap_Lib.env);
		    SearchControls ctls = new SearchControls();
		    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			      
			//search location for samAccount      
			NamingEnumeration answer = ctx.search(ApplicationFunctions.OU_DC,"(&(objectCategory=Person)(objectClass=User)(sAMAccountName=" + LookUp +"))", ctls);

			 
			if (!answer.hasMore()) {
				return false;
			}
			return true;	
	}
	
	
	//LDAP Find DN with SAM 
	public static void FindDN (String LookUpDN) throws NamingException{
		DirContext ctx = new InitialDirContext(Ldap_Lib.env);
	    SearchControls ctls = new SearchControls();
		
		
		
		NamingEnumeration answer = ctx.search(ApplicationFunctions.OU_DC,"(&(objectCategory=Person)(objectClass=User)(sAMAccountName=" + LookUpDN +"))", ctls);
    	
    	SearchResult sr = (SearchResult)answer.next();
	    System.out.println(">>>" + sr.getName());
	    
	    DirContext ctx2 = new InitialDirContext(Ldap_Lib.env);  
	    Attributes answer2 	= ctx.getAttributes(sr.getName() + "," + ApplicationFunctions.OU_DC);
	    
	    System.out.println("distinguishedName: " + answer2.get("distinguishedName").get());
	    LookUpDN			= "" + answer2.get("distinguishedName").get();
	    System.out.println("This is the DN you are looking for? " + LookUpDN);
	}
	
	
	
	
	

	
	
	
	
						
}//end; Core class

