package app.nichepro.model;


import android.os.Parcel;

/**
 * The Class BaseResponseObject.
 * Parcelable base object custom data objects that can passed around the activities
 * through intents
 */
public class SponsoredResponseObject extends BaseResponseObject {

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	
	private String name;
	private String date;
	private String sponsored_date;
	private String cancer_type;
	

	
	public int describeContents() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	public void writeToParcel(Parcel dest, int flags) {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSponsored_date() {
		return sponsored_date;
	}

	public void setSponsored_date(String sponsored_date) {
		this.sponsored_date = sponsored_date;
	}

	public String getCancer_type() {
		return cancer_type;
	}

	public void setCancer_type(String cancer_type) {
		this.cancer_type = cancer_type;
	}

	
	

	

}
