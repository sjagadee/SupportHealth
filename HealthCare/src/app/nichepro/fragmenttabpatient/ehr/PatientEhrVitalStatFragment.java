package app.nichepro.fragmenttabpatient.ehr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.PatientVitalStatListResponseObject;
import app.nichepro.model.PatientVitalStatResponseObject;

public class PatientEhrVitalStatFragment  extends BaseFragment {

	PatientVitalStatListResponseObject vitalstatListObject;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_ehr_vitalstat, container,
				false);

		ListView list = (ListView) view.findViewById(R.id.vitalstatlist);

		// Creating the list adapter and populating the list
		ArrayAdapter<PatientVitalStatResponseObject> listAdapter = new VitalAdapter(this,
				inflater.getContext(), inflater, R.layout.vital_data_cell);

		for (PatientVitalStatResponseObject element : vitalstatListObject.VITALSTATS_LIST) {
			listAdapter.add(element);

		}
		list.setAdapter(listAdapter);

		return view;
	}

}
