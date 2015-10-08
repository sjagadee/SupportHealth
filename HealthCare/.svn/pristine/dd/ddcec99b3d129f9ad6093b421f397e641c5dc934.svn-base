package app.nichepro.fragmenttabpatient.info;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;

/**
 * A simple implementation of list adapter.
 */
public class PatientInfoListAdapter extends ArrayAdapter<String> {
	LayoutInflater minflater;

	public PatientInfoListAdapter(Context context,
			LayoutInflater inflater, int textViewResourceId) {
		super(context, textViewResourceId);
		minflater = inflater;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {

			convertView = minflater.inflate(R.layout.patient_list_cell,
					null);
		}

		((TextView) convertView.findViewById(R.id.name)).setText(getItem(position));
		

		return convertView;
	}
}