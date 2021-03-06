package app.nichepro.fragmenttabpatient.queries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.db.EmailMessage;

public class EmailDetailFragment extends BaseFragment {

	EmailMessage msg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.email_detail_screen, container,
				false);

		TextView user = (TextView) view.findViewById(R.id.user);
		TextView email = (TextView) view.findViewById(R.id.emailTxt);
		TextView subject = (TextView) view.findViewById(R.id.subject);

		Button reply = (Button) view.findViewById(R.id.bSendEmail);
		if (msg != null) {
			user.setText(msg.getUSERNAME());
			subject.setText(msg.getSUBJECT());
			email.setText(msg.getMESSAGE());
		}

		reply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		return view;
	}

}
