package sfeir.gwt.albumphoto.client.rpc;

import java.util.List;

import sfeir.gwt.albumphoto.client.model.Photographie;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("picasaService")
public interface PicasaService extends RemoteService {
    public List<Photographie> getPhotos(String sujet, Integer page) throws Exception;
}
