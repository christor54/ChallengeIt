package com.challengeit.android;

import java.util.ArrayList;
import java.util.List;


public class DummyContent {


    public static List<DummyContent> items = new ArrayList<DummyContent>();

    static {
        items.add(new DummyContent(R.drawable.tile_a));
        items.add(new DummyContent(R.drawable.tile_b));
        items.add(new DummyContent(R.drawable.tile_c));
        items.add(new DummyContent(R.drawable.tile_d));
        items.add(new DummyContent(R.drawable.tile_e));
        items.add(new DummyContent(R.drawable.tile_f));
        items.add(new DummyContent(R.drawable.tile_g));
        items.add(new DummyContent(R.drawable.tile_h));
        items.add(new DummyContent(R.drawable.tile_i));
        items.add(new DummyContent(R.drawable.tile_j));
        items.add(new DummyContent(R.drawable.tile_k));
        items.add(new DummyContent(R.drawable.tile_l));
        items.add(new DummyContent(R.drawable.tile_m));
        items.add(new DummyContent(R.drawable.tile_n));
        items.add(new DummyContent(R.drawable.tile_o));
        items.add(new DummyContent(R.drawable.tile_p));
        items.add(new DummyContent(R.drawable.tile_q));
        items.add(new DummyContent(R.drawable.tile_r));
        items.add(new DummyContent(R.drawable.tile_s));
        items.add(new DummyContent(R.drawable.tile_t));
        items.add(new DummyContent(R.drawable.tile_u));
        items.add(new DummyContent(R.drawable.tile_v));
        items.add(new DummyContent(R.drawable.tile_w));
        items.add(new DummyContent(R.drawable.tile_x));
        items.add(new DummyContent(R.drawable.tile_y));
        items.add(new DummyContent(R.drawable.tile_z));
    }


    private String description;
    private int pictureId;


    public DummyContent(int drawableId) {
        this.description = null;
        this.pictureId = drawableId;
    }


    public DummyContent(String description, int drawableId) {
        this.description = description;
        this.pictureId = drawableId;
    }


    public void setName(String name) {
        this.description = name;
    }


    public String getName() {
        return description;
    }


    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }


    public int getPictureId() {
        return pictureId;
    }
}
