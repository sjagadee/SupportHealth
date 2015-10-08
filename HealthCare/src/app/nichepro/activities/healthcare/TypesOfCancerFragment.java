package app.nichepro.activities.healthcare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TypesOfCancerFragment extends Fragment {

	String[] mListContent = { "Breast Cancer", "Cervical Cancer",
			"Ovarian Cancer", "Uterine Cancer"};

	@Override
	public View onCreateView(final LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.allaboutcancer, container, false);

		ListView list = (ListView) view.findViewById(R.id.lvCancerExpansion);

		// (ListView)findViewById(R.id.lvCancerExpansion);

		// Creating the list adapter and populating the list
		ArrayAdapter<String> listAdapter = new TypesOfCancerListAdapter(
				inflater.getContext(), inflater,
				R.layout.allaboutcancer_listitem);

		for (String element : mListContent) {
			listAdapter.add(element);

		}

		list.setAdapter(listAdapter);

		// Creating an item click listener, to open/close our toolbar for each
		// item
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				Intent intent = new Intent();
				if (position == 0) {
					intent.setClass(getActivity().getBaseContext(), AboutBreastCancerDetailActivity.class);
				}else if (position == 1){
					intent.setClass(getActivity().getBaseContext(), AboutCervicalCancerDetailActivity.class);
				}else if (position == 2){
					intent.setClass(getActivity().getBaseContext(), AboutOvarianCancerDetailActivity.class);
				}else if (position == 3){
					intent.setClass(getActivity().getBaseContext(), AboutUterineCancerDetailActivity.class);
				}else {
					intent.setClass(getActivity().getBaseContext(), NewlyDiagnosedActivity.class);
				}
				
				startActivity(intent);
			}
		});
		return view;
	}

	class TypesOfCancerListAdapter extends ArrayAdapter<String> {
		LayoutInflater minflater;

		public TypesOfCancerListAdapter(Context context,
				LayoutInflater inflater, int textViewResourceId) {
			super(context, textViewResourceId);
			minflater = inflater;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = minflater.inflate(
						R.layout.allaboutcancer_listitem, null);
			}
			String value = getItem(position);
			((TextView) convertView.findViewById(R.id.title)).setText(value);
			// Resets the toolbar to be closed
			TextView toolbar = (TextView) convertView.findViewById(R.id.detail);
			toolbar.setVisibility(View.GONE);
			return convertView;
		}
	}
}
