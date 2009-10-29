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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Composant permettant d'afficher une photo et un titre
 * 
 */

public class Miniature extends Composite {
    interface Binder extends UiBinder<Widget, Miniature> {
    }

    private static final Binder binder = GWT.create(Binder.class);

    @UiField
    Image miniature;
    @UiField
    Label label;

    private String destination;

    /**
     * Affiche une photo miniature avec le titre centré en dessous
     * 
     * @param url
     *            Url de la photo
     * @param titre
     *            Titre de la photo
     */
    public Miniature(String url, String titre, String destination) {
        this.destination = destination;
        initWidget(binder.createAndBindUi(this));
        miniature.setUrl(url);
        label.setText(titre);
    }

    @UiHandler("miniature")
    public void onClick(ClickEvent event) {
        Window.open(destination, "Image", "menubar=no, status=no, scrollbars=no, menubar=no, width=200, height=100");
    }
}
