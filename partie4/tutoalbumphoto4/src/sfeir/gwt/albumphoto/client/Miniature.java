package sfeir.gwt.albumphoto.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

/**
 * Composant permettant d'afficher une photo et un titre
 * 
 */

public class Miniature extends Widget {
    interface Binder extends UiBinder<Element, Miniature> {
    }

    private static final Binder binder = GWT.create(Binder.class);


    @UiField
    ImageElement miniature;
    @UiField
    SpanElement label;

    /**
     * Affiche une photo miniature avec le titre centré en dessous
     * 
     * @param url
     *            Url de la photo
     * @param titre
     *            Titre de la photo
     */
    public Miniature(String url, String titre, final String destination) {
        setElement(binder.createAndBindUi(this));
        miniature.setSrc(url);
//        Image.wrap(miniature).addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//                Window.open(destination, "Image", "menubar=no, status=no, scrollbars=no, menubar=no, width=200, height=100");
//            }
//        });
        label.setInnerHTML(titre);
    }
}
