package Java_Threaded_Fedex_Tracker;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


@SuppressWarnings("serial")
public class FedXGUI extends JFrame implements ActionListener {



	String serviceName= "";
	float weightLbs = 0;
	String weight = "";
	String packageType = "";
	String packageSize = "";
	String signatureName = "";
	String pieceNum = "";
	String instruction = "";
	int trackingID;
	String sourceLocation = "";
	String destLocation = "";


	public ResultSet resultSet2 = null;
	public static PreparedStatement preparedStatement1 = null;
	public static Connection connect = null;
	public static int len = 0;


	private final static int INFO_SIZE = 30;
	private JTextField _info = new JTextField("Welcome to FedEx Package Services!!",100);

	private JFrame frame =  new JFrame();
	private String bnames[]={ "New Packet","Enter Tracking ID"};

	private String lnames[]={ "Fedex Service", "Weight in Lbs./kgs ", "Package Type", 
			"Signature Service", "Enter Total Pieces", "Special Instructions", 
			"Source Location", "Destination Location","Tracking ID"};
	private String lnames1= "Your Tracking ID is";
	private String bnames2[]={ "Enter"};
	private String mnames[]={ "Reset", "Exit"};

	private JButton buttons[];
	private JLabel labels[];
	private JLabel labels1;
	private JButton buttons2[];   
	private JTextField tfields[];
	private JMenuItem menuitems[];

	@SuppressWarnings("rawtypes")
	private static JComboBox comboBox[];
	private JMenuBar bar = new JMenuBar();
	private JMenu file = new JMenu("File");
	private int selecteditem = 0;

	public FedXGUI() {
		super("FedX Packet Info"); //here super class is JFrame. So sets the frame title.
		//		initialize();
		setSize(800,800);       //setSize(int width, int height)
		setLocation(100,100);
		setlabels();
		setcomboBox();
		setbuttons();
		settextfield();
		setbutton2();
		setmenubar();
		ContainerSetup();
		setVisible(true);
	}

	/**	 Declares the Cities of source and Destination
	 *	 Sets it as null 
	 */	

	private void setcomboBox() {
		comboBox = new JComboBox[2];
		for(int i = 0; i< comboBox.length; i++) {
			comboBox[i] = new JComboBox<Object>(PackageThread.locationArray);
			comboBox[i].setSelectedItem(null);
		}
		disablecomboBox();
	}


	/**	 Declares the Labels: Fedex Service", "Weight in Lbs./kgs ", "Package Type", 
	 *			"Signature Service", "Enter Total Pieces", "Special Instructions", 
	 *			"Source Location", "Destination Location" and Tracking ID is
	 *	 Set Alignment and whether its enabled for user.
	 */
	private void setlabels()
	{
		labels = new JLabel[lnames.length];
		for (int i = 0; i < lnames.length; i++) 
		{
			labels[i] = new JLabel(lnames[i]);
			labels[i].setHorizontalAlignment(SwingConstants.CENTER);
			labels[i].setEnabled(false);
		}
		labels1 = new JLabel(lnames1);							// Tracking Id is:
		labels1.setHorizontalAlignment(SwingConstants.CENTER);
		labels1.setEnabled(false);
	}

	/**
	 * Declares the Buttons: New Packet, Enter Tracking ID
	 * Sets ActionListener and listens to the button
	 */
	private void setbuttons()
	{
		buttons = new JButton[bnames.length];
		for (int i = 0; i < bnames.length; i++) 
		{
			buttons[i] = new JButton( bnames[i]);
			buttons[i].addActionListener(this);
		}
	}

	/**
	 * Declares the TextField : Fedex Service", "Weight in Lbs./kgs ", "Package Type", 
	 *			"Signature Service", "Enter Total Pieces", "Special Instructions", 
	 *			"Source Location", "Destination Location", "Tracking ID".
	 *Limit 30 words
	 *Initially not enabled
	 */
	private void settextfield()
	{
		tfields = new JTextField[lnames.length-2];
		for (int i = 0; i < lnames.length-2; i++) 
		{
			tfields[i] = new JTextField(INFO_SIZE);
			tfields[i].setEnabled(false);
		}
	}

	/**
	 * Declares the Enter Button
	 * Listens to the button
	 */
	private void setbutton2(){
		buttons2 = new JButton[bnames2.length];
		for (int i = 0; i < bnames2.length; i++) 
		{
			buttons2[i] = new JButton(bnames2[i]);
			buttons2[i].addActionListener(this);
		}
	}

	/**
	 * Declares menu items : "Reset", "Exit".
	 * Sets ActionListener and listens.
	 */
	private void setmenubar()
	{
		menuitems = new JMenuItem[mnames.length];
		for (int i = 0; i < mnames.length; i++) 
		{
			menuitems[i] = new JMenuItem(mnames[i]);
			menuitems[i].addActionListener(this);
		}

	}

