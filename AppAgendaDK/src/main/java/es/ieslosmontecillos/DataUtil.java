package es.ieslosmontecillos;

import com.gluonhq.connect.GluonObservableList;
import com.gluonhq.connect.GluonObservableObject;
import com.gluonhq.connect.converter.JsonConverter;
import com.gluonhq.connect.provider.DataProvider;
import com.gluonhq.connect.provider.RestClient;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javax.json.Json;
import javax.json.JsonObject;

public class DataUtil {
    private ObservableList<Provincia> oldProvincias = FXCollections.observableArrayList();
    private ObservableList<Persona> oldPersonas = FXCollections.observableArrayList();
    private ObservableList<UserBody>olUsuarios = FXCollections.observableArrayList();

    private final String server = "192.168.100.22";

    public void obtenerTodasProvincias() {
        RestClient restClient = RestClient.create().method("GET").host("http://"+server+":8080").path("/api/v1/PROVINCIA");
        GluonObservableList<Provincia> provincias = DataProvider.retrieveList(restClient.createListDataReader(Provincia.class));
        provincias.addListener(new ListChangeListener<Provincia>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Provincia> c){
                if((c.next())){
                    oldProvincias.add(c.getList().get(c.getFrom()));
                }
            }
        });
    }

    public ObservableList<Provincia> getOldProvincias() {
        return oldProvincias;
    }
    public void obtenerTodasPersonas() {
        RestClient restClient = RestClient.create().method("GET").host("http://"+server+":8080").path("api/v1/PERSONA");
        GluonObservableList<Persona> personas = DataProvider.retrieveList(restClient.createListDataReader(Persona.class));

        personas.addListener(new ListChangeListener<Persona>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Persona> c){
                if ((c.next())) {
                    oldPersonas.add(c.getList().get(c.getFrom()));
                }
            }
        });
    }

    public ObservableList<Persona> getOldPersonas() {
        return oldPersonas;
    }
    public ObservableList<UserBody>getOlUsuarios() {
        return olUsuarios;
    }
    public void obtenerTodosUsuarios() {
        RestClient restClient = RestClient.create().method("GET").host("http://192.168.100.22:8081").path("/api/usuario/usuarios");
        GluonObservableList<UserBody> usuarios = DataProvider.retrieveList(restClient.createListDataReader(UserBody.class));

        usuarios.addListener(new ListChangeListener<UserBody>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends UserBody> c){
                if ((c.next())) {
                    olUsuarios.add(c.getList().get(c.getFrom()));
                }
            }
        });
    }

    public void eliminarPersona(Persona persona) {
        int idPersona = persona.getId().intValue();

        RestClient restClient = RestClient.create().method("DELETE").host("http://"+server+":8080").path("/api/v1/PERSONA/"+idPersona);
        GluonObservableList<Persona> personas = DataProvider.retrieveList(restClient.createListDataReader(Persona.class));
    }

    public void addPersona(Persona persona) {
        int idPersona = persona.getId().intValue();
        JsonConverter<Persona>converter = new JsonConverter<>(Persona.class);
        JsonObject json = converter.writeToJson(persona);
        String dataBody = json.toString();

        RestClient restClient = RestClient.create().method("POST")
                .host("http://"+server+":8080")
                .path("/api/v1/PERSONA")
                .dataString(dataBody)
                .contentType("application/json");
        GluonObservableObject<Persona> personaNueva = DataProvider.retrieveObject(restClient.createObjectDataReader(Persona.class));
    }

    public void actualizarPersona(Persona persona) {
        int idPersona = persona.getId().intValue();
        JsonConverter<Persona>converter = new JsonConverter<>(Persona.class);
        JsonObject json = converter.writeToJson(persona);
        String dataBody = json.toString();
        RestClient restClient = RestClient.create().method("PUT")
                .host("http://"+server+":8080")
                .path("/api/v1/PERSONA/"+idPersona)
                .dataString(dataBody)
                .contentType("application/json");
        GluonObservableObject<Persona> personaActualizada = DataProvider.retrieveObject(restClient.createObjectDataReader(Persona.class));
    }


    public void actualizarUsuario(UserBody user) {
        int id = user.getId();

        JsonConverter<UserBody>converter = new JsonConverter<>(UserBody.class);
        JsonObject json = converter.writeToJson(user);
        String dataBody = json.toString();
        RestClient restClient = RestClient.create().method("PUT").host("http://192.168.100.22:8081").path("/api/usuario/"+id).dataString(dataBody).contentType("application/json");
        GluonObservableObject<UserBody>usuarioActualizado = DataProvider.retrieveObject(restClient.createObjectDataReader(UserBody.class));

    }

    public Persona findPersonaById(Integer id) {
        int idPersona = id.intValue();
        RestClient restClient = RestClient.create().method("GET").host("http://"+server+":8080").path("/api/v1/PERSONA/"+idPersona);
        GluonObservableObject<Persona> persona = DataProvider.retrieveObject(restClient.createObjectDataReader(Persona.class));
        persona.initializedProperty().addListener((obs,ov,nv) ->{
          if(nv && persona.get() != null) {
              System.out.println("Recuperando persona seleccionada de la BD" + persona.get().getNombre() + " " + persona.get().getApellidos());
          }
        });
        return persona.get();
    }

    public Provincia findProvinciaById(Integer id) {
        int idProvincia = id.intValue();
        RestClient restClient = RestClient.create().method("GET").host("http://"+server+":8080").path("/api/v1/PROVINCIA/"+idProvincia);
        GluonObservableObject<Provincia> provincia = DataProvider.retrieveObject(restClient.createObjectDataReader(Provincia.class));
        provincia.initializedProperty().addListener((obs,ov,nv) ->{
            if(nv && provincia.get() != null) {
                System.out.println("Recuperando provincia seleccionada de la BD" + provincia.get().getCodigo() + "-" + provincia.get().getNombre());
            }
        });
        return provincia.get();
    }





}
