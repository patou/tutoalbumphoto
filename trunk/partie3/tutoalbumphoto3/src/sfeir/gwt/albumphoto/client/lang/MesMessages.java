package sfeir.gwt.albumphoto.client.lang; 

import com.google.gwt.i18n.client.Constants;

/**
 * Interface contenant toutes les clés de langues du fichier MesMesaages.properties
 * Chaque clé de langue est une méthode de l'interface qui retourne une chaine de caractère.
 * L'interface étend Constants ou Messages (permet d'avoir des messages parametré)
 */
public interface MesMessages extends Constants {
    
    String ajouter();

    String rechercher();

    String precedent();

    String suivant();
    
    String sujet();
    
    String texteRecherche();
    
    String texteAjout();
    
    String url();
    
    String titre();

    String annuler();

}
