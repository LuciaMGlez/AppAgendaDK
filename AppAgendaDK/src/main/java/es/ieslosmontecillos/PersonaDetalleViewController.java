package es.ieslosmontecillos;

import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;
import java.util.Optional;
import java.util.zip.DataFormatException;

public class PersonaDetalleViewController {
    public static final char CASADO='C';
    public static final char SOLTERO='S';
    public static final char VIUDO='V';
    public static final String CARPETA_FOTOS = "Fotos";

    private Pane rootAgendaView;
    private TableView tableViewPrevio;
    private Persona persona;
    private DataUtil dataUtil;
    private boolean nuevaPersona;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private Label lbSalario;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private DatePicker datePickerFechaNacimiento;
    @FXML
    private Label lbFechaNacimiento;
    @FXML
    private TextField textFieldNumHijos;
    @FXML
    private CheckBox checkBoxJubilado;
    @FXML
    private Label lbFoto;
    @FXML
    private RadioButton rbSoltero;
    @FXML
    private RadioButton rbCasado;
    @FXML
    private Label lbEstadoCivil;
    @FXML
    private TextField textFieldSalario;
    @FXML
    private Label lbJubilacion;
    @FXML
    private RadioButton rbViudo;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private Label lbNombre;
    @FXML
    private Label lbNumHijos;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private Label lbTelefono;
    @FXML
    private Label lbProvincia;
    @FXML
    private Label lbEmail;
    @FXML
    private Label lbApellidos;
    @FXML
    private TextField textFieldApellidos;
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonGuardar;
    @FXML
    private ComboBox<Provincia> comboBoxProvincia;
    @FXML
    private Button buttonExaminar;
    @FXML
    private AnchorPane viewPersona;


    public void setRootAgendaView(Pane rootAgendaView) {
        this.rootAgendaView = rootAgendaView;
    }

    public void setTableViewPrevio(TableView tableViewPrevio) {
        this.tableViewPrevio = tableViewPrevio;
    }

    public void setPersona(Persona persona, boolean nuevaPersona) {
        if(!nuevaPersona){
            this.persona = persona;
        }else {
            this.persona = new Persona();
        }
        this.nuevaPersona = nuevaPersona;
    }
    public void setDataUtil(DataUtil dataUtil){
        this.dataUtil = dataUtil;
    }

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        persona.setNombre(textFieldNombre.getText());
        persona.setApellidos(textFieldApellidos.getText());
        persona.setEmail(textFieldEmail.getText());
        persona.setTelefono(textFieldTelefono.getText());
        boolean errorFormato = false;

        if(!textFieldNumHijos.getText().isEmpty()){
            try{
                persona.setNumHijos(Integer.valueOf(textFieldNumHijos.getText()));
            }catch(NumberFormatException ex){
                errorFormato = true;
                Alert alert  = new Alert(Alert.AlertType.INFORMATION, "Numero de hijos no valido");
                alert.showAndWait();
                textFieldNumHijos.requestFocus();
            }
        }

        if(!textFieldSalario.getText().isEmpty()){
            try{
                Double dSalario = (Double.valueOf(textFieldSalario.getText()).doubleValue());
                persona.setSalario(dSalario);
            }catch(NumberFormatException ex){
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Salario no valido");
                alert.showAndWait();
                textFieldSalario.requestFocus();
            }
        }

        persona.setJubilado(checkBoxJubilado.isSelected()? 1 : 0);

        if(rbSoltero.isSelected()){
            persona.setEstadoCivil(String.valueOf(CASADO));
        }else if(rbCasado.isSelected()){
            persona.setEstadoCivil(String.valueOf(SOLTERO));
        }else if(rbViudo.isSelected()){
            persona.setEstadoCivil(String.valueOf(VIUDO));
        }

        if(datePickerFechaNacimiento.getValue() != null){
            LocalDate localDate = datePickerFechaNacimiento.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaComoCadena = sdf.format(date);
            persona.setFechaNacimiento(fechaComoCadena);
        }else{
            persona.setFechaNacimiento(null);
        }

        if(comboBoxProvincia.getValue() != null){
            persona.setProvincia(comboBoxProvincia.getValue());
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Debe indicar una provincia");
            alert.showAndWait();
            errorFormato = true;
        }

        if(nuevaPersona){
            dataUtil.addPersona(persona);
        }else{
            dataUtil.actualizarPersona(persona);
        }
        int numFilaSeleccionada;
        if(nuevaPersona){
            tableViewPrevio.getItems().add(persona);
            numFilaSeleccionada = tableViewPrevio.getItems().size()-1;
            tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
            tableViewPrevio.scrollTo(numFilaSeleccionada);
        }else{
            numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
            tableViewPrevio.getItems().set(numFilaSeleccionada, persona);
        }
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();

