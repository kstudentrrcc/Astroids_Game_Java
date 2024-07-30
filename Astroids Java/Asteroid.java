import java.awt.*;
import java.util.Random;

/**
 * Represents an asteroid in the game with position, movement, and size.
 */
public class Asteroid {
    private int x, y; // Position of the asteroid (coordinates)
    private double angle; // Angle of movement in radians
    private int speed; // Speed of movement
    private int size; // Size of the asteroid (diameter)

    /**
     * Constructs an Asteroid with random position, movement angle, speed, and size.
     * Ensures that the asteroid does not spawn overlapping with the spaceship.
     */
    public Asteroid() {
        Random rand = new Random();
        do {
            // Randomly initialize the asteroid's position
            x = rand.nextInt(GamePanel.WIDTH); // X-coordinate within game panel width
            y = rand.nextInt(GamePanel.HEIGHT); // Y-coordinate within game panel height
            
            // Randomly initialize movement angle (0 to 2π)
            angle = rand.nextDouble() * 2 * Math.PI;
            
            // Randomly initialize speed (1 to 3)
            speed = rand.nextInt(3) + 1;
            
            // Randomly initialize size (10 to 30)
            size = rand.nextInt(20) + 10;
            
        // Ensure the asteroid does not spawn overlapping with the spaceship
        } while (overlapsWithShip());
    }

    /**
     * Checks if the asteroid's position overlaps with the spaceship's position.
     * 
     * @return true if the asteroid overlaps with the spaceship, false otherwise
     */
    private boolean overlapsWithShip() {
        // Create a bounding rectangle for the asteroid
        Rectangle asteroidBounds = new Rectangle(x - size / 2, y - size / 2, size, size);
        
        // Create a bounding rectangle for the spaceship (assumed to be centered)
        Rectangle shipBounds = new Rectangle(GamePanel.WIDTH / 2 - 10, GamePanel.HEIGHT / 2 - 10, 20, 20);
        
        // Check if the asteroid's bounding rectangle intersects with the spaceship's bounding rectangle
        return asteroidBounds.intersects(shipBounds);
    }

    /**
     * Updates the asteroid's position based on its speed and angle of movement.
     * Ensures the asteroid wraps around the screen edges.
     */
    public void move() {
        // Update the asteroid's position based on speed and angle
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
        
        // Wrap around screen edges if the asteroid moves off the visible area
        if (x < -size) x = GamePanel.WIDTH + size; // Wrap left
        if (x > GamePanel.WIDTH + size) x = -size; // Wrap right
        if (y < -size) y = GamePanel.HEIGHT + size; // Wrap top
        if (y > GamePanel.HEIGHT + size) y = -size; // Wrap bottom
    }

    /**
     * Draws the asteroid on the provided Graphics object.
     * 
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.setColor(Color.white); // Set the color for the asteroid
        // Draw the asteroid as an oval centered at (x, y) with the specified size
        g.drawOval(x - size / 2, y - size / 2, size, size);
    }

    /**
     * Gets the X-coordinate of the asteroid.
     * 
     * @return the X-coordinate of the asteroid
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y-coordinate of the asteroid.
     * 
     * @return the Y-coordinate of the asteroid
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the size (diameter) of the asteroid.
     * 
     * @return the size of the asteroid
     */
    public int getSize() {
        return size;
    }
}
