package es.ieslosmontecillos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {

    private TableView tableViewPrevio;
    private DataUtil dataUtil;
    private UserBody userSelected;
    private boolean nuevoUser;
    private Pane rootAdminView;
    private ObservableList<UserBody> olUsers;

    @javafx.fxml.FXML
    private TextField tfPasswd;
    @javafx.fxml.FXML
    private TextField tfEmail;
    @javafx.fxml.FXML
    private TableView<UserBody> tableView;
    @javafx.fxml.FXML
    private TableColumn<UserBody, Boolean> columnVigencia;
    @javafx.fxml.FXML
    private TableColumn<UserBody, Integer>columId
            ;
    @javafx.fxml.FXML
    private TableColumn<UserBody, String> columnPasswd;
    @javafx.fxml.FXML
    private TableColumn<UserBody, String> columnEmail;

    public void setTableViewPrevio(TableView tableViewPrevio) {
        this.tableViewPrevio = tableViewPrevio;
    }
    public void setRootAdminView(Pane rootAdminView) {
        this.rootAdminView = rootAdminView;
    }
    public void setOlUsers(ObservableList<UserBody> olUsers) {this.olUsers = olUsers;}

    public void cargarTodosUsuarios(){
        tableView.setItems(FXCollections.observableArrayList(olUsers));
    }


    public void setUser(UserBody user, boolean nuevoUser) {
        if(!nuevoUser){
            this.userSelected = user;
        }else {
            this.userSelected = new UserBody();
        }
        this.nuevoUser = nuevoUser;
    }

    public void setDataUtil(DataUtil dataUtil) {
        this.dataUtil = dataUtil;
    }

    @javafx.fxml.FXML
    public void onSave(ActionEvent actionEvent) {

        if(!tfEmail.getText().isBlank()){
            userSelected.setEmail(tfEmail.getText());
        }
        if(!tfPasswd.getText().isBlank()){
            userSelected.setClave(tfPasswd.getText());
        }
        userSelected = new UserBody();
        this.setUser(userSelected, true);

    }

    @javafx.fxml.FXML
    public void onExit(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnPasswd.setCellValueFactory(new PropertyValueFactory<>("clave"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnVigencia.setCellValueFactory(new PropertyValueFactory<>("vigencia"));
        //tableView.setItems(tableViewPrevio.getItems());
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            userSelected = newValue;
            if(userSelected != null){
                tfEmail.setText(userSelected.getEmail());
                tfPasswd.setText(userSelected.getClave());
            }else{
                tfEmail.setText("");
                tfPasswd.setText("");
            }
        });
        
    }
}
