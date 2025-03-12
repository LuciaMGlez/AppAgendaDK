package es.ieslosmontecillos;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.event.Event;
import javafx.scene.paint.Color;

import java.io.IOException;

public class InicioController {

    private DataUtil dataUtil;
    ObservableList oldProv;
    ObservableList oldPers;
    ObservableList olUsers;


    private Pane rootMain = new Pane();
    @FXML
    private TextField tfUser;
    @FXML
    private Label lbError;
    @FXML
    private PasswordField tfPasswd;

    @FXML
    public void iniciaApp(Event event) {
       /* try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AdminView.fxml"));
            Pane rootAgendaView = fxmlLoader.load();
           rootMain.getChildren().add(rootAgendaView);

            AgendaViewController agendaViewController = fxmlLoader.getController();
            AdminViewController adminViewController = fxmlLoader.getController();
            Pane rootAdminView = fxmlLoader.load();
            rootMain.getChildren().add(rootAdminView);

            agendaViewController.setDataUtil(dataUtil);
            agendaViewController.setOldProvincias(oldProv);
            agendaViewController.setOldPersonas(oldPers);
            agendaViewController.cargarTodasPersonas();
        }catch (IOException e){
            System.out.println("IOException: "+ e);
        }*/
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
    public void setOlUsers(ObservableList oldUsers) {
        this.olUsers = oldUsers;
    }


    @FXML
    public void onLogin(ActionEvent actionEvent) {
        if (tfUser.getText().isBlank() || tfPasswd.getText().isBlank()){
            lbError.setText("Complete los campos");
            lbError.setTextFill(Color.RED);
        }else if(tfUser.getText().equals("Admin") && tfPasswd.getText().equals("Admin")){
            lbError.setText("Logueado");
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/AdminView.fxml"));
                Pane rootAdminView = fxmlLoader.load();
                AdminViewController adminViewController = fxmlLoader.getController();
                rootMain.getChildren().add(rootAdminView);

                adminViewController.setDataUtil(dataUtil);
                adminViewController.setOlUsers(olUsers);
                adminViewController.cargarTodosUsuarios();
                //adminViewController.setTableViewPrevio();

            }catch (IOException e){
                System.out.println("IOException: "+ e);
            }
        } else{
            lbError.setText("Usuario incorrecto");
        }
    }

}
