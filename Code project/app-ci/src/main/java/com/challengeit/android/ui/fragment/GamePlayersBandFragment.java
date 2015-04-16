package com.challengeit.android.ui.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.challengeit.android.R;
import com.challengeit.android.persistence.PersistenceSingleton;

/*
 * Expose the rank of players of the game
 */
public class GamePlayersBandFragment extends Fragment{
    public static final String ARG_SECTION_NUMBER = "section_number";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_game_players_band, container, false);
        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new PlayerAdapter(getActivity()));
        return rootView;
    }

    public class PlayerAdapter extends BaseAdapter {
        private Context mContext;

        public PlayerAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return PersistenceSingleton.getInstance().getGameClicked().getPlayers().size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);

                imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT
                       , GridView.LayoutParams.WRAP_CONTENT));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(1, 1, 1, 1);


                textView = new TextView(mContext);
                textView.setText(PersistenceSingleton.getInstance().getGameClicked().getPlayers().get(position).getPlayername());

                textView.setPadding(1, 100, 1, 1);
            } else {
                imageView = (ImageView) convertView;
            }
            if(PersistenceSingleton.getInstance().getIsMasterClicked()) {
                imageView.setImageResource(R.drawable.christophe_profile_facebook);
            }
            else{
                imageView.setImageResource(R.drawable.profile_picture_chris);
            }
            return imageView;
        }


    }
}
