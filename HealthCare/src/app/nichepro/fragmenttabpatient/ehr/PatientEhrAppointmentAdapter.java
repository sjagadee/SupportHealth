package app.nichepro.fragmenttabpatient.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientAppointmentResponseObject;

public class PatientEhrAppointmentAdapter extends
		ArrayAdapter<PatientAppointmentResponseObject> {
	LayoutInflater minflater;

	static class ViewHolder {
		TextView dateTxt;
		TextView statusTxt;
		TextView reasonTxt;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public PatientEhrAppointmentAdapter(Context context,
			LayoutInflater inflater, int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = minflater.inflate(R.layout.patient_list_app_cell,
					null);
			holder = new ViewHolder();

			holder.dateTxt = ((TextView) convertView.findViewById(R.id.date));
			holder.statusTxt = ((TextView) convertView
					.findViewById(R.id.status));
			holder.reasonTxt = ((TextView) convertView
					.findViewById(R.id.reason));

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
			holder.dateTxt.setText(d[0]);
		}

		if (d != null && d.length > 1) {
			holder.dateTxt.setText(d[0] + ":" + d[1]);
		}
		holder.reasonTxt.setText(paro.visitTypeId);

		return convertView;
	}
}
