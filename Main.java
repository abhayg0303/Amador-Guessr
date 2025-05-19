 public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameGUI game = new GameGUI();
            game.startGame();
        });
    }
}