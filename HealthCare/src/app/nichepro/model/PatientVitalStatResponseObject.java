package app.nichepro.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PatientVitalStatResponseObject extends BaseResponseObject {

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
	@SerializedName("DiagnosisId")
	public String DiagnosisId;
	@SerializedName("weight")
	public String weight;
	@SerializedName("pulseRate")
	public String pulseRate;
	@SerializedName("height")
	public String height;
	@SerializedName("diastolicBloodPressure")
	public String diastolicBloodPressure;
	@SerializedName("systolicBloodPressure")
	public String systolicBloodPressure;
	@SerializedName("respirationRate")
	public String respirationRate;

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
