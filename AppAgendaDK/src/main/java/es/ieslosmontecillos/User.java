package es.ieslosmontecillos;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlElement;

public class User {
    private final StringProperty type= new SimpleStringProperty();
    private final IntegerProperty rpta = new SimpleIntegerProperty();
    private final StringProperty message= new SimpleStringProperty();
    private final ObjectProperty<UserBody> body= new SimpleObjectProperty<>();

    @XmlElement(name = "type")
    public String getType() {
        return type.get();
    }
    public StringProperty typeProperty() {
        return type;
    }
    public void setType(String type) {
        this.type.set(type);
    }


    @XmlElement(name = "rpta")
    public int getRpta() {
        return rpta.get();
    }
    public IntegerProperty rptaProperty() {
        return rpta;
    }
    public void setRpta(int rpta) {
        this.rpta.set(rpta);
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message.get();
    }
    public StringProperty messageProperty() {
        return message;
    }
    public void setMessage(String message) {
        this.message.set(message);
    }

    @XmlElement(name = "body")
    public UserBody getBody() {
        return body.get();
    }
    public ObjectProperty<UserBody> bodyProperty() {
        return body;
    }
    public void setBody(UserBody body) {
        this.body.set(body);
    }
}
