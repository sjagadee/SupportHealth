package app.nichepro.fragmenttabpatient.ehr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientAllergiesListResponseObject;
import app.nichepro.model.PatientAllergiesResponseObject;

public class PatientEhrAllergiesFragment extends BaseFragment {
	
	PatientAllergiesListResponseObject allergiesListObject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.patient_ehr_allergies, container, false);

		if (allergiesListObject != null && allergiesListObject.PROCEDURE_LIST.size() > 0) {
			PatientAllergiesResponseObject paro = allergiesListObject.PROCEDURE_LIST.get(0);
			TextView nameTxt = (TextView)view.findViewById(R.id.patientName);
			nameTxt.setText(paro.partyName);
		}
		
		ListView list = (ListView) view.findViewById(R.id.allergieslist);

		// Creating the list adapter and populating the list
		ArrayAdapter<PatientAllergiesResponseObject> listAdapter = new PatientAllergiesListAdapter(
				inflater.getContext(), inflater, R.layout.allergies_list_cell);

		for (PatientAllergiesResponseObject element : allergiesListObject.PROCEDURE_LIST) {
			listAdapter.add(element);

		}
		list.setAdapter(listAdapter);

		return view;
	}
}
