package sfeir.gwt.albumphoto.client;

import java.util.List;

import sfeir.gwt.albumphoto.client.images.MesImages;
import sfeir.gwt.albumphoto.client.lang.MesMessages;
import sfeir.gwt.albumphoto.client.model.Photographie;
import sfeir.gwt.albumphoto.client.rpc.PicasaService;
import sfeir.gwt.albumphoto.client.rpc.PicasaServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * La classe principale de notre application implémente EntryPoint pour préciser
 * qu'elle est le point d'entrée de l'application, le main() d'une application
 * classique
 * 
 */
public class Tutoalbumphoto implements EntryPoint, ClickHandler {

    private MesMessages mesMessages = GWT.create(MesMessages.class);
    private MesImages mesImages = GWT.create(MesImages.class);

    private final PicasaServiceAsync picasaService = GWT
            .create(PicasaService.class);

    private Integer page = 0;
    private String sujetEnCours;

    private FlowPanel liste;
    private Button boutonAjouter;
    private Button boutonRechercher;
    private Button boutonPrec;
    private Button boutonSuiv;

    /**
     * C'est la première methode qui sera appelée à l'instanciation de la
     * classe, toutes classes implémentant EntryPoint doit définir cette méthode.
     * C'est ici qu'on initialise généralement les variables et qu'on construit
     * l'interface graphique. On peut le comparer au Main d'une application java
     * ou d'un programme C
     */
    public void onModuleLoad() {
        /*
         * Liste de toutes les miniatures Le FlowPanel affiche ses composants à
         * la suite les uns des autres Il n'ajoute aucun code html entre les
         * deux. Nous avons mis notre composant Miniature en flot: left; Ce qui
         * nous permet de les afficher en plusieurs lignes.
         */

        liste = new FlowPanel();
        liste.add(new Miniature(
                "http://www.programmez.com/img/magazines/couverture_119.jpg",
                "Mai 2009", ""));
        liste.add(new Miniature(
                "http://www.programmez.com/img/magazines/couverture_118.jpg",
                "Avril 2009", ""));

        // Création du bouton pour ajouter une image
        boutonAjouter = new Button(mesMessages.ajouter());
        // On ajoute un évènement sur le clic du bouton
        boutonAjouter.addClickHandler(this);

        // Création du bouton pour rechercher des images
        boutonRechercher = new Button(mesMessages.rechercher());
        // On ajoute un évènement sur le clic du bouton
        boutonRechercher.addClickHandler(this);

        
        
        boutonPrec = new Button(mesMessages.precedent());
        boutonPrec.setEnabled(false);
        boutonPrec.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                page--;
                rechercher(sujetEnCours);
            }
        });

        boutonSuiv = new Button(mesMessages.suivant());
        boutonSuiv.setEnabled(false);
        boutonSuiv.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                page++;
                rechercher(sujetEnCours);
            }
        });

        // Layout qui contient notre liste et les boutons à ajouter
        VerticalPanel verticalPanel = new VerticalPanel();
        HorizontalPanel horizontalPanel = new HorizontalPanel();

        horizontalPanel.add(mesImages.logo().createImage());
        horizontalPanel.add(mesImages.spacer().createImage());
        horizontalPanel.add(boutonAjouter);
        horizontalPanel.add(mesImages.spacer().createImage());
        horizontalPanel.add(boutonRechercher);
        horizontalPanel.add(mesImages.spacer().createImage());
        horizontalPanel.add(boutonPrec);
        horizontalPanel.add(mesImages.spacer().createImage());
        horizontalPanel.add(boutonSuiv);

        verticalPanel.add(horizontalPanel);
        verticalPanel.add(liste);
        // On ajoute dans notre page notre layout
        RootPanel.get().add(verticalPanel);
    }

    /**
     * Cette fonction est appelé lors du clique sur le bouton ajouter On vérifie
     * si la source de l'évènement est bien le bouton Et on affiche la fenêtre
     * d'édition
     * 
     * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
     */
    public void onClick(ClickEvent event) {
        if (event.getSource() == boutonAjouter) {
            // On crée la fenêtre
            FormulaireAjout fenetreAjout = new FormulaireAjout();
            // On l'affiche
            fenetreAjout.show();
            // On ajoute un évènement sur la fermeture de la fenêtre
            fenetreAjout.addCloseHandler(new CloseHandler<PopupPanel>() {

                /**
                 * Cette fonction est appelé quand la fenêtre d'ajout est fermé
                 * ou caché On récuppére l'accès à la fenêtre pour accèder aux
                 * deux champs texte et on ajoute l'image dans la liste
                 * 
                 * @see com.google.gwt.event.logical.shared.CloseHandler#onClose(com.google.gwt.event.logical.shared.CloseEvent)
                 */
                @Override
                public void onClose(CloseEvent<PopupPanel> event) {
                    FormulaireAjout fenetreAjout = (FormulaireAjout) event
                            .getTarget();
                    if (fenetreAjout != null) {
                        // On réccupére l'url et le titre dans les champs de la
                        // fenêtre
                        String url = fenetreAjout.saisieUrl.getText();
                        String titre = fenetreAjout.saisieTitre.getText();
                        // Si l'url a été remplis, on ajoute l'image
                        if (url.length() != 0)
                            liste.add(new Miniature(url, titre, ""));
                    }
                }

            });
        }

        if (event.getSource() == boutonRechercher) {
            // On crée la fenêtre
            FormulaireRecherche fenetreRecherche = new FormulaireRecherche();
            // On l'affiche
            fenetreRecherche.show();
            // On ajoute un évènement sur la fermeture de la fenêtre
            fenetreRecherche.addCloseHandler(new CloseHandler<PopupPanel>() {

                /**
                 * Cette fonction est appelé quand la fenêtre de recherche est
                 * fermée ou cachée. On récuppére l'accès à la fenêtre pour
                 * accèder aux deux champs texte et on ajoute l'image dans la
                 * liste
                 * 
                 * @see com.google.gwt.event.logical.shared.CloseHandler#onClose(com.google.gwt.event.logical.shared.CloseEvent)
                 */
                @Override
                public void onClose(CloseEvent<PopupPanel> event) {
                    FormulaireRecherche fenetreRecherche = (FormulaireRecherche) event
                            .getTarget();
                    if (fenetreRecherche != null) {
                        // On réccupére l'url et le titre dans les champs de la
                        // fenêtre
                        String titre = fenetreRecherche.saisieSujet.getText();
                        // Si l'url a été remplis, on ajoute l'image
                        if (titre.length() != 0)
                            rechercher(titre);
                    }
                }

            });
        }
    }

    public void rechercher(String sujet) {

        if (page == 0) {
            boutonPrec.setEnabled(false);
        } else
            boutonPrec.setEnabled(true);
        boutonSuiv.setEnabled(true);

        sujetEnCours = sujet;

        picasaService.getPhotos(sujet, page,
            new AsyncCallback<List<Photographie>>() {
    
                @Override
                public void onFailure(Throwable caught) {
                    Window.alert("Erreur lors de l'appel de la fonction distante getPhotos : " + caught.getMessage());
                }
    
                @Override
                public void onSuccess(List<Photographie> result) {
                    liste.clear();
                    for (Photographie p : result) {
                        liste.add(new Miniature(p.getPhotoMiniatureUrl(), p
                                .getPhotoTitre(), p.getPicasaUrl()));
                    }
                }
            });
    }
}