package app.nichepro.fragmenttabpatient.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;

public class PatientHospitalLocatorFragment extends BaseFragment {


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.app_tab_d_second_screen,
				container, false);


		return view;
	}

	private OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			/* Go to next fragment in navigation stack */
			// mActivity.pushFragments(Constants.TAB_QUERIES, new
			// AppTabCSecondFragment(),true,true);
		}
	};
}
