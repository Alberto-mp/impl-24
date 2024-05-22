package uca.iiss;

public class Robot {
    private String id;
    private String nombre;
    private String material;
    private int annioFabricacion;
    private String fabricante;

    // Constructor por defecto
    public Robot() {
        this.id = "";
        this.nombre = "";
        this.material = "";
        this.annioFabricacion = 2024;
        this.fabricante = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getAnnioFabricacion() {
        return annioFabricacion;
    }

    public void setAnnioFabricacion(int annioFabricacion) {
        this.annioFabricacion = annioFabricacion;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}
