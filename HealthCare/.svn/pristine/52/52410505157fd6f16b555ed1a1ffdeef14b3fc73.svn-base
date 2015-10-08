package app.nichepro.fragmenttabdoctor.appointments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import app.nichepro.HealthCareApp;
import app.nichepro.activities.healthcare.AlarmReceiverActivity;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.animation.AnimationFactory;
import app.nichepro.animation.AnimationFactory.FlipDirection;
import app.nichepro.fragmenttabdoctor.home.DoctorAppointmentAdapter;
import app.nichepro.fragmenttabpatient.ehr.PatientEHRFragment;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.PatientAppointmentListResponseObject;
import app.nichepro.model.PatientAppointmentResponseObject;
import app.nichepro.model.db.DoctroAlarmData;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.LoginType;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;
import app.nichepro.util.db.DbUtils;

public class DoctorAppointmentsFragment extends BaseFragment implements
		OnClickListener, ResponseParserListener, UpdateUiFromAdapterListener {

	PatientAppointmentListResponseObject mAppointmentData;
	PatientAppointmentListResponseObject mPastAppointmentData;
	private WebRequestTask ehrRequestTask;
	private ResponseMesssagType msgType;
	private Button btnUpEvents;
	private Button btnPrevEvents;
	private Boolean isToggle;
	private ViewAnimator viewAnimator;
	private ListView mUpcomingListView;
	private ListView mPastListView;
	ArrayAdapter<PatientAppointmentResponseObject> mUpcominglistAdapter;
	ArrayAdapter<PatientAppointmentResponseObject> mPastlistAdapter;
	private HealthCareApp myApp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		myApp = (HealthCareApp) getActivity().getApplication();
		View view = inflater
				.inflate(R.layout.doctor_appointments_tab_first_screen,
						container, false);

		TextView nameTxt = (TextView) view.findViewById(R.id.patientName);
		nameTxt.setText(myApp.getLoggedInUser().LOGIN_DETAILS.lastName + " "
				+ myApp.getLoggedInUser().LOGIN_DETAILS.firstName);

		mUpcomingListView = (ListView) view.findViewById(R.id.appointmentlist);

		mPastListView = (ListView) view
				.findViewById(R.id.pastpatientappointmentlist);
		viewAnimator = (ViewAnimator) view.findViewById(R.id.viewFlipper);

		btnUpEvents = (Button) view.findViewById(R.id.up_appointment);
		btnPrevEvents = (Button) view.findViewById(R.id.past_appointment);
		btnUpEvents.setSelected(true);
		isToggle = true;
		btnUpEvents.setOnClickListener(this);
		btnPrevEvents.setOnClickListener(this);
		// Creating the list adapter and populating the list
		mUpcominglistAdapter = new DoctorAppointmentAdapter(
				inflater.getContext(), inflater, R.layout.doctor_app_list_cell,
				this, true);

		mPastlistAdapter = new DoctorAppointmentAdapter(inflater.getContext(),
				inflater, R.layout.doctor_app_list_cell, this, true);
		requestForUpApp();
		return view;
	}

	void populatePast() {
		for (PatientAppointmentResponseObject element : mPastAppointmentData.APPOINTMENT_LIST) {
			mPastlistAdapter.add(element);

		}
		mPastListView.setAdapter(mPastlistAdapter);
		mPastListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {

						PatientAppointmentResponseObject element = mPastAppointmentData.APPOINTMENT_LIST
								.get(position);

						PatientEHRFragment pef = new PatientEHRFragment(
								LoginType.Physician,
								Constants.TAB_APPOINTMENTS, element.patientId);
						// pef.mTabName = Constants.TAB_APPOINTMENTS;
						// pef.mPatientId = element.patientId;
						mDoctorActivity.pushFragments(
								Constants.TAB_APPOINTMENTS, pef, true, true);
					}
				});
	}

	void populateUpcoming() {
		List<DoctroAlarmData> alarmList = DbUtils
				.getDoctorAppAlarmList(getActivity());

		for (PatientAppointmentResponseObject element : mAppointmentData.APPOINTMENT_LIST) {
			if (element.appointmentId != null
					&& element.appointmentId.length() > 0) {
				int appid = Integer.parseInt(element.appointmentId);
				if (alarmList != null && alarmList.size() > 0) {
					for (DoctroAlarmData doctroAlarmData : alarmList) {
						if (doctroAlarmData.getAppointmentid() == appid) {
							element.setAlarmAlreadySet(true);

						} else {
							element.setAlarmAlreadySet(false);
						}
					}
				}
			}

			mUpcominglistAdapter.add(element);

		}
		mUpcomingListView.setAdapter(mUpcominglistAdapter);
		mUpcomingListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {
						PatientAppointmentResponseObject element = mAppointmentData.APPOINTMENT_LIST
								.get(position);

						PatientEHRFragment pef = new PatientEHRFragment(
								LoginType.Physician,
								Constants.TAB_APPOINTMENTS, element.patientId);
						// pef.isFromDoctor = true;
						// pef.mTabName = Constants.TAB_APPOINTMENTS;
						// pef.mPatientId = element.patientId;
						mDoctorActivity.pushFragments(
								Constants.TAB_APPOINTMENTS, pef, true, true);

					}
				});

	}

	public void requestForPastApp() {
		ehrRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.APPOINTMENT_LIST), getActivity(),
				this);

		ehrRequestTask.AddParam(Constants.PARAM_patientId, myApp
				.getLoggedInUser().getPartyId());

		ehrRequestTask.AddParam(Constants.PARAM_partyType,
				Constants.VALUE_DOCTOR);
		ehrRequestTask
				.AddParam(Constants.PARAM_dateValue, Constants.VALUE_PAST);
		ehrRequestTask.execute();
	}

	public void requestForUpApp() {
		ehrRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.APPOINTMENT_LIST), getActivity(),
				this);

		ehrRequestTask.AddParam(Constants.PARAM_doctorId, myApp
				.getLoggedInUser().getPartyId());

		ehrRequestTask.AddParam(Constants.PARAM_partyType,
				Constants.VALUE_DOCTOR);
		ehrRequestTask.AddParam(Constants.PARAM_dateValue,
				Constants.VALUE_UPCOMING);
		ehrRequestTask.execute();
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.AppointmentDoctorListMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				if (isToggle == true) {
					PatientAppointmentListResponseObject palro = (PatientAppointmentListResponseObject) items
							.get(0);
					if (palro.APPOINTMENT_LIST.size() > 0) {
						this.mAppointmentData = palro;
						populateUpcoming();

					} else {
						UIUtilities.showConfirmationAlert(getActivity(),
								R.string.dialog_title_information,
								"No Upcoming Appointment found",
								R.string.dialog_button_Ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

									}
								});
					}

				} else {

					PatientAppointmentListResponseObject palro = (PatientAppointmentListResponseObject) items
							.get(0);
					if (palro.APPOINTMENT_LIST.size() > 0) {
						this.mPastAppointmentData = palro;
						populatePast();

					} else {
						UIUtilities.showConfirmationAlert(getActivity(),
								R.string.dialog_title_information,
								"No Past Appointment found",
								R.string.dialog_button_Ok,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {

									}
								});
					}

				}

			} else {

				if (isToggle == true) {
					UIUtilities.showConfirmationAlert(getActivity(),
							R.string.dialog_title_information,
							"No Upcoming Appointment found",
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
				} else {
					UIUtilities.showConfirmationAlert(getActivity(),
							R.string.dialog_title_information,
							"No Past Appointment found",
							R.string.dialog_button_Ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
				}

			}
		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(getActivity(),
						ero.getErrorText());
			} else
				UIUtilities.showServerError(getActivity());

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(getActivity());
		} else {
			UIUtilities.showServerError(getActivity());
		}
	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(getActivity(), ex.getMessage());

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.up_appointment:
			if (isToggle == false) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnUpEvents.setTextColor(Color.BLACK);
				btnPrevEvents.setTextColor(Color.WHITE);
				btnUpEvents.setSelected(true);
				btnPrevEvents.setSelected(false);
				if (this.mAppointmentData == null) {
					requestForUpApp();
				}
			}
			isToggle = true;
			break;
		case R.id.past_appointment:
			if (isToggle == true) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnUpEvents.setTextColor(Color.WHITE);
				btnPrevEvents.setTextColor(Color.BLACK);
				btnUpEvents.setSelected(false);
				btnPrevEvents.setSelected(true);
				if (this.mPastAppointmentData == null) {
					requestForPastApp();
				}
			}
			isToggle = false;
			break;
		}

	}

	@Override
	public void updateUI(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(String message, int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(int position) {
		// TODO Auto-generated method stub
		PatientAppointmentResponseObject paro = mAppointmentData.APPOINTMENT_LIST
				.get(position);
		int appid = Integer.parseInt(paro.appointmentId);

		if (paro.isAlarmAlreadySet()) {
			List<DoctroAlarmData> alarmList = DbUtils
					.getDoctorAppAlarmList(getActivity());
			DoctroAlarmData alarmData = null;
			if (alarmList != null && alarmList.size() > 0) {
				for (DoctroAlarmData doctroAlarmData : alarmList) {
					if (doctroAlarmData.getAppointmentid() == appid) {
						alarmData = doctroAlarmData;
						break;
					}
				}
			}
			DbUtils.removeDoctorAppAlarm(getActivity(), alarmData);
			paro.setAlarmAlreadySet(false);

		} else {
			Bundle bundle = new Bundle();
			bundle.putString("appid", paro.appointmentId);
			bundle.putString("actorType", LoginType.Physician.toString());

			bundle.putString("alarm", "Appointment for" + paro.patientName
					+ "  Reason" + paro.description);
			Intent intent = new Intent(getActivity(),
					AlarmReceiverActivity.class);

			intent.putExtras(bundle);
			PendingIntent operation = PendingIntent.getActivity(getActivity(),
					appid, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			forday(paro.createdDate, operation);
			DoctroAlarmData alarm = new DoctroAlarmData();
			alarm.setAppointmentid(appid);
			DbUtils.addDoctorAppAlarm(getActivity(), alarm);
			paro.setAlarmAlreadySet(true);

		}

	}

	public void forday(String date, PendingIntent pendingIntent) {

		int mm = 0;
		int dd = 0;
		int yyyy = 0;
		int hr = 0;
		int min = 0;

		if (date != null && date.length() > 0) {
			String[] appointmentDate = date.split("T");
			if (appointmentDate != null && appointmentDate.length > 0) {
				String[] d = (appointmentDate[0]).split("-");
				mm = Integer.parseInt(d[0]);
				dd = Integer.parseInt(d[1]);
				yyyy = Integer.parseInt(d[2]);

			}

			if (appointmentDate != null && appointmentDate.length > 1) {
				String[] time = (appointmentDate[1]).split(":");
				hr = Integer.parseInt(time[0]);
				min = Integer.parseInt(time[1]);
			}
		}

		Calendar setCal = Calendar.getInstance();
		Calendar curCal = Calendar.getInstance();

		setCal.set(Calendar.YEAR, yyyy);
		setCal.set(Calendar.MONTH, mm - 1);
		setCal.set(Calendar.DAY_OF_MONTH, dd);
		setCal.set(Calendar.HOUR_OF_DAY, hr);
		setCal.set(Calendar.MINUTE, min);

		long milis = setCal.getTimeInMillis();
		AlarmManager alarmManager = (AlarmManager) getActivity()
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, milis, pendingIntent);

		Log.i("Calender", "Week: " + setCal.get(Calendar.DAY_OF_WEEK));
		Log.i("Calender", "Year: " + setCal.get(Calendar.YEAR));
		Log.i("Calender", "Month: " + setCal.get(Calendar.MONTH));
		Log.i("Calender", "Day of Month: " + setCal.get(Calendar.DAY_OF_MONTH));
		Log.i("Calender", "Hour of Day: " + setCal.get(Calendar.HOUR_OF_DAY));
		Log.i("Calender", "Minut: " + setCal.get(Calendar.MINUTE));

		setCal.set(Calendar.DAY_OF_WEEK, curCal.get(Calendar.DAY_OF_WEEK));
		setCal.set(Calendar.YEAR, curCal.get(Calendar.YEAR));
		setCal.set(Calendar.MONTH, curCal.get(Calendar.MONTH));
		setCal.set(Calendar.DAY_OF_MONTH, curCal.get(Calendar.DAY_OF_MONTH));
		setCal.set(Calendar.HOUR_OF_DAY, curCal.get(Calendar.HOUR_OF_DAY));
		setCal.set(Calendar.MINUTE, curCal.get(Calendar.MINUTE));

	}

}
