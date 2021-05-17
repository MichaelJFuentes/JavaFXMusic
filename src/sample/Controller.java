package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;
import sample.Model.Datasource;

public class Controller {

    @FXML
    private TableView artistTable;
    @FXML
    private ProgressBar progressBar;

    @FXML
    public void listArtist() {
        Task<ObservableList<Artist>> task = new GetAllArtistTask();
        artistTable.itemsProperty().bind(task.valueProperty());
        progressBar.progressProperty().bind(task.progressProperty());

        progressBar.setVisible(true);

        task.setOnSucceeded(e -> progressBar.setVisible(false));
        task.setOnFailed(e -> progressBar.setVisible(false));

        new Thread(task).start();
    }

    public void listAlbumsForArtist() {
        final Artist artist = (Artist) artistTable.getSelectionModel().getSelectedItem();
        if (artist == null) {
            System.out.println("No Artist Selected");
            return;
        }

        Task<ObservableList<Album>> task = new Task<ObservableList<Album>>() {
            @Override
            public ObservableList<Album> call() throws Exception {
                return FXCollections.observableArrayList(
                        Datasource.getInstance().queryAlbumForArtistId(artist.getId()));
            }
        };
        artistTable.itemsProperty().bind(task.valueProperty());
        new Thread(task).start();
    }

}

class GetAllArtistTask extends Task {

    @Override
    public ObservableList<Artist> call() {
        return FXCollections.observableArrayList(Datasource.getInstance().queryArtists(Datasource.ORDER_BY_ACS));
    }
}
