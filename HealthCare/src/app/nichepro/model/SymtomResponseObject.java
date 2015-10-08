package app.nichepro.model;

import android.os.Parcel;
import app.nichepro.util.EnumFactory.SymTomType;

import com.google.gson.annotations.SerializedName;

public class SymtomResponseObject extends BaseResponseObject {

	public SymTomType mSymtomType;

	public SymTomType getmSymtomType() {
		return mSymtomType;
	}

	public String getmDescription() {
		return mDescription;
	}

	public void setmDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String mDescription;

	public void setmSymtomType(SymTomType mSymtomType) {
		this.mSymtomType = mSymtomType;
	}

	@SerializedName("patientId")
	public String patientId;
	@SerializedName("symptomId")
	public String symptomId;
	@SerializedName("description")
	public String description;
	@SerializedName("fromDate")
	public String fromDate;
	@SerializedName("lastUpdatedStamp")
	public String lastUpdatedStamp;
	@SerializedName("lastUpdatedTxStamp")
	public String lastUpdatedTxStamp;
	@SerializedName("createdStamp")
	public String createdStamp;
	@SerializedName("createdTxStamp")
	public String createdTxStamp;

	@SerializedName("trackDescription")
	public String trackDescription;

	@SerializedName("severityLevel")
	public String severityLevel;

	public boolean isSelected;

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

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
