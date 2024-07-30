import java.awt.*;

/**
 * Represents a bullet fired by the spaceship in the game.
 */
public class Bullet {
    private int x, y; // Current position of the bullet (coordinates)
    private final double angle; // Angle of movement in radians
    private final int speed = 10; // Speed at which the bullet moves
    private boolean active; // Flag to determine if the bullet is still active or has gone off-screen

    /**
     * Constructs a Bullet with a specified position and angle of movement.
     * 
     * @param x the initial X-coordinate of the bullet
     * @param y the initial Y-coordinate of the bullet
     * @param angle the angle of movement in radians
     */
    public Bullet(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.active = true; // Initialize bullet as active
    }

    /**
     * Updates the bullet's position based on its speed and angle of movement.
     * Deactivates the bullet if it moves off-screen.
     */
    public void move() {
        // Update bullet's position based on speed and angle
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
        
        // Deactivate the bullet if it goes off-screen
        if (x < 0 || x > GamePanel.WIDTH || y < 0 || y > GamePanel.HEIGHT) {
            active = false; // Bullet is no longer active
        }
    }

    /**
     * Draws the bullet on the provided Graphics object.
     * 
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.setColor(Color.white); // Set color for the bullet
        // Draw the bullet as a filled oval centered at (x, y) with diameter 4
        g.fillOval(x - 2, y - 2, 4, 4);
    }

    /**
     * Checks if the bullet is still active (i.e., has not gone off-screen).
     * 
     * @return true if the bullet is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Gets the X-coordinate of the bullet.
     * 
     * @return the X-coordinate of the bullet
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the Y-coordinate of the bullet.
     * 
     * @return the Y-coordinate of the bullet
     */
    public int getY() {
        return y;
    }
}