	/**
	 * Gets Content Pane: Adds menu items to menu file.
	 * 					Adds file to menu bar and sets menu bar.
	 * Sets nature, color, alignment and grid layout for text fields,
	 *  				buttons and labels. 
	 */
	private void ContainerSetup()
	{
		Container c = getContentPane();

		for (int i = 0; i < mnames.length; i++) file.add(menuitems[i]);
		bar.add(file);
		setJMenuBar(bar);

		//North Layout
		_info.setEditable(false);
		_info.setBackground(Color.LIGHT_GRAY );
		c.add(_info,BorderLayout.NORTH);


		//South Layout			
		JPanel spanel = new JPanel();
		spanel.setLayout(new GridLayout(bnames2.length+1,0));
		spanel.add(labels1);			
		for (int i = 0; i < bnames2.length; i++) spanel.add(buttons2[i]);
		c.add(spanel,BorderLayout.SOUTH);

		//Center Layout
		JPanel cpanel = new JPanel();
		cpanel.setBorder(BorderFactory.createLoweredBevelBorder());
		cpanel.setLayout(new GridLayout(lnames.length,2));
		for (int i = 0; i < lnames.length-3; i++) 
		{
			cpanel.add(labels[i]);
			cpanel.add(tfields[i]);
		}
		cpanel.add(labels[6]);
		for (int i = 0; i <comboBox.length;i++) {
			cpanel.add(comboBox[i]);	
			cpanel.add(labels[7]);
			cpanel.add(comboBox[i]);	
		}

		cpanel.add(labels[8]);
		cpanel.add(tfields[6]);			

		c.add(cpanel,BorderLayout.CENTER);	

		//West Layout
		JPanel wpanel = new JPanel();
		wpanel.setLayout(new GridLayout(2,0));
		for (int i = 0; i< bnames.length; i++) wpanel.add(buttons[i]);
		c.add(wpanel,BorderLayout.WEST);

	}

	/**
	 * Initializes all text field to "" 
	 */
	private void resetinfo()
	{
		for (int i=0; i< lnames.length-2; i++) 
		{
			tfields[i].setText("");
		}
		for (int i=0; i<comboBox.length;i++)
		comboBox[i].setSelectedItem(null);
	}

	/**
	 * Enables buttons on the west: New Packet, Enter Tracking ID
	 */
	private void inablebuttonsAll()
	{
		for (int i=0; i< buttons.length; i++) 
		{
			buttons[i].setEnabled(true);
		}
	}

	/**
	 * Enables DropBox
	 */
	private void inablecomboBox()
	{
		for (int i=0; i< comboBox.length; i++) 
		{
			comboBox[i].setEnabled(true);
			comboBox[i].addActionListener(this);	
			comboBox[i].setSelectedItem(null);
		}
	}

	/**
	 * Disables labels and their text fields
	 */
	private void disableinfoAll()
	{
		for (int i=0; i <lnames.length; i++) 
		{
			labels[i].setEnabled(false);

		}
		for (int i=0; i <lnames.length-2; i++) 
			tfields[i].setEnabled(false);
		labels1.setEnabled(false);
	}

	/**
	 * Enables label and text field
	 * @param b
	 */
	private void inableinfo(int b)
	{
		for (int i = 0; i < lnames.length; i++) 
		{
			if (b == i) 
				labels[i].setEnabled(true);
		} // end for
		for (int i = 0; i < lnames.length-2; i++) {
			if (b-2 == i) 
				tfields[i].setEnabled(true);	
		}
		labels1.setEnabled(true);
	}

	/**
	 * Disables buttons on the west: New Packet, Enter Tracking ID
	 */
	//		@SuppressWarnings("unused")
	//		private void disablebuttons(int b)
	//		{
	//			for (int i=0; i< buttons.length; i++) 
	//			{
	//				if (b == i) buttons[i].setEnabled(false);
	//			}
	//		}

	private void disablecomboBox()
	{
		for (int i=0; i< comboBox.length; i++) 
		{
			comboBox[i].setEnabled(false);

		}
	}
	String src = "";
	String dst = "";
	
