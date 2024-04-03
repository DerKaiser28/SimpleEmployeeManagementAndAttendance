import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import objects.dept;
import objects.emp;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;

import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JScrollPane;

public class showDept extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private mainFrame parent;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*try {
			showDept dialog = new showDept(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * Create the dialog.
	 */
	public showDept(mainFrame f) {
		setTitle("Departments");
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
						if(JOptionPane.showConfirmDialog(null ,"All Employees Within The Department Will Be Deleted Along With The Department, Are You Sure ?", "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
							int selectedRow = table.getSelectedRow();
							String deptNo = (String) table.getModel().getValueAt(selectedRow, 0);
							new db().deleteDept(deptNo);
							refresh();
							 parent.refreshCombo();
							 parent.refreshEmp();
							 parent.displayAttendanceTable();
						}
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
		dept[] e = new db().getDept();
		DefaultTableModel m = new DefaultTableModel(new String[]{"Dept. No", "Dept. Name"}, 0);
		
		for (dept dept: e) {
			if (dept.getDeptNo() != 0000)
				m.addRow(dept.getTableRow());
		}
		
		table.setModel(m);
	}
}
