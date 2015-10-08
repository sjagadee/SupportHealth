
package app.nichepro.model;

import android.os.Parcel;

/**
 * The Class BaseResponseObject.
 * Parcelable base object custom data objects that can passed around the activities
 * through intents
 */
public class EventsResponseObject extends BaseResponseObject {

	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	
	private String name;
	private String date;
	private String location;
	private String contact;
	

	
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	

	

}
