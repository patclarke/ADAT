//import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.net.URL;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
import java.text.ParseException;
//import java.util.Enumeration;
//import java.util.Hashtable;
//import java.util.InvalidPropertiesFormatException;
//import java.util.Properties;


















import javax.imageio.ImageIO;
//import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
//import javax.swing.Icon;
//import javax.swing.ImageIcon;
//import javax.swing.ImageIcon;
//import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;










import java.awt.Image;
//import java.awt.Image;
import java.awt.Label;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;
//import javax.swing.JCheckBox;

//import com.jgoodies.forms.factories.DefaultComponentFactory;


public class Main {
	//Connect to Classes
	static Core 					c 								= new Core();
	static ApplicationFunctions 	appfun              			= new ApplicationFunctions();
	
	public static String 					PASS;
	public static String[] 					AccountStatus 			= {"Enabled","Disabled"};
	
	private static JFrame 					frame 					= null;
	
	public static JTextField 				FName					= null;	
	public static JTextField 				LName 					= null;
	public static JTextField 				HPhone					= null;
	public static JTextField 				CPhone 					= null;	
	public static JTextField 				sAddress				= null;
	public static JTextField 				city 					= null;
	public static JTextField 				zip						= null;
	public static JTextField 				state					= null;
	public static JTextField 				allscriptsNumber		= null;
	public static JTextField 				credentials				= null;
	public static JTextField 				TITLE					= null;
	public static JTextField 				AccountName				= null;
	public static JTextField				IPext					= null;
	
	public static JFormattedTextField 		HCPhone 				= null;	
	
	public static JLabel 					lblthumbnail;
	public static JLabel 					lblCONSOLE						= new JLabel();
	public static JLabel            		lblVersion                      = new JLabel();
	public static Label             		ADPhoto 						= new Label("");
	public static JLabel 					lblAccountStatus 				= new JLabel("Account Status:");
	
	public static JLabel 					lblDisabled 					= new JLabel("Disabled");
	public static JLabel 					lblEnabled 						= new JLabel("Enabled");
	
	public static JButton 					btnCreate 						= new JButton("Create");
	public static JButton 					btnUpDate 						= new JButton("Update");
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//public static JComboBox			position 						= new JComboBox(Core.fetchArrayFromPropFile("Org"));
	public static JComboBox			position 						= null;
	
