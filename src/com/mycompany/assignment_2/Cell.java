package assignment2;

import java.awt.Color;

/**
 *
 * @author Mohammed Ayoub
 */

import javax.swing.*;

class Cell extends JButton {
    private boolean hasShip;
    private boolean isFired;

    public Cell(Board playerBoard, boolean hasShip) {
        this.hasShip = hasShip;
        this.isFired = false;  // Initially, no cell is fired upon
        this.setBackground(Color.BLUE);  // Set the initial background to blue

    }

    public boolean isIdle() {
        return !isFired;
    }

    public boolean hasShip() {
        return this.hasShip;
    }

    public void setHasShip(boolean hasShip) {
        this.hasShip = hasShip;
    }

    public void fire() {
        if(!isFired)
        {
            isFired = true;
            if(hasShip)
                this.setBackground(Color.RED);
            else
                this.setBackground(Color.WHITE);
        }
    }

    public void reset() {
        hasShip = false;
        isFired = false;
        this.setBackground(Color.BLUE);
    }

    @Override
    public String toString() {
        if (hasShip)
            return "1";
        return "0";
    }
}
