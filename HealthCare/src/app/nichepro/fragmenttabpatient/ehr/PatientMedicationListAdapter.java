package app.nichepro.fragmenttabpatient.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientMedicationResponseObject;
import app.nichepro.util.UpdateUiFromAdapterListener;

/**
 * A simple implementation of list adapter.
 */
class PatientMedicationListAdapter extends
		ArrayAdapter<PatientMedicationResponseObject> implements
		OnClickListener {
	LayoutInflater minflater;
	UpdateUiFromAdapterListener mUpdateUI;
	boolean mIsFromDoctor;

	static class ViewHolder {

		/** The note view. */
		TextView medicineText;

		TextView quantityText;

		/** The date name. */
		TextView startDateText;

		TextView indicationText;
		TextView indication;

		TextView statusText;

		Button setReminder;

		/**
		 * Reset the content of cell to remove the flickering effect
		 */
		public void reset() {

		}
	}

	public PatientMedicationListAdapter(Context context,
			LayoutInflater inflater, int textViewResourceId,
			UpdateUiFromAdapterListener updateUI, boolean isFromDoctor) {
		super(context, textViewResourceId);
		minflater = inflater;
		mUpdateUI = updateUI;
		mIsFromDoctor = isFromDoctor;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = minflater
					.inflate(R.layout.medication_list_cell, null);
			holder = new ViewHolder();

			holder.medicineText = ((TextView) convertView
					.findViewById(R.id.medicine));

			holder.quantityText = ((TextView) convertView
					.findViewById(R.id.quantity));
			holder.startDateText = ((TextView) convertView
					.findViewById(R.id.startdate));
			holder.statusText = ((TextView) convertView
					.findViewById(R.id.status));
			holder.setReminder = (Button) convertView
					.findViewById(R.id.btnSetReminder);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		PatientMedicationResponseObject pmro = getItem(position);

		String[] d = null;
		if (pmro.fromDate != null && !pmro.fromDate.isEmpty()) {
			d = pmro.fromDate.split("T");
		}

		if (d != null && d.length > 0) {
			holder.startDateText.setText(d[0]);
		}

		holder.medicineText.setText(pmro.medicationName);
		holder.quantityText.setText(pmro.strengthValue);
		holder.statusText.setText(pmro.status);

		if (mIsFromDoctor) {
			holder.setReminder.setVisibility(View.GONE);
		} else {
			holder.setReminder.setTag(position);

			holder.setReminder.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int position = (Integer) v.getTag();
					mUpdateUI.updateUI(position);
					// mBaseInstance.mActivity.pushFragments(Constants.TAB_EHR,
					// new CreateAlarmFragment(), true, true);
				}
			});

		}

		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