	//public static JComboBox 		homeOffice 						= new JComboBox(officeStrings);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComboBox 		homeOffice 						= new JComboBox(Core.fetchArrayFromPropFile("Office"));
	
	
	public static JComboBox 		StatuscomboBox 					= new JComboBox(AccountStatus);
	
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	static void initialize() {
	  	frame = new JFrame("A.D.A.T.");
	  	frame.setBounds(100, 100, 636, 361);
	  	frame.setVisible(true);
	  	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  	frame.getContentPane().setLayout(null);
		
		//set look and feel 
		c.windoLook();
		

		Core.get_prop("Test");
		

		//"First Name" Label
		JLabel FirstName = new JLabel("* First Name");
		FirstName.setBounds(10, 27, 71, 14);
		frame.getContentPane().add(FirstName);
		
		//"Personal Cell" Label
		JLabel PersCell = new JLabel("Personal Cell");
		PersCell.setBounds(10, 102, 79, 14);
		frame.getContentPane().add(PersCell);
		
		//"Last Name" Label
		JLabel LastName = new JLabel("* Last Name");
		LastName.setBounds(10, 52, 71, 14);
		frame.getContentPane().add(LastName);
		
		//"Home Phone" Label
		JLabel HomePhone = new JLabel("Home Phone");
		HomePhone.setBounds(10, 77, 79, 14);
		frame.getContentPane().add(HomePhone);
		
		//"Cell Phone" Label
		JLabel CellPhone = new JLabel("Work Cell");
		CellPhone.setBounds(10, 127, 71, 14);
		frame.getContentPane().add(CellPhone);
		
		//"Home Office" Label
		JLabel HomeOffice = new JLabel("*  Home Office");
		HomeOffice.setBounds(10, 152, 95, 14);
		frame.getContentPane().add(HomeOffice);
		
		//"Street Address" Label
		JLabel lblStreetAddress = new JLabel("* Street Address");
		lblStreetAddress.setBounds(210, 27, 95, 14);
		frame.getContentPane().add(lblStreetAddress);
		
		//"City" Label
		JLabel lblCity = new JLabel("* City");
		lblCity.setBounds(210, 52, 46, 14);
		frame.getContentPane().add(lblCity);
		
		//"Zip" Label
		JLabel lblZip = new JLabel("* Zip");
		lblZip.setBounds(210, 77, 46, 14);
		frame.getContentPane().add(lblZip);
		
		//"State" Label
		JLabel lblState = new JLabel("* State");
		lblState.setBounds(210, 99, 46, 14);
		frame.getContentPane().add(lblState);
		
		//"AllScripts#" Label
		JLabel lblAllscripts = new JLabel("AllScripts#");
		lblAllscripts.setBounds(210, 127, 71, 14);
		frame.getContentPane().add(lblAllscripts);
		
		//"Credentials" Label
		JLabel lblCredentials = new JLabel("Description");
		lblCredentials.setBounds(210, 152, 71, 14);
		frame.getContentPane().add(lblCredentials);
		
		//"Organizational Unit" Label
		JLabel lblPosition = new JLabel("* OU");
		lblPosition.setBounds(210, 202, 84, 14);
		frame.getContentPane().add(lblPosition);
		
		//"Title" Label
		JLabel lblTitle = new JLabel("* Title");
		lblTitle.setBounds(210, 177, 46, 14);
		frame.getContentPane().add(lblTitle);
		
		//Console Label
		lblCONSOLE.setBounds(10, 231, 246, 20);
		//lblCONSOLE.setText("VARIABLE");
		frame.getContentPane().add(lblCONSOLE);
		
		//Account Name Label
		JLabel lblAccountName = new JLabel("Account Name:");
		lblAccountName.setBounds(10, 280, 106, 31);
		frame.getContentPane().add(lblAccountName);
		
		//IP Phone ext
		JLabel lblIpPhoneExt = new JLabel("Extension");
		lblIpPhoneExt.setBounds(10, 177, 92, 14);
		frame.getContentPane().add(lblIpPhoneExt);
		
		//Version Label
		JLabel lblVersion = new JLabel("Version 1.9");
		lblVersion.setBounds(546, 302, 64, 9);
		frame.getContentPane().add(lblVersion);
		
		//DC Label
		JLabel lblDc = new JLabel("DC:");
		lblDc.setBounds(338, 288, 35, 14);
		frame.getContentPane().add(lblDc);

		
		
		
		//Label for the AD Thumbnail Photo
		lblthumbnail = new JLabel("");
		//lblthumbnail.setIcon(new ImageIcon("Images/Defualtthumbnail.png"));
		lblthumbnail.setIcon(new ImageIcon("Defualtthumbnail.png"));
		lblthumbnail.setBounds(440, 27, 152, 186);
		frame.getContentPane().add(lblthumbnail);
		
		
		
		
		
		
		
		//Label for Search For Account
		JLabel lblSearchForAccount = new JLabel("Account Look Up");
		lblSearchForAccount.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblSearchForAccount.setBounds(10, 254, 160, 20);
		frame.getContentPane().add(lblSearchForAccount);
		
		//Label for Account Creator
		JLabel lblAccountCreator = new JLabel("Account Creator");
		lblAccountCreator.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAccountCreator.setBounds(10, 0, 160, 20);
		frame.getContentPane().add(lblAccountCreator);
		
		
		
		//First Name Text Box
		FName = new JTextField();		
		FName.setBounds(99, 24, 86, 20);
		frame.getContentPane().add(FName);
		FName.setColumns(10);
		
		
		MaskFormatter mask = null;
        try {
			mask = new MaskFormatter("###-###-####");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
        mask.setPlaceholderCharacter(' ');
		
		//Home Cell Phone Text Box
		//HCPhone = new JTextField();
        HCPhone = new JFormattedTextField(mask);
		HCPhone.setBounds(99, 99, 86, 20);
		frame.getContentPane().add(HCPhone);
		HCPhone.setColumns(10);
		
		//Last Name Text Box
		LName = new JTextField();
		LName.setBounds(99, 49, 86, 20);
		frame.getContentPane().add(LName);
		LName.setColumns(10);
		
		//Home Phone Text Box
		//HPhone = new JTextField();
		HPhone = new JFormattedTextField(mask);
		HPhone.setBounds(99, 74, 86, 20);
		frame.getContentPane().add(HPhone);
		HPhone.setColumns(10);
		
		//Cell Phone Text Box
		//CPhone = new JTextField();
		CPhone = new JFormattedTextField(mask);
		CPhone.setBounds(99, 124, 86, 20);
		frame.getContentPane().add(CPhone);
		CPhone.setColumns(10);
		
		//Street Address Text Box
		sAddress = new JTextField();
		sAddress.setBounds(315, 24, 86, 20);
		frame.getContentPane().add(sAddress);
		sAddress.setColumns(10);
		
		//City Text Box
		city = new JTextField();
		city.setBounds(315, 49, 86, 20);
		frame.getContentPane().add(city);
		city.setColumns(10);
		
		//Zip Text Box
		zip = new JTextField();
		zip.setBounds(315, 74, 86, 20);
		frame.getContentPane().add(zip);
		zip.setColumns(10);
		
		//State Text Box
		state = new JTextField();
		state.setBounds(315, 99, 86, 20);
		frame.getContentPane().add(state);
		state.setColumns(10);
		
		//AllScripts Number Text Box
		allscriptsNumber = new JTextField();
		allscriptsNumber.setBounds(315, 124, 86, 20);
		frame.getContentPane().add(allscriptsNumber);
		allscriptsNumber.setColumns(10);
		
		//Credentials Text Box
		credentials = new JTextField();
		credentials.setBounds(315, 149, 86, 20);
		frame.getContentPane().add(credentials);
		credentials.setColumns(10);
		
		//Title Text Box 
		TITLE = new JTextField();
		TITLE.setBounds(315, 174, 86, 20);
		frame.getContentPane().add(TITLE);
		TITLE.setColumns(10);
		
		//Account Name Text Box
		AccountName = new JTextField();
		AccountName.setBounds(99, 285, 86, 20);
		frame.getContentPane().add(AccountName);
		AccountName.setColumns(10);
		
		//IP Phone ext Text Box 
		IPext = new JTextField();
		IPext.setBounds(99, 174, 86, 20);
		frame.getContentPane().add(IPext);
		IPext.setColumns(10);
		
		//Bottom Separator
		JSeparator separator = new JSeparator();
		separator.setBounds(3, 254, 616, 2);
		frame.getContentPane().add(separator);
		
		
		//Home Office Combo Box 
		homeOffice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//ApplicationFunctions.homeOffice();
				position.setEnabled(true);
			}
		});	
		homeOffice.setBounds(99, 149, 86, 20);
		frame.getContentPane().add(homeOffice);
		
		//Position Combo Box
		position = new JComboBox(Core.fetchArrayFromPropFile("Org"));
		position.setEnabled(false);
		/*
		position.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					appfun.position();
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
		*/
		
		
		
		
		//DC ComboBox
		JComboBox DCcomboBox = new JComboBox();
		for (int i = 0; i < Core.user_Domain.size(); i++) {
			DCcomboBox.addItem(Core.user_Domain.get("DC" +i));
			} 
		//comboBox.addItem(Core.user_Domain.get("DC"+ 1));
		DCcomboBox.setBounds(371, 283, 165, 25);
		frame.getContentPane().add(DCcomboBox);
		DCcomboBox.setSelectedItem(LogIn.LDAPServer );
		DCcomboBox.addActionListener(new ActionListener(){	
			//Action Performed 
			public void actionPerformed(ActionEvent event2){	
				LogIn.LDAPServer = DCcomboBox.getSelectedItem().toString();
			}	
		});	
		
		
		
		position.setBounds(315, 199, 86, 20);
		frame.getContentPane().add(position);
		
		//"Create" Button
		btnCreate.addActionListener(new ActionListener(){
			//Action Performed 
		   	public void actionPerformed(ActionEvent ae){
		   		
		   		appfun.homeOffice();
		   		//Try and get position
		   		try {
		   			appfun.position();
				} catch (NamingException e) {
					Core.echo("Not able to run appfun.position()");
				}
		   		//Try and create account
		   		try {
					appfun.create();
				} catch (NamingException e) {
					Core.echo("Not able to run appfun.create()");
				}
		   		
		   	}
		});   	
		btnCreate.setBounds(3, 197, 86, 23);
		frame.getContentPane().add(btnCreate);
		
		//UpDate button
		btnUpDate.setVisible(false);
		btnUpDate.addActionListener(new ActionListener(){
			//Action Performed 
		   	public void actionPerformed(ActionEvent update){
		   		Core.echo("inside update");
		   		//AD_Replace_Attribute.update_user_dropbox();
		   		AD_Replace_Attribute.AccountStatus();
		   		AD_Replace_Attribute.update_user_info();
		   	}
		});
		btnUpDate.setBounds(3, 197, 86, 23);
		frame.getContentPane().add(btnUpDate);
		
		//Clear Button 
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener(){
			//Action Performed 
	   		public void actionPerformed(ActionEvent ae){
	   			FName.setText				(null);
	   			HCPhone.setText				(null);
	   			LName.setText				(null);
	   			HPhone.setText				(null);
	   			CPhone.setText				(null);	
	   			sAddress.setText			(null);
	   			city.setText				(null);
	   			zip.setText					(null);
	   			state.setText				(null);
	   			allscriptsNumber.setText	(null);
	   			credentials.setText			(null);
	   			TITLE.setText				(null);
	   			lblCONSOLE.setText          (null);
	   			AccountName.setText			(null);
	   			IPext.setText				(null);
	   			
	   			lblthumbnail.setIcon(new ImageIcon("Defualtthumbnail.png"));
	   			lblDisabled.setVisible(false);
	   			lblEnabled.setVisible(false);
	        	StatuscomboBox.setVisible(false);
	        	lblAccountStatus.setVisible(false);
	   			
	   			//AD_Search_Attribute = null;
	   			//Set Drop down box
	   			homeOffice.setSelectedIndex(0);
	   			

	   	
	   			position.setSelectedIndex(0);
	   			position.setEnabled(false);
	   			
		        //Turn on create button
		        btnCreate.setVisible		(true);
		        //Turn off update button
		        btnUpDate.setVisible		(false);
		        
		        //Turn on editing FName text box
		        FName.setEditable(true);
		        //Turn on editing LName text box
		        LName.setEditable(true);
		        
		        //garbage collection
		        System.gc(); 
	   		}
		});	
		btnClear.setBounds(96, 197, 89, 23);
		frame.getContentPane().add(btnClear);
		
		//Search Button
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent search){
					try {
						String SAMLookUp = AccountName.getText();
						AD_Search_Attribute.searchAtr(SAMLookUp);
						AD_Search_Attribute.setAtr(SAMLookUp);
					} catch (NamingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});	
		btnSearch.setBounds(205, 284, 89, 23);
		frame.getContentPane().add(btnSearch);			
		
		//Account Status Label
		//JLabel lblAccountStatus = new JLabel("Account Status:");
		lblAccountStatus.setBounds(210, 227, 95, 14);
		frame.getContentPane().add(lblAccountStatus);
		lblAccountStatus.setVisible(false);
		
		
		//Disabled label
		//JLabel lblDisabled = new JLabel("Disabled");
		lblDisabled.setBounds(501, 6, 46, 14);
		frame.getContentPane().add(lblDisabled);
		lblDisabled.setVisible(false);
		
		//Enabled label
		//JLabel lblEnabled = new JLabel("Enabled");
		lblEnabled.setBounds(501, 6, 46, 14);
		frame.getContentPane().add(lblEnabled);
		lblEnabled.setVisible(false);
		
		//Account Status Combobox
		//JComboBox StatuscomboBox = new JComboBox();
		StatuscomboBox.setBounds(315, 224, 86, 20);
		frame.getContentPane().add(StatuscomboBox);
		StatuscomboBox.setVisible(false);
		StatuscomboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent search){
			//AD_Replace_Attribute.AccountStatus();
			}
		});	
	}
}
