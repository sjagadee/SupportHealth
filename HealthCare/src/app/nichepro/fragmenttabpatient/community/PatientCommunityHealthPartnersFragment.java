package app.nichepro.fragmenttabpatient.community;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import app.nichepro.activities.healthcare.BaseFragment;
import app.nichepro.activities.healthcare.R;

public class PatientCommunityHealthPartnersFragment extends BaseFragment {

	private WebView mWebView;
	ProgressDialog mProgress;
	public String url;
	public String title;

	@Override
	public boolean onBackPressed() {
		if (mWebView.canGoBack() == true) {
			mWebView.goBack();
			return true;
		}
		return false;
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.commonwebviewui, container,
				false);

		mProgress = ProgressDialog.show(getActivity(), "Loading",
				"Please wait for a moment...");
		WebViewClient yourWebClient = new WebViewClient() {
			// Override page so it's load on my view only
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// This line we let me load only pages inside Firstdroid Webpage
				// if ( url.contains("firstdroid") == true )
				// //Load new URL Don't override URL Link
				// return false;
				mProgress.show();

				view.loadUrl(url);

				// Return true to override url loading (In this case do
				// nothing).
				return true;

			}

			public void onPageFinished(WebView view, String url) {
				if (mProgress.isShowing()) {
					mProgress.dismiss();
				}
			}

			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				handler.proceed(); // Ignore SSL certificate errors
			}
		};

		RelativeLayout header = (RelativeLayout)view.findViewById(R.id.healderlayout);
		header.setVisibility(View.GONE);
		mWebView = (WebView) view.findViewById(R.id.commonWebView);

		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.setWebViewClient(yourWebClient);
		final Activity activity = getActivity();

		mWebView.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {
				activity.setTitle("Loading...");
				activity.setProgress(progress * 100);
				if (progress == 100)
					activity.setTitle("My title");
			}
		});
		mWebView.loadUrl(url);

		return view;
	}

}
