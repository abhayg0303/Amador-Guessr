import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameGUI {
    private final List<Location> locations;
    private Location currentLocation;
    private int score = 0;
    private int round = 0;
    private final int totalRounds = 5;
    private boolean hasGuessed = false;

    private JFrame frame;
    private JLabel imageLabel;
    private JPanel buttonsPanel;
    private JLabel feedbackLabel;

    public GameGUI() {
        this.locations = new ArrayList<>();
        loadLocations();
        Collections.shuffle(locations);
    }

    public void startGame() {
        frame = new JFrame("Amador Valley GeoGuessr");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(imageLabel, BorderLayout.CENTER);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, 1));
        frame.add(buttonsPanel, BorderLayout.SOUTH);

        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        frame.add(feedbackLabel, BorderLayout.NORTH);

        nextRound();
        frame.setVisible(true);
    }

    private void loadLocations() {
        // Replace these paths with actual image paths
        locations.add(new Location("Science Building", "images/Amador-Science.jpg"));
        locations.add(new Location("Football Field", "images/Football Field.jpg"));
        locations.add(new Location("Gym", "images/Gym.jpg"));
        locations.add(new Location("Quad", "images/Quad.jpg"));
        locations.add(new Location("Front Office", "images/FrontOffice.jpg"));
    }

    private void nextRound() {
        if (round >= totalRounds) {
            showFinalScore();
            return;
        }

        hasGuessed = false;
        round++;

        currentLocation = locations.get(new Random().nextInt(locations.size()));
        displayImage(currentLocation.getImagePath());
        setupChoices();

        feedbackLabel.setText("Round " + round + ": Where is this?");
    }

    private void displayImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        imageLabel.setCursor(Cursor.getDefaultCursor());
    }

    private void setupChoices() {
        buttonsPanel.removeAll();
        List<Location> shuffledChoices = new ArrayList<>(locations);
        Collections.shuffle(shuffledChoices);

        for (Location loc : shuffledChoices) {
            JButton button = new JButton(loc.getName());
            button.addActionListener(e -> handleGuess(loc));
            buttonsPanel.add(button);
        }

        frame.revalidate();
        frame.repaint();
    }

    private void handleGuess(Location guess) {
        if (hasGuessed) return;
        hasGuessed = true;

        if (guess.getName().equals(currentLocation.getName())) {
            score++;
            feedbackLabel.setText("Correct!");
        } else {
            feedbackLabel.setText("Wrong! It was: " + currentLocation.getName());
        }

        for (Component comp : buttonsPanel.getComponents()) {
            if (comp instanceof JButton) {
                comp.setEnabled(false);
            }
        }

        imageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imageLabel.setCursor(Cursor.getDefaultCursor());
                imageLabel.removeMouseListener(this);
                nextRound();
            }
        });
    }

    private void showFinalScore() {
        JOptionPane.showMessageDialog(frame,
                "Game Over! You scored " + score + " out of " + totalRounds);
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI game = new GameGUI();
            game.startGame();
        });
    }
}
