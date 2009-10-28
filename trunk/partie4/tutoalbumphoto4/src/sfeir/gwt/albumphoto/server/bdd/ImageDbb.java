package sfeir.gwt.albumphoto.server.bdd;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

import sfeir.gwt.albumphoto.server.entity.Photo;

public class ImageDbb {
    private static PersistenceManagerFactory pmf;
    private static PersistenceManager pm;
    
    private static final int PHOTOS_PAR_PAGE = 10;
    
    public static synchronized PersistenceManager getPersistenceManager() {
        if(pmf == null)
            pmf = JDOHelper.getPersistenceManagerFactory("nontransactional-datasource");
        if(pm == null || pm.isClosed()){
            pm = pmf.getPersistenceManager();
        }
        return pm;
    }
    
    public Photo creer(String titre, byte[] contenu) {
        Photo images = new Photo();
        images.setTitre(titre);
        images.setContenu(new Blob(contenu));
        PersistenceManager persistenceManager = getPersistenceManager();
        return persistenceManager.makePersistent(images);
    }
    
    public Photo rechercher(Key cle) {
        PersistenceManager persistenceManager = getPersistenceManager();
        return persistenceManager.getObjectById(Photo.class, cle);
    }
    
    public void supprimer(Key cle) {
        PersistenceManager persistenceManager = getPersistenceManager();
        Photo image = persistenceManager.getObjectById(Photo.class, cle);
        persistenceManager.deletePersistent(image);
    }
    
    public List<Photo> liste(int page) {
        List<Photo> liste = new ArrayList<Photo>();
        PersistenceManager persistenceManager = getPersistenceManager();
        int premiereImages = (page - 1) * PHOTOS_PAR_PAGE;
        Query requete = persistenceManager.newQuery(Photo.class);
        requete.setOrdering("titre ASC");
        requete.setRange(premiereImages, premiereImages + PHOTOS_PAR_PAGE);
        liste = (List<Photo>) requete.execute();
        return liste;
    }
}
