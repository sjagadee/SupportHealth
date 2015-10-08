package app.nichepro.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class PatientNoteResponseObject extends BaseResponseObject {

	public boolean isSelected;
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@SerializedName("noteId")
	public String noteId;
	@SerializedName("noteName")
	public String noteName;
	@SerializedName("noteInfo")
	public String noteInfo;

	@SerializedName("noteDateTime")
	public String noteDateTime;
	@SerializedName("lastUpdatedStamp")
	public String lastUpdatedStamp;
	@SerializedName("lastUpdatedTxStamp")
	public String lastUpdatedTxStamp;
	@SerializedName("createdStamp")
	public String createdStamp;
	@SerializedName("createdTxStamp")
	public String createdTxStamp;

	@SerializedName("partyId")
	public String partyId;

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
