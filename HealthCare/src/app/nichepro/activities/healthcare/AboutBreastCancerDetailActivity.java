package app.nichepro.activities.healthcare;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import app.nichepro.model.KeyValue;

@SuppressLint("SetJavaScriptEnabled")
public class AboutBreastCancerDetailActivity extends BaseDefaultActivity {

	ArrayList<KeyValue> mCancerData;
	private WebView mWebView;

	public void initializeHash() {
		mCancerData = new ArrayList<KeyValue>();
		KeyValue kv = new KeyValue();
		kv.setKey("Overview");
		kv.setValue("Breast cancer is a malignant (progressive and uncontrolled growth) of tumor that starts in the cells of the breast. Breast cancer is more than 100 times more common in women than in men. Women age 40 and older should have a screening mammogram every year and should continue to do so for as long as they are in good health.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Statistics");
		kv.setValue("Breast cancer is the second leading cause of cancer death in women, exceeded only by lung cancer.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Risk factors");
		kv.setValue("The primary risk factors for breast cancer are female sex and older age. Other potential risk factors include lack of childbearing or breastfeeding, higher hormone levels, diet, and obesity.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Symptoms");
		kv.setValue("A lump in the breast, change in the way the breast looks, a change in the nipple, and fluid coming out of the nipple are some of the symptoms.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Diagnosis");
		kv.setValue("Diagnosis involves physical exam, and a mammogram, which is an X-ray of the breast.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Staging");
		kv.setValue("There are eight stages: 0, I, IIA, IIB, IIIA, IIIB, IIIC, and IV.  Stage 0 is non-invasive. Stage IV describes invasive breast cancer that has spread beyond the breast.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Treatment");
		kv.setValue("Breast cancer is usually treated with surgery, radiation therapy, chemotherapy, hormone therapy, or targeted therapy.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Side effects");
		kv.setValue("Cancer treatment can have side effects such as loss of appetite, nausea and vomiting, weakness and fatigue, mouth soreness, hair loss, weight gain, premature menopause, lowered resistance to infections, bleeding, and diarrhea.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("After treatment");
		kv.setValue("When treatment ends, follow-up appointments are essential. Lab tests or x-rays and scans may be required to look for signs of cancer or treatment side effects.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Current research");
		kv.setValue("Current research continues to uncover lifestyle factors and habits that alter breast cancer risk. Ongoing studies are looking at the effect of exercise, weight gain or loss, and diet on breast cancer risk.");
		mCancerData.add(kv);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if (mWebView.canGoBack() == true) {
					mWebView.goBack();
				} else {
					finish();
				}
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allaboutcancerwebview);

		WebViewClient yourWebClient = new WebViewClient() {
			// Override page so it's load on my view only
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// This line we let me load only pages inside Firstdroid Webpage
				// if ( url.contains("firstdroid") == true )
				// //Load new URL Don't override URL Link
				// return false;
				view.loadUrl(url);

				// Return true to override url loading (In this case do
				// nothing).
				return true;
			}
		};

		mWebView = (WebView) findViewById(R.id.webView1);

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.setWebViewClient(yourWebClient);
		final Activity activity = (Activity) this;

		mWebView.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {
				activity.setTitle("Loading...");
				activity.setProgress(progress * 100);
				if (progress == 100)
					activity.setTitle("My title");
			}
		});
		mWebView.loadUrl("http://192.168.1.117/cancer/cancer-info.html");
		// initializeHash();
		//
		// RelativeLayout hView = (RelativeLayout) findViewById(R.id.headings);
		// hView.setVisibility(View.VISIBLE);
		// TextView headerView = (TextView) findViewById(R.id.header);
		// headerView.setText("Breast Cancer");
		//
		// ListView list = (ListView) findViewById(R.id.lvCancerExpansion);
		//
		// // Creating the list adapter and populating the list
		// ArrayAdapter<KeyValue> listAdapter = new CustomListAdapter(this,
		// this.getLayoutInflater(), R.layout.allaboutcancer_listitem);
		//
		// for (KeyValue element : mCancerData) {
		// listAdapter.add(element);
		//
		// }
		//
		// list.setAdapter(listAdapter);
		//
		// // Creating an item click listener, to open/close our toolbar for
		// each
		// // item
		// list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		// public void onItemClick(AdapterView<?> parent, final View view,
		// int position, long id) {
		//
		// TextView txtView = (TextView) view.findViewById(R.id.detail);
		//
		// // Creating the expand animation for the item
		// ExpandAnimation expandAni = new ExpandAnimation(txtView, 500);
		//
		// // Start the animation on the toolbar
		// txtView.startAnimation(expandAni);
		// }
		// });
	}
}
