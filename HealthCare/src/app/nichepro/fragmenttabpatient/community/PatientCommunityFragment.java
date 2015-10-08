package app.nichepro.fragmenttabpatient.community;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import app.nichepro.HealthCareApp;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.fragmenttabpatient.info.PatientInfoListAdapter;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.WebLinks;

public class PatientCommunityFragment extends BaseFragment {

	ArrayList<String> infoData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		infoData = new ArrayList<String>();
		infoData.add("Latest On Cancer");
		infoData.add("Request For Sponsor");
		infoData.add("Forum");
		infoData.add("Events");
		infoData.add("Health Partners");

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
					// mActivity.pushFragments(Constants.TAB_COMMUNITY,
					// new PatientCommunityLOCFragment(), true, true);
					LatestOnCancerWebViewFragment locwvf = new LatestOnCancerWebViewFragment();
					locwvf.header = "General Information";
					locwvf.url = "http://feeds.feedburner.com/ncinewsreleases";
					mActivity.pushFragments(Constants.TAB_COMMUNITY, locwvf,
							true, true);
					break;
				case 1:
					mActivity.pushFragments(Constants.TAB_COMMUNITY,
							new PatientCommunityRequestSponsorFragment(), true,
							true);

					break;
				case 2: {
					PatientCommunityHealthPartnersFragment pchpf = new PatientCommunityHealthPartnersFragment();
					HealthCareApp myApp = (HealthCareApp) getActivity()
							.getApplication();

					pchpf.url = WebLinks.getHtmlLink(myApp
							.getHtmlLinkMap(ResponseMesssagType.CancerFormMessageType));
					pchpf.title = "Cancer Forum";

					mActivity.pushFragments(Constants.TAB_COMMUNITY, pchpf,
							true, true);
				}

					break;
				case 3:

					// requestForUpComingEvents();

					mActivity.pushFragments(Constants.TAB_COMMUNITY,
							new PatientCommunityEventsFragments(), true, true);
					break;
				case 4: {
					PatientCommunityHealthPartnersFragment pchpf = new PatientCommunityHealthPartnersFragment();
					HealthCareApp myApp = (HealthCareApp) getActivity()
							.getApplication();

					pchpf.url = WebLinks.getHtmlLink(myApp
							.getHtmlLinkMap(ResponseMesssagType.HealthPartnersMessageType));
					pchpf.title = "Health Partners";
					mActivity.pushFragments(Constants.TAB_COMMUNITY, pchpf,
							true, true);
				}
					break;
				}

			}
		});

		return view;
	}

}