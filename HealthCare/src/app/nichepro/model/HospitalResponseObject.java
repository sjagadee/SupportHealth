package app.nichepro.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

public class HospitalResponseObject extends BaseResponseObject {

	@SerializedName("facilityId")
	public String facilityId;
	@SerializedName("facilityTypeId")
	public String facilityTypeId;
	@SerializedName("parentFacilityId")
	public String parentFacilityId;
	@SerializedName("ownerPartyId")
	public String ownerPartyId;
	@SerializedName("facilityName")
	public String facilityName;
	@SerializedName("facilitySize")
	public String facilitySize;
	@SerializedName("openedDate")
	public String openedDate;
	@SerializedName("closedDate")
	public String closedDate;
	@SerializedName("minLevelInventory")
	public String minLevelInventory;
	@SerializedName("address1")
	public String address1;
	@SerializedName("address2")
	public String address2;
	@SerializedName("directions")
	public String directions;
	@SerializedName("city")
	public String city;
	@SerializedName("postalCode")
	public String postalCode;
	@SerializedName("postalCodeExt")
	public String postalCodeExt;
	@SerializedName("lattitudeId")
	public String lattitudeId;
	@SerializedName("longitudeId")
	public String longitudeId;
	@SerializedName("geoPointId")
	public String geoPointId;
	@SerializedName("description")
	public String description;
	@SerializedName("countryGeoId")
	public String countryGeoId;
	@SerializedName("stateProvinceGeoId")
	public String stateProvinceGeoId;
	@SerializedName("postalCodeGeoId")
	public String postalCodeGeoId;
	@SerializedName("webSiteId")
	public String webSiteId;
	@SerializedName("telephoneNo")
	public String telephoneNo;
	@SerializedName("state")
	public String state;
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
