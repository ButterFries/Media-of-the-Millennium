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

import com.developersOfTheMillennium.motm.utils.AddReport;


public class TemporaryReportButtonFrag extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_temporary_report_button, container, false);
//        // Inflate the layout for this fragment
//        //get the spinner from the xml.
//        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner1);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.report_type_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setPrompt("Select your report type");
//
//        spinner.setAdapter(
//                new NothingSelectedSpinnerAdapter(
//                        adapter,
//                        R.layout.testys,
//                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
//                        this.getContext()));
        // Review Button
        final Button reportBtn = rootView.findViewById(R.id.report_button);
        reportBtn.setOnClickListener(this);

        return rootView;
    }
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.report_button:
                //TODO: CHANGE MEDIAID (1)
                //Media ID / account Info / accountType
                AddReport addReportRequest = (AddReport) new AddReport((MainActivity) getActivity(), view).execute("1");
                break;
            // case R.id.addToBookmarks:

//                //TODO: CHANGE MEDIAID (1)
//                //Media ID / account Info / accountType
//                AddBookmark addRequest2 = (AddBookmark) new AddBookmark((MainActivity) getActivity(), view).execute("1", AppGlobals.user, AppGlobals.userType);
//                break;
        }
    }
}
