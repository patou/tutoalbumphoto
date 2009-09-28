package sfeir.gwt.albumphoto.server.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType=IdentityType.APPLICATION)
public class Photo {
    
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key cle;
    @Persistent
    private String titre;
    @Persistent
    private Blob contenu;
    
    public Photo() {
    }

    public Key getCle() {
        return cle;
    }

    public void setCle(Key cle) {
        this.cle = cle;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Blob getContenu() {
        return contenu;
    }

    public void setContenu(Blob contenu) {
        this.contenu = contenu;
    }
}
