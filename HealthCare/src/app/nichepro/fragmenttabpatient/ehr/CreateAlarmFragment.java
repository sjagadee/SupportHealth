package app.nichepro.fragmenttabpatient.ehr;

import java.util.List;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import app.nichepro.activities.healthcare.AlarmReceiverActivity;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.fragmenttabpatient.info.PatientAlarmFragment;
import app.nichepro.model.db.AlarmData;
import app.nichepro.util.Constants;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.db.DbUtils;

public class CreateAlarmFragment extends BaseFragment implements
		OnClickListener {

	private RelativeLayout createAlarm;
	private ListView mAlarmListView;
	ArrayAdapter<AlarmData> listAdapter;
	List<AlarmData> alarmListData = null;
	private Button delete;
	private LayoutInflater mInflater;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.create_alarm_list, container,
				false);
		mInflater = inflater;
		delete = (Button) view.findViewById(R.id.bDelete);
		delete.setOnClickListener(this);
		mAlarmListView = (ListView) view.findViewById(R.id.alarmList);
		createAlarm = (RelativeLayout) view.findViewById(R.id.createAlarm);
		createAlarm.setOnClickListener(this);

		createAlarmList();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		createAlarmList();
	}

	void createAlarmList() {
		listAdapter = new AlarmListAdapter(mInflater.getContext(), mInflater,
				R.layout.alarm_list_cell);
		alarmListData = DbUtils.getAlarmList(getActivity());

		if (alarmListData != null && alarmListData.size() > 0) {
			for (AlarmData alarmData : alarmListData) {
				listAdapter.add(alarmData);
			}
			mAlarmListView.setAdapter(listAdapter);

		} else
			mAlarmListView.setAdapter(listAdapter);

		// Creating an item click listener, to open/close our toolbar for each
		// item
		mAlarmListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent,
							final View view, int position, long id) {
						AlarmData ad = alarmListData.get(position);
						PatientAlarmFragment paf = new PatientAlarmFragment();
						paf.mAlarmData = ad;
						paf.isEditScreen = true;
						mActivity.pushFragments(Constants.TAB_EHR, paf, true,
								true);
					}
				});
	}

	void deleteAlarm() {
		int count = 0;

		for (AlarmData element : alarmListData) {
			if (element.isDeleteAlarm == true) {
				++count;
			}
		}
		if (count > 0) {

			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

			alert.setTitle(R.string.dialog_title_information);
			alert.setMessage("Press delete to remove the alarm!");
			// Set an EditText view to get user input

			alert.setPositiveButton("Delete",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {

							deleteAlarmFromDb();
						}
					});

			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							// Canceled.
						}
					});

			alert.show();

		} else {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"Please select an Alarm", R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bDelete: {
			deleteAlarm();
		}
			break;
		case R.id.createAlarm:
			mActivity.pushFragments(Constants.TAB_EHR,
					new PatientAlarmFragment(), true, true);
		default:
			break;
		}

	}

	public void deleteAlarmFromDb() {

		for (AlarmData element : alarmListData) {
			if (element.isDeleteAlarm == true) {
				Intent intent = new Intent(getActivity(),
						AlarmReceiverActivity.class);

				AlarmManager alarmManager = (AlarmManager) (AlarmManager) getActivity()
						.getSystemService(Context.ALARM_SERVICE);
				if (element.getMondayrequestedcode() != -1) {
					PendingIntent operation = PendingIntent.getActivity(
							getActivity(), element.getMondayrequestedcode(),
							intent, PendingIntent.FLAG_CANCEL_CURRENT);
					alarmManager.cancel(operation);
				}

				if (element.getTuesdayrequestedcode() != -1) {
					PendingIntent operation = PendingIntent.getActivity(
							getActivity(), element.getTuesdayrequestedcode(),
							intent, PendingIntent.FLAG_CANCEL_CURRENT);
					alarmManager.cancel(operation);
				}

				if (element.getWednesdayrequestedcode() != -1) {
					PendingIntent operation = PendingIntent.getActivity(
							getActivity(), element.getWednesdayrequestedcode(),
							intent, PendingIntent.FLAG_CANCEL_CURRENT);
					alarmManager.cancel(operation);
				}

				if (element.getThursdayrequestedcode() != -1) {
					PendingIntent operation = PendingIntent.getActivity(
							getActivity(), element.getThursdayrequestedcode(),
							intent, PendingIntent.FLAG_CANCEL_CURRENT);
					alarmManager.cancel(operation);
				}

				if (element.getFridayrequestedcode() != -1) {
					PendingIntent operation = PendingIntent.getActivity(
							getActivity(), element.getFridayrequestedcode(),
							intent, PendingIntent.FLAG_CANCEL_CURRENT);
					alarmManager.cancel(operation);
				}

				if (element.getSatrudayrequestedcode() != -1) {
					PendingIntent operation = PendingIntent.getActivity(
							getActivity(), element.getSatrudayrequestedcode(),
							intent, PendingIntent.FLAG_CANCEL_CURRENT);
					alarmManager.cancel(operation);
				}

				if (element.getSundayrequestedcode() != -1) {
					PendingIntent operation = PendingIntent.getActivity(
							getActivity(), element.getSundayrequestedcode(),
							intent, PendingIntent.FLAG_CANCEL_CURRENT);
					alarmManager.cancel(operation);
				}

				DbUtils.removeAlarm(getActivity(), element);
			}
		}
		createAlarmList();

	}
}
