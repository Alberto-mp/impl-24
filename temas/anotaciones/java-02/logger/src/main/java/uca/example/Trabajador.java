package uca.example;

public class Trabajador {
    public Trabajador(String nombre, String ocupacion, String dni, String contraseña, double salario){
        this.nombre = nombre;
        this.ocupacion = ocupacion;
        this.dni = dni;
        this.contraseña = contraseña;
        this.salario = salario;
    }

    @UsuarioAttribute
    private String nombre;
    
    @UsuarioAttribute
    private String ocupacion;
    
    @AdministradorAttribute
    private String dni;
    
    @AdministradorAttribute
    private String contraseña;
    
    @JefeAttribute
    private double salario;
}
