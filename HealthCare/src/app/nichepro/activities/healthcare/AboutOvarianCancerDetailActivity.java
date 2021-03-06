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

public class AboutOvarianCancerDetailActivity extends BaseDefaultActivity {

	ArrayList<KeyValue> mCancerData;

	public void initializeHash() {
		mCancerData = new ArrayList<KeyValue>();
		KeyValue kv = new KeyValue();
		kv.setKey("Overview");
		kv.setValue("The ovaries are two small glands, located on either side of the uterus. They produce female sex hormones and store and release eggs (ova). Ovarian cancer happens when cells that are not normal grow in one or both the ovaries.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Statistics");
		kv.setValue("Ovarian cancer is the ninth most common cancer among women, excluding non-melanoma skin cancers. It ranks fifth in cancer deaths among women. ");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Risk factors");
		kv.setValue("Age (most ovarian cancers develop after menopause), obesity, reproductive history, use of fertility drugs and a family history of ovarian cancer are some of the risk factors.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Symptoms");
		kv.setValue("Symptoms of ovarian cancer include frequent bloating, pain in the belly or pelvis, trouble eating or feeling full quickly, and urinary problems such as an urgent need to urinate or urinating more often than usual. ");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Diagnosis");
		kv.setValue("Diagnosis includes physical exam, use of imaging methods such as computed tomography (CT) scans, magnetic resonance imaging (MRI) scans, and ultrasound studies.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Staging");
		kv.setValue("The stages of uterine cancer consist of stages I to IV, and recurrent cancer. In stage I, cancer is contained within the ovary (or ovaries). In stage IV, the cancer has spread to other organs located outside the abdominal cavity. ");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Treatment");
		kv.setValue("There are three forms of treatment of ovarian cancer. The primary one is surgery, chemotherapy is the second important modality, and the third is radiation treatment.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Side effects");
		kv.setValue("Side effects vary depending on the type of treatment and can include hair loss, nausea, vomiting, mouth sores, diarrhea, and urinary discomfort. ");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("After treatment");
		kv.setValue("Follow-up for ovarian cancer usually includes a careful general physical exam and blood tests for tumor markers that help recognize recurrence.");
		mCancerData.add(kv);

		kv = new KeyValue();
		kv.setKey("Current research");
		kv.setValue("Current research is eventually is expected to lead to new drugs for preventing and treating familial ovarian cancer. Researchers are also testing new ways to screening for ovarian cancer.");
		mCancerData.add(kv);
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allaboutcancer);
		initializeHash();

		RelativeLayout hView = (RelativeLayout) findViewById(R.id.headings);
		hView.setVisibility(View.VISIBLE);
		TextView headerView = (TextView) findViewById(R.id.header);
		headerView.setText("Ovarian Cancer");

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
