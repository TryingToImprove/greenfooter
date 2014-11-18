package greenfooter;

import greenfoot.Actor;
import greenfoot.World;
import greenfoot.core.WorldHandler;
import greenfoot.gui.input.InputManager;
import greenfoot.platforms.WorldHandlerDelegate;
import java.awt.event.MouseEvent;

class WorldHandlerDelegateStandalone implements WorldHandlerDelegate {

    private final GreenfootStandaloneScenarioViewer viewer;
    private final boolean lockScenario;

    private WorldHandler worldHandler;

    public WorldHandlerDelegateStandalone(GreenfootStandaloneScenarioViewer viewer, boolean lockScenario) {
        this.viewer = viewer;
        this.lockScenario = lockScenario;
    }

    @Override
    public boolean maybeShowPopup(MouseEvent e) {
        // Not used in standalone
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Not used in standalone
    }

    @Override
    public void setWorld(final World oldWorld, final World newWorld) {
        // Not used in standalone
    }

    @Override
    public void setWorldHandler(WorldHandler handler) {
        this.worldHandler = handler;
    }

    @Override
    public void addActor(Actor actor, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void instantiateNewWorld() {
        WorldHandler.getInstance().clearWorldSet();

        if (!WorldHandler.getInstance().checkWorldSet()) {
            WorldHandler.getInstance().setWorld(viewer.instantiateNewWorld());
        }
    }

    @Override
    public InputManager getInputManager() {
        InputManager inputManager = new InputManager();
        inputManager.setDragListeners(null, null, null);

        if (lockScenario) {
            inputManager.setIdleListeners(null, null, null);
            inputManager.setMoveListeners(null, null, null);
        } else {
            inputManager.setIdleListeners(worldHandler, worldHandler, worldHandler);
            inputManager.setMoveListeners(worldHandler, worldHandler, worldHandler);
        }
        return inputManager;
    }

    @Override
    public void discardWorld(World world) {
        // Not used in standalone 
    }

    @Override
    public void actorDragged(Actor actor, int xCell, int ycell) {
        // Not used in standalone
    }

    @Override
    public void objectAddedToWorld(Actor actor) {
        // Not used in standalone
    }

}
