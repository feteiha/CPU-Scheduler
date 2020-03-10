import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


public class Window {

	private JFrame frmAddProcess;
	private JTextField nameTB;
	private JTextField arrivalTB;
	private JTextField burstTB;
	private JTextField priorityTB;
	private GUI gui;
	private JTextField colorTB;


	public Window(GUI gui) {
		this.gui = gui;
		initialize();
		this.frmAddProcess.setVisible(true);
		
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAddProcess = new JFrame();
		frmAddProcess.setTitle("Add Process");
		frmAddProcess.setResizable(false);
		frmAddProcess.setBounds(800, 100, 369, 319);
		frmAddProcess.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Name");
		
		nameTB = new JTextField();
		nameTB.setColumns(10);
		
		JLabel lblArrivalTime = new JLabel("Arrival Time");
		
		arrivalTB = new JTextField();
		arrivalTB.setColumns(10);
		
		JLabel lblBurstTime = new JLabel("Burst Time");
		
		burstTB = new JTextField();
		burstTB.setColumns(10);
		
		JLabel lblPriority = new JLabel("Priority");
		
		priorityTB = new JTextField();
		priorityTB.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if ( nameTB.getText().length() == 0 ) {
					showMessage( "Process name is required" , "Info"); return;
				}
				
				if ( colorTB.getText().length() == 0 ) {
					showMessage( "Process color is required" , "Info"); return;
				}
				
				if ( arrivalTB.getText().length() == 0 ) {
					showMessage( "Process arrival time is required" , "Info"); return;
				}
				
				if ( burstTB.getText().length() == 0 ) {
					showMessage( "Process burst time is required" , "Info"); return;
				}
				
				if ( priorityTB.getText().length() == 0 ) {
					showMessage( "Process priority is required" , "Info"); return;
				}
				
				
				
				Field field = null;
		        try {
		            field = Color.class.getField(colorTB.getText().toString());
		        } 
		        catch (Exception e) {
		        	showError( "( " + colorTB.getText() + " ) is not a valid color" );
		        	return ;
		        }
				
		        Color color = null;
		    
		        try {
		        	color = (Color)field.get(null);
		        }
		        catch (Exception e) {
		        	showError( "( " + colorTB.getText() + " ) is not a valid color" );
		        	return ;
				}
		        
		        try {
		        	Double.parseDouble(arrivalTB.getText());
		        }
		        catch (Exception e) {
		        	showError( "( " + arrivalTB.getText() + " ) is not a valid decimal value" );
		        	return ;
				}
		        
		        try {
		        	Double.parseDouble(burstTB.getText());
		        }
		        catch (Exception e) {
		        	showError( "( " + burstTB.getText() + " ) is not a valid decimal value" );
		        	return ;
				}
		        
		        try {
		        	 Integer.parseInt(priorityTB.getText());
		        }
		        catch (Exception e) {
		        	showError( "( " + priorityTB.getText() + " ) is not a valid intger value" );
		        	return ;
				}
		        
		        Process p = new Process(nameTB.getText(), color, Double.parseDouble(arrivalTB.getText()),
		        		Double.parseDouble(burstTB.getText()), Integer.parseInt(priorityTB.getText()) );
		        gui.addProcess(p);
		        frmAddProcess.dispose();
				
			}
		});
		
		colorTB = new JTextField();
		colorTB.setColumns(10);
		
		JLabel lblColor_1 = new JLabel("Color");
		GroupLayout groupLayout = new GroupLayout(frmAddProcess.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(54)
					.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
					.addGap(67)
					.addComponent(nameTB, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addGap(47))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(54)
					.addComponent(lblColor_1, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
					.addGap(57)
					.addComponent(colorTB, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addGap(47))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(54)
					.addComponent(lblArrivalTime, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
					.addGap(24)
					.addComponent(arrivalTB, GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
					.addGap(47))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(54)
					.addComponent(lblBurstTime, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
					.addGap(25)
					.addComponent(burstTB, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(47))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(54)
					.addComponent(lblPriority, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
					.addGap(46)
					.addComponent(priorityTB, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
					.addGap(47))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(93)
					.addComponent(btnSubmit, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
					.addGap(92))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(nameTB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblColor_1))
						.addComponent(colorTB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblArrivalTime))
						.addComponent(arrivalTB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(25)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblBurstTime))
						.addComponent(burstTB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(lblPriority))
						.addComponent(priorityTB))
					.addGap(35)
					.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addGap(22))
		);
		frmAddProcess.getContentPane().setLayout(groupLayout);

	}
	
	public static void showError( String _error ) {
		final JPanel panel = new JPanel();
	    JOptionPane.showMessageDialog(panel, _error, "Error", JOptionPane.ERROR_MESSAGE);   
	}
	
	public static void showMessage( String _message , String title ) {
		final JPanel panel = new JPanel();
	    JOptionPane.showMessageDialog(panel, _message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
