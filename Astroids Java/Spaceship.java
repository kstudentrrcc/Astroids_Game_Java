import java.awt.*;

/**
 * Represents the spaceship in the game.
 */
public class Spaceship {
    private int x, y; // Current position of the spaceship (coordinates)
    private final int size; // Size of the spaceship (diameter for a square representation)
    private int speed; // Current speed of the spaceship
    private double angle; // Direction in which the spaceship is pointing, in radians
    private boolean accelerating; // Indicates if the spaceship is accelerating

    /**
     * Constructs a Spaceship with a specified position.
     * 
     * @param x the initial X-coordinate of the spaceship
     * @param y the initial Y-coordinate of the spaceship
     */
    public Spaceship(int x, int y) {
        this.x = x;
        this.y = y;
        this.size = 20; // Size of the spaceship (diameter of the square representation)
        this.speed = 0; // Initial speed is zero
        this.angle = 0; // Initial angle (facing right)
        this.accelerating = false; // Not accelerating initially
    }

    /**
     * Updates the spaceship's position based on its speed and angle.
     * Applies acceleration if the spaceship is accelerating and friction otherwise.
     * Ensures the spaceship wraps around screen edges.
     */
    public void move() {
        if (accelerating) {
            speed += 1; // Increase speed if accelerating
        } else {
            speed *= 0.99; // Apply friction to reduce speed when not accelerating
        }

        // Limit the maximum and minimum speed to prevent excessive velocity
        if (speed > 5) speed = 5;
        if (speed < -5) speed = -5;

        // Update position based on current speed and angle
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);

        // Wrap around screen edges to create a continuous playing field
        if (x < 0) x = GamePanel.WIDTH;
        if (x > GamePanel.WIDTH) x = 0;
        if (y < 0) y = GamePanel.HEIGHT;
        if (y > GamePanel.HEIGHT) y = 0;
    }

    /**
     * Draws the spaceship on the provided Graphics object.
     * 
     * @param g the Graphics object used for drawing
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED); // Set the color for the spaceship
        Graphics2D g2d = (Graphics2D) g.create(); // Create a Graphics2D object for rotation
        g2d.rotate(angle, x, y); // Rotate the graphics context to match spaceship's angle
        int sizeHalf = size / 2;
        // Draw a square representing the spaceship centered at (x, y)
        g2d.fillRect(x - sizeHalf, y - sizeHalf, size, size);
        g2d.dispose(); // Dispose of the graphics context to release resources
    }

    /**
     * Sets whether the spaceship is accelerating.
     * 
     * @param accelerating true if the spaceship should accelerate, false otherwise
     */
    public void setAccelerating(boolean accelerating) {
        this.accelerating = accelerating;
    }

    /**
     * Adjusts the angle of the spaceship based on the direction of turning.
     * 
     * @param direction the direction to turn (-1 for left, 1 for right)
     */
    public void setTurning(int direction) {
        angle += direction * 0.1; // Update angle based on turning direction
    }

    /**
     * Gets the current X-coordinate of the spaceship.
     * 
     * @return the X-coordinate of the spaceship
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the current Y-coordinate of the spaceship.
     * 
     * @return the Y-coordinate of the spaceship
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the current angle of the spaceship.
     * 
     * @return the angle of the spaceship in radians
     */
    public double getAngle() {
        return angle;
    }

    /**
     * Resets the spaceship's speed to zero.
     */
    public void reset() {
        speed = 0; // Set speed to zero
    }
}
