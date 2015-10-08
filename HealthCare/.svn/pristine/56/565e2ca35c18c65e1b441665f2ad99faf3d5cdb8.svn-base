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
import app.nichepro.model.PatientDiagnosisListResponseObject;
import app.nichepro.model.PatientDiagnosisResponseObject;

public class PatientEhrDiagnosisFragment extends BaseFragment {

	PatientDiagnosisListResponseObject diagnosisListObject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_ehr_diagnosis, container,
				false);

		if (diagnosisListObject != null && diagnosisListObject.DIAGNOSIS_LIST.size() > 0) {
			PatientDiagnosisResponseObject paro = diagnosisListObject.DIAGNOSIS_LIST.get(0);
			TextView nameTxt = (TextView)view.findViewById(R.id.patientName);
			nameTxt.setText(paro.partyName);
		}
		
		
		ListView list = (ListView) view.findViewById(R.id.diagnosislist);

		// Creating the list adapter and populating the list
		ArrayAdapter<PatientDiagnosisResponseObject> listAdapter = new PatientDiagnosisListAdapter(
				inflater.getContext(), inflater, R.layout.diagnosis_list_cell);

		for (PatientDiagnosisResponseObject element : diagnosisListObject.DIAGNOSIS_LIST) {
			listAdapter.add(element);

		}
		list.setAdapter(listAdapter);

		return view;
	}

}