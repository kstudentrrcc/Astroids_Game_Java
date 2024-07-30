import java.awt.*;
import javax.swing.*;

/**
 * GameOverPanel is a JPanel that displays the game over screen with the final score and a restart button.
 */
public class GameOverPanel extends JPanel {

    // Dimensions of the game over panel
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    
    private int finalScore; // Stores the final score when the game ends

    private GameOverListener listener; // Listener to handle restart button action

    /**
     * Constructor to initialize the GameOverPanel.
     * 
     * @param listener the listener to handle restart actions
     * @param finalScore the final score to be displayed
     */
    public GameOverPanel(GameOverListener listener, int finalScore) {
        this.listener = listener; // Set the listener for restart actions
        this.finalScore = finalScore; // Initialize the final score
        
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Set the size of the panel
        setBackground(Color.BLACK); // Set the background color to black
        setFocusable(true); // Make sure the panel can receive focus

        // Create and configure the restart button
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(e -> listener.restartGame()); // Add action listener to restart the game
        add(restartButton); // Add the restart button to the panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call superclass method to ensure proper painting
        
        g.setColor(Color.WHITE); // Set the color for the text
        g.setFont(new Font("Arial", Font.BOLD, 36)); // Set font for game over message
        
        // Draw the game over message
        String gameOverMessage = "GAME OVER";
        int gameOverWidth = g.getFontMetrics().stringWidth(gameOverMessage); // Calculate width of the message
        g.drawString(gameOverMessage, WIDTH / 2 - gameOverWidth / 2, HEIGHT / 2 - 50); // Center the message horizontally and adjust vertical position
        
        // Draw the final score
        String scoreMessage = "Final Score: " + finalScore;
        int scoreWidth = g.getFontMetrics().stringWidth(scoreMessage); // Calculate width of the score message
        g.drawString(scoreMessage, WIDTH / 2 - scoreWidth / 2, HEIGHT / 2 + 50); // Center the score message horizontally and adjust vertical position
    }

    /**
     * Interface for listening to game over events, specifically to restart the game.
     */
    interface GameOverListener {
        void restartGame(); // Method to be called when the game should be restarted
    }
}
