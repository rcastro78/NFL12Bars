package com.applaudostudios.nfl12bars.models;

import java.io.Serializable;

/**
 * Created by RafaelCastro on 28/5/15.
 */
public class BarVenue implements Serializable {

    private int id;
    private String name,address;
    private String imageUrl;
    private String schedules;
    private static final long serialVersionUID = -121394956765877456L;

    public BarVenue(int id, String name, String address, String imageUrl,String schedules) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
        this.schedules = schedules;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getSchedules() {
        return schedules;
    }

    public void setSchedules(String schedules) {
        this.schedules = schedules;
    }


}
