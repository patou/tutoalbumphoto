package sfeir.gwt.albumphoto.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

/**
 * La classe principale de notre application Implemente EntryPoint pour préciser
 * qu'elle est le point d'entré de l'application, le main() d'une applicaiton
 * classique
 * 
 * @author Gregory Durelle
 */
public class Tutoalbumphoto implements EntryPoint {

    /**
     * C'est la première methode qui sera appelée au l'instanciation de la
     * classe, toute classe implémentant EntryPoint doit définir cette méthode.
     * C'est ici qu'on initialise généralement les variables et qu'on cosntruit
     * l'interface graphique.
     */
    public void onModuleLoad() {
        Window.alert("Coucou");
    }
}
