package app.nichepro.fragmenttabpatient.queries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;


public class AppTabDSecondFragment extends BaseFragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
        View view       =   inflater.inflate(R.layout.app_tab_d_second_screen, container, false);
        return view;
	}

}
