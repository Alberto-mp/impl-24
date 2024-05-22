import java.util.Set;
import java.util.HashSet;
import uca.iiss.Robot;

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