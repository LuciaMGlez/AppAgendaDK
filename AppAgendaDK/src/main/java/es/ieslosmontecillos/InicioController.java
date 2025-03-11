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


    private Pane rootMain = new Pane();
    @FXML
    private TextField tfUser;
    @FXML
    private Label lbError;
    @FXML
    private PasswordField tfPasswd;

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


    @FXML
    public void onLogin(ActionEvent actionEvent) {

        ObservableList<Usuario> user = FXCollections.observableArrayList();

        user.addListener(new ListChangeListener<Usuario>() {
            boolean verificado = true;
            Usuario usuarioNuevo = new Usuario();

            @Override
            public void onChanged(Change<? extends Usuario> change) {
                for (Usuario usuario : dataUtil.getOlUsuarios()) {
                    if (!tfUser.getText().equals(usuario.getEmail()) || tfPasswd.getText().equals(usuario.getClave())) {
                        verificado = false;
                        System.out.println("Error 1");
                        break;
                    } else if (usuario.isVigencia() == false) {
                        verificado = false;
                        System.out.println("Error 2");
                        break;
                    }
                }

                if (!verificado || tfUser.getText().equals(usuarioNuevo.getEmail()) || tfPasswd.getText().equals(usuarioNuevo.getClave())) {
                    lbError.setText("Usuario y/o contraseña incorrecto. Inténtelo con otro usuario");
                    lbError.setTextFill(Color.RED);
                } else if (!verificado || usuarioNuevo.isVigencia() == false) {
                    lbError.setText("Usuario deshabilitado. Intentelo con otro usuario");
                    lbError.setTextFill(Color.RED);
                } else {
                    lbError.setText("Usuario logueado");
                    lbError.setTextFill(Color.GREEN);

                }
            }
        });
    }

}
