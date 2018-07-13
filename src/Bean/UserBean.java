package Bean;

/**
 * Start
 * 实体类，用于存储user的相关信息
 * @author 宁志豪
 **/
public class UserBean {
	private int UserId;
	private String UserName;
	private String Sex;
	private int Age;
	private String Profession;
	private String Record;
	private String Password;
	private String Description;
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public String getProfession() {
		return Profession;
	}
	public void setProfession(String profession) {
		Profession = profession;
	}
	public int getAge() {
		return Age;
	}
	public void setAge(int age) {
		Age = age;
	}
	public String getRecord() {
		return Record;
	}
	public void setRecord(String record) {
		Record = record;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	
	
}
/**
 * END
 * @author 宁志豪
 */