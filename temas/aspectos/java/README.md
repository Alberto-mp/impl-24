# Aspectos en Java

El concepto de aspectos en la programación está relacionado con modularización y separación de preocupaciones en un programa. El objetivo es aislar y tratar aspectos específicos de un problema sin afectar a la estructura del código.

Para visualizar este concepto, hemos realizado un pequeño ejemplo en java usando AspectJ, una extensión del lenguaje java que implementa los conceptos de la programación orientada a aspectos.

# Ejemplo: Inspección de Robots

Imaginemos que somos un trabajador de una empresa que se encarga de inspeccionar robots. Nuestra empresa, recibe una serie de robots y cuando almacena sus datos, debemos comprobar si mediante estos podemos detectar si un robot necesita una inspección o podemos darle nuestro sello de calidad. Para nuestra empresa, un robot necesita una inspección si:

- El robot fue fabricado hace más de 5 años
- El robot está hecho de Hierro o Cobre, debido a que estos materiales son especialmente susceptibles al óxido.
- El fabricante del robot es la empresa MakeBotSA, una empresa que históricamente ha fabricado robots que han resultado defectuosos.

Veamos que gracias a la programación orientada a aspectos, podemos dar con una solución bastante sencilla para automatizar este proceso.

Esta solución se encuentra en los archivos [Robot.java](aspectos/src/main/java/uca/iiss/Robot.java) y [InspeccionCalidad.aj](aspectos/src/main/aspect/InspeccionCalidad.aj)

```java
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
```

```aspectj
public aspect InspeccionCalidad {
    private final String fabricanteInspeccion = "MakeBotSA";
    private final int annioAct = 2024;
    private Set<Robot> robotsInspeccion = new HashSet<>();

    //Pointcut, cuando se establezca el material, annio de fabricación o fabricante
    pointcut llamadasSetter(Robot robot):
        target(robot) &&
        (call(void Robot.setMaterial(String)) ||
         call(void Robot.setAnnioFabricacion(int)) ||
         call(void Robot.setFabricante(String)));

    // Consejo para manejar la lógica de inspección
    after(Robot robot): llamadasSetter(robot) {
        if (annioAct - robot.getAnnioFabricacion() > 5 || 
            robot.getMaterial().equals("Hierro") || 
            robot.getMaterial().equals("Cobre") || 
            robot.getFabricante().equals(fabricanteInspeccion)) {
            robotsInspeccion.add(robot);
        }
    }

    // Métodos para obtener los robots que deben ser inspeccionados
    public Set<Robot> getRobotsInspeccion() {
        return robotsInspeccion;
    }
}
```

La clase `Robot` es una clase sencilla, simplemente guarda los atributos de cada robot. Por comodidad, solo tenemos un constructor predeterminado que inicializa los atributos a unos valores por defecto, y luego seremos nosotros los que utilizaremos los setters para guardar los datos del robot.

Más interesate resulta el aspecto `InspeccionCalidad`. El aspecto esta constituido por su punto de corte o pointcut `llamadasSetter()`. Este punto de corte activará el aspecto cuando un objeto de la clase `Robot` llame a cualquiera de los 3 setters que son relevantes para nuestras condiciones de inspección.
La logica de inspección esta en el consejo que se ejecutará después (por ello lo de `after`) de que se active el punto de corte. Este consejo comprobará si alguno de los datos del robot se corresponde con alguna de las condiciones de inspección y si es así, lo almacena en una lista.

Con el método `getRobotsInspeccion()` podremos obtener en cualquier momento la lista de robots que deben ser inspeccionados.

# Pruebas

Tendremos 1 fichero de prueba para estas clases:

- [InspeccionCalidadTest.java](aspectos/src/test/java/InspeccionCalidadTest.java)

En él encontraremos unas sencillas pruebas para verificar la correción de las clases. En nuestro fichero de configuración [pom.xml](aspectos/pom.xml), podemos ver que hemos añadido el paquete jUnit para realizar los tests, así como AspectJ para trabajar con aspectos.

Para la ejecución de las pruebas, se ha realizado un pipeline de Github Actions para ejecutarlas automáticamente. El fichero se encuentra [aquí](../../../.github/workflows/aspectos.java.yml) y puede ser ejecutado desde GitHub en el apartado 'Actions'.