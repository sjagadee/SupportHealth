package app.nichepro.fragmenttabpatient.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientDiagnosisResponseObject;

public class PatientDiagnosisListAdapter extends
	ArrayAdapter<PatientDiagnosisResponseObject> {
LayoutInflater minflater;

static class ViewHolder {

	/** The note view. */
	TextView complaint;

	/** The date name. */
	TextView date;

	/**
	 * Reset the content of cell to remove the flickering effect
	 */
	public void reset() {

	}
}

public PatientDiagnosisListAdapter(Context context,
		LayoutInflater inflater, int textViewResourceId) {
	super(context, textViewResourceId);
	minflater = inflater;

}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;
	if (convertView == null) {
		convertView = minflater
				.inflate(R.layout.diagnosis_list_cell, null);
		holder = new ViewHolder();

		holder.complaint = ((TextView) convertView
				.findViewById(R.id.complaint));
		holder.date = ((TextView) convertView
				.findViewById(R.id.date));
	
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}

	PatientDiagnosisResponseObject pmro = getItem(position);

	String[] d = null;
	if (pmro.createdStamp != null && !pmro.createdStamp.isEmpty()) {
		d = pmro.createdStamp.split("T");
	}

	if (d != null && d.length > 0) {
		holder.date.setText(d[0]);
	}

	holder.complaint.setText(pmro.description);

	return convertView;
}
}