package com.developersOfTheMillennium.motm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.developersOfTheMillennium.motm.utils.AddReport;


public class TemporaryReportButtonFrag extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_temporary_report_button, container, false);

        // Review Button
        final Button reportBtn = rootView.findViewById(R.id.report_button);
        reportBtn.setOnClickListener(this);

        return rootView;
    }
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.report_button:
                if (AppGlobals.userType.equals("guest")) {
                    CharSequence text = "Please sign in to report";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), text, duration);
                    toast.show();
                }
                else {
                //TODO: CHANGE REVIEWID (1) to actual review ID
                //reviewID
                AddReport addReportRequest = (AddReport) new AddReport((MainActivity) getActivity(), view).execute("1"); }

                break;
            // case R.id.addToBookmarks:

//                //TODO: CHANGE MEDIAID (1)
//                //Media ID / account Info / accountType
//                AddBookmark addRequest2 = (AddBookmark) new AddBookmark((MainActivity) getActivity(), view).execute("1", AppGlobals.user, AppGlobals.userType);
//                break;
        }
    }
}
