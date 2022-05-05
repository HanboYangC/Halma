package frame;

import Controller.GamePanel;
import View.ChessBoard;

import javax.swing.*;
import java.awt.*;

public class Windows extends JFrame {
    private int width;
    private int height;
    private String title;
    GamePanel gamePanel;
    ChessBoard chessBoard;
    private boolean is2;


    public Windows(String title, int width, int height, boolean is2) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.width = width;
        this.height = height;
        this.is2=is2;
        this.setSize(width,height);
        this.setIconImage(new ImageIcon("src\\frame\\f0fcd4e90d41ac412bf918b14d31077b.png").getImage());
        this.title=title;
        SpringLayout lay=new SpringLayout();
        JPanel content=new JPanel(lay);


        gamePanel=new GamePanel(is2);
        content.add(gamePanel);
        lay.putConstraint(SpringLayout.EAST,gamePanel,0,SpringLayout.EAST,content);
        lay.putConstraint(SpringLayout.SOUTH,gamePanel,0,SpringLayout.SOUTH,content);
        lay.putConstraint(SpringLayout.NORTH,gamePanel,0,SpringLayout.NORTH,content);
        //lay.putConstraint(SpringLayout.WEST,gamePanel,60,SpringLayout.EAST,content);

        chessBoard=new ChessBoard(is2,gamePanel);
        content.add(chessBoard);
        lay.putConstraint(SpringLayout.WEST,chessBoard,0,SpringLayout.WEST,content);
        lay.putConstraint(SpringLayout.SOUTH,chessBoard,0,SpringLayout.SOUTH,content);
        lay.putConstraint(SpringLayout.NORTH,chessBoard,0,SpringLayout.NORTH,content);
        lay.putConstraint(SpringLayout.EAST,chessBoard,-200,SpringLayout.EAST,content);

        lay.putConstraint(SpringLayout.WEST,gamePanel,0,SpringLayout.EAST,chessBoard);
        this.add(content);

    }
    public Windows(String title, int width, int height, boolean is2,ChessBoard chessBoard) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.width = width;
        this.height = height;
        this.is2=is2;
        this.setSize(width,height);
        this.setIconImage(new ImageIcon("src\\frame\\f0fcd4e90d41ac412bf918b14d31077b.png").getImage());
        this.title=title;
        SpringLayout lay=new SpringLayout();
        JPanel content=new JPanel(lay);


        gamePanel=new GamePanel(is2);
        gamePanel.setHint(chessBoard.isHint());
        content.add(gamePanel);
        lay.putConstraint(SpringLayout.EAST,gamePanel,0,SpringLayout.EAST,content);
        lay.putConstraint(SpringLayout.SOUTH,gamePanel,0,SpringLayout.SOUTH,content);
        lay.putConstraint(SpringLayout.NORTH,gamePanel,0,SpringLayout.NORTH,content);
        //lay.putConstraint(SpringLayout.WEST,gamePanel,60,SpringLayout.EAST,content);

        this.chessBoard=chessBoard;
        content.add(chessBoard);
        lay.putConstraint(SpringLayout.WEST,chessBoard,0,SpringLayout.WEST,content);
        lay.putConstraint(SpringLayout.SOUTH,chessBoard,0,SpringLayout.SOUTH,content);
        lay.putConstraint(SpringLayout.NORTH,chessBoard,0,SpringLayout.NORTH,content);
        lay.putConstraint(SpringLayout.EAST,chessBoard,-200,SpringLayout.EAST,content);

        lay.putConstraint(SpringLayout.WEST,gamePanel,0,SpringLayout.EAST,chessBoard);
        this.add(content);

    }

    @Override
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public boolean isIs2() {
        return is2;
    }

    public void setIs2(boolean is2) {
        this.is2 = is2;
    }
}
