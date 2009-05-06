package sfeir.gwt.albumphoto.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * La classe principale de notre application Implemente EntryPoint pour préciser
 * qu'elle est le point d'entrée de l'application, le main() d'une applicaiton
 * classique
 * 
 */
public class Tutoalbumphoto implements EntryPoint, ClickHandler, CloseHandler<PopupPanel> {

    private FlowPanel liste;
    private Button boutonAjouter;
   
    /**
     * C'est la première methode qui sera appelée au l'instanciation de la
     * classe, toute classe implémentant EntryPoint doit définir cette méthode.
     * C'est ici qu'on initialise généralement les variables et qu'on construit
     * l'interface graphique.
     * On peut le comparer au Main d'une application java ou d'un programme C
     */
    public void onModuleLoad() {
        /* Liste de toutes les miniatures
         * Le FlowPanel affiche ses composants à la suite les uns des autres
         * Il n'ajoute aucun code html entre les deux.
         * Nous avons mis notre composant Miniature en flot: left;
         * Ce qui nous permet de les afficher en plusieurs lignes.
         */
        liste = new FlowPanel();        
        liste.add(new Miniature("http://www.programmez.com/img/magazines/couverture_119.jpg", "Mai 2009"));
        liste.add(new Miniature("http://www.programmez.com/img/magazines/couverture_118.jpg", "Avril 2009"));
        // Création du bouton pour ajouter une image
        boutonAjouter = new Button("Ajouter");
        // On ajoute un évènement sur le clique du bouton
        boutonAjouter.addClickHandler(this);
        // Layout qui contient notre liste et le bouton à ajouter
        VerticalPanel verticalPanel = new VerticalPanel();
        verticalPanel.add(liste);
        verticalPanel.add(boutonAjouter);
        // On ajoute dans notre page notre layout
        RootPanel.get().add(verticalPanel);
    }
      
    /** 
     * Cette fonction est appelé lors du clique sur le bouton ajouter
     * On vérifie si la source de l'évènement est bien le bouton
     * Et on affiche la fenêtre d'édition
     * 
     * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
     */
    public void onClick(ClickEvent event) {
        if (event.getSource() == boutonAjouter) {
            // On crée la fenêtre
            Formulaire fenetreAjout = new Formulaire();
            // On l'affiche
            fenetreAjout.show();
            // On ajoute un évènement sur la fermeture de la fenêtre
            fenetreAjout.addCloseHandler(this);
        }      
    }

    /** 
     * Cette fonction est appelé quand la fenêtre d'ajout est fermé ou caché
     * On récuppére l'accès à la fenêtre pour accèder aux deux champs texte
     * et on ajoute l'image dans la liste
     * 
     * @see com.google.gwt.event.logical.shared.CloseHandler#onClose(com.google.gwt.event.logical.shared.CloseEvent)
     */
    public void onClose(CloseEvent<PopupPanel> event) {
        // On récupére la fenêtre qui viens d'être fermé
        Formulaire fenetreAjout = (Formulaire) event.getTarget();
        if (fenetreAjout != null) {
            // On réccupére l'url et le titre dans les champs de la fenêtre
            String url = fenetreAjout.saisieUrl.getText();
            String titre = fenetreAjout.saisieTitre.getText();
            // Si l'url a été remplis, on ajoute l'image
            if (url.length() != 0)
                liste.add(new Miniature(url, titre));
        }
    }
}
