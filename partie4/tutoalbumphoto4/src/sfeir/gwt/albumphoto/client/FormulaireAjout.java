package sfeir.gwt.albumphoto.client;

import sfeir.gwt.albumphoto.client.lang.MesMessages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

/**
 * Composant affichant une boite de dialogue avec les champs Url et titre et un
 * bouton ajouter qui cache la fenêtre lors du clique.
 */
public class FormulaireAjout extends DialogBox {

//    interface Binder extends UiBinder<DialogBox, FormulaireAjout> { }
//    private static final Binder binder = GWT.create(Binder.class);
    
    
    private MesMessages mesMessages = GWT.create(MesMessages.class);

    // Champ de saisie de l'URL
    public FileUpload saisieImage = new FileUpload();
    // Champ de saisie du titre
    public TextBox saisieTitre = new TextBox();
    //Formulaire pour envoyer l'image
    private FormPanel form = new FormPanel();

    /**
     * Constructeur de la classe
     */
    public FormulaireAjout() {
        // Appelle le constructeur de la classe DialogBox
        super();
        
        // Indique le titre de la fenêtre
        setText(mesMessages.texteAjout());
        saisieTitre.setName("titre");
        saisieImage.setName("image");
        // Layout pour afficher les éléments dans un tableau
        Grid grid = new Grid(3, 2);
        grid.setWidget(0, 0, new Label(mesMessages.url()));
        grid.setWidget(0, 1, saisieImage);
        grid.setWidget(1, 0, new Label(mesMessages.titre()));
        grid.setWidget(1, 1, saisieTitre);
        Button boutonAjouter = new Button(mesMessages.ajouter());
        Button boutonAnnuler = new Button(mesMessages.annuler());
        grid.setWidget(2, 0, boutonAjouter);
        grid.setWidget(2, 1, boutonAnnuler);
        /*
         * On ajoute l'évènement sur le clique du bouton Pour cacher la fenêtre
         * On crée directement le Handler grace à une fonction anonyme
         */
        boutonAnnuler.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                FormulaireAjout.this.hide();
            }
        });
        
        boutonAjouter.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                FormulaireAjout.this.form.submit();
            }
        });
        // On ajoute notre layout au formulaire
        form.setAction("/image/");
        form.setMethod(FormPanel.METHOD_POST);
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.add(grid);
        form.addSubmitCompleteHandler(new SubmitCompleteHandler() {
            
            @Override
            public void onSubmitComplete(SubmitCompleteEvent event) {
                Window.alert(event.getResults());
                FormulaireAjout.this.hide(true);
            }
        });
        add(form);
        // On centre la fenêtre dans la page
        center();
    }
}
