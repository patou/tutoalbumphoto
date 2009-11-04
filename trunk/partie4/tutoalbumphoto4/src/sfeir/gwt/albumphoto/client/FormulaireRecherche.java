package sfeir.gwt.albumphoto.client;

import sfeir.gwt.albumphoto.client.lang.MesMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Composant affichant une boite de dialogue avec les champs Url et titre et un bouton ajouter qui cache la fenêtre lors du clique.
 */
public class FormulaireRecherche extends DialogBox {

    private MesMessages mesMessages = GWT.create(MesMessages.class);

    //Interface qui sera implémenté par GWT qui contiendra la création de l'interface UiBinder venant du fichier XML
    interface Binder extends UiBinder<Widget, FormulaireRecherche> {}
    private static final Binder binder = GWT.create(Binder.class);

    @UiField
    Button boutonAjouter;
    // Champ de saisie du Sujet
    @UiField
    public TextBox saisieSujet;

    /**
     * Constructeur de la classe
     */
    public FormulaireRecherche() {
        // Appelle le constructeur de la classe DialogBox
        super(true);
        // Indique le titre de la fenêtre
        setText(mesMessages.texteRecherche());

        //On crée l'interface venant du fichier XML
        add(binder.createAndBindUi(this));
        
        // On centre la fenêtre dans la page
        center();
    }

    /*
     * On ajoute l'évènement sur le clic du bouton Pour cacher la fenêtre
     */
    
    @UiHandler("boutonAjouter")
    public void onClick(ClickEvent event) {
        FormulaireRecherche.this.hide();
    }
}
