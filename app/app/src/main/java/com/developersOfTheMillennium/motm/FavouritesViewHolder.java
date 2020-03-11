package com.developersOfTheMillennium.motm;
import android.widget.TextView;

//hold a reference to the relevant Views in each row of the ListView so we don't have to perform the expensive findViewById() as often
public class FavouritesViewHolder {
    public TextView titleDisplayText;
    private int mediaID;

    public int get_mediaID() { return this.mediaID; }
    public void set_mediaID(int ID) { this.mediaID = ID;}


}


