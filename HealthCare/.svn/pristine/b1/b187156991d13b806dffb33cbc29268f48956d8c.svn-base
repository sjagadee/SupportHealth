package app.nichepro.fragmenttabpatient.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientAllergiesResponseObject;

public class PatientAllergiesListAdapter extends
	ArrayAdapter<PatientAllergiesResponseObject> {
LayoutInflater minflater;

static class ViewHolder {

	TextView allergiesType;
	TextView result;
	TextView description;

	
	public void reset() {

	}
}

public PatientAllergiesListAdapter(Context context,
		LayoutInflater inflater, int textViewResourceId) {
	super(context, textViewResourceId);
	minflater = inflater;

}

@Override
public View getView(int position, View convertView, ViewGroup parent) {
	ViewHolder holder;
	if (convertView == null) {
		convertView = minflater
				.inflate(R.layout.allergies_list_cell, null);
		holder = new ViewHolder();

		holder.allergiesType = ((TextView) convertView.findViewById(R.id.allergy_type));
		holder.result = ((TextView) convertView.findViewById(R.id.result));
		holder.description = ((TextView) convertView.findViewById(R.id.description));
		
	
		convertView.setTag(holder);
	} else {
		holder = (ViewHolder) convertView.getTag();
	}

	PatientAllergiesResponseObject paro = getItem(position);


	holder.allergiesType.setText(paro.type);
	holder.result.setText(paro.result);
	holder.description.setText(paro.desscription);
	
	return convertView;
}
}