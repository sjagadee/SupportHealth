package app.nichepro.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PatientResponseObject extends BaseResponseObject {

	@SerializedName("partyId")
	public String partyId;
	@SerializedName("roleTypeId")
	public String roleTypeId;
	@SerializedName("salutation")
	public String salutation;
	@SerializedName("firstName")
	public String firstName;
	@SerializedName("middleName")
	public String middleName;
	@SerializedName("lastName")
	public String lastName;
	@SerializedName("personalTitle")
	public String personalTitle;
	@SerializedName("suffix")
	public String suffix;
	@SerializedName("nickname")
	public String nickname;
	@SerializedName("firstNameLocal")
	public String firstNameLocal;
	@SerializedName("middleNameLocal")
	public String middleNameLocal;
	@SerializedName("lastNameLocal")
	public String lastNameLocal;
	@SerializedName("otherLocal")
	public String otherLocal;
	@SerializedName("memberId")
	public String memberId;
	@SerializedName("gender")
	public String gender;
	@SerializedName("birthDate")
	public String birthDate;
	@SerializedName("deceasedDate")
	public String deceasedDate;
	@SerializedName("height")
	public String height;
	@SerializedName("weight")
	public String weight;
	@SerializedName("mothersMaidenName")
	public String mothersMaidenName;
	@SerializedName("maritalStatus")
	public String maritalStatus;
	@SerializedName("socialSecurityNumber")
	public String socialSecurityNumber;
	@SerializedName("passportNumber")
	public String passportNumber;

	@SerializedName("passportExpireDate")
	public String passportExpireDate;
	@SerializedName("totalYearsWorkExperience")
	public String totalYearsWorkExperience;
	@SerializedName("comments")
	public String comments;
	@SerializedName("employmentStatusEnumId")
	public String employmentStatusEnumId;
	@SerializedName("residenceStatusEnumId")
	public String residenceStatusEnumId;
	
	@SerializedName("occupation")
	public String occupation;
	@SerializedName("yearsWithEmployer")
	public String yearsWithEmployer;
	@SerializedName("monthsWithEmployer")
	public String monthsWithEmployer;
	@SerializedName("existingCustomer")
	public String existingCustomer;
	@SerializedName("cardId")
	public String cardId;
	@SerializedName("mobileNo")
	public String mobileNo;
	@SerializedName("specialization")
	public String specialization;
	@SerializedName("city")
	public String city;
	@SerializedName("state")
	public String state;
	@SerializedName("pinCode")
	public String pinCode;
	@SerializedName("lastUpdatedStamp")
	public String lastUpdatedStamp;
	@SerializedName("lastUpdatedTxStamp")
	public String lastUpdatedTxStamp;
	@SerializedName("createdStamp")
	public String createdStamp;
	@SerializedName("createdTxStamp")
	public String createdTxStamp;
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

}
