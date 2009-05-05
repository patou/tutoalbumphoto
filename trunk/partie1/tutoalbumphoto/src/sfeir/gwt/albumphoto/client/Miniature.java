package sfeir.gwt.albumphoto.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


/**
 * Composant permettant d'afficher une photo et un titre
 *
 */
public class Miniature extends Composite {
    VerticalPanel panel;
    Image miniature;
    Label label;
    public Miniature(String url, String titre) {
        miniature = new Image(url);
        label = new Label(titre);
        label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel = new VerticalPanel();
        panel.add(miniature);
        panel.add(label);
        initWidget(panel);
        setHeight("80px");
        setWidth("80px");
        setStyleName("miniature-float");
    }
}
