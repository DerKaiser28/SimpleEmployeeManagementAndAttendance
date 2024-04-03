package objects;

public class attendance implements TableCompat{
	private int empNo;
	private String name, department;
	private int daysPresent;
	private double salaryDue;
	
	public attendance(int en, String n, int dp, String i, double sd) {
		setEmpNo(en);
		setName(n);
		setDaysPresent(dp);
		setSalaryDue(sd);
		setDepartment(i);
	}
	
	public int getEmpNo() {
		return empNo;
	}
	
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getDaysPresent() {
		return daysPresent;
	}

	public void setDaysPresent(int daysPresent) {
		this.daysPresent = daysPresent;
	}

	public double getSalaryDue() {
		return salaryDue;
	}

	public void setSalaryDue(double salaryDue) {
		this.salaryDue = salaryDue;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String[] getTableRow() {
		String[] row = new String[5];
		row[0] = String.valueOf(getEmpNo());
		row[1] = getName();
		row[2] = getDepartment();
		row[3] = String.valueOf(getDaysPresent());
		row[4] = "$"+String.valueOf(getSalaryDue());
		return row;
	}
}
