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
        VerticalPanel verticalPanel = new VerticalPanel();
        liste = new FlowPanel();
        
        liste.add(new Miniature("http://www.programmez.com/img/magazines/couverture_119.jpg", "Mai 2009"));
        liste.add(new Miniature("http://www.programmez.com/img/magazines/couverture_118.jpg", "Avril 2009"));
        boutonAjouter = new Button("Ajouter");
        boutonAjouter.addClickHandler(this);
        verticalPanel.add(liste);
        verticalPanel.add(boutonAjouter);
        RootPanel.get().add(verticalPanel);
    }
      
    @Override
    public void onClick(ClickEvent event) {
        if (event.getSource() == boutonAjouter) {
            Formulaire fenetreAjout = new Formulaire();
            fenetreAjout.show();
            fenetreAjout.addCloseHandler(this);
        }      
    }

    @Override
    public void onClose(CloseEvent<PopupPanel> event) {
        Formulaire fenetreAjout = (Formulaire) event.getTarget();
        if (fenetreAjout != null) {
            String url = fenetreAjout.saisieUrl.getText();
            String titre = fenetreAjout.saisieTitre.getText();
            if (!url.isEmpty())
                liste.add(new Miniature(url, titre));
        }
    }
}
