public class GameGUI {
    private final List<Location> locations;
    private Location currentLocation;
    private int score = 0;
    private int round = 0;
    private final int totalRounds = 5;

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

    private void loadLocations() { //MAKE SURE TO ADD THE NAME OF THE WEB FILE 
        locations.add(new Location("Science Bulding", Amador-Science.jpg));
        locations.add(new Location("Cafeteria", ));
        locations.add(new Location("Gym", ));
        locations.add(new Location("Quad", ));
        locations.add(new Location("Front Office", ));
    }

    private void nextRound() {
        if (round >= totalRounds) {
            showFinalScore();
            return;
        }

        round++;
        currentLocation = locations.get(new Random().nextInt(locations.size()));
        displayImage(currentLocation.getImagePath());
        setupChoices();
    }

    private void displayImage(String path) {
        ImageIcon icon = new ImageIcon(path);
        Image scaledImage = icon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void setupChoices() {
        buttonsPanel.removeAll();
        feedbackLabel.setText("Round " + round + ": Where is this?");

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
        if (guess.getName().equals(currentLocation.getName())) {
            score++;
            feedbackLabel.setText("Correct!");
        } else {
            feedbackLabel.setText("Wrong! It was: " + currentLocation.getName());
        }

        Timer timer = new Timer(1500, e -> nextRound());
        timer.setRepeats(false);
        timer.start();
    }

    private void showFinalScore() {
        JOptionPane.showMessageDialog(frame, "Game Over! You scored " + score + " out of " + totalRounds);
        frame.dispose();
    }
}