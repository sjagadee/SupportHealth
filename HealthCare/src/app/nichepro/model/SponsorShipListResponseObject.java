package app.nichepro.model;

import java.util.List;

import android.os.Parcel;

public class SponsorShipListResponseObject extends BaseResponseObject{
	
	public List<SponsorShipResponseObject> RESPONSE;

	
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
