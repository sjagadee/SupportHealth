package app.nichepro.activities.healthcare;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import app.nichepro.model.KeyValue;

public class AboutUterineCancerDetailActivity extends BaseDefaultActivity {

	ArrayList<KeyValue> mCancerData;

	public void initializeHash() {
		mCancerData = new ArrayList<KeyValue>();
		KeyValue kv = new KeyValue();
		kv.setKey("Overview");
		kv.setValue("Uterine cancers begin when normal cells in the uterus change and grow uncontrollably, forming a mass called a tumor. A tumor can be benign (noncancerous) or malignant (cancerous, meaning it can spread to other parts of the body).");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Statistics");
		kv.setValue("Uterine cancer is the fourth most common cancer and the eighth most common cause of cancer death for women in the United States. ");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Risk factors");
		kv.setValue("Abnormal overgrowth of Endometrium (the mucous membrane that lines the uterus), obesity,  prolonged use of estrogen (without progesterone) in treatment, history of taking the drug Tamoxifen, history of radiation therapy, and family history are some risk factors.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Symptoms");
		kv.setValue("Common symptoms of uterine cancer include abnormal vaginal bleeding, spotting or discharge, pain or difficulty when emptying the bladder, pain during sex, and pain in the pelvic area.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Diagnosis");
		kv.setValue("Diagnosis involves physical exam, blood tests, and one or more of the following tests: pelvic exam, ultrasound, and biopsy. ");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Staging");
		kv.setValue("The stages of uterine cancer include stages I to IV and recurrent cancer. In stage I, the cancer is restricted to the uterus. In stage IV, the cancer has spread beyond the pelvis. Recurrent uterine cancer is cancer that has recurred after it has been treated.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Treatment");
		kv.setValue("The treatment includes surgery, radiation therapy, hormone therapy, and chemotherapy.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Side effects");
		kv.setValue("Side effects vary depending on the type of treatment and include pain, general fatigue, hair loss in the treated area, loss of appetite, and fatigue.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("After treatment");
		kv.setValue("Follow-up care is essential and may include a physical exam, a pelvic exam, a chest x-ray, and laboratory tests.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Current research");
		kv.setValue("Researchers are studying less extensive methods of surgery to remove the uterus. They are also looking at a combination of surgery, radiation, and chemotherapy. Research on new drugs, new drug combinations, and biological therapies are part of current research.");
		mCancerData.add(kv);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allaboutcancer);
		initializeHash();

		RelativeLayout hView = (RelativeLayout) findViewById(R.id.headings);
		hView.setVisibility(View.VISIBLE);
		TextView headerView = (TextView) findViewById(R.id.header);
		headerView.setText("Uterine Cancer");

		ListView list = (ListView) findViewById(R.id.lvCancerExpansion);

		// Creating the list adapter and populating the list
		ArrayAdapter<KeyValue> listAdapter = new CustomListAdapter(this,
				this.getLayoutInflater(), R.layout.allaboutcancer_listitem);

		for (KeyValue element : mCancerData) {
			listAdapter.add(element);

		}

		list.setAdapter(listAdapter);

		// Creating an item click listener, to open/close our toolbar for each
		// item
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, final View view,
					int position, long id) {

				TextView txtView = (TextView) view.findViewById(R.id.detail);

				// Creating the expand animation for the item
				ExpandAnimation expandAni = new ExpandAnimation(txtView, 500);

				// Start the animation on the toolbar
				txtView.startAnimation(expandAni);
			}
		});
	}
}
