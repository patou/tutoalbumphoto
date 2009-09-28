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

public class ImageServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 6214634721115949600L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ImageDbb service = new ImageDbb();
        if (ServletFileUpload.isMultipartContent(req)) {
            ServletFileUpload servletFileUpload = new ServletFileUpload(new AppEngineFileItemFactory());
            try {
                List<FileItem> fileItems = servletFileUpload.parseRequest(req);
                String titre = null;
                byte[] contenu = null;
                for (FileItem fileItem : fileItems) {
                    if (!fileItem.isFormField()) {
                        contenu = IOUtils.toByteArray(fileItem.getInputStream());
                    } else {
                        titre = fileItem.getString();
                    }
                }
                if (titre == null || contenu == null) {
                    resp.getOutputStream().print("Tous les champs n'ont pas été remplis");
                    return;
                }
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
        String cle = req.getParameter("cle");
        String taille = req.getParameter("taille");
        if (cle == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "La clé de l'image est requise");
        } else {
            try {
                Key imageCle = KeyFactory.stringToKey(cle);
                Photo image = service.rechercher(imageCle);
                resp.setContentType("image/jpeg");
                if (taille != null && taille.equals("mini")) {
                    ImagesService imagesService = ImagesServiceFactory.getImagesService();
                    Image imageOriginale = ImagesServiceFactory.makeImage(image.getContenu().getBytes());
                    Transform resize = ImagesServiceFactory.makeResize(200, 300);
                    Image imageMiniature = imagesService.applyTransform(resize, imageOriginale);
                    resp.getOutputStream().write(imageMiniature.getImageData());
                }
                else {
                    resp.getOutputStream().write(image.getContenu().getBytes());
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, e.getLocalizedMessage());
            }
        }
    }

}
