package app.nichepro.activities.healthcare;

import java.util.HashMap;
import java.util.Stack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import app.nichepro.fragmenttabpatient.community.PatientCommunityFragment;
import app.nichepro.fragmenttabpatient.ehr.PatientEHRFragment;
import app.nichepro.fragmenttabpatient.home.PatientHomeFragment;
import app.nichepro.fragmenttabpatient.info.PatientInfoFragment;
import app.nichepro.fragmenttabpatient.info.PatientInfoMyNotesFragment;
import app.nichepro.fragmenttabpatient.queries.PatientQueryFragment;
import app.nichepro.util.Constants;
import app.nichepro.util.EnumFactory.LoginType;

public class PatientTabActivity extends BaseDefaultFragmentActivity {
	/* Your Tab host */
	private TabHost mTabHost;

	/* A HashMap of stacks, where we use tab identifier as keys.. */
	private HashMap<String, Stack<Fragment>> mStacks;

	/* Save current tabs identifier in this.. */
	private String mCurrentTab;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.app_main_tab_fragment_layout);

		/*
		 * Navigation stacks for each tab gets created.. tab identifier is used
		 * as key to get respective stack for each tab
		 */
		mStacks = new HashMap<String, Stack<Fragment>>();
		mStacks.put(Constants.TAB_HOME, new Stack<Fragment>());
		mStacks.put(Constants.TAB_INFO, new Stack<Fragment>());
		mStacks.put(Constants.TAB_EHR, new Stack<Fragment>());
		mStacks.put(Constants.TAB_QUERIES, new Stack<Fragment>());
		mStacks.put(Constants.TAB_COMMUNITY, new Stack<Fragment>());

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setOnTabChangedListener(listener);
		mTabHost.setup();

		initializeTabs();
	}

	private View createTabView(final int id) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabs_icon, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
		imageView.setImageDrawable(getResources().getDrawable(id));
		return view;
	}

	public void initializeTabs() {
		/* Setup your tab icons and content views.. Nothing special in this.. */
		TabHost.TabSpec spec = mTabHost.newTabSpec(Constants.TAB_HOME);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.tab_home_state_btn));
		mTabHost.addTab(spec);

		mTabHost.setCurrentTab(0);

		spec = mTabHost.newTabSpec(Constants.TAB_INFO);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});

		spec.setIndicator(createTabView(R.drawable.tab_info_state_btn));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(Constants.TAB_EHR);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.tab_ehr_state_btn));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(Constants.TAB_QUERIES);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.tab_query_state_btn));
		mTabHost.addTab(spec);

		spec = mTabHost.newTabSpec(Constants.TAB_COMMUNITY);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		spec.setIndicator(createTabView(R.drawable.tab_community_state_btn));
		mTabHost.addTab(spec);

		// mTabHost.getTabWidget().setDividerDrawable(R.drawable.tab_divider);

	}

	/* Comes here when user switch tab, or we do programmatically */
	TabHost.OnTabChangeListener listener = new TabHost.OnTabChangeListener() {
		public void onTabChanged(String tabId) {
			/* Set current tab.. */
			mCurrentTab = tabId;

			if (mStacks.get(tabId).size() == 0) {
				/*
				 * First time this tab is selected. So add first fragment of
				 * that tab. Dont need animation, so that argument is false. We
				 * are adding a new fragment which is not present in stack. So
				 * add to stack is true.
				 */
				if (tabId.equals(Constants.TAB_INFO)) {
					pushFragments(tabId, new PatientInfoFragment(), false, true);
				} else if (tabId.equals(Constants.TAB_EHR)) {
					pushFragments(tabId, new PatientEHRFragment(
							LoginType.Patient, null, null), false, true);
				} else if (tabId.equals(Constants.TAB_HOME)) {
					pushFragments(tabId, new PatientHomeFragment(), false, true);
				} else if (tabId.equals(Constants.TAB_QUERIES)) {
					pushFragments(tabId, new PatientQueryFragment(), false,
							true);
				} else if (tabId.equals(Constants.TAB_COMMUNITY)) {
					pushFragments(tabId, new PatientCommunityFragment(), false,
							true);
				}
			} else {
				/*
				 * We are switching tabs, and target tab is already has atleast
				 * one fragment. No need of animation, no need of stack pushing.
				 * Just show the target fragment
				 */
				pushFragments(tabId, mStacks.get(tabId).lastElement(), false,
						false);
			}
		}
	};

	/*
	 * Might be useful if we want to switch tab programmatically, from inside
	 * any of the fragment.
	 */
	public void setCurrentTab(int val) {
		mTabHost.setCurrentTab(val);
	}

	/*
	 * To add fragment to a tab. tag -> Tab identifier fragment -> Fragment to
	 * show, in tab identified by tag shouldAnimate -> should animate
	 * transaction. false when we switch tabs, or adding first fragment to a tab
	 * true when when we are pushing more fragment into navigation stack.
	 * shouldAdd -> Should add to fragment navigation stack (mStacks.get(tag)).
	 * false when we are switching tabs (except for the first time) true in all
	 * other cases.
	 */
	public void pushFragments(String tag, Fragment fragment,
			boolean shouldAnimate, boolean shouldAdd) {
		if (shouldAdd)
			mStacks.get(tag).push(fragment);
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (shouldAnimate)
			ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	public void popFragments() {
		/*
		 * Select the second last fragment in current tab's stack.. which will
		 * be shown after the fragment transaction given below
		 */
		Fragment fragment = mStacks.get(mCurrentTab).elementAt(
				mStacks.get(mCurrentTab).size() - 2);

		/* pop current fragment from stack.. */
		mStacks.get(mCurrentTab).pop();

		/*
		 * We have the target fragment in hand.. Just show it.. Show a standard
		 * navigation animation
		 */
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
		ft.replace(R.id.realtabcontent, fragment);
		ft.commit();
	}

	@Override
	public void onBackPressed() {
		if (((BaseFragment) mStacks.get(mCurrentTab).lastElement())
				.onBackPressed() == false) {
			/*
			 * top fragment in current tab doesn't handles back press, we can do
			 * our thing, which is
			 * 
			 * if current tab has only one fragment in stack, ie first fragment
			 * is showing for this tab. finish the activity else pop to previous
			 * fragment in stack for the same tab
			 */
			if (mStacks.get(mCurrentTab).size() == 1) {
				super.onBackPressed(); // or call finish..
			} else {
				popFragments();
			}
		} else {
			// do nothing.. fragment already handled back button press.
		}
	}

	/*
	 * Imagine if you wanted to get an image selected using ImagePicker intent
	 * to the fragment. Ofcourse I could have created a public function in that
	 * fragment, and called it from the activity. But couldn't resist myself.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (mStacks.get(mCurrentTab).size() == 0) {
			return;
		}

		/* Now current fragment on screen gets onActivityResult callback.. */
		mStacks.get(mCurrentTab).lastElement()
				.onActivityResult(requestCode, resultCode, data);
	}

	protected void onSaveInstanceState(Bundle outState) {
		outState.putInt("LastTab", mTabHost.getCurrentTab());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Fragment fb = mStacks.get(mCurrentTab).lastElement();
			if (fb.getClass().getSimpleName().compareTo("PatientQueryFragment") == 0) {
				((PatientQueryFragment) fb).myOnKeyDown(KeyEvent.KEYCODE_MENU);
			} else if (fb.getClass().getSimpleName()
					.compareTo("PatientInfoMyNotesFragment") == 0) {
				((PatientInfoMyNotesFragment) fb)
						.myOnKeyDown(KeyEvent.KEYCODE_MENU);
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
