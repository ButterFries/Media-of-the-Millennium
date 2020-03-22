package com.developersOfTheMillennium.motm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.developersOfTheMillennium.motm.utils.Favorites.RemoveFavorite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FavouritesAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final JSONArray items; //fix MPP is inside AppDB
    //String contextType;

    public FavouritesAdapter(Context context, JSONArray items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
        //this.contextType = c;
    }

    @Override
    public int getCount() {
        return this.items.length();
    }

    @Override
    public JSONObject getItem(int position) {
        try {
            return this.items.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        try {
            return this.items.getJSONObject(position).hashCode();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final JSONObject item = getItem(position);

        if(convertView == null) {
            // If convertView is null we have to inflate a new layout
            convertView = this.inflater.inflate(R.layout.favourites_layout, parent, false); //is parent = FavoritesFrag?
            final FavouritesViewHolder viewHolder = new FavouritesViewHolder();
            viewHolder.titleDisplayText = (TextView) convertView.findViewById(R.id.titleDisplayText);

            // We set the view holder as tag of the convertView so we can access the view holder later on.
            convertView.setTag(viewHolder);
        }

        // Retrieve the view holder from the convertView
        final FavouritesViewHolder viewHolder = (FavouritesViewHolder) convertView.getTag();

        // Bind the values to the views
        try {
            viewHolder.titleDisplayText.setText(item.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            int ID = 0;
            ID = Integer.parseInt(item.getString("mediaId"));
            viewHolder.set_mediaID(ID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Button deleteView = convertView.findViewById(R.id.deleteButton);
        deleteView.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                Log.e("Click Error", "Click error");
                AlertDialog.Builder adb=new AlertDialog.Builder(parent.getContext());
                adb.setTitle("Remove?");
                adb.setMessage("Are you sure you want to remove " + viewHolder.titleDisplayText.getText().toString());
                final int positionToRemove = position; //may have bug with FINAL INT POSITION USAGE
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        items.remove(positionToRemove);
                        //TODO: media ID / user info / user type
                        RemoveFavorite removeRequest = (RemoveFavorite) new RemoveFavorite((MainActivity) parent.getContext(), v).execute(Integer.toString(viewHolder.get_mediaID()), AppGlobals.user, AppGlobals.userType);
                        notifyDataSetChanged(); //USED TO NOTIFY ADAPTER, MAY NOT BE NEEDED INSIDE ADAPTER CLASS
                    }});
                adb.show();
            }
        });

        return convertView;
    }
}
