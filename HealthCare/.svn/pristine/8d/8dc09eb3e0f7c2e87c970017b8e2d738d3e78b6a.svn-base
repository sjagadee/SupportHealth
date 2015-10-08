package app.nichepro.fragmenttabdoctor.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientAppointmentResponseObject;

/**
 * A simple implementation of list adapter.
 */
public class DoctorAppointmentListAdapter extends
		ArrayAdapter<PatientAppointmentResponseObject> {
	LayoutInflater minflater;

	static class ViewHolder {

		TextView timeText;

		TextView nameText;

		TextView patientIdText;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public DoctorAppointmentListAdapter(Context context,
			LayoutInflater inflater, int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = minflater.inflate(R.layout.doctor_home_list_cell,
					null);
			holder = new ViewHolder();

			holder.nameText = ((TextView) convertView.findViewById(R.id.name));
			holder.patientIdText = ((TextView) convertView
					.findViewById(R.id.patientid));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PatientAppointmentResponseObject paro = getItem(position);

		holder.nameText.setText(paro.patientName);
		holder.patientIdText.setText(paro.patientId);
		return convertView;
	}

}