import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jfree.ui.RefineryUtilities;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


public class GUI {

	private JFrame frmCpuScheduler;
	private JTable table;
	static DefaultTableModel model ;
	private JScrollPane scrollPane;
	private JButton btnAddProcess;
	ArrayList<Process> process = new ArrayList<Process>();
	private JLabel waitingTimelbl;
	private JLabel TurnaroundTimelbl;
	private JLabel waitingTimeValue;
	private JLabel TurnaroundValue;
	private JTextField ContextSwitchTF;
	private JLabel lblNewLabel;
	private JTextField quantumTF;
	private JLabel lblQuantumTime;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frmCpuScheduler.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCpuScheduler = new JFrame();
		frmCpuScheduler.setTitle("CPU Scheduler");
		frmCpuScheduler.setBounds(700, 100, 631, 653);
		frmCpuScheduler.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeTable();
		
		btnAddProcess = new JButton("Add Process");
		btnAddProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runAddProcess();
			}

			
		});
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setName("Types");
		comboBox.addItem("SJF");
		comboBox.addItem("SRTF");
		comboBox.addItem("Priority Scheduling");
		comboBox.addItem("AG");
		
		JButton btnSimulate = new JButton("Simulate");
		btnSimulate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if 		( process.isEmpty() ) {
					showMessage("you must add at least one process to simulate" , "Info" ); return;
				}
				else if ( ContextSwitchTF.getText().length() == 0 ) {
					showMessage("Conext Switch can't be empty" , "Info" ); return;
				}
				else if ( quantumTF.getText().length() == 0 ) {
					showMessage("Quantum can't be empty" , "Info" ); return;
				}
				
				boolean valid1 = false , valid2 = false;
				try {
					Double.parseDouble( ContextSwitchTF.getText() );
					valid1 = true;
				}
				catch (Exception e) {
					// TODO: handle exception
					showError("Context Switch value show be decimal value");
					return;
				}
				
				try {
					Double d = Double.parseDouble( quantumTF.getText() );
					if ( d.compareTo(0.0) == 0 ) {
						showError("Quantum value must be greater than zero");
						return;
					}
					valid2 = true;
				}
				catch (Exception e) {
					// TODO: handle exception
					showError("Quantum value show be decimal value");
					return;
				}
				
				if ( valid1 && valid2 ) {
					for ( int i = 0; i < process.size(); ++i ) {
						process.get(i).quantum = Double.parseDouble( quantumTF.getText() );
					}
					
					if (comboBox.getSelectedItem().equals("SJF")) {
						ShortestJobFirst sjf = new ShortestJobFirst(process);
						sjf.contextSwitching = Double.parseDouble( ContextSwitchTF.getText() );
						sjf.process();
					} else if (comboBox.getSelectedItem().equals("SRTF")) {
						ShortestRemainingTimeFirst srtf = new ShortestRemainingTimeFirst(process);
						srtf.contextSwitching = Double.parseDouble( ContextSwitchTF.getText() );
						srtf.process();
					} else if (comboBox.getSelectedItem().equals("Priority Scheduling")) {
						PriorityScheduling ps = new PriorityScheduling(process);
						ps.contextSwitching = Double.parseDouble( ContextSwitchTF.getText() );
						ps.process();
					} else {
						AG object = new AG(process); object.simulate();
					}
					
					for ( int i = 0; i < process.size(); ++i ) {
						table.setValueAt( process.get(i).waitingTime , i, 5);
						table.setValueAt( process.get(i).turnArroundTime , i, 6);
					}
					
					double averageWaiting = calcAverageWaitingTime(process);
	                double averageTurnaround = calcAverageTurnarroundTime(process);
	                waitingTimeValue.setText(Double.toString(averageWaiting));
	                TurnaroundValue.setText(Double.toString(averageTurnaround));

					GanttChart demo = new GanttChart(comboBox.getSelectedItem().toString(), process);
					
			        demo.pack();
			        RefineryUtilities.centerFrameOnScreen(demo);
			        demo.setVisible(true);
			        demo.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
				}
			}

		});
		
		waitingTimelbl = new JLabel("Average Waiting Time:");
		waitingTimelbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		TurnaroundTimelbl = new JLabel("Average Turnaround Time:");
		TurnaroundTimelbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		waitingTimeValue = new JLabel("");
		waitingTimeValue.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		TurnaroundValue = new JLabel("");
		TurnaroundValue.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		ContextSwitchTF = new JTextField();
		ContextSwitchTF.setColumns(10);
		
		lblNewLabel = new JLabel("Context Switch:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		quantumTF = new JTextField();
		quantumTF.setColumns(10);
		
		lblQuantumTime = new JLabel("Quantum Time:");
		lblQuantumTime.setFont(new Font("Tahoma", Font.PLAIN, 13));
		GroupLayout groupLayout = new GroupLayout(frmCpuScheduler.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(lblQuantumTime, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 363, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(ContextSwitchTF, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(quantumTF, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
							.addComponent(btnAddProcess, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addGap(2))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(waitingTimelbl, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
									.addGap(33)
									.addComponent(waitingTimeValue, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
								.addComponent(TurnaroundTimelbl, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(181)
									.addComponent(TurnaroundValue, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
							.addComponent(btnSimulate, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addGap(2)))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
					.addGap(8)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQuantumTime, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(ContextSwitchTF, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(quantumTF, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(comboBox, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(btnAddProcess, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(16)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(waitingTimelbl, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(waitingTimeValue, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(TurnaroundTimelbl, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
								.addComponent(TurnaroundValue, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
							.addGap(12))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnSimulate, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addGap(16))))
		);
		frmCpuScheduler.getContentPane().setLayout(groupLayout);
				
	}

	private void initializeTable() {
		table = new JTable(new DefaultTableModel(new Object[]{"Name", "Color", "Arrived", "Burst", "Priority" , "Waiting" , "Turnaround"},0));
		table.setBounds(500, 28, 292, 392);
		table.setFont(new Font("Consolas", Font.BOLD, 14));
		table.setFillsViewportHeight(true);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), Color.BLACK, Color.BLACK, Color.BLACK));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.setDefaultRenderer(Object.class, centerRenderer);
		table.setIntercellSpacing(new Dimension(40,5));
		table.setRowHeight(30);
		model = (DefaultTableModel) table.getModel();
		
		scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(1000, 226));	
		
	}

	public void runAddProcess() {
		@SuppressWarnings("unused")
		Window window = new Window(this);
	}

	public void addProcess(Process p) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int index = process.size();
		TableCellRenderer tableCellRenderer = new TableCellRenderer()
		{
		   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		   {
		      JLabel label = new JLabel();
		      label.setOpaque(true);
		      if (value != null) label.setText(value.toString());

		      if (row  == index)
		      {
		         label.setBackground(p.color);
		      }

		      return label;
		   }
		   
		};
		TableColumn column = table.getColumnModel().getColumn(1);
		
		column.setCellRenderer(tableCellRenderer);
		model.addRow(new Object[] {p.processName,"",p.arrivalTime,p.burstTime,p.priority});
		process.add(p);
	}
	
	public double calcAverageWaitingTime(ArrayList<Process> process) {
        double answer = 0;
        for (int i=0; i< process.size(); i++) {
            answer += process.get(i).waitingTime;
        }
        answer = answer / process.size();
        return answer;
    }

    public double calcAverageTurnarroundTime(ArrayList<Process> process) {
        double answer = 0;
        for (int i=0; i< process.size(); i++) {
            answer += process.get(i).turnArroundTime;
        }
        answer = answer / process.size();
        return answer;
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
