import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JSplitPane;
import java.awt.GridLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.DefaultTableModel;

import db.db;
import objects.attendance;
import objects.dept;

import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.BorderLayout;
import java.awt.Button;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class mainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nameTT;
	private JTextField salTT;
	private JTextField deptTT;
	private JComboBox deptCB;
	private showEmp se;
	private showDept de;
	private JTable table;
	private showAtten at;
	private showAbs ab;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public mainFrame() {		
		setTitle("Employee And Attendance System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 941, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setEnabled(false);
		splitPane.setResizeWeight(0.44);
		splitPane.setBorder(null);
		contentPane.add(splitPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		splitPane.setLeftComponent(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Employee Management");
		lblNewLabel.setFont(new Font("STHeiti", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 6, 393, 34);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Add New Employee");
		lblNewLabel_2.setFont(new Font("STHeiti", Font.PLAIN, 13));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(6, 80, 393, 16);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Name: ");
		lblNewLabel_3.setFont(new Font("STHeiti", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(16, 137, 122, 16);
		panel.add(lblNewLabel_3);
		
		nameTT = new JTextField();
		nameTT.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		nameTT.setBounds(109, 132, 279, 26);
		panel.add(nameTT);
		nameTT.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Department: ");
		lblNewLabel_4.setFont(new Font("STHeiti", Font.PLAIN, 13));
		lblNewLabel_4.setBounds(16, 179, 122, 16);
		panel.add(lblNewLabel_4);
		
		dept[] depts = new db().getDept();
		deptCB = new JComboBox(depts);
		deptCB.setBounds(109, 175, 290, 27);
		panel.add(deptCB);
		
		JLabel lblNewLabel_5 = new JLabel("Base Salary: ");
		lblNewLabel_5.setFont(new Font("STHeiti", Font.PLAIN, 13));
		lblNewLabel_5.setBounds(16, 219, 122, 16);
		panel.add(lblNewLabel_5);
		
		salTT = new JTextField();
		salTT.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		salTT.setBounds(109, 214, 279, 26);
		panel.add(salTT);
		salTT.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Add New Department");
		lblNewLabel_6.setFont(new Font("STHeiti", Font.PLAIN, 13));
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBounds(6, 307, 393, 16);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Department Name: ");
		lblNewLabel_7.setFont(new Font("STHeiti", Font.PLAIN, 13));
		lblNewLabel_7.setBounds(16, 351, 133, 16);
		panel.add(lblNewLabel_7);
		
		deptTT = new JTextField();
		deptTT.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		deptTT.setBounds(161, 346, 227, 26);
		panel.add(deptTT);
		deptTT.setColumns(10);
		
		Button button = new Button("Add Employee");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (nameTT.getText().isBlank() || salTT.getText().isBlank() || ((dept) deptCB.getSelectedItem()).getDeptNo() == 0000) {
					JOptionPane.showMessageDialog(null, "Error: Enter Details In All Fields!", "Error!", JOptionPane.ERROR_MESSAGE);
				}else if(!checkText(nameTT.getText())){
					JOptionPane.showMessageDialog(null, "Error: Names Cannot Have Numbers Or Special Characters!", "Error!", JOptionPane.ERROR_MESSAGE);
				}else if(!isNumber(salTT.getText())) {
					JOptionPane.showMessageDialog(null, "Error: Base Salary Cannot Have Alphabets or Special Characters", "Error!", JOptionPane.ERROR_MESSAGE);
				}else{
					String name = nameTT.getText();
					int dept = ((dept) deptCB.getSelectedItem()).getDeptNo();
					String baseSal = salTT.getText();
					String s =  "Are You Sure You Want To Add: \n\nName: "+name+"\nDepartment: "+dept+"\nBase Salary: "+baseSal;
					if(JOptionPane.showConfirmDialog(null , s , "Confirm Addition Of Employee", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
						new db().addEmp(name, dept, Double.parseDouble(baseSal));
					}
					se.refresh();
					displayAttendanceTable();
				}
			}
		});
		
		button.setFont(new Font("STHeiti", Font.PLAIN, 12));
		button.setBackground(UIManager.getColor("Button.background"));
		button.setName("AddEmp");
		button.setBounds(133, 260, 147, 29);
		panel.add(button);
		
		Button button_1 = new Button("Add Department");
		
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String deptName = deptTT.getText();
				if (deptName.isBlank()) {
					JOptionPane.showMessageDialog(null, "Error: Enter A Department Name!", "Error!", JOptionPane.ERROR_MESSAGE);
				}else if(!checkText(deptName)){
					JOptionPane.showMessageDialog(null, "Error: Names Cannot Have Numbers Or Special Characters!", "Error!", JOptionPane.ERROR_MESSAGE);
				}else {
					if(JOptionPane.showConfirmDialog(null , "Are You Sure You Want To Add: \nDepartment: "+deptName , "Confirm Addition Of Department", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
						new db().addDept(deptName);
						refreshCombo();
						de.refresh();
						displayAttendanceTable();
					}
				}
			}
		});
		
		button_1.setFont(new Font("STHeiti", Font.PLAIN, 12));
		button_1.setName("AddEmp");
		button_1.setBackground(UIManager.getColor("Button.background"));
		button_1.setBounds(133, 394, 147, 29);
		panel.add(button_1);
		
		Button showEmp = new Button("Show Employees");
		
		se = new showEmp(this);
		showEmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!se.isVisible()) {
					se.setVisible(true);
				}				
			}
		});
		
		showEmp.setFont(new Font("STHeiti", Font.PLAIN, 12));
		showEmp.setBounds(10, 494, 147, 29);
		panel.add(showEmp);
		
		Button showDept = new Button("Show Departments");
		
		de =  new showDept(this);
		showDept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!de.isVisible()) {
					de.setVisible(true);
				}
			}
		});
		
		showDept.setFont(new Font("STHeiti", Font.PLAIN, 12));
		showDept.setBounds(237, 494, 158, 29);
		panel.add(showDept);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Attendance");
		lblNewLabel_1.setFont(new Font("STHeiti", Font.BOLD, 20));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(6, 6, 505, 37);
		panel_1.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Present");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String empNo = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
				modifyAttendance(empNo, true);
				at.refresh();
				ab.refresh();
			}
		});
		
		btnNewButton.setFont(new Font("STHeiti", Font.PLAIN, 13));
		btnNewButton.setBounds(25, 452, 117, 29);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Absent");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String empNo = (String) table.getModel().getValueAt(table.getSelectedRow(), 0);
				modifyAttendance(empNo, false);
				ab.refresh();
				at.refresh();
			}
		});
		
		btnNewButton_1.setFont(new Font("STHeiti", Font.PLAIN, 13));
		btnNewButton_1.setBounds(378, 452, 117, 29);
		panel_1.add(btnNewButton_1);
		
		
		at = new showAtten(this);
		JButton btnNewButton_2 = new JButton("Show Attendence");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!at.isVisible()){
					at.setVisible(true);
				}
			}
		});
		btnNewButton_2.setFont(new Font("STHeiti", Font.PLAIN, 13));
		btnNewButton_2.setBounds(192, 425, 139, 37);
		panel_1.add(btnNewButton_2);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 55, 505, 340);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 493, 328);
		panel_2.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		ab = new showAbs(this);
		JButton btnNewButton_2_1 = new JButton("Show Absence");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!ab.isVisible()){
					ab.setVisible(true);
				}
			}
		});
		btnNewButton_2_1.setFont(new Font("STHeiti", Font.PLAIN, 13));
		btnNewButton_2_1.setBounds(192, 474, 139, 37);
		panel_1.add(btnNewButton_2_1);
		displayAttendanceTable();
		
		splitPane.setDividerLocation(0.5);
		
	}

	public boolean checkText(String text) {
		for (char c: text.toCharArray()) {
			if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
				System.out.println(c);
				return false;
			}
		}
		return true;
	}
	
	public boolean isNumber(String num) {
		try {
			if (Double.parseDouble(num) < 0)
				return false;
			return true;
		}catch(java.lang.NumberFormatException e) {
			return false;
		}
	}
	
	public void refreshCombo() {
		deptCB.removeAllItems();
		for (dept d: new db().getDept())
			deptCB.addItem(d);
	}
	
	public void refreshEmp() {
		se.refresh();
	}
	
	public void displayAttendanceTable() {
		attendance[] e = new db().getAttendance();
		DefaultTableModel m = new DefaultTableModel(new String[]{"Emp. No", "Name", "Dept. Name", "Days Present", "Salary Due"}, 0);
		
		for (attendance atten: e) {
				m.addRow(atten.getTableRow());
		}
		
		table.setModel(m);
	}
	
	private void modifyAttendance(String empNo, boolean at) {
		if (at) {
			new db().modifyAttendance(empNo);
		}else {
			new db().modifyAbsence(empNo);
		}
		displayAttendanceTable();
	}
}
