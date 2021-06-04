package app.fotoschicas.premium.personas;

import java.io.Serializable;

public class Persona implements Serializable {
    private String nombre;
    private String imagen;

    public Persona() {
    }

    public Persona(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}

