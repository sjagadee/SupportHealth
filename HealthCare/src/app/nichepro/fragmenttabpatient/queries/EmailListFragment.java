package app.nichepro.fragmenttabpatient.queries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.MessageListResponseObject;

public class EmailListFragment extends BaseFragment {

	public MessageListResponseObject msgList;
	public boolean isDoctor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.email_list_screen, container,
				false);

		ListView list = (ListView) view.findViewById(R.id.email_list);

		// Creating the list adapter and populating the list
//		ArrayAdapter<EmailMessage> listAdapter = new EmailListAdapter(
//				inflater.getContext(), inflater,
//				R.layout.email_message_list_cell);
//
//		for (EmailMessage msg : msgList.msgList) {
//			msg.setQTY(null);
//			listAdapter.add(msg);
//
//		}
//		list.setAdapter(listAdapter);
//		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			public void onItemClick(AdapterView<?> parent, final View view,
//					int position, long id) {
//				EmailMessage eMsg = msgList.msgList.get(position);
//				EmailDetailFragment edf = new EmailDetailFragment();
//				edf.msg = eMsg;
//				if (isDoctor) {
//					mDoctorActivity.pushFragments(Constants.TAB_QUERIES, edf,
//							true, true);
//				} else
//					mActivity.pushFragments(Constants.TAB_QUERIES, edf, true,
//							true);
//			}
//		});

		return view;
	}

}
