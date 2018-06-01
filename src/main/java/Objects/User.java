package Objects;

import java.sql.Date;

public class User {
	private int userId;
	private String userType;
	private String firstName;
	private String lastname;
	private int phonenumber;
	private String password;
	private String salt;
	private String status;
	private int addressIdFk;
	private Date dateOfBirth;
	private String photo;
	private String username;
	
	public User(int userID, String userType, String firstName, String lastName, int phonenumber, String password, String salt, String status,
			int adresIDFK, String photo, Date dateOfBirth, String userName) {

		this.userId = userID;
		this.userType = userType;
		this.firstName = firstName;
		this.lastname = lastName;
		this.phonenumber = phonenumber;
		this.password = password;
		this.salt = salt;
		this.setStatus(status);
		this.addressIdFk = adresIDFK;
		this.setPhoto(photo);
		this.dateOfBirth = dateOfBirth;
		this.setUserName(userName);
	}
	
	public User(String userType, String firstName, String lastName, int phonenumber, String password, String salt, String status,
			int adresIDFK, String photo, Date dateOfBirth, String userName) {

		this.userType = userType;
		this.firstName = firstName;
		this.lastname = lastName;
		this.phonenumber = phonenumber;
		this.password = password;
		this.salt = salt;
		this.setStatus(status);
		this.addressIdFk = adresIDFK;
		this.setPhoto(photo);
		this.dateOfBirth = dateOfBirth;
		this.setUserName(userName);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirsName(String firstName) {
		this.firstName = firstName;
	}

	public int getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (salt != null){
			this.password = password;
		}else{
			salt = "";
		}
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		if (salt != null){
			this.salt = salt;
		}else{
			this.salt = "";
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if (status != null){
			this.status = status;
		}else{
			this.status = "";
		}
	}

	public int getAddressIdFk() {
		return addressIdFk;
	}

	public void setAddressIdFk(int addressIdFk) {
		this.addressIdFk = addressIdFk;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		if (photo != null){
			this.photo = photo;
		}else{
			this.photo = "";
		}
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String username) {
		if (username != null){
			this.username = username;
		}else{
			this.username = "";
		}
	}
}
