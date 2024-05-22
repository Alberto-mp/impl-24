package uca.example;

public class Empresa {
    public Empresa(String nombre, String cif, String direccion){
        this.nombre = nombre;
        this.cif = cif;
        this.direccion = direccion;
    }

    @UsuarioAttribute
    private String nombre;
    
    @AdministradorAttribute
    private String direccion;
    
    @JefeAttribute
    private String cif;
}
