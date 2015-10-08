package app.nichepro.fragmenttabpatient.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientVitalStatResponseObject;

public class VitalAdapter extends ArrayAdapter<PatientVitalStatResponseObject> {
	LayoutInflater minflater;

	BaseFragment mFragment;

	static class ViewHolder {

		TextView height;

		TextView weight;

		TextView bloodPressureDia;

		TextView bloodPressureSys;
		TextView pulseRate;
		TextView respirationRate;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public VitalAdapter(BaseFragment fragment, Context context,
			LayoutInflater inflater, int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
		mFragment = fragment;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.vital_data_cell, null);
			holder = new ViewHolder();

			holder.height = ((TextView) convertView.findViewById(R.id.height));

			holder.weight = ((TextView) convertView.findViewById(R.id.weight));

			holder.bloodPressureDia = ((TextView) convertView
					.findViewById(R.id.bpDia));

			holder.bloodPressureSys = ((TextView) convertView
					.findViewById(R.id.bpSys));
			holder.pulseRate = ((TextView) convertView.findViewById(R.id.pulse));

			holder.respirationRate = ((TextView) convertView
					.findViewById(R.id.respiration));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PatientVitalStatResponseObject pmro = getItem(position);

		holder.height.setText(pmro.height);
		holder.bloodPressureSys.setText(pmro.systolicBloodPressure);
		holder.weight.setText(pmro.weight);
		holder.pulseRate.setText(pmro.pulseRate);
		holder.respirationRate.setText(pmro.respirationRate);
		holder.bloodPressureDia.setText(pmro.diastolicBloodPressure);
		return convertView;
	}

}
