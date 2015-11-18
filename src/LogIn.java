import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Scanner;








//import javax.naming.NamingEnumeration;
//import javax.naming.NamingException;
//import javax.naming.directory.Attributes;
//import javax.naming.directory.DirContext;
//import javax.naming.directory.InitialDirContext;
//import javax.naming.directory.SearchControls;
//import javax.naming.directory.SearchResult;
//import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JComboBox;


public class LogIn {
	//Connect to Classes
	Core 				 		c 								= new Core();
	
	
	public static String 					PASS;
	public static String 					UsrCN;
	public static String                    USER;
	public static String                 	UsrDomain;
	public static String  					LDAP;
	public static String 					LDAPServer;

	
	final JFrame frame = new JFrame ("Connect to DC");
	private JTextField userField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn window = new LogIn();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogIn() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Connect to DC PopUp Window
		//frame = new JFrame();
		frame.setBounds(50, 50, 401, 265);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		//set look and feel 
		c.windoLook();
		
		final JPasswordField pwdPassword;
		
		
		//Password Box
		pwdPassword = new JPasswordField();
		pwdPassword.setText("");
		pwdPassword.setBounds(113, 136, 165, 25);
		frame.getContentPane().add(pwdPassword);
		
		
		//Username TextBox
		userField = new JTextField();
		userField.setColumns(10);
		userField.setEditable(false);
		userField.setBounds(113, 88, 165, 26);
		frame.getContentPane().add(userField);
		
		
		
		//Get Domain Name
		try {
	   		UsrDomain = Core.cmd("cmd /c echo %userdomain%");
			System.out.println("Domain is " + UsrDomain);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		//Get list of all DC on Domain	
		try {
			Core.cmdHash("cmd /c nltest /dclist:" + UsrDomain + " | findstr heartnhome");
		  } catch (Exception e) {
		    e.printStackTrace();
		  }
		
		
		//Get Users Name and OU 
	   	try {
	   		UsrCN = Core.cmd("cmd /c GPRESULT /z | findstr \"CN=\"");
			System.out.println("THEN HERE " + UsrCN);
			userField.setText(UsrCN);
			userField.setCaretPosition(0);
			pwdPassword.requestFocus();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
					
					
		//"Password" Label
		JLabel lblPW = new JLabel("Password:");
		lblPW.setBounds(27, 136, 64, 25);
		frame.getContentPane().add(lblPW);
		
		
		//"User" Label
		JLabel lblUser = new JLabel("User:");
		lblUser.setBounds(27, 76, 64, 50);
		frame.getContentPane().add(lblUser);
					
					
		//Connect Button
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(153, 179, 86, 23);
		frame.getContentPane().add(btnConnect);
		frame.getRootPane().setDefaultButton(btnConnect);
		
		
		//LDAP Label
		JLabel lblLdap = new JLabel("LDAP:");
		lblLdap.setBounds(27, 44, 46, 14);
		frame.getContentPane().add(lblLdap);
		
		
		
		//LDAP ComboBox
		JComboBox comboBox = new JComboBox();
		for (int i = 0; i < Core.user_Domain.size(); i++) {
			comboBox.addItem(Core.user_Domain.get("DC" +i));
			} 
		//comboBox.addItem(Core.user_Domain.get("DC"+ 1));
		comboBox.setBounds(113, 41, 165, 25);
		frame.getContentPane().add(comboBox);
		
		comboBox.addActionListener(new ActionListener(){	
			//Action Performed 
			public void actionPerformed(ActionEvent event2){	
				LDAPServer = comboBox.getSelectedItem().toString();
			}	
		});	
		comboBox.setSelectedIndex(1);
		
		
		
	   	//Action Listener for Text Fields
		btnConnect.addActionListener(new ActionListener(){	
			//Action Performed 
			public void actionPerformed(ActionEvent event2){			
				//Get Variables
			   	//USER = logIn.getText();
			   	//PASS = pwdPassword.getPassword();
			   	PASS 	= new String(pwdPassword.getPassword());
			   	USER  	= new String(userField.getText());
			  
	   			
	   			//Authenticate
	   			if(Ldap_Lib.connect_to_ldap(PASS, USER)){
	   				System.out.println("Launching Init.");	
	   				Main.initialize();
	   				frame.setVisible(false);	
	   			}else{
	   				//password incorrect notification
	   				System.out.println("Unable to log in.");
	   			}
			   }
		});
				
	}
}
