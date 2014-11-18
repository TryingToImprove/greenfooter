package greenfooter;

import bluej.Config;
import greenfoot.World;
import greenfoot.actions.PauseSimulationAction;
import greenfoot.actions.ResetWorldAction;
import greenfoot.actions.RunSimulationAction;
import greenfoot.core.ProjectProperties;
import greenfoot.core.Simulation;
import greenfoot.core.WorldHandler;
import greenfoot.gui.WorldCanvas;
import greenfoot.gui.input.mouse.LocationTracker;
import greenfoot.platforms.standalone.ActorDelegateStandAlone;
import greenfoot.platforms.standalone.SimulationDelegateStandAlone;
import greenfoot.util.GreenfootUtil;
import greenfoot.util.StandalonePropStringManager;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class GreenfootStandaloneScenarioViewer extends JPanel {

    private ProjectProperties properties;
    private Simulation sim;
    private WorldCanvas canvas;

    private boolean isInitialized = false;

    private Constructor<?> worldConstructor;

    public GreenfootStandaloneScenarioViewer() {

    }

    public void initialize() {
        if (!isInitialized) {
            Properties p = new Properties();

            try (InputStream is = getClass().getClassLoader().getResourceAsStream("standalone.properties")) {
                p.load(is);
            } catch (IOException ex) {
                Logger.getLogger(GreenfootStandaloneScenarioViewer.class.getName()).log(Level.SEVERE, null, ex);
            }

            Config.initializeStandalone(new StandalonePropStringManager(p));

            final String worldClassName = Config.getPropString("main.class");
            final boolean lockScenario = Config.getPropBoolean("scenario.lock");

            try {
                GreenfootUtil.initialise(new GreenfootUtilDelegateStandalone());
                properties = new ProjectProperties();

                ActorDelegateStandAlone.setupAsActorDelegate();
                ActorDelegateStandAlone.initProperties(properties);

                // We must construct the simulation before the world, as a call to
                // Greenfoot.setSpeed() requires a call to the simulation instance.
                Simulation.initialize(new SimulationDelegateStandAlone());

                guiSetup(lockScenario);

                Class<?> worldClass = Class.forName(worldClassName);
                worldConstructor = worldClass.getConstructor(new Class[]{});
            } catch (SecurityException | IllegalArgumentException | ClassNotFoundException | NoSuchMethodException ex) {
                Logger.getLogger(GreenfootStandaloneScenarioViewer.class.getName()).log(Level.SEVERE, null, ex);
            }

            isInitialized = true;
        }
    }

    private void guiSetup(boolean lockScenario) {
        canvas = new WorldCanvas(null);
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                canvas.requestFocusInWindow();
                canvas.requestFocus();
            }
        });

        WorldHandler.initialise(canvas, new WorldHandlerDelegateStandalone(this, lockScenario));
        WorldHandler worldHandler = WorldHandler.getInstance();
        sim = Simulation.getInstance();
        sim.attachWorldHandler(worldHandler);
        LocationTracker.initialize();
        this.add(canvas, BorderLayout.CENTER);

        RunSimulationAction.getInstance().attachSimulation(sim);
        ResetWorldAction.getInstance().attachSimulation(sim);
        PauseSimulationAction.getInstance().attachSimulation(sim);
    }

    public void run() {
        if (!isInitialized) {
            initialize();
        }

        RunSimulationAction.getInstance().actionPerformed(null);

        canvas.requestFocusInWindow();
        canvas.requestFocus();
    }

    public void stop() {
        if (!isInitialized) {
            initialize();
        }

        ResetWorldAction.getInstance().actionPerformed(null);
    }

    public void pause() {
        if (!isInitialized) {
            initialize();
        }

        PauseSimulationAction.getInstance().actionPerformed(null);
    }

    public World instantiateNewWorld() {
        World world = null;

        try {
            world = (World) worldConstructor.newInstance(new Object[]{});
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(GreenfootStandaloneScenarioViewer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return world;
    }

    public World getWorld() {
        return WorldHandler.getInstance().getWorld();
    }

    public ReentrantReadWriteLock getWorldLock(World world) {
        return WorldHandler.getInstance().getWorldLock();
    }
}
