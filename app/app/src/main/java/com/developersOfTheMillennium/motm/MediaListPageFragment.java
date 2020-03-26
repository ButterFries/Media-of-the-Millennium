package com.developersOfTheMillennium.motm;

import android.os.Bundle;
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

import com.developersOfTheMillennium.motm.utils.GetMediaIDs;
import com.developersOfTheMillennium.motm.utils.GetPicture;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MediaListPageFragment extends Fragment implements View.OnClickListener {
    private List<ImageButton> add_media = new ArrayList<ImageButton>();
    private EditText review_title;
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
    private FloatingActionButton add;
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
        searchView = v.findViewById(R.id.search);

        for(int i = 0; i<add_media.size();i++){
            add_media.get(i).setOnClickListener(this);
        }


        return v;


    }
    public void onClick(View view) {
        Fragment fragment;
        for(int i =0;i<add_media.size();i++){
            if(add_media.get(i).getTag().equals("0")){
                try {
                    change_image(add_media,"2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            }else if(add_media.get(i).getTag().equals("1")){
                System.out.println(add_media.get(i).getTag());
                Log.d("MediaProfileFrag","media ");
                fragment = new MediaProfilePageFragment();
                replaceFragment(fragment);
                break;
            }
        }


    }

    public void change_image(List<ImageButton>add_media, String mediaId) throws Exception {
        for(int i = 0; i<add_media.size();i++){
            if(add_media.get(i).getTag().equals("0")){
                try {
                     GetPicture pic = (GetPicture) new GetPicture((MainActivity) getActivity()).execute(mediaId, add_media.get(i));
                     add_media.get(i).setTag("1");
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

    public void replaceFragment(Fragment someFragment) {
        //FragmentTransaction transaction = getFragmentManager().beginTransaction();
        androidx.fragment.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_view, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
