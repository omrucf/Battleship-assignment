package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Battleship extends JFrame implements ActionListener {

    private Board playerBoard;
    private JButton startNewGameButton;
    private JButton revealButton;
    private JButton showStatisticsButton;
    private JComboBox<String> diffBox;

    public Battleship() {
        setTitle("Battleship");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        playerBoard = new Board();

        playerBoard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add this line

        JPanel controlPanel = new JPanel();
        startNewGameButton = new JButton("Start New Game");
        revealButton = new JButton("Reveal");
        showStatisticsButton = new JButton("Show Game Statistics");

        String[] levels = { "Easy", "Medium", "Hard" };
        diffBox = new JComboBox<>(levels);

        startNewGameButton.addActionListener(this);
        revealButton.addActionListener(this);
        showStatisticsButton.addActionListener(this);
        diffBox.addActionListener(this);

        controlPanel.add(startNewGameButton);
        controlPanel.add(revealButton);
        controlPanel.add(showStatisticsButton);
        controlPanel.add(diffBox);

        add(controlPanel, BorderLayout.NORTH);
        add(playerBoard, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startNewGameButton) {
            JOptionPane.showMessageDialog(this, "Starting a new game!");
            playerBoard.resetBoard();
        } else if (e.getSource() == revealButton) {
            JOptionPane.showMessageDialog(this, "Revealing ship locations...");
            playerBoard.revealShips();// Logic to reveal the ships on the board
        } else if (e.getSource() == showStatisticsButton) {
            JOptionPane.showMessageDialog(this, "Displaying game statistics...");
            playerBoard.showStatistics(); // Display game statistics
        } else if (e.getSource() == diffBox) { // Handle JComboBox selection
            String selectedOption = (String) diffBox.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Changing mode to: " + selectedOption + "...");
            playerBoard.setMode(selectedOption);
            playerBoard.resetBoard();
            if (selectedOption == "Easy")
                playerBoard.setTrials(40);
            else if (selectedOption == "Medium")
                playerBoard.setTrials(30);
            else if (selectedOption == "Hard")
                playerBoard.setTrials(20);
        }
    }

    public static void main(String[] args) {

        System.out.println("************************************************************");
        System.out.println("*                                                          *");
        System.out.println("*          Names: Mohamed Ayoub and Omar Yossuf            *");
        System.out.println("*          SIDs:  900212213 and 900212166                  *");
        System.out.println("*          Sec: 01                                         *");     
        System.out.println("*                                                          *");
        System.out.println("************************************************************");
        System.out.println();
        new Battleship();
    }
}