package com.challengeit.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.PlayerWithGame;

import java.util.ArrayList;

public class PlayerGamesListAdapter extends ArrayAdapter<PlayerWithGame> {
    private final ArrayList<PlayerWithGame> players;
    private int gameItemLayoutId;



    public PlayerGamesListAdapter(Context context, int itemLayoutId, ArrayList<PlayerWithGame> games) {
        super(context, itemLayoutId, games);
        this.players = games;
        this.gameItemLayoutId = itemLayoutId;
    }

    static class ViewHolder {//use view holder for optimizing the list view
        TextView title;
        TextView subtitle;
        TextView playerName;
        TextView playerPoints;
        TextView numberChallenge;
        TextView pointsToBeEarned;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder = null;

        if (v == null) {//if not already inflated, inflate v
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_game_play, null);
            holder = new ViewHolder();
            holder.title = (TextView) v.findViewById(R.id.game_title);
            holder.subtitle = (TextView) v.findViewById(R.id.game_subtitle);
            holder.playerName = (TextView) v.findViewById(R.id.game_player_name);
            holder.playerPoints = (TextView) v.findViewById(R.id.game_player_points);
            holder.numberChallenge = (TextView) v.findViewById(R.id.game_number_challenge);
            holder.pointsToBeEarned = (TextView) v.findViewById(R.id.game_points_to_be_earned);
            v.setTag(holder);
        } else {
             holder = (ViewHolder) v.getTag();
        }

       // List <Game> games = new getGamesFromPlayerTask();

        PlayerWithGame player = players.get(position);
        String title, subtitle,playername;
        if (player != null) {
            if(player.getGame()!=null) {
                if (holder.title != null) {
                    title=player.getGame().getGameName();
                    holder.title.setText(player.getGame().getGameName());
                }
                if (holder.subtitle != null) {
                    subtitle=player.getGame().getDescription();
                    holder.subtitle.setText(player.getGame().getDescription());
                }
                if (holder.playerName != null) {
                    playername=player.getPlayer().getPlayername();
                    holder.playerName.setText(player.getPlayer().getPlayername());
                }
                if (holder.playerPoints != null) {
                    holder.playerPoints.setText(player.getPlayer().getScore().toString());
                }
                if (holder.numberChallenge != null) {
                    holder.numberChallenge.setText("0");
                }
                if (holder.pointsToBeEarned != null) {
                    holder.pointsToBeEarned.setText("0");
                }
            }
        }
        return v;


    }
}

