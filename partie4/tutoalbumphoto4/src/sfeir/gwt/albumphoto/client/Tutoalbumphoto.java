package sfeir.gwt.albumphoto.client;

import java.util.List;

import sfeir.gwt.albumphoto.client.FormulaireRecherche.Binder;
import sfeir.gwt.albumphoto.client.lang.MesMessages;
import sfeir.gwt.albumphoto.client.model.Photographie;
import sfeir.gwt.albumphoto.client.ressources.MesImages;
import sfeir.gwt.albumphoto.client.rpc.ImageService;
import sfeir.gwt.albumphoto.client.rpc.ImageServiceAsync;
import sfeir.gwt.albumphoto.client.rpc.PicasaService;
import sfeir.gwt.albumphoto.client.rpc.PicasaServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;

/**
 * La classe principale de notre application implémente EntryPoint pour préciser qu'elle est le point d'entrée de l'application, le main() d'une application classique
 * 
 */
public class Tutoalbumphoto implements EntryPoint, ClickHandler, AsyncCallback<List<Photographie>> {

    private MesMessages mesMessages = GWT.create(MesMessages.class);
    private MesImages mesImages = GWT.create(MesImages.class);

    private final PicasaServiceAsync picasaService = GWT
            .create(PicasaService.class);
    private final ImageServiceAsync service = GWT
    .create(ImageService.class);

    private Integer page = 0;
    private String sujetEnCours;

    private FlowPanel liste;
    private Button boutonAjouter;
    private Button boutonRechercher;
    private Button boutonMesphotos;
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

        // Création du bouton pour ajouter une image
        boutonAjouter = new Button(mesMessages.ajouter());
        // On ajoute un évènement sur le clic du bouton
        boutonAjouter.addClickHandler(this);

        // Création du bouton pour rechercher des images
        boutonRechercher = new Button(mesMessages.rechercher());
        // On ajoute un évènement sur le clic du bouton
        boutonRechercher.addClickHandler(this);
        
