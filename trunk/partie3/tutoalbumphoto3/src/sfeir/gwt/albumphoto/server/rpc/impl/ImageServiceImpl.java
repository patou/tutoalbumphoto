package sfeir.gwt.albumphoto.server.rpc.impl;

import java.util.ArrayList;
import java.util.List;

import sfeir.gwt.albumphoto.client.model.Photographie;
import sfeir.gwt.albumphoto.client.rpc.ImageService;
import sfeir.gwt.albumphoto.server.bdd.ImageDbb;
import sfeir.gwt.albumphoto.server.entity.Photo;

import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ImageServiceImpl extends RemoteServiceServlet implements ImageService {
    /**
     * 
     */
    private static final long serialVersionUID = 6717959913279362762L;

    public List<Photographie> getImages(int page) {
        ImageDbb service = new ImageDbb();
        List<Photo> liste = service.liste(page);
        List<Photographie> listePhoto = new ArrayList<Photographie>();
        for (Photo image : liste) {
            Photographie photo = new Photographie();
            photo.setPhotoTitre(image.getTitre());
            String cle = KeyFactory.keyToString(image.getCle());
            photo.setPhotoMiniatureUrl("/image/?taille=mini&cle="+cle);
            photo.setPhotoUrl("/image/?cle="+cle);
            listePhoto.add(photo);
        }
        return listePhoto;
    }
}
