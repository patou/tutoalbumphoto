package sfeir.gwt.albumphoto.client;

import sfeir.gwt.albumphoto.client.lang.MesMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Composant affichant une boite de dialogue avec les champs Url et titre et un
 * bouton ajouter qui cache la fenêtre lors du clique.
 */
public class FormulaireRecherche extends DialogBox {
    
    private MesMessages mesMessages = GWT.create(MesMessages.class);
    
    // Champ de saisie du Sujet
    public TextBox saisieSujet = new TextBox();

    /**
     * Constructeur de la classe
     */
    public FormulaireRecherche() {
        // Appelle le constructeur de la classe DialogBox
        super(true);
        // Indique le titre de la fenêtre
        setText(mesMessages.texteRecherche());
        // Layout pour afficher les éléments dans un tableau
        Grid grid = new Grid(3, 2);
        grid.setWidget(0, 0, new Label(mesMessages.sujet()));
        grid.setWidget(0, 1, saisieSujet);
        Button boutonAjouter = new Button(mesMessages.rechercher());
        grid.setWidget(1, 1, boutonAjouter);
        /*
         * On ajoute l'évènement sur le clique du bouton Pour cacher la fenêtre
         * On crée directement le Handler grace à une fonction anonyme
         */
        boutonAjouter.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                FormulaireRecherche.this.hide();
            }
        });
        // On ajoute notre layout à la fenêtre
        add(grid);
        // On centre la fenêtre dans la page
        center();
    }
}