        StackPane rootMain = (StackPane)viewPersona.getScene().getRoot();
        rootMain.getChildren().remove(viewPersona);
        rootAgendaView.setVisible(true);

    }

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();

        StackPane rootMain = (StackPane)viewPersona.getScene().getRoot();
        rootMain.getChildren().remove(viewPersona);
        rootAgendaView.setVisible(true);

    }

    @FXML
    private void onActionButtonExaminar(ActionEvent event) {
        File carpetaFotos = new File(CARPETA_FOTOS);
        if(!carpetaFotos.exists()){
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Imagenes (jgp, png)","*.jpg", "*.png"), new FileChooser.ExtensionFilter("Todos los archivos", "."));
        File file = fileChooser.showOpenDialog(viewPersona.getScene().getWindow());
        if(file != null){
            try{
                Files.copy(file.toPath(), new File(CARPETA_FOTOS+"/"+file.getName()).toPath());
                persona.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            }catch(FileAlreadyExistsException ex){
                Alert alert = new Alert(Alert.AlertType.WARNING,"Nombre de archivo duplicado");
                alert.showAndWait();
            }catch (IOException ex){
                Alert alert = new Alert(Alert.AlertType.WARNING, "No se hah podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }


    public void mostrarDatos() throws ParseException {
        textFieldNombre.setText(persona.getNombre());
        textFieldApellidos.setText(persona.getApellidos());
        textFieldEmail.setText(persona.getEmail());
        textFieldTelefono.setText(persona.getTelefono());

        if(persona.getNumHijos() != null){
            textFieldNumHijos.setText(String.valueOf(persona.getNumHijos()));
        }
        if(persona.getSalario() != null){
            textFieldSalario.setText(String.valueOf(persona.getSalario()));
        }
        if(persona.getJubilado() != null && persona.getJubilado() == 1){
            checkBoxJubilado.setSelected(true);
        }else {
            checkBoxJubilado.setSelected(false);
        }

        if(persona.getEstadoCivil() != null){
            switch(persona.getEstadoCivil().charAt(0)){
                case CASADO:
                    rbCasado.setSelected(true);
                    break;
                case SOLTERO:
                    rbSoltero.setSelected(true);
                    break;
                case VIUDO:
                    rbViudo.setSelected(true);
                    break;
            }
        }

        if(persona.getFechaNacimiento() != null){
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fecNac = formato.parse(persona.getFechaNacimiento());
            LocalDate fechaNac = fecNac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            datePickerFechaNacimiento.setValue(fechaNac);
        }
        comboBoxProvincia.setItems(dataUtil.getOldProvincias());
        comboBoxProvincia.setCellFactory(
                (ListView<Provincia> l)-> new ListCell<Provincia>() {
                    @Override
                    protected void updateItem(Provincia provincia, boolean empty) {
                        super.updateItem(provincia, empty);
                        if(provincia == null || empty){
                            setText("");
                        }else{
                            setText(provincia.getCodigo() + "-" + provincia.getNombre());
                        }
                    }
                }
        );
        comboBoxProvincia.setConverter(new javafx.util.StringConverter<Provincia>() {
            @Override
            public String toString(Provincia provincia) {
                if(provincia == null){
                    return null;
                }else{
                    return provincia.getCodigo()+"-"+provincia.getNombre();
                }
            }

            @Override
            public Provincia fromString(String s) {
                return null;
            }
        });

        if(persona.getProvincia() != null){
            comboBoxProvincia.setValue(persona.getProvincia());
        }

        if(persona.getFoto() != null){
            String imageFileName = persona.getFoto();
            File file = new File(CARPETA_FOTOS+"/"+imageFileName);
            if(file.exists()){
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imagen en " + file.toURI().toString());
                alert.showAndWait();
            }
        }



    }

    @Deprecated
    private void onActionSuprimirFoto(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresion de imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a la imagen\n "+ "quitar la foto pero MANTENER el archivo \n, no CANCELAR la operación?");
        alert.setContentText("Elija la opcion deseada: ");

        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener  =new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonTypeEliminar){
            String imageFileName = persona.getFoto();
            File file = new File(CARPETA_FOTOS+"/"+imageFileName);
            if(file.exists()){
                file.delete();
            }
            persona.setFoto(null);
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener) {
            persona.setFoto(null);
            imageViewFoto.setImage(null);
        }
    }
}
