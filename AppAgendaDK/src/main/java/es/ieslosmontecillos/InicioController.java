package es.ieslosmontecillos;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.Event;

import java.io.IOException;

public class InicioController {

    private DataUtil dataUtil;
    ObservableList oldProv;
    ObservableList oldPers;
    private Pane rootMain = new Pane();

    @FXML
    public void iniciaApp(Event event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AgendaView.fxml"));
            Pane rootAgendaView = fxmlLoader.load();
            rootMain.getChildren().add(rootAgendaView);

            AgendaViewController agendaViewController = fxmlLoader.getController();
            agendaViewController.setDataUtil(dataUtil);
            agendaViewController.setOldProvincias(oldProv);
            agendaViewController.setOldPersonas(oldPers);
            agendaViewController.cargarTodasPersonas();
        }catch (IOException e){
            System.out.println("IOException: "+ e);
        }
    }

    public void setRootMain(Pane rootMain) {
        this.rootMain = rootMain;
    }

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }
    public void setOldProv(ObservableList oldProv) {
        this.oldProv = oldProv;
    }
    public void setOldPers(ObservableList oldPers) {
        this.oldPers = oldPers;
    }
}
