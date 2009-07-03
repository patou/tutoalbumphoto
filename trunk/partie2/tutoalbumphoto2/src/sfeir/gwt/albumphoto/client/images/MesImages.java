package sfeir.gwt.albumphoto.client.images;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface MesImages extends ImageBundle {
    /**
     * Utilise le fichier logo.jpg
     */
    AbstractImagePrototype logo();
    /**
     * Utilise le fichier precedent.png
     */
    AbstractImagePrototype precedent();
    /**
     * Utilise le fichier suivant.png
     */
    AbstractImagePrototype suivant();
    /**
     * Utilise le fichier spacer.png
     */
    AbstractImagePrototype spacer();
    /**
     * Utilise le fichier fr.png
     */
    AbstractImagePrototype fr();
    /**
     * Utilise le fichier en.png
     */
    AbstractImagePrototype en();
}
