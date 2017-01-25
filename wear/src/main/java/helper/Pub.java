package helper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by diegocunha on 11/01/17.
 */

public class Pub {

    String id;
    @SerializedName("name")
    String namePub;
    @SerializedName("street")
    String streetPub;


    public Pub() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamePub() {
        return namePub;
    }

    public void setNamePub(String namePub) {
        this.namePub = namePub;
    }

    public String getStreetPub() {
        return streetPub;
    }

    public void setStreetPub(String streetPub) {
        this.streetPub = streetPub;
    }
}