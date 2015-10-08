package app.nichepro.model.db;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class UserRegistrationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String SALUTATTION = "salutation";
	public static String USERNAME = "username";
	public static String FIRSTNAME = "firstname";
	public static String MIDDLENAME = "middlename";
	public static String LASTNAME = "lastname";
	public static String DOB = "dob";
	public static String CITY = "city";
	public static String STATE = "state";
	public static String ZIPCODE = "zipcode";
	public static String PHONENUMBER = "phonenumber";
	public static String EMAIL = "email";
	public static String PASSWORD = "password";
	public static String CPASSWORD = "cpassword";
	public static String GENDER = "gender";
	public static String SPECIALIZATION = "specialization";
 	
	
	public static String getGENDER() {
		return GENDER;
	}

	public static void setGENDER(String gENDER) {
		GENDER = gENDER;
	}

	public static String getSPECIALIZATION() {
		return SPECIALIZATION;
	}

	public static void setSPECIALIZATION(String sPECIALIZATION) {
		SPECIALIZATION = sPECIALIZATION;
	}

	@DatabaseField(generatedId = true)
	int id;
	@DatabaseField
	String username;

	public static String getUSERNAME() {
		return USERNAME;
	}

	public static void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	
	public static String getSALUTATTION() {
		return SALUTATTION;
	}

	public static void setSALUTATTION(String sALUTATTION) {
		SALUTATTION = sALUTATTION;
	}


	public static String getFIRSTNAME() {
		return FIRSTNAME;
	}

	public static void setFIRSTNAME(String fIRSTNAME) {
		FIRSTNAME = fIRSTNAME;
	}

	public static String getMIDDLENAME() {
		return MIDDLENAME;
	}

	public static void setMIDDLENAME(String mIDDLENAME) {
		MIDDLENAME = mIDDLENAME;
	}

	public static String getLASTNAME() {
		return LASTNAME;
	}

	public static void setLASTNAME(String lASTNAME) {
		LASTNAME = lASTNAME;
	}

	public static String getSTATE() {
		return STATE;
	}

	public static void setSTATE(String sTATE) {
		STATE = sTATE;
	}

	public static String getZIPCODE() {
		return ZIPCODE;
	}

	public static void setZIPCODE(String zIPCODE) {
		ZIPCODE = zIPCODE;
	}

	public static String getPHONENUMBER() {
		return PHONENUMBER;
	}

	public static void setPHONENUMBER(String pHONENUMBER) {
		PHONENUMBER = pHONENUMBER;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@DatabaseField
	String firstname;
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@DatabaseField
	String gender;
	@DatabaseField
	String specialization;
	@DatabaseField
	String salutation;
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	@DatabaseField
	String middlename;
	@DatabaseField
	String lastname;
	@DatabaseField
	String dob;
	@DatabaseField
	String city;
	@DatabaseField
	String state;
	@DatabaseField
	String zipcode;
	@DatabaseField
	String phonenumber;
	@DatabaseField
	String email;
	@DatabaseField
	String password;
	@DatabaseField
	String cpassword;
	
	@Override
	public String toString() {
		return "UserRegistrationInfo []";
	}

	public UserRegistrationInfo() {
		// needed by ormlite
	}

	public UserRegistrationInfo(String username, String firstname, String lastname, String middlename, String dob,
			String city, String state, String zipcode, String phonenumber, String email, String password, String cpassword, String gender, String specialization, String salutation) {
		
		this.username = username;
		this.firstname = firstname;
		this.salutation = salutation;
		this.specialization = specialization;
		this.gender = gender;
		this.lastname = lastname;
		this.middlename = middlename;
		this.dob = dob;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.phonenumber = phonenumber;
		this.email = email;
		this.password = password;
		this.cpassword = cpassword;
	}
}
