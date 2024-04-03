package objects;

public class emp implements TableCompat{
	private int empNo;
	private String empName;
	private int deptNo;
	private double sal;
	
	public emp(int en, String em, int dn, double s) {
		setEmpNo(en);
		setEmpName(em);
		setDeptNo(dn);
		setSal(s);
	}
	
	public int getEmpNo() {
		return empNo;
	}
	
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	
	public String getEmpName() {
		return empName;
	}
	
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}
	
	public String[] getTableRow() {
		String[] row = new String[4];
		row[0] = String.valueOf(getEmpNo());
		row[1] = getEmpName();
		row[2] = String.valueOf(getDeptNo());
		row[3] = String.valueOf(getSal());
		return row;
	}
}
