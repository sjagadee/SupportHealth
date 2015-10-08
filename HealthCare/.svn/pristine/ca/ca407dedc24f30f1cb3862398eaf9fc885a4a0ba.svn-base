package app.nichepro.fragmenttabpatient.community;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.fragmenttabpatient.info.PatientInfoListAdapter;
import app.nichepro.util.Constants;

public class PatientCommunityForumFragment extends BaseFragment {

	ArrayList<String> infoData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoData = new ArrayList<String>();
		infoData.add("Breast Cancer");
		infoData.add("Cervical Cancer");
		infoData.add("Uthrine Cancer");
		infoData.add("Ovarian Cancer");


		View view = inflater.inflate(R.layout.community_first_screen,
				container, false);
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
					mActivity.pushFragments(Constants.TAB_COMMUNITY,
							new PatientBreastCancerThread(), true, true);
					break;
				case 1:
					mActivity.pushFragments(Constants.TAB_COMMUNITY,
							new PatientCervicalCancerThread(), true, true);
					
					break;
				case 2:
					mActivity.pushFragments(Constants.TAB_COMMUNITY,
							new PatientUthrineCancerThread(), true, true);
					break;
				case 3:
					mActivity.pushFragments(Constants.TAB_COMMUNITY,
							new PatientOvarianCancerThread(), true, true);
					break;
				}
				
			}
		});

		return view;
	}
}
