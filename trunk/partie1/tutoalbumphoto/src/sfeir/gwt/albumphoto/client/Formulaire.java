package sfeir.gwt.albumphoto.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class Formulaire extends DialogBox {
    public TextBox saisieUrl = new TextBox();
    public TextBox saisieTitre = new TextBox();

    public Formulaire() {
        super();
        setText("Ajouter une nouvelle image");
        Grid grid = new Grid(3, 2);
        grid.setWidget(0, 0, new Label("Url"));
        grid.setWidget(0, 1, saisieUrl);
        grid.setWidget(1, 0, new Label("Titre"));
        grid.setWidget(1, 1, saisieTitre);
        Button boutonAjouter = new Button("Ajouter");
        grid.setWidget(2, 1, boutonAjouter);
        boutonAjouter.addClickHandler(new ClickHandler(){
            @Override
            public void onClick(ClickEvent event) {
               Formulaire.this.hide();       
            }});
        add(grid);
        center();
    }
}
