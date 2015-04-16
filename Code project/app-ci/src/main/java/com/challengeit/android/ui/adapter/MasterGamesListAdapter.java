package com.challengeit.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.MasterWithGame;

import java.util.ArrayList;

public class MasterGamesListAdapter extends ArrayAdapter<MasterWithGame> {
    private final ArrayList<MasterWithGame> masters;
    private int gameItemLayoutId;


    public MasterGamesListAdapter(Context context, int itemLayoutId, ArrayList<MasterWithGame> masters) {
        super(context, itemLayoutId, masters);
        this.masters = masters;
        this.gameItemLayoutId = itemLayoutId;
    }

    static class ViewHolder {//use view holder for optimizing the list view
        TextView title;
        TextView subtitle;
        TextView numberChallengeCompleted;
        TextView numberResponse;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder = null;

        if (v == null) {//if not already inflated, inflate v
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_game_master, null);
            holder = new ViewHolder();
            holder.title = (TextView) v.findViewById(R.id.game_title);
            holder.subtitle = (TextView) v.findViewById(R.id.game_subtitle);
            holder.numberChallengeCompleted = (TextView) v.findViewById(R.id.game_number_challenge_completed);
            holder.numberResponse = (TextView) v.findViewById(R.id.game_number_response);
            v.setTag(holder);
        } else {
             holder = (ViewHolder) v.getTag();
        }

        MasterWithGame master = masters.get(position);

        if (master != null) {
            if(master.getGame()!=null) {
                if (holder.title != null) {
                    holder.title.setText(master.getGame().getGameName());
                }
                if (holder.subtitle != null) {
                    holder.subtitle.setText(master.getGame().getDescription());
                }
                if (holder.numberChallengeCompleted != null) {
                    if(master.getGame().getChallenges()!=null)
                        holder.numberChallengeCompleted.setText(String.valueOf(master.getGame().getChallenges().size()));
                }
                if (holder.numberResponse != null) {
                    holder.numberResponse.setText("0");
                }
            }
        }
        return v;
    }

    public void addGame(MasterWithGame masterWithGame){
        this.add(masterWithGame);
        this.notifyDataSetChanged();
    }
}

