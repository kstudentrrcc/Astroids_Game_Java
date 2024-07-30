import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * StartScreenPanel is a JPanel that displays the start screen for the game.
 * It contains a title, an instruction label, and a start button.
 */
public class StartScreenPanel extends JPanel {

    // GUI components
    private final JButton startButton;
    private final JLabel titleLabel;
    private final JLabel instructionLabel;

    // Listener interface for start button action
    private StartScreenListener startScreenListener;

    /**
     * Constructor to initialize the start screen panel.
     */
    public StartScreenPanel() {
        // Set the preferred size of the panel
        setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
        
        // Set the background color of the panel
        setBackground(Color.BLACK);
        
        // Use BorderLayout for the panel layout
        setLayout(new BorderLayout());

        // Initialize and configure the title label
        titleLabel = new JLabel("Asteroids Game", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set font for title
        titleLabel.setForeground(Color.WHITE); // Set text color to white

        // Initialize and configure the instruction label
        instructionLabel = new JLabel("Click the start button to play", JLabel.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font for instructions
        instructionLabel.setForeground(Color.WHITE); // Set text color to white

        // Initialize and configure the start button
        startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font size for button text
        startButton.setPreferredSize(new Dimension(120, 40)); // Set the size of the button
        startButton.setFocusable(false); // Remove focusability to avoid focus issues
        startButton.setMargin(new Insets(5, 10, 5, 10)); // Set padding inside the button
        // Add action listener to handle button click events
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Notify the listener when the button is clicked
                if (startScreenListener != null) {
                    startScreenListener.onStartGame();
                }
            }
        });

        // Create a panel to hold the title, instruction, and button
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false); // Make the center panel transparent
        centerPanel.setLayout(new GridLayout(3, 1, 0, 20)); // Use GridLayout for vertical arrangement
        // Add components to the center panel
        centerPanel.add(titleLabel);
        centerPanel.add(instructionLabel);
        centerPanel.add(startButton);

        // Add the center panel to the main panel using BorderLayout.CENTER
        add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Sets the listener that will be notified when the start button is clicked.
     * 
     * @param listener the listener to set
     */
    public void setStartScreenListener(StartScreenListener listener) {
        this.startScreenListener = listener;
    }

    /**
     * Interface for listening to start button events.
     */
    public interface StartScreenListener {
        /**
         * Called when the start button is clicked.
         */
        void onStartGame();
    }
}

