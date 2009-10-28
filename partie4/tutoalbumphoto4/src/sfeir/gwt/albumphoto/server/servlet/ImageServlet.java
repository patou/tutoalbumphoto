package sfeir.gwt.albumphoto.server.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import sfeir.gwt.albumphoto.server.bdd.ImageDbb;
import sfeir.gwt.albumphoto.server.entity.Photo;
import sfeir.gwt.albumphoto.server.file.AppEngineFileItemFactory;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ImageServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 6214634721115949600L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ImageDbb service = new ImageDbb();
        // On vérifie que le formulaire est bien de type multipart
        if (ServletFileUpload.isMultipartContent(req)) {
            ServletFileUpload servletFileUpload = new ServletFileUpload(new AppEngineFileItemFactory());
            try {
                List<FileItem> fileItems = servletFileUpload.parseRequest(req);
                String titre = null;
                byte[] contenu = null;
                for (FileItem fileItem : fileItems) {
                    // Le champs contenant le fichier
                    if (!fileItem.isFormField()) {
                        contenu = IOUtils.toByteArray(fileItem.getInputStream());
                    }
                    // Le champ titre
                    else {
                        titre = fileItem.getString();
                    }
                }
                // Message d'erreur si tous les champs n'ont pas été remplie
                if (titre == null || contenu == null) {
                    resp.getOutputStream().print("Tous les champs n'ont pas été remplis");
                    return;
                }
                // Ajout de la photo dans la base de donnée
                service.creer(titre, contenu);
                resp.getOutputStream().print("Envoie réussis de la photo");
            } catch (Exception e) {
                System.out.println(e);
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ImageDbb service = new ImageDbb();
        String cle = req.getParameter("cle"); // Clé de la photo
        String taille = req.getParameter("taille"); // Si taille=mini redimentionner la photo
        if (cle == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "La clé de l'image est requise");
        } else {
            try {
                Key imageCle = KeyFactory.stringToKey(cle); // On transforme la chaine de caractère en une clé de la base de donnée
                Photo image = service.rechercher(imageCle);
                resp.setContentType("image/jpeg");
                if (taille != null && taille.equals("mini")) { // On affiche la version miniature de la photo
                    MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
                    if (!memcache.contains(imageCle)) {
                        ImagesService imagesService = ImagesServiceFactory.getImagesService();
                        Image imageOriginale = ImagesServiceFactory.makeImage(image.getContenu().getBytes());
                        Transform resize = ImagesServiceFactory.makeResize(200, 300);
                        Image imageMiniature = imagesService.applyTransform(resize, imageOriginale);
                        byte[] imageData = imageMiniature.getImageData();
                        memcache.put(imageCle, imageData); //On le stock dans le cache
                        resp.getOutputStream().write(imageData); // En écrit le contenu de la photo miniature dans la réponse de la servlet
                    } else {
                        resp.getOutputStream().write((byte[]) memcache.get(imageCle)); // En écrit le contenu du cache dans la réponse de la servlet
                    }
                } else {
                    resp.getOutputStream().write(image.getContenu().getBytes()); // En écrit le contenu de la photo dans la réponse de la servlet
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getLocalizedMessage()); // Si il y a une erreur
            }
        }
    }

}
