package edu.kis.powp.jobs2d;
import edu.kis.powp.jobs2d.magicpresets.FiguresJoe;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.powp.jobs2d.magicpresets.FiguresJane;
import edu.kis.powp.jobs2d.drivers.adapter.AbstractDriverAdapter;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.jobs2d.drivers.adapter.LineDrawerAdapter;
import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.adapter.DrawPanelControllerAdapter;
import edu.kis.powp.jobs2d.events.SelectChangeVisibleOptionListener;
import edu.kis.powp.jobs2d.events.SelectTestFigureOptionListener;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.command.FigureCommandFactory;
import edu.kis.powp.command.ComplexCommand;
public class TestJobs2dPatterns {
	private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/**
	 * Setup test concerning preset figures in context.
	 * 
	 * @param application Application context.
	 */
	private static void setupPresetTests(Application application) {
		SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
				DriverFeature.getDriverManager());
        application.addTest("Figure Joe 2", (ActionEvent e) -> {
            FiguresJoe.figureScript2(DriverFeature.getDriverManager().getCurrentDriver());
        });
		application.addTest("Figure Joe 1", selectTestFigureOptionListener);
        application.addTest("Figure Jane", (ActionEvent e) -> {
            Job2dDriver currentDriver = DriverFeature.getDriverManager().getCurrentDriver();
            AbstractDriverAdapter adapter = new AbstractDriverAdapter(currentDriver);
            FiguresJane.figureScript(adapter);

        });
        application.addTest("Rectangle Command", (ActionEvent e) -> {
            ComplexCommand rectangle = FigureCommandFactory.createRectangle(-100, -100, 200, 200);
            rectangle.execute(DriverFeature.getDriverManager().getCurrentDriver());
        });

        application.addTest("Triangle Command", (ActionEvent e) -> {
            ComplexCommand triangle = FigureCommandFactory.createTriangle(0, -100, 100, 100, -100, 100);
            triangle.execute(DriverFeature.getDriverManager().getCurrentDriver());
        });

        application.addTest("Circle Command", (ActionEvent e) -> {
            ComplexCommand circle = FigureCommandFactory.createCircle(0, 0, 100, 36);
            circle.execute(DriverFeature.getDriverManager().getCurrentDriver());
        });
	}

	/**
	 * Setup driver manager, and set default driver for application.
	 * 
	 * @param application Application context.
	 */
	private static void setupDrivers(Application application) {
		Job2dDriver loggerDriver = new LoggerDriver();
		DriverFeature.addDriver("Logger Driver", loggerDriver);
		DriverFeature.getDriverManager().setCurrentDriver(loggerDriver);

        Job2dDriver testDriver = new DrawPanelControllerAdapter(DrawerFeature.getDrawerController());
		DriverFeature.addDriver("Buggy Simulator", testDriver);

        DrawPanelController controller = DrawerFeature.getDrawerController();

        Job2dDriver specialLineDriver = new LineDrawerAdapter(controller, LineFactory.getSpecialLine());
        DriverFeature.addDriver("Special Line Adapter", specialLineDriver);

        Job2dDriver dottedLineDriver = new LineDrawerAdapter(controller, LineFactory.getDottedLine());
        DriverFeature.addDriver("Dotted Line Adapter", dottedLineDriver);
		DriverFeature.updateDriverInfo();
	}

	/**
	 * Auxiliary routines to enable using Buggy Simulator.
	 * 
	 * @param application Application context.
	 */
	private static void setupDefaultDrawerVisibilityManagement(Application application) {
		DefaultDrawerFrame defaultDrawerWindow = DefaultDrawerFrame.getDefaultDrawerFrame();
		application.addComponentMenuElementWithCheckBox(DrawPanelController.class, "Default Drawer Visibility",
				new SelectChangeVisibleOptionListener(defaultDrawerWindow), true);
		defaultDrawerWindow.setVisible(true);
	}

	/**
	 * Setup menu for adjusting logging settings.
	 * 
	 * @param application Application context.
	 */
	private static void setupLogger(Application application) {
		application.addComponentMenu(Logger.class, "Logger", 0);
		application.addComponentMenuElement(Logger.class, "Clear log",
				(ActionEvent e) -> application.flushLoggerOutput());
		application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
		application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
		application.addComponentMenuElement(Logger.class, "Warning level",
				(ActionEvent e) -> logger.setLevel(Level.WARNING));
		application.addComponentMenuElement(Logger.class, "Severe level",
				(ActionEvent e) -> logger.setLevel(Level.SEVERE));
		application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Application app = new Application("2d jobs Visio");
				DrawerFeature.setupDrawerPlugin(app);
				setupDefaultDrawerVisibilityManagement(app);

				DriverFeature.setupDriverPlugin(app);
				setupDrivers(app);
				setupPresetTests(app);
				setupLogger(app);

				app.setVisibility(true);
			}
		});
	}

}
