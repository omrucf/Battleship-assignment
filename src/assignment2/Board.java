package assignment2;

import javax.swing.*;
import java.awt.*;

class Board extends JPanel {
    private Cell[][] cells;
    private int[] shipLengths = { 5, 4, 3, 2, 1 };
    private int completeShips = 0;
    private int total_ships;
    private String mode = "Easy";
    private int trials = 40;
    private int IDC = 0;
    private int[][] ships_id;
    private String[][] ships_type;
    private JLabel remainingAttemptsLabel;

    public Board() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel(new GridLayout(10, 10, 10, 10));
        cells = new Cell[10][10];
        ships_id = new int[10][10];
        ships_type = new String[10][10];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                cells[row][col] = new Cell(this, false);
                ships_id[row][col] = -1;
                ships_type[row][col] = "None";

                int currentRow = row;
                int currentCol = col;
                cells[row][col].addActionListener(e -> fireAtCell(currentRow, currentCol));

                gridPanel.add(cells[row][col]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        remainingAttemptsLabel = new JLabel("Remaining Attempts: " + trials);
        remainingAttemptsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(remainingAttemptsLabel, BorderLayout.SOUTH);  // Place label at the bottom

        placeAllShips();
    }

    public void placeAllShips() {
        for (int i = 0; i < shipLengths.length; i++) {
            if (mode.equals("Easy"))
                total_ships = 3;
            else if (mode.equals("Medium"))
                total_ships = 2;
            else if (mode.equals("Hard"))
                total_ships = 1;

            while (total_ships > 0)
                placeShip(shipLengths[i]);
        }
    }

    private void placeShip(int ship_size) {
        boolean vertical = Math.random() < 0.5;
        int start_row = (int) (Math.random() * 10);
        int start_col = (int) (Math.random() * 10);

        boolean canPlace = true;
        if (vertical && start_row + ship_size - 1 < 10) {
            for (int i = 0; i < ship_size; i++) {
                if (cells[start_row + i][start_col].hasShip()) {
                    canPlace = false;
                }
            }
            if (canPlace) {
                for (int i = 0; i < ship_size; i++) {
                    cells[start_row + i][start_col].setHasShip(true);
                    ships_id[start_row + i][start_col] = IDC;
                    ships_type[start_row + i][start_col] = getShipType(ship_size);
                }
                IDC++;
                total_ships--;
            }
        } else if (!vertical && start_col + ship_size - 1 < 10) {
            for (int i = 0; i < ship_size; i++) {
                if (cells[start_row][start_col + i].hasShip()) {
                    canPlace = false;
                }
            }
            if (canPlace) {
                for (int i = 0; i < ship_size; i++) {
                    cells[start_row][start_col + i].setHasShip(true);
                    ships_id[start_row][start_col + i] = IDC;
                    ships_type[start_row][start_col + i] = getShipType(ship_size);
                }
                IDC++;
                total_ships--;
            }
        }
    }

    private String getShipType(int ship_size) {
        switch (ship_size) {
            case 5:
                return "Aircraft carrier";
            case 4:
                return "Battleship";
            case 3:
                return "Destroyer";
            case 2:
                return "Submarine";
            case 1:
                return "Patrol boat";
            default:
                return "None";
        }
    }

    public void revealShips(boolean failed) {
        StringBuilder boardRepresentation = new StringBuilder();

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                // cells[row][col].fire();

                boardRepresentation.append(cells[row][col].toString());

                if (cells[row][col].hasShip()) {
                    if (ships_type[row][col].equals("Aircraft carrier")) {
                        cells[row][col].setBackground(Color.GREEN);
                    } else if (ships_type[row][col].equals("Battleship")) {
                        cells[row][col].setBackground(Color.ORANGE);
                    } else if (ships_type[row][col].equals("Destroyer")) {
                        cells[row][col].setBackground(Color.YELLOW);
                    } else if (ships_type[row][col].equals("Submarine")) {
                        cells[row][col].setBackground(Color.GRAY);
                    } else if (ships_type[row][col].equals("Patrol boat")) {
                        cells[row][col].setBackground(Color.CYAN);
                    }
                    cells[row][col].repaint();
                } else {
                    cells[row][col].setBackground(Color.WHITE);
                    cells[row][col].repaint();
                }
            }
            boardRepresentation.append("\n");
        }

        if (failed) {
            int hitCount = 0, missCount = 0, attempts = 0;

            for (int ro = 0; ro < 10; ro++) {
                for (int co = 0; co < 10; co++) {
                    if (!cells[ro][co].isIdle()) {
                        attempts++;
                        if (cells[ro][co].hasShip())
                            hitCount++;
                        else
                            missCount++;
                    }
                }
            }

            var choice = JOptionPane.showConfirmDialog(this,
                    "Game Over!\n*****************************\nTotal Ships: " + IDC + "\n" +
                            "Hits: " + hitCount + "\n" +
                            "Misses: " + missCount + "\n" +
                            "Attempts: " + attempts + "\n" +
                            "Complete Ships: " + completeShips + "\n*****************************\nTry Again?",
                    "Game Over!",
                    JOptionPane.YES_NO_OPTION);

            if (choice == 0)
                this.resetBoard();
            else
                JOptionPane.showMessageDialog(this, boardRepresentation.toString(),
                        "Revealed Ship Locations",
                        JOptionPane.INFORMATION_MESSAGE);
        } else
            JOptionPane.showMessageDialog(this, boardRepresentation.toString(),
                    "Revealed Ship Locations",
                    JOptionPane.INFORMATION_MESSAGE);
    }

    private void fireAtCell(int row, int col) {
        if (cells[row][col].isIdle()) {
            cells[row][col].fire();
            trials--;

            // Update remaining attempts label
            remainingAttemptsLabel.setText("Remaining Attempts: " + trials);

            if (trials == 0) {
                revealShips(true);
            }


        }
    }

    public void resetBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                cells[row][col].reset();
                ships_id[row][col] = -1;
                ships_type[row][col] = "None";
            }
        }
        IDC = 0;
        completeShips = 0;

        setTrialsBasedOnMode();
        remainingAttemptsLabel.setText("Remaining Attempts: " + trials);

        if (mode == "Easy")
            setTrials(40);
        else if (mode == "Medium")
            setTrials(30);
        else if (mode == "Hard")
            setTrials(20);

        placeAllShips();
        revalidate();
        repaint();
    }

    public void setMode(String m) {
        this.mode = m;
        setTrialsBasedOnMode();
        remainingAttemptsLabel.setText("Remaining Attempts: " + trials);
    }

    private void setTrialsBasedOnMode() {
        switch (mode) {
            case "Easy":
                trials = 40;
                break;
            case "Medium":
                trials = 30;
                break;
            case "Hard":
                trials = 20;
                break;
        }
    }

    public void setTrials(int t) {
        this.trials = t;
        remainingAttemptsLabel.setText("Remaining Attempts: " + trials);
    }

    public void showStatistics() {
        int hitCount = 0, missCount = 0, attempts = 0;

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (!cells[row][col].isIdle()) {
                    attempts++;
                    if (cells[row][col].hasShip())
                        hitCount++;
                    else
                        missCount++;
                }
            }
        }

        JOptionPane.showMessageDialog(this,
                "Total Ships: " + IDC + "\n" +
                        "Hits: " + hitCount + "\n" +
                        "Misses: " + missCount + "\n" +
                        "Attempts: " + attempts + "\n" +
                        "Complete Ships: " + completeShips);
    }
}
