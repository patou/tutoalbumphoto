package sfeir.gwt.albumphoto.server.rpc.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import sfeir.gwt.albumphoto.client.model.Photographie;
import sfeir.gwt.albumphoto.client.rpc.PicasaService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class PicasaServiceImpl extends RemoteServiceServlet implements
        PicasaService {

    private static final long serialVersionUID = 105701672087995778L;

    private static final int PHOTOS_PAR_PAGE = 10;

    @Override
    public List<Photographie> getPhotos(String sujet, Integer page) throws Exception {

        ArrayList<Photographie> photos = new ArrayList<Photographie>();

        // création d'une fabrique de documents
        DocumentBuilderFactory fabrique = DocumentBuilderFactory
                .newInstance();

        // création d'un constructeur de documents
        DocumentBuilder constructeur = fabrique.newDocumentBuilder();

        int index = 1 + page * PHOTOS_PAR_PAGE;

        URL fluxRss = new URL(
                "http://picasaweb.google.com/data/feed/api/all?kind=photo&q="
                        + sujet + "&start-index=" + index + "&max-results="
                        + PHOTOS_PAR_PAGE);

        Document d = constructeur.parse(fluxRss.openConnection()
                .getInputStream());

        NodeList entrees = d.getElementsByTagName("entry");

        for (int i = 0; i < entrees.getLength(); i++) {

            NodeList childNodes = entrees.item(i)
                    .getChildNodes();
            String picasaUrlphotoUrl = childNodes.item(9).getAttributes().getNamedItem("href").getNodeValue();
            String photoUrl = childNodes.item(6).getAttributes().getNamedItem("src").getNodeValue();
            int indexOf = photoUrl.lastIndexOf("/");
            String photoMiniatureUrl = photoUrl.substring(0, indexOf) + "/s144" + photoUrl.substring(indexOf);
            Photographie pt = new Photographie(photoMiniatureUrl, picasaUrlphotoUrl, childNodes.item(4).getFirstChild().getNodeValue());
            photos.add(pt);
        }
        return photos;
    }
}
