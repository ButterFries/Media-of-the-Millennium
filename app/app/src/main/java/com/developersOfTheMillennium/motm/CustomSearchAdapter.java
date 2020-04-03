package com.developersOfTheMillennium.motm;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

public class CustomSearchAdapter extends SimpleCursorAdapter {

    private Context mContext;
    private Context appContext;
    private int layout;
    private Cursor cr;
    private final LayoutInflater inflater;

    public CustomSearchAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.layout = layout;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.cr = c;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        final String mediaID = ((TextView) view.findViewById(R.id.search_row_id)).getText().toString();

        LinearLayout main = view.findViewById(R.id.search_row_layout);
        main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Bundle args = new Bundle();
                MediaProfilePageFragment Frag = new MediaProfilePageFragment();
                args.putInt("mediaID", Integer.parseInt(mediaID));
                Frag.setArguments(args);

                ((MainActivity) v.getContext()).replaceFragment(Frag);

                try {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(((MainActivity) v.getContext()).getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {}
            }
        });
    }
}