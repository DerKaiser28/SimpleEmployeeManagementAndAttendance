import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import db.db;
import objects.emp;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

public class showEmp extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private mainFrame parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			showEmp dialog = new showEmp(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public showEmp(mainFrame f) {
		setTitle("Employees");
		parent = f;
		setBounds(100, 100, 540, 334);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 528, 255);
		contentPanel.add(scrollPane);
		{
			table = new JTable();
			refresh();
			scrollPane.setViewportView(table);
		}
		
		{
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnNewButton = new JButton("Delete");
				btnNewButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectedRow = table.getSelectedRow();
						String empNo = (String) table.getModel().getValueAt(selectedRow, 0);
						new db().deleteEmp(empNo);
						refresh();
						parent.displayAttendanceTable();
					}
				});
				buttonPane.add(btnNewButton);
			}
			{
				JButton cancelButton = new JButton("Close");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void refresh() {
		emp[] e = new db().getEmp();
		DefaultTableModel m = new DefaultTableModel(new String[]{"EmpNo", "Name", "Dept. No", "Base Salary"}, 0);
		
		for (emp emp: e) {
			m.addRow(emp.getTableRow());
		}
		
		table.setModel(m);
	}
}
