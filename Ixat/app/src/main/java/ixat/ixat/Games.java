package ixat.ixat;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hp on 2016-06-23.
 */
public class Games implements Serializable {

    private int id;
    private String gameUri;
    private String gameDescription;
    private Drawable icon;
    private ArrayList<ApplicationInfo> selectedGames;

    public Games(){};

    public Games(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public Games(String gameUri, String gameDescription, Drawable icon)
    {
        this.id = this.getId() + 1;
        this.gameUri = gameUri;
        this.gameDescription = gameDescription;
        this.icon = icon;
    }

    public String getGameUri() {
        return gameUri;
    }

    public void setGameUri(String gameUri) {
        this.gameUri = gameUri;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public ArrayList<ApplicationInfo> getSelectedGames() {
        return selectedGames;
    }

    public void setSelectedGames(ArrayList<ApplicationInfo> selectedGames) {
        this.selectedGames = selectedGames;
    }
}
