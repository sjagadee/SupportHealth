package app.nichepro;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.LocationManager;
import app.nichepro.base.BaseApp;
import app.nichepro.model.LoginResponseObject;
import app.nichepro.model.db.DoctorRegistrationInfo;
import app.nichepro.model.db.UserRegistrationInfo;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.Log;
import app.nichepro.util.WebRequestTask;

/**
 * The Class HealthCareApp. App object for maintaining the global application
 * state.
 */
@ReportsCrashes(formKey = "dDI5cHhBSVc1UkdqZkZwN19kRGI5SGc6MQ")
public class HealthCareApp extends BaseApp{

	/** The TAG. */
	private static String TAG = HealthCareApp.class.getSimpleName();

	private UserRegistrationInfo patientLoginInfo;
	private DoctorRegistrationInfo doctorLoginInfo;

	private String deviceUUID;
	private String deviceMobileNo;
	/** The location manager. */
	private LocationManager locationManager = null;

	/** The Dialog. Hold the reference for the progress dialog */
	public WeakReference<ProgressDialog> Dialog = null;

	/**
	 * The weekly dialog. Hold the reference for the progress dialog as there
	 * are two separate request for weekly ad fetching
	 * */
	public WeakReference<ProgressDialog> weeklyDialog = null;

	/** The waiting. */
	public Boolean waiting = false;

	/** The waiting on pause. */
	public Boolean waitingOnPause = false;

	
	private LoginResponseObject loggedInUser;
	

	
	HashMap<ResponseMesssagType, String> htmlLinkMap = new HashMap<ResponseMesssagType, String>();
	public HashMap<ResponseMesssagType, String> getHtmlLinkMap() {
		return htmlLinkMap;
	}




	public void setHtmlLinkMap(HashMap<ResponseMesssagType, String> htmlLinkMap) {
		this.htmlLinkMap = htmlLinkMap;
	}




	public String getHtmlLinkMap(ResponseMesssagType responsetype) {
		return htmlLinkMap.get(responsetype);
	}

	


	/**
	 * Gets the location manager.
	 * 
	 * @return the location manager
	 */
	public LocationManager getLocationManager() {
		if (locationManager == null)
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		return locationManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		Log.d(TAG, "In OnCreate");
		//ACRA.init(this);
		super.onCreate();
	}
	
	
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onLowMemory()
	 */
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onTerminate() clean up the timer on app end
	 */
	@Override
	public void onTerminate() {

		super.onTerminate();
	}

	public UserRegistrationInfo getPatientLoginInfo() {
		return patientLoginInfo;
	}

	public void setPatientLoginInfo(UserRegistrationInfo patientLoginInfo) {
		this.patientLoginInfo = patientLoginInfo;
	}

	public DoctorRegistrationInfo getDoctorLoginInfo() {
		return doctorLoginInfo;
	}

	public void setDoctorLoginInfo(DoctorRegistrationInfo doctorLoginInfo) {
		this.doctorLoginInfo = doctorLoginInfo;
	}

	public String getDeviceUUID() {
		return deviceUUID;
	}

	public void setDeviceUUID(String deviceUUID) {
		this.deviceUUID = deviceUUID;
	}

	public String getDeviceMobileNo() {
		return deviceMobileNo;
	}

	public void setDeviceMobileNo(String deviceMobileNo) {
		this.deviceMobileNo = deviceMobileNo;
	}

	HashMap<Integer, WeakReference<WebRequestTask>> currentRunningTasksMap = null;

	public void addWebRequestTask(int dialogId, WebRequestTask wrt) {
		if (currentRunningTasksMap == null)
			currentRunningTasksMap = new HashMap<Integer, WeakReference<WebRequestTask>>();

		currentRunningTasksMap.put(Integer.valueOf(dialogId),
				new WeakReference<WebRequestTask>(wrt));
	}

	public void cleanRunningWebRequestTask(int dialogId) {
		if (currentRunningTasksMap != null && !currentRunningTasksMap.isEmpty()) {
			WeakReference<WebRequestTask> wwrt = currentRunningTasksMap
					.get(Integer.valueOf(dialogId));
			if (wwrt != null) {
				WebRequestTask wrt = wwrt.get();
				if (wrt != null) {
					wrt.cancelTask();
					currentRunningTasksMap.remove(Integer.valueOf(dialogId));
				}
			}
		}
	}

	public LoginResponseObject getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(LoginResponseObject loggedInUser) {
		this.loggedInUser = loggedInUser;
	}


	

}
