package es.ieslosmontecillos;

import javafx.beans.property.*;

import javax.xml.bind.annotation.XmlElement;

public class Persona {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellidos = new SimpleStringProperty();
    private final ObjectProperty provincia = new SimpleObjectProperty();
    private final StringProperty telefono = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty fechaNacimiento = new SimpleStringProperty();
    private final IntegerProperty numHijos = new SimpleIntegerProperty();
    private final DoubleProperty salario = new SimpleDoubleProperty();
    private final StringProperty estadoCivil = new SimpleStringProperty();
    private final IntegerProperty jubilado = new SimpleIntegerProperty();
    private final StringProperty foto = new SimpleStringProperty();

    //campo id
    @XmlElement(name = "id")
    public Integer getId(){
        return id.get();
    }
    public IntegerProperty idProperty(){
        return id;
    }
    public void setId(Integer id){
        this.id.set(id);
    }

    //campo nombre
    @XmlElement(name="nombre")
    public String getNombre(){
        return nombre.get();
    }
    public StringProperty nombreProperty(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre.set(nombre);
    }

    //campo apellidos
    @XmlElement(name = "apellidos")
    public String getApellidos(){
        return apellidos.get();
    }
    public StringProperty apellidosProperty(){
        return apellidos;
    }
    public void setApellidos(String apellidos){
        this.apellidos.set(apellidos);
    }

    //campo telefono
    @XmlElement(name = "telefono")
    public String getTelefono(){
        return telefono.get();
    }
    public StringProperty telefonoProperty(){
        return telefono;
    }
    public void setTelefono(String telefono){
        this.telefono.set(telefono);
    }

    //campo email
    @XmlElement(name="email")
    public String getEmail(){
        return email.get();
    }
    public StringProperty emailProperty(){
        return email;
    }
    public void setEmail(String email){
        this.email.set(email);
    }

    //campo provincia
    @XmlElement(name = "provincia")
    public Provincia getProvincia(){
        return(Provincia) provincia.get();
    }
    public ObjectProperty provinciaProperty(){
        return provincia;
    }
    public void setProvincia(Provincia provincia){
        this.provincia.set(provincia);
    }

    //campo Fecha Nacimiento
    @XmlElement(name="fechaNacimiento")
    public String getFechaNacimiento(){
        return fechaNacimiento.get();
    }
    public StringProperty fechaNacimientoProperty(){
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento){
        this.fechaNacimiento.set(fechaNacimiento);
    }

    //campo Num Hijos
    @XmlElement(name="num_hijos")
    public Integer getNumHijos(){
        return numHijos.get();
    }
    public IntegerProperty numHijosProperty(){
        return numHijos;
    }
    public void setNumHijos(Integer numHijos){
        if(numHijos != null){
            this.numHijos.set(numHijos);
        }else{
            this.numHijos.set(0);
        }
    }

    //campo Estado Civil
    @XmlElement(name="estado_civil")
    public String getEstadoCivil(){
        return estadoCivil.get();
    }
    public StringProperty estadoCivilProperty(){
        return estadoCivil;
    }
    public void setEstadoCivil(String estadoCivil){
        this.estadoCivil.set(estadoCivil);
    }

    //campo salario
    @XmlElement(name = "salario")
    public Double getSalario(){
        return salario.get();
    }
    public DoubleProperty salarioProperty(){
        return salario;
    }
    public void setSalario(Double salario){
        if(salario != null){
            this.salario.set(salario);
        }else{
            this.salario.set(0);
        }
    }

    //campo jubilado
    @XmlElement (name="jubilado")
    public Integer getJubilado(){
        return jubilado.get();
    }
    public IntegerProperty jubiladoProperty(){
        return jubilado;
    }
    public void setJubilado(Integer jubilado){
        this.jubilado.set(jubilado);
    }

    //campo foto
    @XmlElement(name="foto")
    public String getFoto(){
        return foto.get();
    }
    public StringProperty fotoProperty(){
        return foto;
    }
    public void setFoto(String foto){
        this.foto.set(foto);
    }

}
