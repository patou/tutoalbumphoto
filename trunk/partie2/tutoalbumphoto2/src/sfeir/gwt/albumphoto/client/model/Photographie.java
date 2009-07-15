package sfeir.gwt.albumphoto.client.model;

import java.io.Serializable;

public class Photographie implements Serializable {

    private static final long serialVersionUID = -3006587958250286140L;

    /**
     * L'url de la photo sur Picasa
     */
    public String picasaUrl;
    /**
     * L'url de la photo
     */
    public String photoUrl;
    /**
     * L'url de la photo miniature
     */
    public String photoMiniatureUrl;
    /**
     * Titre de la photo
     */
    public String photoTitre;

    public Photographie() {

    }

    public Photographie(String picasaUrl, String photoUrl, String photoTitle) {
        this.picasaUrl = picasaUrl;
        this.photoUrl = photoUrl;
        this.photoTitre = photoTitle;

        int index = photoUrl.lastIndexOf("/");
        photoMiniatureUrl = photoUrl.substring(0, index) + "/s144"
                + photoUrl.substring(index);
    }

    public String getPicasaUrl() {
        return picasaUrl;
    }

    public void setPicasaUrl(String picasaUrl) {
        this.picasaUrl = picasaUrl;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoMiniatureUrl() {
        return photoMiniatureUrl;
    }

    public void setPhotoMiniatureUrl(String photoMiniatureUrl) {
        this.photoMiniatureUrl = photoMiniatureUrl;
    }

    public String getPhotoTitre() {
        return photoTitre;
    }

    public void setPhotoTitre(String photoTitre) {
        this.photoTitre = photoTitre;
    }

}
