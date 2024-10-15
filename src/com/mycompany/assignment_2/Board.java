/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_2;

/**
 *
 * @author Mohammed Ayoub
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Board extends JPanel {
    private JButton[][] cells;
    private boolean[][] hasShip;
    private boolean[][] isFired;
    private int[] shipLengths = {5, 4, 3, 2, 1}; // Lengths of each ship type
    private int completeShips = 0; // Track complete ships found
    private int total_ships;
    private String mode = "Easy";
    private int trials = 40;
    private int IDC = 0;
    private int [][] ships_id;
    private String [][] ships_type;
    


    public Board() {
    	 // Set the look and feel to ensure consistent rendering
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // 10x10 grid layout
        setLayout(new GridLayout(10, 10, 10, 10));
        cells = new JButton[10][10];
        hasShip = new boolean[10][10];
        isFired = new boolean[10][10];
        
        ships_id = new int [10][10];
        ships_type = new String [10][10];
        
        
        // Initialize each cell in the grid and set up properties
        
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                cells[row][col] = new JButton();
                cells[row][col].setBackground(Color.BLUE);
                cells[row][col].setPreferredSize(new Dimension(50, 50));
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 0, true));

//                hasShip[row][col] = Math.random() < 0.2;
//                placeAllShips();
                ships_id[row][col] = -1;
                ships_type[row][col] = "None";
                isFired[row][col] = false;
                hasShip[row][col] = false;

                // Set up ActionListener for each cell
                int currentRow = row;
                int currentCol = col;
                cells[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fireAtCell(currentRow, currentCol);
                    }
                });

                add(cells[row][col]);
            }
        }
        placeAllShips();
    }

    public void placeAllShips() 
    {
    	
    	for(int i = 0; i < shipLengths.length; i++)
    	{
    		if(mode == "Easy")
    			total_ships = 3;
    		else if(mode == "Medium")
    			total_ships = 2;
    		else if (mode == "Hard")
    			total_ships = 1;
    		while(total_ships > 0)
    			placeShip(shipLengths[i]);
    	}
    	
    	printBoard();
    	
    	for(int i = 0; i < 10; i++)
    	{
    		for(int j = 0; j < 10; j++)
				System.out.printf("%-3s",ships_id[i][j]);
    		System.out.print("\n");
    	}
    	return;
    }
    
    private void placeShip(int ship_size)
    {
    	boolean vertical = Math.random() < 0.5 ? false : true;
    	
    	int start_row = (int) (Math.random() * 10);
    	int start_col = (int) (Math.random() * 10);    	
    	System.out.printf("strw: %d, stcl: %d, shsz: %d\n", start_row, start_col, ship_size );
    	
    	boolean canPlace = true;
    	
    	if(start_row + ship_size - 1 < 9 && vertical)
    	{
    		
    		for (int i = 0; i < ship_size; i++)
    		{
    			if(hasShip[start_row + i][start_col])
    			{
    				canPlace = false;
    			}
    			if(start_col + 1 != 10 && hasShip[start_row + i][start_col + 1])
    			{
    				canPlace = false;
    			}
    			if(start_col - 1 != -1 && hasShip[start_row + i][start_col - 1])
    			{
    				canPlace = false;
    			}
    		}
    		
    		if(canPlace)
    		{
    			for (int i = 0; i < ship_size; i++)
    			{
    				hasShip[start_row + i][start_col] = true;
    				ships_id[start_row + i][start_col] = IDC;
    				if(ship_size == 5)
    	    			ships_type[start_row + i][start_col] = "Aircraft carrier";
    				else if(ship_size == 4)
    	    			ships_type[start_row + i][start_col] = "Battleship";
    				else if(ship_size == 3)
    	    			ships_type[start_row + i][start_col] = "Destroyer";
    				else if(ship_size == 2)
    	    			ships_type[start_row + i][start_col] = "Submarine";
    				else if(ship_size == 1)
    	    			ships_type[start_row + i][start_col] = "Patrol boat";
    			}
    			IDC++;
    			total_ships--;
    		}
    		
    	}
    	else if (start_col + ship_size - 1 < 9 && !vertical)
    	{
    		for (int i = 0; i < ship_size; i++)
    		{
    			if(hasShip[start_row][start_col + i])
    			{
    				canPlace = false;
    			}
    			if(start_row + 1 != 10 && hasShip[start_row + 1][start_col + i])
    			{
    				canPlace = false;
    			}
    			if(start_row - 1 != -1 && hasShip[start_row - 1][start_col + i])
    			{
    				canPlace = false;
    			}
    		}
    		
    		if(canPlace)
    		{
    			for (int i = 0; i < ship_size; i++)
    			{
    				hasShip[start_row][start_col + i] = true;
    				ships_id[start_row][start_col + i] = IDC;
    				if(ship_size == 5)
    	    			ships_type[start_row][start_col + i] = "Aircraft carrier";
    				else if(ship_size == 4)
    	    			ships_type[start_row][start_col + i] = "Battleship";
    				else if(ship_size == 3)
    	    			ships_type[start_row][start_col + i] = "Destroyer";
    				else if(ship_size == 2)
    	    			ships_type[start_row][start_col + i] = "Submarine";
    				else if(ship_size == 1)
    	    			ships_type[start_row][start_col + i] = "Patrol boat";
    				
    			}
    			IDC++;
    			total_ships--;
    		}
    	}
    		
    	
    }

    private void fireAtCell(int row, int col) {
        if (!isFired[row][col]) {
            isFired[row][col] = true;
            trials--; 
            
            if (hasShip[row][col]) {
            	boolean last = true;
                int id = ships_id[row][col];
                ships_id[row][col] = -1;
                if(row + 1 != 10 && ships_id[row + 1][col] == id)
                	last = false;
                if(row - 1 != -1 && ships_id[row - 1][col] == id)
                	last = false;
                if(col + 1 != 10 && ships_id[row][col + 1] == id)
                	last = false;
                if(col - 1 != -1 && ships_id[row][col - 1] == id)
                	last = false;
                
                if(last)
                	this.completeShips++;
                cells[row][col].setBackground(Color.RED);
            } else {
                cells[row][col].setBackground(Color.WHITE);
            }
            if(trials == 0)
            {
            	showStatistics();
            	revealShips();
            	
            }
        }
    }


    public void resetBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                cells[row][col].setBackground(Color.BLUE);
                hasShip[row][col] = false;
                isFired[row][col] = false;
                ships_id[row][col] = -1;
                ships_type[row][col] = "None";
                IDC = 0;
                completeShips = 0;
                cells[row][col].repaint(); // Ensure the cell is repainted
            }
        }
        placeAllShips();
    }

    public void revealShips() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
            	isFired[row][col] = true;
                if (hasShip[row][col]) {
//                    cells[row][col].setBackground(Color.GRAY);
                	if(ships_type[row][col] == "Aircraft carrier")
                		cells[row][col].setBackground(Color.GREEN);
                	if(ships_type[row][col] == "Battleship")
                		cells[row][col].setBackground(Color.ORANGE);
                	if(ships_type[row][col] == "Destroyer")
                		cells[row][col].setBackground(Color.YELLOW);
                	if(ships_type[row][col] == "Submarine")
                		cells[row][col].setBackground(Color.LIGHT_GRAY);
                	if(ships_type[row][col] == "Patrol boat")
                		cells[row][col].setBackground(Color.CYAN);
                    cells[row][col].repaint(); // Ensure the cell is repainted
                }
            }
        }
    }
    
    public void printBoard()
    {
    	for(int i = 0; i < 10; i++)
    	{
    		for(int j = 0; j < 10; j++)
    			if(hasShip[i][j])
    				System.out.print(1);
    			else
    				System.out.print(0);
    		System.out.print("\n");
    	}
    }
    
    public void setMode(String m)
    {
    	this.mode = m;
    }
    
    public void setTrials(int t)
    {
    	this.trials = t;
    }

    public void showStatistics() {
        int totalShips = shipLengths.length; // Total ships based on lengths array
        int hitCount = 0, missCount = 0, attempts = 0;
        
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                if (hasShip[row][col]) totalShips++;
                if (isFired[row][col]) {
                    attempts++;
                    if (hasShip[row][col]) hitCount++;
                    else missCount++;
                }
            }
        }

        JOptionPane.showMessageDialog(this, 
            "Total Ships: " + IDC + "\n" +
            "Hits: " + hitCount + "\n" +
            "Misses: " + missCount + "\n" +
            "Attempts: " + attempts + "\n" +
            "Complete Ships: " + completeShips); // Include complete ships in stats
    }

}
