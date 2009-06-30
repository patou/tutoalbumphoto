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
public class FormulaireAjout extends DialogBox {

    private MesMessages mesMessages = GWT.create(MesMessages.class);

    // Champ de saisie de l'URL
    public TextBox saisieUrl = new TextBox();
    // Champ de saisie du titre
    public TextBox saisieTitre = new TextBox();

    /**
     * Constructeur de la classe
     */
    public FormulaireAjout() {
        // Appelle le constructeur de la classe DialogBox
        super();
        // Indique le titre de la fenêtre
        setText(mesMessages.texteAjout());
        // Layout pour afficher les élèments dans un tableau
        Grid grid = new Grid(3, 2);
        grid.setWidget(0, 0, new Label(mesMessages.url()));
        grid.setWidget(0, 1, saisieUrl);
        grid.setWidget(1, 0, new Label(mesMessages.titre()));
        grid.setWidget(1, 1, saisieTitre);
        Button boutonAjouter = new Button(mesMessages.ajouter());
        grid.setWidget(2, 1, boutonAjouter);
        /*
         * On ajoute l'évènement sur le clique du bouton Pour cacher la fenêtre
         * On crée directement le Handler grace à une fonction anonyme
         */
        boutonAjouter.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                FormulaireAjout.this.hide();
            }
        });
        // On ajoute notre layout à la fenêtre
        add(grid);
        // On centre la fenêtre dans la page
        center();
    }
}
