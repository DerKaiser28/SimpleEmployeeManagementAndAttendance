package objects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class showAtAb implements TableCompat {
	
	private int empNo;
	private String dept;
	private String name;
	private String date;
	
	public showAtAb(int empNo, String dept, String name, String date){
		setEmpNo(empNo);
		setDept(dept);
		setName(name);
		setDate(date);
	}

	public int getEmpNo() {
		return empNo;
	}


	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).format(DateTimeFormatter.ISO_LOCAL_DATE);
	}
	
	public String[] getTableRow() {
		String[] row = new String[4];
		row[0] = String.valueOf(getEmpNo());
		row[1] = getName();
		row[2] = getDept();
		row[3] = getDate();
		return row;
	}
}
