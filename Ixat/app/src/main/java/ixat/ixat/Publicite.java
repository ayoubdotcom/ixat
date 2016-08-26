package ixat.ixat;

/**
 * Created by hp on 2016-06-29.
 */
public class Publicite {
    private String descriptionPub;
    private String url;
    private int nbAffiche;

    public Publicite() {
    }

    public Publicite(String url, String descriptionPub, int nbVue) {
        this.url = url;
        this.descriptionPub = descriptionPub;
        this.nbAffiche = nbVue;
    }

    public String getDescriptionPub() {
        return descriptionPub;
    }

    public void setDescriptionPub(String descriptionPub) {
        this.descriptionPub = descriptionPub;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNbAffiche() {
        return nbAffiche;
    }

    public void setNbAffiche(int nbAffiche) {
        this.nbAffiche = nbAffiche;
    }
}
