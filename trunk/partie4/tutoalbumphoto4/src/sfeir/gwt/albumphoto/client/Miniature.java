package sfeir.gwt.albumphoto.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Composant permettant d'afficher une photo et un titre
 * 
 */

public class Miniature extends Composite {
    interface Binder extends UiBinder<Widget, Miniature> {
    }

    private static final Binder binder = GWT.create(Binder.class);

    /**
     * Ces champs sont crées dans le fichier XML et seront automatiquement lié
     */
    @UiField
    Image miniature;
    @UiField
    Label label;

    // Liens à ouvir lors du clique sur l'image
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
        // On crée l'interface venant du fichier XML et on lie avec les propriétes de la classe
        initWidget(binder.createAndBindUi(this));
        // On initialise les widgets
        miniature.setUrl(url);
        label.setText(titre);
    }

    /**
     * Ce Handler sera automatiquement ajouté au clique sur la miniature et sur le label
     * revient à faire :
     * miniature.addClickHandler(this);
     * @param event
     */
    @UiHandler({"miniature","label"})
    public void onClick(ClickEvent event) {
        Window.open(destination, "Image", "menubar=no, status=no, scrollbars=no, menubar=no, width=200, height=100");
    }
}

