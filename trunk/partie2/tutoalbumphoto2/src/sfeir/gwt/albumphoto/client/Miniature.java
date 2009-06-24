package sfeir.gwt.albumphoto.client;

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
     * @param url Url de la photo
     * @param titre Titre de la photo
     */
    public Miniature(String url, String titre) {
        miniature = new Image(url);
        label = new Label(titre);
        // Centre le titre
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        // Le VerticalPanel affiche ses enfants verticalement dans l'ordre d'ajout des composants 
        panel = new VerticalPanel();
        panel.add(miniature);
        panel.add(label);
        // Fonction importante qui indique a la classe parent quel est le composant à afficher
        // Initialise l'affichage
        initWidget(panel);
        // Modifie la taille du composant
        setHeight("80px");
        setWidth("80px");
        /* Ajoute une classe CSS au code (<div class="miniature-float"></div>)
         Cela permet de styliser notre composant
         Ajouter dans votre fichier CSS : 
         .miniature-float {
            float: left;
            margin: 20px;
         }
         */ 
        setStyleName("miniature-float");
    }
}
