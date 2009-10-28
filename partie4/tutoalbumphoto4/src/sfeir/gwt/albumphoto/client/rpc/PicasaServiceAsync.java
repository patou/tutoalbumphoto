package sfeir.gwt.albumphoto.client.rpc;

import java.util.List;

import sfeir.gwt.albumphoto.client.model.Photographie;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface PicasaServiceAsync {
    public void getPhotos(String sujet, Integer page, AsyncCallback<List<Photographie>> callback);
}
