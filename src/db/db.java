package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

import objects.attendance;
import objects.dept;
import objects.emp;
import objects.showAtAb;

import java.sql.PreparedStatement;

public class db {
	private Connection conn;
	
	public db(){
		connect();
		close();
	}
	
	private void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");  
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AES","root", "");
		} catch (SQLException e) {
			System.out.println("Connection Failed!: "+e);
		} catch(ClassNotFoundException e) {
			System.out.println("Class Not Found!");
		}
	}
	
	private void close() {
		try {
			conn.close();
		}catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public dept[] getDept() {
		ArrayList<dept> depts = new ArrayList<dept>();
		depts.add(new dept("Choose Department", 0000));
		try {
			connect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM dept");
			while(rs.next()) {
				depts.add(new dept(rs.getString(2), rs.getInt(1)));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
		return Arrays.copyOf(depts.toArray(), depts.size(), dept[].class);
	}
	
	public emp[] getEmp() {
		ArrayList<emp> emps = new ArrayList<emp>();
		try {
			connect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM emp");
			while(rs.next()) {
				emps.add(new emp(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4)));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
		return Arrays.copyOf(emps.toArray(), emps.size(), emp[].class);
	}
	
	public void addEmp(String name, int dept, double baseSal) {
		try {
			connect();
			int id = new Random().nextInt(100000);
			PreparedStatement p = conn.prepareStatement("INSERT INTO emp VALUES(?,?,?,?);");
			p.setInt(1, id);
			p.setString(2, name);
			p.setDouble(3, dept);
			p.setDouble(4, baseSal);
			p.execute();
			JOptionPane.showMessageDialog(null, "Employee Added Sucessfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
	}

	public void addDept(String deptName) {
		try {
			connect();
			int id = new Random().nextInt(10000);
			PreparedStatement p = conn.prepareStatement("INSERT INTO dept VALUES(?,?);");
			p.setInt(1, id);
			p.setString(2, deptName);
			p.execute();
			JOptionPane.showMessageDialog(null, "Department Added Sucessfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
	}
	
	public void deleteEmp(String en) {
		try {
			connect();
			PreparedStatement ap = conn.prepareStatement("DELETE FROM attendance where empNo = ?");
			ap.setString(1, en);
			ap.execute();
			PreparedStatement p = conn.prepareStatement("DELETE FROM emp where empNo = ?;");
			p.setString(1, en);
			p.execute();
			JOptionPane.showMessageDialog(null, "Employee Deleted Sucessfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
	}
	
	public void deleteDept(String dn) {
		try {
			connect();
			PreparedStatement ed = conn.prepareStatement("DELETE FROM emp where deptNo = ?;");
			ed.setString(1, dn);
			ed.execute();
			PreparedStatement p = conn.prepareStatement("DELETE FROM dept where deptNo = ?;");
			p.setString(1, dn);
			p.execute();
			JOptionPane.showMessageDialog(null, "Department And All Employees In The Department Deleted Sucessfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
	}

	public attendance[] getAttendance() {
		ArrayList<attendance> atten = new ArrayList<attendance>();
		try {
			connect();
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT e.empNo, e.empName, d.name as deptName, COALESCE(COUNT(a.date), 0) as daysAttended,"
					+ " e.baseSal + (1500 * COALESCE(COUNT(a.date), 0)) as salaryDue"
					+ " FROM emp e"
					+ " LEFT JOIN dept d ON d.deptNo = e.deptNo"
					+ " LEFT JOIN attendance a ON a.empNo = e.empNo"
					+ " GROUP BY e.empNo, e.empName, d.name, e.baseSal;");
			while(rs.next()) {
				atten.add(new attendance(rs.getInt("empNo"), rs.getString("empName"), rs.getInt("daysAttended"), rs.getString("deptName"), rs.getDouble("salaryDue")));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
		return Arrays.copyOf(atten.toArray(), atten.size(), attendance[].class);
	}

	public void modifyAttendance(String empNo) {
		try {
			connect();
			PreparedStatement delAb = conn.prepareStatement("DELETE FROM absent where empNo = ? AND date = ?;");
			delAb.setString(1, empNo);
			delAb.setString(2, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			delAb.execute();
			PreparedStatement ma = conn.prepareStatement("INSERT into attendance values(?, ?);");
			ma.setString(1, empNo);
			ma.setString(2, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			ma.execute();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
	}
	
	public void modifyAbsence(String empNo) {
		try {
			connect();
			PreparedStatement delAb = conn.prepareStatement("DELETE FROM attendance where empNo = ? AND date = ?;");
			delAb.setString(1, empNo);
			delAb.setString(2, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			delAb.execute();
			PreparedStatement ma = conn.prepareStatement("INSERT into absent values(?, ?);");
			ma.setString(1, empNo);
			ma.setString(2, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
			ma.execute();
		}catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
	}
	
	public showAtAb[] getAllAttendance() {
		ArrayList<showAtAb> at = new ArrayList<showAtAb>();
		try {
			connect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT a.empNo, e.empName, d.name AS deptName, a.date\n"
					+ "FROM attendance a\n"
					+ "INNER JOIN emp e ON a.empNo = e.empNo\n"
					+ "INNER JOIN dept d ON e.deptNo = d.deptNo  -- Join with dept table\n"
					+ "ORDER BY a.date ASC;\n"
					+ "");
			while(rs.next()) {
				at.add(new showAtAb(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
		return Arrays.copyOf(at.toArray(), at.size(), showAtAb[].class);
	}
	
	public showAtAb[] getAllAbsent() {
		ArrayList<showAtAb> at = new ArrayList<showAtAb>();
		try {
			connect();
			ResultSet rs = conn.createStatement().executeQuery("SELECT a.empNo, e.empName, d.name AS deptName, a.date\n"
					+ "FROM absent a\n"
					+ "INNER JOIN emp e ON a.empNo = e.empNo\n"
					+ "INNER JOIN dept d ON e.deptNo = d.deptNo  -- Join with dept table\n"
					+ "ORDER BY a.date ASC;\n"
					+ "");
			while(rs.next()) {
				at.add(new showAtAb(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
			}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Error: "+e, "Error!", JOptionPane.ERROR_MESSAGE);
		}finally {
			close();
		}
		return Arrays.copyOf(at.toArray(), at.size(), showAtAb[].class);
	}
	
}



