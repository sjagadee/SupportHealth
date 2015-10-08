package app.nichepro.activities.healthcare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressLint("SetJavaScriptEnabled")
public class AboutCancerActivity extends BaseDefaultActivity {
	/* Your Tab host */
	// private TabHost tHost;
	private WebView mWebView;
	ProgressDialog mProgress;
	public String url;

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
		Intent intent = getIntent();

		url = intent.getStringExtra("url");
		mProgress = ProgressDialog.show(this, "Loading",
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
			
			public void onReceivedSslError(WebView view, SslErrorHandler handler,
					SslError error) {
				handler.proceed(); // Ignore SSL certificate errors
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
		mWebView.loadUrl(url);

	}

	
}
