package com.developersOfTheMillennium.motm;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class FavouritesAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private final List<MediaProfilePage> items; //fix MPP is inside AppDB

    private FavouritesAdapter(Context context, List<MediaprofilePage> items) {
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public Item getItem(int position) {
        return this.items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.items.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MediaProfilePage item = getItem(position);

        if(convertView == null) {
            // If convertView is null we have to inflate a new layout
            convertView = this.inflater.inflate(R.layout.favourites_layout, parent, false); //is parent = FavoritesFrag?
            final FavouritesViewHolder viewHolder = new FavouritesViewHolder();
            viewHolder.titleDisplayText = (TextView) convertView.findViewById(R.id.titleDisplayText);

            //viewHolder.set_mediaID(convertView.findViewById(R.id.mediaID));

            // We set the view holder as tag of the convertView so we can access the view holder later on.
            convertView.setTag(viewHolder);
        }

        // Retrieve the view holder from the convertView
        final FavouritesViewHolder viewHolder = (FavouritesViewHolder) convertView.getTag();

        // Bind the values to the views
        viewHolder.titleDisplayText.setText(item.getTitle());
        viewHolder.set_mediaID(item.getID());

        return convertView;
    }
}
