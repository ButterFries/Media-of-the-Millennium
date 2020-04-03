package com.developersOfTheMillennium.motm.utils.Searchbar;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.developersOfTheMillennium.motm.CustomSearchAdapter;
import com.developersOfTheMillennium.motm.MainActivity;
import com.developersOfTheMillennium.motm.R;
import com.developersOfTheMillennium.motm.ssl.SecureHTTPClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.developersOfTheMillennium.motm.MainActivity.JSON;

public class GetSearchResults extends AsyncTask<String, Void, Boolean> {

    private static MainActivity activity;
    private static SecureHTTPClient HTTPSClient;

    private JSONArray matches;
    private SearchView searchView;
    private String search;

    public GetSearchResults(MainActivity a, SearchView s) {
        activity = a;
        HTTPSClient = new SecureHTTPClient(activity.getResources().getString(R.string.server_address)
                +":"+activity.getResources().getString(R.string.server_port), activity);

        this.searchView = s;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        search = params[0];

        return review(search);
    }

    protected boolean review(String search) {

        //Email
        JSONObject data = new JSONObject();

        try {
            data.put("query", search);

            Log.i("query", search);

            JSONObject rtn = postRequest("mediaTitleSearch", data);
            int error_code = rtn.getInt("error_code");

            if (error_code == 0) {
                matches = rtn.getJSONArray("matches");
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            Log.e("ERROR Login", "JSON Parsing: " + e);
        }

        return false;
    }


    private JSONObject postRequest(String context, JSONObject data) {
        JSONObject rtn = null;

        /***   create http client request  ***/
        Log.i("ValidateAccount", "Creating "+context+" request");

        RequestBody requestBody = RequestBody.create(JSON, data.toString());

        Request request = new Request.Builder()
                .url("https://"+activity.getResources().getString(R.string.server_address)+
                        ":"+activity.getResources().getString(R.string.server_port)+"/"+context)
                .addHeader("User-Agent", "motm "+context+" request")  // add request headers
                .post(requestBody)
                .build();

        try (Response response = HTTPSClient.run(request)) {


            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            String responseData = response.body().string();

            try {
                rtn = new JSONObject(responseData);
                Log.i("postRequest", "--complete");
            }catch (JSONException e) {
                Log.e("ERROR postRequest "+context, "String to Json Parse Error");
                throw new JSONException("String to Json Parse Error");
            }

        } catch (Exception e) {
            Log.e("HTTPS Request Error", ""+e);
        }

        return rtn;
    }

    // the onPostexecute method receives the return type of doInBackGround()
    protected void onPostExecute(Boolean result) {
        // do something with the result, for example display the received Data in a ListView
        // in this case, "result" would contain the "someLong" variable returned by doInBackground();

        String[] columns = new String[] {"_id", "search_row_title", "search_row_id"};
        int[] to = new int[] {-1, R.id.search_row_title, R.id.search_row_id};

        MatrixCursor cursor = new MatrixCursor(columns);

        //TAKE TO HOME PAGE
        if (result && search.equals(searchView.getQuery().toString())) {

            for (int i = 0; i < matches.length(); i++) {
                try {
                    String title = ((JSONObject)matches.get(i)).getString("title");
                    String mediaID = ((JSONObject)matches.get(i)).getString("mediaID");

                    Log.i("title ", title);

                    if (title.isEmpty() || title==null)
                        throw new JSONException("Could not find media title");

                    cursor.newRow()
                            .add("_id", i)
                            .add("search_row_title", title)
                            .add("search_row_id", mediaID);
                }
                catch (Exception e) {
                    Log.i("GetSearchResults", "Couldn't get search results");
                }
            }

            CustomSearchAdapter adap = new CustomSearchAdapter(activity, R.layout.search_row, cursor, columns, to);

            try {
                searchView.getSuggestionsAdapter().swapCursor(cursor);
            } catch (Exception e) {
                searchView.setSuggestionsAdapter(adap);
            }
        }
    }
}
