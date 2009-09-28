package sfeir.gwt.albumphoto.client.rpc;

import java.util.List;

import sfeir.gwt.albumphoto.client.model.Photographie;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ImageServiceAsync {

    void getImages(int page, AsyncCallback<List<Photographie>> callback);

}
