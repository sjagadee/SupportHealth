package app.nichepro.fragmenttabdoctor.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientAppointmentResponseObject;
import app.nichepro.util.UpdateUiFromAdapterListener;

public class DoctorAppointmentAdapter extends
		ArrayAdapter<PatientAppointmentResponseObject> {
	LayoutInflater minflater;
	UpdateUiFromAdapterListener mUpdate;
	boolean isUpcoming;

	static class ViewHolder {

		TextView nameText;

		TextView patientIdText;

		TextView dateText;

		Button setAlarmBtn;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public DoctorAppointmentAdapter(Context context, LayoutInflater inflater,
			int textViewResourceId, UpdateUiFromAdapterListener update,
			boolean flag) {
		super(context, textViewResourceId);
		minflater = inflater;
		mUpdate = update;
		isUpcoming = flag;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = minflater
					.inflate(R.layout.doctor_app_list_cell, null);
			holder = new ViewHolder();
			holder.dateText = ((TextView) convertView.findViewById(R.id.date));
			holder.nameText = ((TextView) convertView.findViewById(R.id.name));
			holder.patientIdText = ((TextView) convertView
					.findViewById(R.id.patientid));
			holder.setAlarmBtn = (Button) convertView
					.findViewById(R.id.setAlarmbtn);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PatientAppointmentResponseObject paro = getItem(position);
		String[] d = null;
		if (paro.createdDate != null && !paro.createdDate.isEmpty()) {
			d = paro.createdDate.split("T");
		}

		if (d != null && d.length > 0) {
			holder.dateText.setText(d[0]);
		}

		if (d != null && d.length > 1) {
			holder.dateText.setText(d[0] + ":" + d[1]);
		}

		holder.nameText.setText(paro.patientName);
		holder.patientIdText.setText(paro.patientId);
		if (isUpcoming == false) {
			holder.setAlarmBtn.setVisibility(View.GONE);
		} else {
			if (paro.isAlarmAlreadySet) {
				holder.setAlarmBtn.setText("Remove Alarm");
			} else {
				holder.setAlarmBtn.setText("Set Alarm");
			}
			holder.setAlarmBtn.setTag(position);
			holder.setAlarmBtn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int position = (Integer) v.getTag();
					PatientAppointmentResponseObject paro = getItem(position);
					if (paro.isAlarmAlreadySet) {
						((Button) v).setText("Set Alarm");
					} else {
						((Button) v).setText("Remove Alarm");
					}
					mUpdate.updateUI(position);
				}
			});
		}
		return convertView;
	}

}
