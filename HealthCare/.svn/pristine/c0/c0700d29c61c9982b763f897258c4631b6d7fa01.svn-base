package app.nichepro.fragmenttabpatient.ehr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import app.nichepro.activities.healthcare.R;
import app.nichepro.model.KeyValue;

/**
 * A simple implementation of list adapter.
 */
class PatientCustomListAdapter extends ArrayAdapter<KeyValue> {
	LayoutInflater minflater;
	
    public PatientCustomListAdapter(Context context, LayoutInflater inflater, int textViewResourceId) {
        super(context,textViewResourceId);
        minflater = inflater;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = minflater.inflate(R.layout.allaboutcancer_listitem, null) ;
            		//getLayoutInflater(getArguments()).inflate(R.layout.allaboutcancer_listitem, null);
        }
        KeyValue kv = getItem(position);
        ((TextView)convertView.findViewById(R.id.title)).setText(kv.getKey());
        ((TextView)convertView.findViewById(R.id.detail)).setText(kv.getValue());
        // Resets the toolbar to be closed
        TextView toolbar = (TextView) convertView.findViewById(R.id.detail);
        ((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
        toolbar.setVisibility(View.GONE);

        return convertView;
    }
}

