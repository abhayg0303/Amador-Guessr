import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.File;

//invokelater calls methods in swing classes
public class GeoGuesser{
     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameGUI().startGame());
     }
    
}