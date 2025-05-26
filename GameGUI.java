import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class GameGUI { 
    
    private final List<Location> locations;
    private Location currentLocation; 
    private Location map = new Location("Map","Amador Map.jpg");
    private int score = 0;
    private int round = 0;
    private final int totalRounds = 10;
    private boolean hasGuessed = false;

    private JFrame frame;
    private JLabel imageLabel;
    private JPanel buttonsPanel;
    private JLabel feedbackLabel; 
    private JPanel imagesPanel;
    private JLabel imageLabel1;
    private JLabel imageLabel2;
 
     
    
    public GameGUI() {
        this.locations = new ArrayList<>();
        loadLocations();
        Collections.shuffle(locations);
    }

    public void startGame() {
        frame = new JFrame("Amador Valley GeoGuessr");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 500);
        frame.setLayout(new BorderLayout()); 

    imagesPanel = new JPanel(new GridLayout(1, 2));
    imageLabel1 = new JLabel();
    imageLabel1.setHorizontalAlignment(JLabel.CENTER);
    imageLabel2 = new JLabel();
    imageLabel2.setHorizontalAlignment(JLabel.CENTER);
    imagesPanel.add(imageLabel1);
    imagesPanel.add(imageLabel2);
    frame.add(imagesPanel, BorderLayout.CENTER);

    buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new GridLayout(0, 1));
    frame.add(buttonsPanel, BorderLayout.SOUTH);

    feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
    frame.add(feedbackLabel, BorderLayout.NORTH);

    nextRound();
    frame.setVisible(true);

        
        //imageLabel = new JLabel();
        // imageLabel.setHorizontalAlignment(JLabel.CENTER);
        // frame.add(imageLabel, BorderLayout.CENTER);

        // buttonsPanel = new JPanel();
        // buttonsPanel.setLayout(new GridLayout(0, 1));
        // frame.add(buttonsPanel, BorderLayout.SOUTH);

        // feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        // frame.add(feedbackLabel, BorderLayout.NORTH);

        // nextRound();
        // frame.setVisible(true);
    }

    private void loadLocations() {
        // Replace actual image paths
       // locations.add(new Location("F", "AR.jpg"));
        locations.add(new Location("B", "Football Field.jpg"));
        locations.add(new Location("K", "tree behind r building.jpg"));  
        locations.add(new Location("G", "j building.jpg"));  
        locations.add(new Location("D","pool.jpg"));  
        locations.add(new Location("E","softball.jpg"));  
        locations.add(new Location("H","library.jpg"));  
        locations.add(new Location("J","r building .jpg")); 
        locations.add(new Location("C", "Q building .jpg"));  
        //Location map = new Location("Map","Amador Map.jpg");
    }

    private void nextRound() { 

        if (round >= totalRounds) {
            showFinalScore();
            return;
        }

        hasGuessed = false;
        round++;
        
        currentLocation = locations.get(new Random().nextInt(locations.size()));
        displayImage(currentLocation.getImagePath(),map.getImagePath());
        setupChoices();

        feedbackLabel.setText("Round " + round + ": Where is this?");
    }

    private void displayImage(String path1, String path2) {
        ImageIcon icon = new ImageIcon(path1);
        Image scaledImage1 = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        imageLabel1.setIcon(new ImageIcon(scaledImage1));
         
         ImageIcon icon2 = new ImageIcon(path2); 
         Image scaledImage2 = icon2.getImage().getScaledInstance(400,300, Image.SCALE_SMOOTH);
        imageLabel2.setIcon(new ImageIcon(scaledImage2)); 
           
        imageLabel1.setCursor(Cursor.getDefaultCursor()); 
        imageLabel2.setCursor(Cursor.getDefaultCursor()); 
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

        imageLabel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        imageLabel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                imageLabel1.setCursor(Cursor.getDefaultCursor());
                imageLabel1.removeMouseListener(this);
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