     // Création du bouton pour rechercher des images
        boutonMesphotos = new Button(mesMessages.mesPhotos());
        // On ajoute un évènement sur le clic du bouton
        boutonMesphotos.addClickHandler(this);

        
        boutonPrec = new Button(new Image(mesImages.precedent()).toString() + mesMessages.precedent());
        boutonPrec.setEnabled(false);
        boutonPrec.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (page > 1) {
                    page--;
                    rechercher(sujetEnCours);
                }
            }
        });

        boutonSuiv = new Button( mesMessages.suivant() + new Image(mesImages.suivant()).toString());
        boutonSuiv.setEnabled(false);
        boutonSuiv.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (liste.getWidgetCount() > 0) {
                    page++;
                    rechercher(sujetEnCours);
                }
            }
        });

        // Layout qui contient notre liste et les boutons à ajouter
        
        HorizontalPanel horizontalPanel = creeBarreDeBoutons();

        DockLayoutPanel dockPanel = new DockLayoutPanel(Unit.PX);
        
        dockPanel.addNorth(horizontalPanel, 50.0);
        boutonPrec.setHeight("25px");
        boutonSuiv.setHeight("25px");
        dockPanel.addWest(boutonPrec, 100);
        dockPanel.addEast(boutonSuiv, 100);
        dockPanel.add(liste);
        dockPanel.layout();
        // On ajoute dans notre page notre layout
        RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
        rootLayoutPanel.add(dockPanel);
        rootLayoutPanel.layout();
        rechercher(null);
    }


    private HorizontalPanel creeBarreDeBoutons() {
        HorizontalPanel horizontalPanel = new HorizontalPanel();

        horizontalPanel.add(new Image(mesImages.logo()));
        horizontalPanel.add(new Image(mesImages.spacer()));
        horizontalPanel.add(boutonAjouter);
        horizontalPanel.add(new Image(mesImages.spacer()));
        horizontalPanel.add(boutonRechercher);
        horizontalPanel.add(new Image(mesImages.spacer()));
        horizontalPanel.add(boutonMesphotos);
        horizontalPanel.add(new Anchor(new Image(mesImages.fr()).toString(), true, "?locale=fr"));
        horizontalPanel.add(new Anchor(new Image(mesImages.en()).toString(), true, "?locale=en"));
        return horizontalPanel;
    }


    public void onClick(ClickEvent event) {
        if (event.getSource() == boutonAjouter) {
            GWT.runAsync(new RunAsyncCallback() {

                @Override
                public void onSuccess() {
                    // On crée la fenêtre
                    FormulaireAjout fenetreAjout = new FormulaireAjout();
                    // On l'affiche
                    fenetreAjout.show();
                    // On ajoute un évènement sur la fermeture de la fenêtre
                    fenetreAjout.addCloseHandler(new CloseHandler<PopupPanel>() {

                        /**
                         * Cette fonction est appelé quand la fenêtre d'ajout est fermé ou caché On récuppére l'accès à la fenêtre pour accèder aux deux champs texte et on ajoute l'image dans la liste
                         * 
                         * @see com.google.gwt.event.logical.shared.CloseHandler#onClose(com.google.gwt.event.logical.shared.CloseEvent)
                         */
                        @Override
                        public void onClose(CloseEvent<PopupPanel> event) {
                            FormulaireAjout fenetreAjout = (FormulaireAjout) event.getTarget();
                            if (fenetreAjout != null && event.isAutoClosed()) {
                                page = 1;
                                rechercher(null);
                            }
                        }

                    });
                }

                @Override
                public void onFailure(Throwable reason) {
                    Window.alert("Erreur lors du chargement du formulaire");
                }
            });
        }
        // On repasse à nos photos
        else if (event.getSource() == boutonMesphotos) {
            page = 1;
            rechercher(null);
        }
        // On recherche des photos sur picasa
        else if (event.getSource() == boutonRechercher) {
            GWT.runAsync(new RunAsyncCallback() {

                @Override
                public void onSuccess() {
                    // On crée la fenêtre
                    FormulaireRecherche fenetreRecherche = new FormulaireRecherche();
                    // On l'affiche
                    fenetreRecherche.show();
                    // On ajoute un évènement sur la fermeture de la fenêtre
                    fenetreRecherche.addCloseHandler(new CloseHandler<PopupPanel>() {

                        /**
                         * Cette fonction est appelé quand la fenêtre de recherche est fermée ou cachée. On récuppére l'accès à la fenêtre pour accèder aux deux champs texte et on ajoute l'image dans la liste
                         * 
                         * @see com.google.gwt.event.logical.shared.CloseHandler#onClose(com.google.gwt.event.logical.shared.CloseEvent)
                         */
                        @Override
                        public void onClose(CloseEvent<PopupPanel> event) {
                            FormulaireRecherche fenetreRecherche = (FormulaireRecherche) event.getTarget();
                            if (fenetreRecherche != null) {
                                // On réccupére le texte entrée par l'utilisateur
                                String titre = fenetreRecherche.saisieSujet.getText();
                                if (titre != null && !titre.isEmpty()) {
                                    rechercher(titre);
                                }

                            }
                        }

                    });

                }

                @Override
                public void onFailure(Throwable reason) {
                    Window.alert("Erreur lors du chargement du formulaire");
                }
            });
        }
    }

    public void rechercher(String sujet) {
        // On active les boutons en fonction de la page courante
        if (page == 0) {
            boutonPrec.setEnabled(false);
        } else
            boutonPrec.setEnabled(true);
        boutonSuiv.setEnabled(true);
        // On efface la liste des photos
        liste.clear();
        if (sujet != null && sujet.length() == 0)
            sujet = null;
        // On enregistre le sujet en cours
        sujetEnCours = sujet;
        // Si le sujet en cours est null, on affiche des images de app engine sinon celle de picasa pour la recherche
        if (sujet == null) {
            service.getImages(page, this);
        } else {
            // On appelle le service en ligne qui va aller chercher les photos sur le site en ligne Picasa
            picasaService.getPhotos(sujet, page, this);
        }
    }

    /**
     * Fonction appelé si l'appel au service distant à échouer ou que le service à retourner une exception
     * 
     * @see com.google.gwt.user.client.rpc.AsyncCallback#onFailure(java.lang.Throwable)
     */
    @Override
    public void onFailure(Throwable caught) {
        Window.alert("Erreur lors de la récupération des Photos : " + caught.getMessage());
    }

    /**
     * Fonction appelé quand l'appel à la fonction distante à retourner son résultat.
     * 
     * @param result
     *            Le retour de la fonction distante
     */
    @Override
    public void onSuccess(List<Photographie> result) {
        liste.clear();
        // Si il n'y a pas de résultat, on affiche un message
        if (result.isEmpty()) {
            boutonPrec.setEnabled(false);
            boutonPrec.setEnabled(false);
            liste.add(new Label(mesMessages.noImages()));
        }
        // On ajoute toute les photos
        for (Photographie p : result) {
            liste.add(new Miniature(p.getPhotoMiniatureUrl(), p.getPhotoTitre(), p.getPhotoUrl()));

        }
    }
}