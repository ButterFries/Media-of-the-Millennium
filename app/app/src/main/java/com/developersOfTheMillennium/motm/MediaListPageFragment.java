package com.developersOfTheMillennium.motm;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.developersOfTheMillennium.motm.utils.GetPicture;
import com.developersOfTheMillennium.motm.utils.saveMediaList;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MediaListPageFragment extends Fragment implements View.OnClickListener {
    private List<ImageButton> add_media = new ArrayList<ImageButton>();
    private EditText list_title;
    private TextView username;
    private ImageButton media1;
    private ImageButton media2;
    private ImageButton media3;
    private ImageButton media4;
    private ImageButton media5;
    private ImageButton media6;
    private ImageButton media7;
    private ImageButton media8;
    private ImageButton media9;
    private ImageButton media10;
    private ImageButton media11;
    private ImageButton media12;
    private JSONArray item_list = new JSONArray();
    private JSONArray new_media = null;
    private Button save;
    private SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_media_list_page_fragment, container, false);


        add_media.add(media1 = v.findViewById(R.id.imageButton));
        add_media.add(media2 = v.findViewById(R.id.imageButton2));
        add_media.add(media3 = v.findViewById(R.id.imageButton3));
        add_media.add(media4 = v.findViewById(R.id.imageButton4));
        add_media.add(media5 = v.findViewById(R.id.imageButton5));
        add_media.add(media6 = v.findViewById(R.id.imageButton6));
        add_media.add(media7 = v.findViewById(R.id.imageButton7));
        add_media.add(media8 = v.findViewById(R.id.imageButton8));
        add_media.add(media9 = v.findViewById(R.id.imageButton9));
        add_media.add(media10 = v.findViewById(R.id.imageButton10));
        add_media.add(media11 = v.findViewById(R.id.imageButton11));
        add_media.add(media12 = v.findViewById(R.id.imageButton12));
        username = v.findViewById(R.id.username_text);
        username.setText(AppGlobals.user);
        list_title = v.findViewById(R.id.list_name);
        save = v.findViewById(R.id.save_button);
        save.setOnClickListener(this);
        String search = null;

        searchView = v.findViewById(R.id.search);

        Bundle recieved = getArguments();
        if(recieved != null){
            list_title.setText(recieved.get("list_name").toString());
            for (int i =0; i<new_media.length();i++){
                try {
                    change_image(add_media,new_media.get(i).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        for(int i = 0; i<add_media.size();i++){
            add_media.get(i).setOnClickListener(this);
        }
        list_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        return v;


    }
    public void onClick(View view) {

        switch(view.getId()){
            case R.id.save_button:
                if(save(list_title.getText().toString(),item_list.toString())){
                    System.out.println(list_title.getText().toString() + item_list.toString());
                }
                break;
            case R.id.imageButton:
                case_switch(add_media,media1,"2");
                break;
            case R.id.imageButton2:
                case_switch(add_media,media2,"2");
                break;
            case R.id.imageButton3:
                case_switch(add_media,media3,"2");
                break;
            case R.id.imageButton4:
                case_switch(add_media,media4,"2");
                break;
            case R.id.imageButton5:
                case_switch(add_media,media5,"2");
                break;
            case R.id.imageButton6:
                case_switch(add_media,media6,"2");
                break;
            case R.id.imageButton7:
                case_switch(add_media,media7,"2");
                break;
            case R.id.imageButton8:
                case_switch(add_media,media8,"2");
                break;
            case R.id.imageButton9:
                case_switch(add_media,media9,"2");
                break;
            case R.id.imageButton10:
                case_switch(add_media,media10,"2");
                break;
            case R.id.imageButton11:
                case_switch(add_media,media11,"2");
                break;
            case R.id.imageButton12:
                case_switch(add_media,media12,"2");
                break;
        }


    }

    public void change_image(List<ImageButton>add_media, String mediaId) throws Exception {
        for(int i = 0; i<add_media.size();i++){
            if(add_media.get(i).getTag().equals("0")){
                try {
                     GetPicture pic = (GetPicture) new GetPicture((MainActivity) getActivity()).execute(mediaId, add_media.get(i));
                     add_media.get(i).setTag("1");
                     item_list.put(mediaId);
                } catch (Exception e) {
                    throw new Exception("(getPicture) -- something went wrong when retrieving picture");
                }
                add_media.get(i+1).setVisibility(View.VISIBLE);
                System.out.println(add_media.get(i+1).toString() +add_media.get(i+1).getVisibility());
                break;
            }
        }

    }
    public int check_case(ImageButton media){
        int holds_image = 1;
        if(media.getTag().equals("0")){
            holds_image = 0;
        }
        return holds_image;
    }
    public void case_switch(List<ImageButton>add_media, ImageButton media,String mediaId){
        Fragment fragment;
        int check = check_case(media);
        switch(check){
            case 0:
                try{
                    change_image(add_media,mediaId);
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
//            case 1:
////                //may elimiate this not sure yet
////                fragment = new MediaProfilePageFragment();
////                replaceFragment(fragment);
////                break;
        }
    }
    private boolean save(String list_title, String media_list){
        if (list_title.length()!=0 ) {
            saveMediaList saveList = (saveMediaList) new saveMediaList((MainActivity) getActivity()).execute(list_title,media_list);
            Log.i("Save List", "Valid credentials");
            return true;
        }
        Log.i("save List", "Invalid credentials");
        return false;
    }
    public List<ImageButton> getAdd_media(){
        return this.add_media;
    }
    public void setAdd_media(JSONArray new_media){
        this.new_media = new_media;
    }

    public void replaceFragment(Fragment someFragment) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