	/**
	 * Actions related to menus and buttons are performed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();	// The object on which the Event initially occurred

		if (source == menuitems[0])  // Reset
		{
			resetinfo();			// Sets Text field to ""
			inablebuttonsAll();		// Enables button access
			disablecomboBox();
		}
		if (source == menuitems[1])  // Exit
		{
			System.exit(1);
		}
		if (source == buttons[0])  // New Packet
		{
			resetinfo();
			tfields[0].setText("FedEx Home Delivery");
			tfields[0].setEditable(false);

			tfields[1].setText("2");
			tfields[1].setEditable(false);

			tfields[2].setText("Package");
			tfields[2].setEditable(false);

			tfields[3].setText("Direct Signature Required");
			tfields[3].setEditable(false);

			tfields[4].setText("5");
			tfields[4].setEditable(false);

			tfields[5].setText("Leave it in mail box");
			tfields[5].setEditable(false);

			inablecomboBox();

			for(int i = 0; i < 8; i++){
				inableinfo(i); 	  // Enables Text field up-to destination field and labels
			}
			selecteditem = 0;	  // flag for Enter button to know from which button operation its pressed
		}
		if (source == buttons[1])  // Enter Tracking ID
		{
			disablecomboBox();
			disableinfoAll();
			inableinfo(8); 		   // Enables Enter Tracking ID text field
			selecteditem = 1;
		}

		if (source == comboBox[0]) {
			src = (String)comboBox[0].getSelectedItem();
		}

		if (source == comboBox[1]) {
			dst = (String)comboBox[1].getSelectedItem();
		}

		if (source == buttons2[0])  //Enter Details
		{
			inablebuttonsAll();		// allows buttons 
			disableinfoAll();		// disables text fields and labels
			disablecomboBox();
			Package p = new Package();
			switch (selecteditem) 
			{
			case 0:
				Random r = new Random();
				String tracking_no = ""+r.nextInt(999999);
				p.tracking_no = tracking_no;
				p.setService(tfields[0].getText());
				p.setWeight(tfields[1].getText());
				p.setPacking(tfields[2].getText());
				p.setSignature_Services(tfields[3].getText());
				p.setPieces(Integer.parseInt(tfields[4].getText()));
				p.setSpecial_Handling_Section(tfields[5].getText());
				p.setSrc(src);
				p.setDest(dst);
				labels1.setText("Your Tracking ID is "+p.tracking_no);
				PackageThread p_thread = new PackageThread(p);
				try {
					p_thread.startThread();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;

			case 1:
				
					getDatabase(tfields[6].getText());									
			}			
		}
	}

	/**
	 * Gets Tracking_history info from database and creates new frame to display it.
	 * @param tracking_no
	 */
	public void getDatabase(String tracking_no){
		//System.out.println("Tracking Number:"+tracking_no);
		try {

			Connection con = SQLConnect.getConnection();
			Statement statement=con.createStatement();
			try {
			resultSet2 = statement.executeQuery("SELECT * from travel_history where Tracking_Number = '"+tracking_no+"'");
			//Array Lists
			ArrayList<Long> date = new ArrayList<>();
			//	ArrayList<String> activity = new ArrayList<>();
			ArrayList<String> location = new ArrayList<>();
			while (resultSet2.next()) {
				date.add(Long.parseLong(resultSet2.getString(1)));
				//	activity.add(resultSet2.getString(3));
				location.add(resultSet2.getString(3));
			}
			
			JFrame frame = new JFrame();
			//setSize(50, 50);
			//setLocation(55, 55);
			frame.setLayout(new GridLayout(25,1));
			JLabel label1 = new JLabel("Travel History");
			frame.add(label1);

			SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss");    
			Date resultdate = new Date(date.get(0));
			JLabel label2 = new JLabel(sdf.format(resultdate));
			frame.add(label2);

			for(int j = 0; j < date.size(); j++){
				resultdate = new Date(date.get(j));
				JLabel label3 = new JLabel(sdf.format(resultdate)+"   "+location.get(j));
				frame.add(label3);
			}
			
			JLabel label0 = new JLabel("");
			frame.add(label0);

			JLabel label4 = new JLabel("Shipment Details");
			frame.add(label4);

			JLabel label01 = new JLabel("");
			frame.add(label01);


			JLabel label5 = new JLabel("Tracking no:"+tfields[6].getText());
			frame.add(label5);

			JLabel label6 = new JLabel("Fedex Service:"+tfields[0].getText());
			frame.add(label6);

			JLabel label7 = new JLabel("Weight:"+tfields[1].getText());
			frame.add(label7);

			JLabel label8 = new JLabel("Signature Services:"+tfields[3].getText());
			frame.add(label8);;

			JLabel label9 = new JLabel("Special Handling Section:"+tfields[5].getText());
			frame.add(label9);

			JLabel label10 = new JLabel("Pieces:"+tfields[4].getText());
			frame.add(label10);

			JLabel label11 = new JLabel("source:"+src);
			frame.add(label11);

			JLabel label12 = new JLabel("destination:"+dst);
			frame.add(label12);				

			frame.setTitle("Tracker Details");
			frame.setSize(600,400);
			frame.setVisible(true);
			}catch(Exception e1) {
				System.out.println("Error : "+e1);
			}

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launch the application.
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {

		SQLConnect.clearDB();
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				try {
					FedXGUI window = new FedXGUI();
					window.frame.setVisible(true);
					window.addWindowListener( new WindowAdapter()
					{
						public void windowClosing(WindowEvent e) { System.exit(0); } 
					});


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
