package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

public class Chess extends JButton {

    private boolean selected;
    private ChessBoardLoc chessBoardLoc;
    //private Square parent;


    private int id;

    public Chess( int x, int y,int id, ImageIcon icon) {
        this.id=id;
        //this.parent= (Square) this.getParent();
        this.chessBoardLoc=new ChessBoardLoc(x,y);
        //this.color = color;
        //this.setBackground(squareColor);
        this.setIcon(icon);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintChess(g);
    }

    private void paintChess(Graphics g) {
        if (selected) { // Draw a + sign in the center of the piece
            g.setColor(new Color(0x009B09));

            g.drawArc(getWidth()/4,getHeight()/4, getWidth()/2, getHeight()/2, 0, 360);
            g.drawArc(getWidth()/8,getHeight()/8, 3*getWidth()/4, 3*getHeight()/4, 0, 360);
            g.drawArc(3*getWidth()/8,3*getHeight()/8, getWidth()/4, getHeight()/4, 0, 360);
            g.drawArc(0,0, getWidth(), getHeight(), 0, 360);
            g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
            g.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        }
    }

    public ChessBoardLoc getChessBoardLoc() {
        return chessBoardLoc;
    }

    public void setChessBoardLoc(ChessBoardLoc chessBoardLoc) {
        this.chessBoardLoc = chessBoardLoc;
    }

    public int getId() {
        return id;
    }


}
