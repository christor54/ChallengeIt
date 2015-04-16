package com.challengeit.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.challengeit.android.R;
import com.challengeit.android.challengeit.userLocalEndpoint.model.Response;

import java.util.ArrayList;

public class ResponsesListAdapter extends ArrayAdapter<Response> {
    private final ArrayList<Response> responses;

    private int gameItemLayoutId;


    public ResponsesListAdapter(Context context, int itemLayoutId, ArrayList<Response> responses1) {
        super(context, itemLayoutId, responses1);
        this.responses = responses1;
        this.gameItemLayoutId = itemLayoutId;
    }

    static class ViewHolder {//use view holder for optimizing the list view
        TextView title;
        TextView description;
        TextView timeLeft;
        ImageView mediaPreview;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder = null;

        if (v == null) {//if not already inflated, inflate v
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_response, null);
            holder = new ViewHolder();
            holder.title = (TextView) v.findViewById(R.id.response_item_title);
            holder.description = (TextView) v.findViewById(R.id.response_item_description);
            holder.timeLeft = (TextView) v.findViewById(R.id.response_item_time_left);
            holder.mediaPreview = (ImageView) v.findViewById(R.id.response_item_media_preview);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        Response response = responses.get(position);
        if (response != null) {
            if(holder.title!=null) {
                holder.title.setText(response.getTitle());
            }
            if(holder.description!=null&&response.getDescription()!=null) {
                holder.description.setText(response.getDescription());
            }
        }

        return v;

    }
}

