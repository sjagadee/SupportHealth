package app.nichepro.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PartyResponseObject extends BaseResponseObject {

	@SerializedName("partyName")
	public String partyName;
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
	@SerializedName("specialization")
	public String specialization;
	@SerializedName("mobileNo")
	public String mobileNo;
	@SerializedName("city")
	public String city;
	@SerializedName("state")
	public String state;
	@SerializedName("pinCode")
	public String pinCode;
	
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
