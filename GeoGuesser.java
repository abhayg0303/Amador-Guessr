import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;

public class GeoGuessrGUI{
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameGUI().startGame());
     }
    
}