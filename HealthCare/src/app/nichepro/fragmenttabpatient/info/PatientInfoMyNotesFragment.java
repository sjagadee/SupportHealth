package app.nichepro.fragmenttabpatient.info;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;
import app.nichepro.HealthCareApp;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;
import app.nichepro.animation.AnimationFactory;
import app.nichepro.animation.AnimationFactory.FlipDirection;
import app.nichepro.model.BaseResponseObject;
import app.nichepro.model.ErrorResponseObject;
import app.nichepro.model.PatientNoteListResponseObject;
import app.nichepro.model.PatientNoteResponseObject;
import app.nichepro.responsehandler.ResponseParserListener;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.LoginType;
import app.nichepro.util.EnumFactory.ResponseMesssagType;
import app.nichepro.util.UIUtilities;
import app.nichepro.util.UpdateUiFromAdapterListener;
import app.nichepro.util.WebLinks;
import app.nichepro.util.WebRequestTask;

public class PatientInfoMyNotesFragment extends BaseFragment implements
		OnClickListener, ResponseParserListener, UpdateUiFromAdapterListener {
	Boolean isToggle;
	Button btnMyNote, btnAddNewNote;
	Button btnSave;
	EditText titleTxt;
	EditText titleUnfo;
	ViewAnimator viewAnimator;
	ListView listview;
	ArrayList<PatientNoteResponseObject> mNoteList;
	WebRequestTask myNoteRequestTask;
	LayoutInflater mInflater;
	private ResponseMesssagType msgType;
	boolean isNoteAddedd;
	TextView txtView;
	private ArrayAdapter<PatientNoteResponseObject> patientNoteAdapter;
	public String mPatientId;
	public String mTabName;
	HealthCareApp myApp;
	private LoginType mActorType;

	public PatientInfoMyNotesFragment(LoginType actorType, String tabName,
			String patientId) {
		this.mActorType = actorType;
		this.mPatientId = patientId;
		this.mTabName = tabName;
	}

	public void initializeHash(PatientNoteListResponseObject noteList) {
		mNoteList = null;
		mNoteList = new ArrayList<PatientNoteResponseObject>();

		for (int i = noteList.note.size() - 1; i >= 0; i--) {
			mNoteList.add(noteList.note.get(i));
		}

	}

	public void inflateAdapter() {
		// Creating the list adapter and populating the list
		patientNoteAdapter = new PatientMyNotesListAdapter(
				mInflater.getContext(), mInflater,
				R.layout.patient_home_list_cell);

		for (PatientNoteResponseObject element : mNoteList) {
			patientNoteAdapter.add(element);

		}

		listview.setAdapter(patientNoteAdapter);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {
				PatientNoteDetailFragment pndf = new PatientNoteDetailFragment();
				pndf.mNoteObject = mNoteList.get(position);
				mActivity.pushFragments(Constants.TAB_INFO, pndf, true, true);

			}
		});

	}

	@Override
	public View onCreateView(final LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_info_mynotes_screen,
				container, false);
		mInflater = inflater;
		isToggle = false;
		isNoteAddedd = false;

		viewAnimator = (ViewAnimator) view.findViewById(R.id.viewFlipper);

		btnAddNewNote = (Button) view.findViewById(R.id.bAddNewNotes);
		btnMyNote = (Button) view.findViewById(R.id.bMyNotes);

		btnMyNote.setSelected(false);
		btnMyNote.setOnClickListener(this);
		btnAddNewNote.setSelected(true);
		btnAddNewNote.setOnClickListener(this);

		listview = (ListView) view.findViewById(R.id.noteList);

		titleTxt = (EditText) view.findViewById(R.id.noteTitleEditTx);
		titleUnfo = (EditText) view.findViewById(R.id.noteInfoEditTxt);

		btnSave = (Button) view.findViewById(R.id.btnSave);
		btnSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				addNote();
			}
		});

		if (mNoteList == null) {
			viewNote();
		} else if (mNoteList.size() > 0) {
			inflateAdapter();
		}

		return view;

	}

	public void viewNote() {
		myNoteRequestTask = new WebRequestTask(
				WebLinks.getLink(WebLinks.LIST_OF_NOTES), getActivity(), this);
		HealthCareApp myApp = (HealthCareApp) getActivity().getApplication();

		if (mActorType != null
				&& mActorType.toString()
						.compareTo(LoginType.Patient.toString()) == 0) {
			myNoteRequestTask.AddParam(Constants.PARAM_partyIdFrom, myApp
					.getLoggedInUser().getPartyId());
		} else if (mActorType != null
				&& mActorType.toString().compareTo(
						LoginType.Physician.toString()) == 0) {

			myNoteRequestTask.AddParam(Constants.PARAM_partyIdFrom, myApp
					.getLoggedInUser().getPartyId());

			myNoteRequestTask.AddParam(Constants.PARAM_partyIdTo, mPatientId);

		}

		// ehrRequestTask.AddParam(Constants.PARAM_noteName, "Doctor");
		// ehrRequestTask.AddParam(Constants.PARAM_noteInfo, "HC_PROVIDER");

		myNoteRequestTask.execute();
	}

	public void addNote() {

		if (titleTxt.getText().toString().length() > 0
				&& titleUnfo.getText().toString().length() > 0) {

			myNoteRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.CREATE_NOTE), getActivity(), this);
			HealthCareApp myApp = (HealthCareApp) getActivity()
					.getApplication();

			if (mActorType != null
					&& mActorType.toString().compareTo(
							LoginType.Patient.toString()) == 0) {
				myNoteRequestTask.AddParam(Constants.PARAM_partyIdFrom, myApp
						.getLoggedInUser().getPartyId());
			} else if (mActorType != null
					&& mActorType.toString().compareTo(
							LoginType.Physician.toString()) == 0) {

				myNoteRequestTask.AddParam(Constants.PARAM_partyIdFrom, myApp
						.getLoggedInUser().getPartyId());

				myNoteRequestTask.AddParam(Constants.PARAM_partyIdTo,
						mPatientId);

			}

			myNoteRequestTask.AddParam(Constants.PARAM_noteName, titleTxt
					.getText().toString());

			myNoteRequestTask.AddParam(Constants.PARAM_noteInfo, titleUnfo
					.getText().toString());
			myNoteRequestTask.execute();
			UIUtilities.hideKeyboard(getActivity(), titleUnfo);
			UIUtilities.hideKeyboard(getActivity(), titleTxt);
		} else {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"Note title and content should not be empty",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		}

	}

	void refreshNoteList() {
		if (isToggle == true) {
			AnimationFactory.flipTransition(viewAnimator,
					FlipDirection.LEFT_RIGHT);
			btnAddNewNote.setTextColor(Color.BLACK);
			btnMyNote.setTextColor(Color.WHITE);
			btnAddNewNote.setSelected(true);
			btnMyNote.setSelected(false);
		}

		isToggle = false;
		if (isNoteAddedd == true) {
			viewNote();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.bAddNewNotes:

			if (isToggle == false) {
				AnimationFactory.flipTransition(viewAnimator,
						FlipDirection.LEFT_RIGHT);
				btnMyNote.setTextColor(Color.BLACK);
				btnAddNewNote.setTextColor(Color.WHITE);
				btnAddNewNote.setSelected(false);
				btnMyNote.setSelected(true);
			}
			isToggle = true;
			break;

		case R.id.bMyNotes:
			refreshNoteList();
			break;
		}
	}

	@Override
	public void onEndParsingMsgType(ResponseMesssagType msgType) {
		// TODO Auto-generated method stub
		this.msgType = msgType;
	}

	@Override
	public void onEndParsingResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub
		if (this.msgType == ResponseMesssagType.PatientNoteMessageType) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information, R.string.note_added,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							isNoteAddedd = true;
							refreshEditFields();
							refreshNoteList();

						}
					});
		} else if (this.msgType == ResponseMesssagType.DeleteNoteMessageType) {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information, R.string.note_deleted,
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							isNoteAddedd = true;
							viewNote();

						}
					});
		} else if (this.msgType == ResponseMesssagType.PatientNoteListMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				initializeHash((PatientNoteListResponseObject) items.get(0));
				inflateAdapter();
			}
		} else if (this.msgType == ResponseMesssagType.ErrorResponseMessageType) {
			if (items != null && !items.isEmpty() && items.size() > 0) {
				ErrorResponseObject ero = (ErrorResponseObject) items.get(0);
				UIUtilities.showErrorWithOkButton(getActivity(),
						ero.getErrorText());
			} else
				UIUtilities.showServerError(getActivity());

		} else if (this.msgType == ResponseMesssagType.UnknownResponseMessageType) {
			UIUtilities.showServerError(getActivity());
		} else {
			UIUtilities.showServerError(getActivity());
		}

	}

	@Override
	public void onErrorResponse(ArrayList<BaseResponseObject> items) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		UIUtilities.showErrorWithOkButton(getActivity(), ex.getMessage());

	}

	public ArrayAdapter<PatientNoteResponseObject> getPatientNoteAdapter() {
		return patientNoteAdapter;
	}

	public void setPatientNoteAdapter(
			ArrayAdapter<PatientNoteResponseObject> patientNoteAdapter) {
		this.patientNoteAdapter = patientNoteAdapter;
	}

	public void deleteMessages() {
		AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

		alert.setTitle("Remove messages!");
		alert.setMessage("Press delete to remove selected Note. This is undo action & can not be un done after selection");
		alert.setPositiveButton("Delete",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						requestToDeleteMessages();
					}
				});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();

	}

	public void requestToDeleteMessages() {
		String commId = null;
		for (PatientNoteResponseObject element : mNoteList) {
			if (element.isSelected) {
				if (commId == null) {
					commId = element.noteId;
				} else
					commId = commId + "," + element.noteId;
			}
		}
		initiateDeleteRequest(commId);
	}

	public void initiateDeleteRequest(String commId) {

		if (commId != null) {
			myNoteRequestTask = new WebRequestTask(
					WebLinks.getLink(WebLinks.DeleteNotes), getActivity(), this);
			myNoteRequestTask.AddParam(Constants.PARAM_noteId, commId);
			myNoteRequestTask.execute();
		} else {
			UIUtilities.showConfirmationAlert(getActivity(),
					R.string.dialog_title_information,
					"You have not selected any Note!",
					R.string.dialog_button_Ok,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
		}
	}

	public void myOnKeyDown(int key_code) {
		if (key_code == KeyEvent.KEYCODE_MENU && isToggle == false) {
			deleteMessages();
		}
	}

	@Override
	public void updateUI(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(String message, int position) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUI(int position) {
		// TODO Auto-generated method stub

	}

	public void refreshEditFields() {
		titleTxt.setText("");
		titleUnfo.setText("");
	}

}
