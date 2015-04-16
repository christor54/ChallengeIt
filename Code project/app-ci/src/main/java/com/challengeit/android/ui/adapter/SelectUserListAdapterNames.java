package com.challengeit.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import com.challengeit.android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectUserListAdapterNames extends ArrayAdapter<String> {
    private HashMap<Integer, Boolean> myChecked = new HashMap<Integer, Boolean>();
    private boolean startWithItemsChecked;
    private final ArrayList<String> users;
    private int playerItemLayoutId;

    public SelectUserListAdapterNames(Context context, int itemLayoutId, ArrayList<String> users, boolean startWithItemsChecked) {
        super(context, itemLayoutId, users);
        this.users = users;
        this.playerItemLayoutId = itemLayoutId;
        this.startWithItemsChecked = startWithItemsChecked;
        if(users!=null) {
            for (int i = 0; i < this.users.size(); i++) {
                myChecked.put(i, false);
            }
        }
    }

    public void toggleChecked(int position){
        if(myChecked.get(position)){
            myChecked.put(position, false);
        }else{
            myChecked.put(position, true);
        }
        notifyDataSetChanged();
    }

    public List<Integer> getCheckedItemPositions() {
        List<Integer> checkedItemPositions = new ArrayList<Integer>();
        for (int i = 0; i < myChecked.size(); i++) {
            if (myChecked.get(i)) {
                (checkedItemPositions).add(i);
            }
        }
        return checkedItemPositions;
    }

    public List<String> getCheckedItems(){
        List<String> checkedItems = new ArrayList<String>();
        for(int i = 0; i < myChecked.size(); i++){
            if (myChecked.get(i)){
                (checkedItems).add(users.get(i));
            }
        }
        return checkedItems;
    }

    public static class ViewHolder {//use view holder for optimizing the list view
//        ImageView icon;
        CheckedTextView name;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        ViewHolder holder = null;

        if (v == null) {//if not already inflated, inflate v
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_user_checkable, null);
            holder = new ViewHolder();
//            holder.icon = (ImageView) v.findViewById(R.id.user_profile_icon);
            holder.name = (CheckedTextView) v.findViewById(R.id.user_name_checkable_text_view);
            v.setTag(holder);
        } else {
             holder = (ViewHolder) v.getTag();
        }
        String title, subtitle, user;
        if(users!=null) {
            user = users.get(position);

            Boolean checked;
            if (user != null) {
                //            if(holder.icon!=null) {
                //            }
                if (holder.name != null) {
                    holder.name.setText(user);
                    checked = myChecked.get(position);
                    if (checked != null) {
                        holder.name.setChecked(checked);
                    }
                }
            }
        }
        return v;
    }

    public void addFriend(String newFriend) {
        this.add(newFriend);
        this.notifyDataSetChanged();
    }
}

