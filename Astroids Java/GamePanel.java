import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * GamePanel is a JPanel that manages the game's state, rendering, and user input.
 * It handles the game loop, drawing the game elements, and managing game events.
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener, StartScreenPanel.StartScreenListener, GameOverPanel.GameOverListener {

    // Dimensions of the game panel
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    
    // Timer delay in milliseconds
    private final int DELAY = 10;
    
    // Game state variables
    private int score; // Player's score
    private int lives; // Number of lives remaining

    // Game components
    private Timer timer; // Timer for game loop
    private Spaceship spaceship; // Player's spaceship
    private final ArrayList<Bullet> bullets; // List of bullets fired by the spaceship
    private ArrayList<Asteroid> asteroids; // List of asteroids in the game

    // Track key states
    private boolean upKeyPressed;
    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private boolean spaceKeyPressed;

    // Game states
    private boolean gameRunning; // Flag to check if the game is running
    private StartScreenPanel startScreenPanel; // Panel displayed at the start of the game
    private GameOverPanel gameOverPanel; // Panel displayed when the game is over

    private JFrame mainFrame; // Reference to the main JFrame

    /**
     * Constructor to initialize the GamePanel.
     * 
     * @param mainFrame the main JFrame that holds this panel
     */
    public GamePanel(JFrame mainFrame) {
        this.mainFrame = mainFrame; // Store reference to the main frame
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Set panel size
        setBackground(Color.BLACK); // Set background color
        setFocusable(true); // Make sure the panel can receive focus

        // Initialize game components
        bullets = new ArrayList<>();
        asteroids = new ArrayList<>();
        score = 0;
        lives = 3; // Set initial number of lives
        gameRunning = false;

        // Initialize start and game over panels
        startScreenPanel = new StartScreenPanel();
        startScreenPanel.setStartScreenListener(this); // Set this panel as listener for start screen events

        gameOverPanel = new GameOverPanel(this, score); // Create game over panel and pass this as listener

        addKeyListener(this); // Add key listener to handle user input
        add(startScreenPanel); // Add start screen panel to this panel

        // Initialize key states
        upKeyPressed = false;
        leftKeyPressed = false;
        rightKeyPressed = false;
        spaceKeyPressed = false;
    }

    /**
     * Starts the game, initializing game components and removing the start screen.
     */
    private void startGame() {
        gameRunning = true; // Set game running flag
        lives = 3; // Reset lives
        score = 0; // Reset score
        spaceship = new Spaceship(WIDTH / 2, HEIGHT / 2); // Initialize spaceship at the center
        timer = new Timer(DELAY, this); // Initialize timer for game loop
        timer.start(); // Start the game loop

        // Spawn initial asteroids
        spawnInitialAsteroids();

        // Remove start screen panel and request focus for game panel
        remove(startScreenPanel);
        requestFocus();
    }

    /**
     * Spawns the initial set of asteroids in the game.
     */
    private void spawnInitialAsteroids() {
        asteroids.clear(); // Clear existing asteroids
        while (asteroids.size() < 5) {
            asteroids.add(new Asteroid()); // Add new asteroids
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass method to ensure proper painting
        if (gameRunning) {
            drawGame(g); // Draw game elements
            drawScore(g); // Draw score and lives
        } else {
            gameOverPanel.repaint(); // Paint game over panel if game is not running
        }
    }

    /**
     * Draws the game elements (spaceship, bullets, asteroids) on the panel.
     * 
     * @param g the Graphics object used for painting
     */
    private void drawGame(Graphics g) {
        spaceship.draw(g); // Draw the spaceship
        for (Bullet bullet : bullets) {
            bullet.draw(g); // Draw each bullet
        }
        for (Asteroid asteroid : asteroids) {
            asteroid.draw(g); // Draw each asteroid
        }
    }

    /**
     * Draws the current score and number of lives on the panel.
     * 
     * @param g the Graphics object used for painting
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.white); // Set color for text
        g.setFont(new Font("Arial", Font.BOLD, 20)); // Set font for score display
        g.drawString("Score: " + score, 20, 30); // Draw score
        g.drawString("Lives: " + lives, WIDTH - 120, 30); // Draw lives
    }

    /**
     * Updates the game state, including spaceship movement, bullet and asteroid positions, and collision detection.
     */
    private void updateGame() {
        if (!gameRunning) {
            return; // Do nothing if the game is not running
        }

        spaceship.move(); // Move the spaceship based on user input

        // Handle spaceship movement based on key states
        if (upKeyPressed) {
            spaceship.setAccelerating(true); // Accelerate spaceship if up key is pressed
        } else {
            spaceship.setAccelerating(false); // Stop accelerating if up key is not pressed
        }
        if (leftKeyPressed) {
            spaceship.setTurning(-1); // Turn spaceship left if left key is pressed
        } else if (rightKeyPressed) {
            spaceship.setTurning(1); // Turn spaceship right if right key is pressed
        } else {
            spaceship.setTurning(0); // Stop turning if no turn keys are pressed
        }

        // Fire bullets if space key is pressed
        if (spaceKeyPressed) {
            double angle = spaceship.getAngle(); // Get spaceship's current angle
            bullets.add(new Bullet(spaceship.getX(), spaceship.getY(), angle)); // Create new bullet
        }

        // Move and check bullets
        for (Bullet bullet : new ArrayList<>(bullets)) {
            bullet.move(); // Move each bullet
            if (!bullet.isActive()) {
                bullets.remove(bullet); // Remove inactive bullets
            }
        }

        // Move asteroids
        for (Asteroid asteroid : new ArrayList<>(asteroids)) {
            asteroid.move(); // Move each asteroid
        }

        // Check collisions between bullets, asteroids, and the spaceship
        checkCollisions();

        // Ensure there are always 5 asteroids in the game
        while (asteroids.size() < 5) {
            asteroids.add(new Asteroid()); // Add new asteroids if needed
        }
    }

    /**
     * Checks for collisions between bullets, asteroids, and the spaceship.
     */
    private void checkCollisions() {
        Rectangle shipBounds = new Rectangle(spaceship.getX() - 10, spaceship.getY() - 10, 20, 20); // Create bounds for the spaceship

        // Check bullet-asteroid collisions
        for (Bullet bullet : new ArrayList<>(bullets)) {
            Rectangle bulletBounds = new Rectangle(bullet.getX() - 2, bullet.getY() - 2, 4, 4); // Create bounds for each bullet
            for (Asteroid asteroid : new ArrayList<>(asteroids)) {
                Rectangle asteroidBounds = new Rectangle(asteroid.getX() - asteroid.getSize() / 2, asteroid.getY() - asteroid.getSize() / 2, asteroid.getSize(), asteroid.getSize()); // Create bounds for each asteroid
                if (bulletBounds.intersects(asteroidBounds)) {
                    bullets.remove(bullet); // Remove bullet if it hits an asteroid
                    score += 10; // Increase score
                    asteroids.remove(asteroid); // Remove asteroid
                    break; // Exit loop to prevent multiple collisions
                }
            }
        }

        // Check ship-asteroid collisions
        for (Asteroid asteroid : new ArrayList<>(asteroids)) {
            Rectangle asteroidBounds = new Rectangle(asteroid.getX() - asteroid.getSize() / 2, asteroid.getY() - asteroid.getSize() / 2, asteroid.getSize(), asteroid.getSize()); // Create bounds for each asteroid
            if (shipBounds.intersects(asteroidBounds)) {
                // Handle ship collision
                lives--; // Decrease lives
                if (lives <= 0) {
                    endGame(); // End game if lives reach zero
                } else {
                    spaceship = new Spaceship(WIDTH / 2, HEIGHT / 2); // Reset spaceship position
                    spaceship.reset(); // Reset spaceship speed
                    asteroids.clear(); // Clear existing asteroids
                    spawnInitialAsteroids(); // Spawn new asteroids
                }
                break; // Exit loop after collision
            }
        }
    }

    /**
     * Ends the game, stops the timer, and shows the game over screen.
     */
    private void endGame() {
        gameRunning = false; // Stop the game
        timer.stop(); // Stop the game timer
        removeKeyListener(this); // Remove key listener
        mainFrame.remove(this); // Remove game panel from the main frame
        mainFrame.add(new GameOverPanel(this, score)); // Add game over panel to the main frame
        mainFrame.revalidate(); // Revalidate the main frame
        mainFrame.repaint(); // Repaint the main frame
    }

    /**
     * Restarts the game, resetting game state and reinitializing components.
     */
    private void restart() {
        gameRunning = true; // Start the game
        lives = 3; // Reset lives
        score = 0; // Reset score
        spaceship = new Spaceship(WIDTH / 2, HEIGHT / 2); // Reinitialize spaceship
        spaceship.reset(); // Reset spaceship speed
        bullets.clear(); // Clear existing bullets
        asteroids.clear(); // Clear existing asteroids
        spawnInitialAsteroids(); // Spawn new asteroids
        timer.restart(); // Restart the game timer

        // Reset key states
        upKeyPressed = false;
        leftKeyPressed = false;
        rightKeyPressed = false;
        spaceKeyPressed = false;

        addKeyListener(this); // Re-add key listener

        mainFrame.remove(gameOverPanel); // Remove game over panel from the main frame
        mainFrame.add(this); // Add game panel back to the main frame
        requestFocus(); // Request focus for game panel
        mainFrame.revalidate(); // Revalidate the main frame
        mainFrame.repaint(); // Repaint the main frame
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateGame(); // Update the game state
        repaint(); // Repaint the panel
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode(); // Get the code of the pressed key
        if (keyCode == KeyEvent.VK_UP) {
            upKeyPressed = true; // Set flag for up key
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            leftKeyPressed = true; // Set flag for left key
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightKeyPressed = true; // Set flag for right key
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spaceKeyPressed = true; // Set flag for space key
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode(); // Get the code of the released key
        if (keyCode == KeyEvent.VK_UP) {
            upKeyPressed = false; // Reset flag for up key
        }
        if (keyCode == KeyEvent.VK_LEFT) {
            leftKeyPressed = false; // Reset flag for left key
        }
        if (keyCode == KeyEvent.VK_RIGHT) {
            rightKeyPressed = false; // Reset flag for right key
        }
        if (keyCode == KeyEvent.VK_SPACE) {
            spaceKeyPressed = false; // Reset flag for space key
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    @Override
    public void onStartGame() {
        startGame(); // Start the game when the start event is received
    }

    @Override
    public void restartGame() {
        restart(); // Restart the game when the restart event is received
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Asteroids Game"); // Create the main frame
        GamePanel gamePanel = new GamePanel(frame); // Create game panel and pass the frame
        frame.add(gamePanel); // Add game panel to the frame
        frame.pack(); // Pack frame contents
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
        frame.setLocationRelativeTo(null); // Center the frame
        frame.setVisible(true); // Make the frame visible
    }
}
