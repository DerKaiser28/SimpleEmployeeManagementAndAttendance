package objects;

public class dept implements TableCompat{
	private String name;
	private int deptNo;
	
	public dept(String name, int deptNo){
		setName(name);
		setDeptNo(deptNo);
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
		
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getDeptNo() {
		return this.deptNo;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return getName();
	}
	
	public String[] getTableRow() {
		String[] row = new String[2];
		row[0] = String.valueOf(getDeptNo());
		row[1] = getName();
		return row;
	}
}
