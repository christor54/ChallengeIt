package com.challengeit.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Challenge;

import java.util.ArrayList;

public class ChallengesListAdapter extends ArrayAdapter<Challenge> {
    private final ArrayList<Challenge> challenges;
    private int gameItemLayoutId;


    public ChallengesListAdapter(Context context, int itemLayoutId, ArrayList<Challenge> challenges) {
        super(context, itemLayoutId, challenges);
        this.challenges = challenges;
        this.gameItemLayoutId = itemLayoutId;
    }

    static class ViewHolder {//use view holder for optimizing the list view
        TextView title;
        TextView description;
        TextView timeLeft;
        TextView pointValue;
        ImageView mediaPreview;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder = null;

        if (v == null) {//if not already inflated, inflate v
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_challenge_player, null);
            holder = new ViewHolder();
            holder.title = (TextView) v.findViewById(R.id.challenge_item_title);
            holder.description = (TextView) v.findViewById(R.id.challenge_item_description);
            holder.timeLeft = (TextView) v.findViewById(R.id.challenge_item_time_left);
            holder.pointValue = (TextView) v.findViewById(R.id.challenge_item_point_value);
            holder.mediaPreview = (ImageView) v.findViewById(R.id.challenge_item_media_preview);
            v.setTag(holder);
        } else {
             holder = (ViewHolder) v.getTag();
        }

        Challenge challenge = challenges.get(position);
        if (challenge != null) {
            if(holder.title!=null) {
                holder.title.setText(challenge.getTitle());
            }

            if(holder.description!=null&&challenge.getDescription()!=null) {
                holder.description.setText(challenge.getDescription());
            }
//            if(holder.pointValue!=null) {
//                holder.pointValue.setText(Integer.toString(challenge_player.getPointValue()));
//            }
        }

        return v;

    }
}

