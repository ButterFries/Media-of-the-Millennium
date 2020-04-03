package com.developersOfTheMillennium.motm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.RetrieveList;
import com.developersOfTheMillennium.motm.utils.getList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserProfilePageFragment extends Fragment implements View.OnClickListener,PopupMenu.OnMenuItemClickListener{
    private MenuItem list_menu1;
    private MenuItem list_menu2;
    private MenuItem list_menu3;
    private JSONObject return_list = null;
    private int save_menu = 0;
    private List<MenuItem> menu_holders = new ArrayList<MenuItem>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.user_profile_fragment, container, false);
        // Need to add button functioning here.

        final Button favoritesBtn = rootView.findViewById(R.id.favoritesButton);
        final Button createAList = rootView.findViewById(R.id.CreateList);
        favoritesBtn.setOnClickListener(this);
        createAList.setOnClickListener(this);


        final Button bookmarksBtn = rootView.findViewById(R.id.bookmarksButton);
        bookmarksBtn.setOnClickListener(this);

        return rootView;
    }
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.favoritesButton:
                //Log.d("MediaProfileFrag","review button pressed");
                fragment = new FavoritesFragment();
                replaceFragment(fragment);
                break;
            case R.id.CreateList:
               PopupMenu popup = new PopupMenu(getActivity(), view);

               MenuInflater inflater = popup.getMenuInflater();
               popup.setOnMenuItemClickListener(this);
               inflater.inflate(R.menu.list_menu, popup.getMenu());
               menu_holders.add(list_menu1=popup.getMenu().findItem(R.id.placeholder_1));
               menu_holders.add(list_menu2=popup.getMenu().findItem(R.id.placeholder_2));
               menu_holders.add(list_menu3=popup.getMenu().findItem(R.id.placeholder_3));
               if(save_menu == 0){
                   try {
                       fetch_list_names(menu_holders);
                       save_menu = 1;
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
               System.out.println(save_menu);
               popup.show();

               break;

            case R.id.bookmarksButton:
//              fragment = new TemporaryReportButtonFrag(); //uncomment to test report button
                fragment = new BookmarksFragment();  //comment if uncommented out line above
                replaceFragment(fragment);
                break;


        }
    }
    public boolean onMenuItemClick(MenuItem item) {
        Fragment newPage = null;
//        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.create_list:
                newPage = new MediaListPageFragment();  //comment if uncommented out line above
                replaceFragment(newPage);
                return true;
            case R.id.placeholder_1:
                // do your code
                fetch_list(list_menu1.getTitle().toString(),return_list);
                return true;
            case R.id.placeholder_2:
                // do your code
                fetch_list(list_menu2.getTitle().toString(),return_list);
                return true;
            case R.id.placeholder_3:
                // do your code
                fetch_list(list_menu3.getTitle().toString(),return_list);
                return true;
            default:
                return false;
        }
    }
    public void fetch_list_names(List<MenuItem> menu_holders) throws Exception {

        getList name = (getList) new getList((MainActivity) getActivity()).execute(AppGlobals.user,menu_holders);
    }
    public void fetch_list(String list_name, JSONObject return_list){

        RetrieveList list = (RetrieveList) new RetrieveList((MainActivity) getActivity()).execute(AppGlobals.user,list_name,return_list);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("save_menu", 1);
        super.onSaveInstanceState(savedInstanceState);
    }
    public void replaceFragment(Fragment someFragment) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
