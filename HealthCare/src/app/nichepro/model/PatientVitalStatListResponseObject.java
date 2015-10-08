package app.nichepro.model;

import java.util.List;

import android.os.Parcel;

public class PatientVitalStatListResponseObject extends BaseResponseObject{
	
	public List<PatientVitalStatResponseObject> VITALSTATS_LIST;

	
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
