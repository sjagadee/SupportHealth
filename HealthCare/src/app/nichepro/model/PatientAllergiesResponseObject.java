package app.nichepro.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PatientAllergiesResponseObject extends BaseResponseObject {

	@SerializedName("partyName")
	public String partyName;
	@SerializedName("patientId")
	public String patientId;
	@SerializedName("codingSystem")
	public String codingSystem;
	@SerializedName("fromDate")
	public String fromDate;
	@SerializedName("medicationId")
	public String medicationId;
	@SerializedName("lastUpdatedStamp")
	public String lastUpdatedStamp;
	@SerializedName("lastUpdatedTxStamp")
	public String lastUpdatedTxStamp;
	@SerializedName("createdStamp")
	public String createdStamp;
	@SerializedName("createdTxStamp")
	public String createdTxStamp;
	@SerializedName("value")
	public String value;
	@SerializedName("medicationName")
	public String medicationName;
	@SerializedName("version")
	public String version;
	@SerializedName("result")
	public String result;
	@SerializedName("type")
	public String type;
	@SerializedName("description")
	public String desscription;
	

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
