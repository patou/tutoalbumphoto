package sfeir.gwt.albumphoto.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Composant permettant d'afficher une photo et un titre
 * 
 */

public class Miniature extends Composite {
    private VerticalPanel panel;
    private Image miniature;
    private Label label;

    /**
     * Affiche une photo miniature avec le titre centré en dessous
     * 
     * @param url
     *            Url de la photo
     * @param titre
     *            Titre de la photo
     */
    public Miniature(String url, String titre, final String destination) {
        miniature = new Image(url);
        miniature.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Window
                        .open(destination, "Image",
                                "menubar=no, status=no, scrollbars=no, menubar=no, width=200, height=100");
            }
        });
        label = new Label(titre);
        // Centre le titre
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        // Le VerticalPanel affiche ses enfants verticalement dans l'ordre
        // d'ajout des composants
        panel = new VerticalPanel();
        panel.add(miniature);
        panel.add(label);
        // Fonction importante qui indique a la classe parent quel est le
        // composant à afficher
        // Initialise l'affichage
        initWidget(panel);
        // Modifie la taille du composant
        setHeight("80px");
        setWidth("80px");
        /*
         * Ajoute une classe CSS au code (<div class="miniature-float"></div>)
         * Cela permet de styliser notre composant Ajouter dans votre fichier
         * CSS : .miniature-float { float: left; margin: 20px; }
         */
        setStyleName("miniature-float");
    }
}
