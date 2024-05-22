import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.util.Set;

import org.junit.jupiter.api.Test;

import uca.iiss.Robot;

public class InspeccionCalidadTest {
    @Test
    public void testRobotRequiereInspeccionPorMaterial() {
        Robot robot = new Robot();

        robot.setId("1");
        robot.setNombre("Robot1");
        robot.setAnnioFabricacion(2019);
        robot.setMaterial("Hierro");
        robot.setFabricante("RobotSA");

        InspeccionCalidad aspect = InspeccionCalidad.aspectOf();
        assertTrue(aspect.getRobotsInspeccion().contains(robot));
    }

    @Test
    public void testRobotRequiereInspeccionPorFabricante() {
        Robot robot = new Robot();

        robot.setId("2");
        robot.setNombre("Robot2");
        robot.setAnnioFabricacion(2022);
        robot.setMaterial("Acero");
        robot.setFabricante("MakeBotSA");

        InspeccionCalidad aspect = InspeccionCalidad.aspectOf();
        assertTrue(aspect.getRobotsInspeccion().contains(robot));
    }

    @Test
    public void testRobotRequiereInspeccionPorAnnioFabricacion() {
        Robot robot = new Robot();

        robot.setId("3");
        robot.setNombre("Robot3");
        robot.setAnnioFabricacion(2017);
        robot.setMaterial("Acero");
        robot.setFabricante("RobotSA");

        InspeccionCalidad aspect = InspeccionCalidad.aspectOf();
        assertTrue(aspect.getRobotsInspeccion().contains(robot));
    }

    @Test
    public void testRobotNoRequiereInspeccion() {
        Robot robot = new Robot();

        robot.setId("4");
        robot.setNombre("Robot4");
        robot.setAnnioFabricacion(2023);
        robot.setMaterial("Aluminio");
        robot.setFabricante("RobotSA");

        InspeccionCalidad aspect = InspeccionCalidad.aspectOf();
        assertFalse(aspect.getRobotsInspeccion().contains(robot));
    }
}
