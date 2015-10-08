package app.nichepro.fragmenttabpatient.community;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.fragmenttabpatient.info.PatientInfoListAdapter;

public class PatientBreastCancerThread extends BaseFragment {

	ArrayList<String> infoData;
	TextView title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoData = new ArrayList<String>();
		infoData.add("General");
		infoData.add("Coping With Breast Cancer");
		infoData.add("Advisory");
		infoData.add("Hospitals");
		infoData.add("Doctors");

		View view = inflater.inflate(R.layout.community_first_screen,
				container, false);
		title = (TextView) view.findViewById(R.id.heading1);
		title.setText("Breast Cancer");
		ListView list = (ListView) view.findViewById(R.id.lvCommunityItems);
		ArrayAdapter<String> listAdapter = new PatientInfoListAdapter(
				inflater.getContext(), inflater, R.layout.patient_list_cell);
		for (String element : infoData) {
			listAdapter.add(element);
		}
		list.setAdapter(listAdapter);

		// Creating an item click listener, to open/close our toolbar for each
		// item
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				switch (position) {
				case 0:

					break;
				case 1:

					break;
				case 2:

					break;
				case 3:

					break;
				case 4:

					break;
				}

			}
		});

		return view;
	}

}
