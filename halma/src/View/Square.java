package View;

import javax.swing.*;
import java.awt.*;

public class Square extends JPanel {
    private Color color;
    private static final int size=1;
    private ChessBoardLoc loc;
    private boolean isReady;

    public Square (Color color,int x,int y,boolean isReady) {
        this.isReady=isReady;
        this.loc=new ChessBoardLoc(x,y);
        this.color = color;
        this.setSize(size,size);
        this.setBackground(color);
        this.setLayout(new GridLayout());
    }

    public Color getColor() {
        return color;
    }

    public ChessBoardLoc getLoc() {
        return loc;
    }

    public void setLoc(ChessBoardLoc loc) {
        this.loc = loc;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}
