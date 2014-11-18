import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)
import java.awt.Color;

/**
 * Space. Something for rockets to fly in...
 * 
 * @author Michael Kolling
 */
public class Space extends World
{
    public Space() 
    {
        super(600, 400, 1);
        GreenfootImage background = getBackground();
        background.setColor(Color.BLACK);
        background.fill();
        createStars(300);
        Explosion.initialiseImages();

        prepare();
    }

    private void createStars(int number) 
    {
        GreenfootImage background = getBackground();             
        for(int i=0; i < number; i++) {            
            int x = Greenfoot.getRandomNumber( getWidth() );
            int y = Greenfoot.getRandomNumber( getHeight() );
            int color = 150 - Greenfoot.getRandomNumber(120);
            background.setColorAt(x,y,new Color(color,color,color));
        }
    }

    /**
     * Prepare the world for the start of the program. That is: create the initial
     * objects and add them to the world.
     */
    private void prepare()
    {
        Rocket rocket = new Rocket();
        addObject(rocket, 261, 193);
        Asteroid asteroid = new Asteroid();
        addObject(asteroid, 463, 82);
        Asteroid asteroid2 = new Asteroid();
        addObject(asteroid2, 280, 297);
        Asteroid asteroid3 = new Asteroid();
        addObject(asteroid3, 93, 131);
    }
}