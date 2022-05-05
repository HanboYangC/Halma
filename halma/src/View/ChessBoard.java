package View;

import Controller.GamePanel;
import frame.Windows;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

//因为设计上的失误，本代码以左下角为p3,右下角为p4
//实际上玩家游玩时以左下角为p4,右下角为p3
public class ChessBoard extends JPanel implements ActionListener, MouseListener {
    private static final int dim = 16;
    private boolean is2Player;
    int who = (int) ((Math.random() * 4) + 1);
    private Square[][] squares;
    private ArrayList<ChessBoardLoc> locArrayList;//所有棋子的坐标
    private ArrayList<Square> temp1 = new ArrayList<>();
    private ArrayList<Square> temp2 = new ArrayList<>();
    private ArrayList<Square> temp3 = new ArrayList<>();
    private ArrayList<Square> temp4 = new ArrayList<>();
    private ArrayList<Chess> chessList;
    private ArrayList<Chess> p1 = new ArrayList<>();
    private ArrayList<Chess> p2 = new ArrayList<>();
    private ArrayList<Chess> p3 = new ArrayList<>();
    private ArrayList<Chess> p4 = new ArrayList<>();
    private GamePanel gamePanel;
    private boolean isWin;
    private boolean hint = false;
    private Chess lastSelected=null;
    private Chess nowSelected = null;
    private int undoTimes=1;


    public ChessBoard(boolean is2Player, GamePanel gamePanel) {

        //设置棋盘
        this.gamePanel = gamePanel;
        squares = new Square[dim][dim];
        chessList = new ArrayList<>();
        locArrayList = new ArrayList<>();
        GridLayout layout = new GridLayout();
        layout.setColumns(dim);
        layout.setRows(dim);
        this.setIs2Player(is2Player);
        this.setLayout(layout);
        this.isWin = false;

        if (this.isIs2Player()) {
            this.gamePanel.getPlayer().setText("Player:" + ((who % 2) + 1));
        } else {
            this.gamePanel.getPlayer().setText("Player:" + ((who % 4) + 1));
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        Square square = new Square(new Color(170, 170, 170), i, j, false);
                        this.add(square);
                        squares[i][j] = square;
                    } else {
                        Square square = new Square(new Color(255, 255, 204), i, j, false);
                        this.add(square);
                        squares[i][j] = square;
                    }
                } else if (i % 2 == 0) {
                    if (j % 2 == 1) {
                        Square square = new Square(new Color(255, 255, 204), i, j, false);
                        this.add(square);
                        squares[i][j] = square;
                    } else {
                        Square square = new Square(new Color(170, 170, 170), i, j, false);
                        this.add(square);
                        squares[i][j] = square;
                    }
                }
            }
        }//加进所有方格

        if (is2Player) {//如果2个人
            for (int i = 0; i < dim; i++) {
                if (i == 0 || i == 1) {
                    for (int j = 0; j < 5; j++) {
                        addChess(i, j, 1, true, true);
                    }
                }
                if (i == 2) {
                    for (int j = 0; j < 4; j++) {
                        addChess(i, j, 1, true, true);
                    }
                }
                if (i == 3) {
                    for (int j = 0; j < 3; j++) {
                        addChess(i, j, 1, true, true);
                    }
                }
                if (i == 4) {
                    for (int j = 0; j < 2; j++) {
                        addChess(i, j, 1, true, true);
                    }
                }
                if (i == 11) {
                    for (int j = 14; j <= 15; j++) {
                        addChess(i, j, 2, true, true);
                    }
                }
                if (i == 12) {
                    for (int j = 13; j <= 15; j++) {
                        addChess(i, j, 2, true, true);
                    }
                }
                if (i == 13) {
                    for (int j = 12; j <= 15; j++) {
                        addChess(i, j, 2, true, true);
                    }
                }
                if (i == 14 || i == 15) {
                    for (int j = 11; j <= 15; j++) {
                        addChess(i, j, 2, true, true);
                    }
                }
            }

            for (int k = 0; k < temp1.size(); k++) {
                int i = temp1.get(k).getLoc().getRow();
                int j = temp1.get(k).getLoc().getColumn();
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        temp1.get(k).setBackground(new Color(0x2C3F35));
                    } else {
                        temp1.get(k).setBackground(new Color(0x36511B));
                    }
                } else {
                    if (j % 2 == 1) {
                        temp1.get(k).setBackground(new Color(0x36511B));

                    } else {
                        temp1.get(k).setBackground(new Color(0x2C3F35));
                    }
                }
            }

            for (int k = 0; k < temp2.size(); k++) {
                int i = temp2.get(k).getLoc().getRow();
                int j = temp2.get(k).getLoc().getColumn();
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        temp2.get(k).setBackground(new Color(0x5C5D08));
                    } else {
                        temp2.get(k).setBackground(new Color(0x9C980A));
                    }
                } else {
                    if (j % 2 == 1) {
                        temp2.get(k).setBackground(new Color(0x9C980A));

                    } else {
                        temp2.get(k).setBackground(new Color(0x5C5D08));
                    }
                }
            }

        }

        if (is2Player == false) {
            for (int i = 0; i < dim; i++) {
                if (i == 0 || i == 1) {
                    for (int j = 0; j < 4; j++) {
                        addChess(i, j, 1, false, true);
                    }
                    for (int j = 12; j < 16; j++) {
                        addChess(i, j, 2, false, true);
                    }
                }
                if (i == 2) {
                    for (int j = 0; j < 3; j++) {
                        addChess(i, j, 1, false, true);
                    }
                    for (int j = 13; j < 16; j++) {
                        addChess(i, j, 2, false, true);
                    }
                }
                if (i == 3) {
                    for (int j = 0; j < 2; j++) {
                        addChess(i, j, 1, false, true);
                    }
                    for (int j = 14; j < 16; j++) {
                        addChess(i, j, 2, false, true);
                    }
                }


                if (i == 12) {
                    for (int j = 0; j < 2; j++) {
                        addChess(i, j, 3, false, true);
                    }
                    for (int j = 14; j < 16; j++) {
                        addChess(i, j, 4, false, true);
                    }

                }
                if (i == 13) {
                    for (int j = 0; j < 3; j++) {
                        addChess(i, j, 3, false, true);
                    }
                    for (int j = 13; j < 16; j++) {
                        addChess(i, j, 4, false, true);
                    }
                }
                if (i == 14 || i == 15) {
                    for (int j = 0; j < 4; j++) {
                        addChess(i, j, 3, false, true);
                    }
                    for (int j = 12; j < 16; j++) {
                        addChess(i, j, 4, false, true);
                    }
                }
            }
            for (int k = 0; k < temp1.size(); k++) {
                int i = temp1.get(k).getLoc().getRow();
                int j = temp1.get(k).getLoc().getColumn();
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        temp1.get(k).setBackground(new Color(0x2C3F35));
                    } else {
                        temp1.get(k).setBackground(new Color(0x36511B));
                    }
                } else {
                    if (j % 2 == 1) {
                        temp1.get(k).setBackground(new Color(0x36511B));

                    } else {
                        temp1.get(k).setBackground(new Color(0x2C3F35));
                    }
                }
            }
            for (int k = 0; k < temp2.size(); k++) {
                int i = temp2.get(k).getLoc().getRow();
                int j = temp2.get(k).getLoc().getColumn();
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        temp2.get(k).setBackground(new Color(0x193F5D));
                    } else {
                        temp2.get(k).setBackground(new Color(0x31609C));
                    }
                } else {
                    if (j % 2 == 1) {
                        temp2.get(k).setBackground(new Color(0x31609C));

                    } else {
                        temp2.get(k).setBackground(new Color(0x193F5D));
                    }
                }
            }
            for (int k = 0; k < temp3.size(); k++) {
                int i = temp3.get(k).getLoc().getRow();
                int j = temp3.get(k).getLoc().getColumn();
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        temp3.get(k).setBackground(new Color(0x5C5D08));
                    } else {
                        temp3.get(k).setBackground(new Color(0x9C980A));
                    }
                } else {
                    if (j % 2 == 1) {
                        temp3.get(k).setBackground(new Color(0x9C980A));

                    } else {
                        temp3.get(k).setBackground(new Color(0x5C5D08));
                    }
                }
            }
            for (int k = 0; k < temp4.size(); k++) {
                int i = temp4.get(k).getLoc().getRow();
                int j = temp4.get(k).getLoc().getColumn();
                if (i % 2 == 1) {
                    if (j % 2 == 1) {
                        temp4.get(k).setBackground(new Color(0x5D0A0D));
                    } else {
                        temp4.get(k).setBackground(new Color(0x9C1F08));
                    }
                } else {
                    if (j % 2 == 1) {
                        temp4.get(k).setBackground(new Color(0x9C1F08));

                    } else {
                        temp4.get(k).setBackground(new Color(0x5D0A0D));
                    }
                }
            }
        }//加进4个人的棋子

        for (int i = 0; i < this.chessList.size(); i++) {
            this.chessList.get(i).addActionListener(this);
        }//给所有棋子加进监听器

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                squares[i][j].addMouseListener(this);
            }
        }//给所有方块加监听器


    }

    public int isWin2(ChessBoard chessBoard) {
        ArrayList<Chess> p1 = chessBoard.getP1();
        ArrayList<Chess> p2 = chessBoard.getP2();

        ArrayList<Square> temp1 = chessBoard.getTemp1();
        ArrayList<Square> temp2 = chessBoard.getTemp2();


        int verifier1 = 0;
        for (int i = 0; i < p1.size(); i++) {
            if (temp2.contains((Square) p1.get(i).getParent())) {//bug
                verifier1++;
            }
        }

        int verifier2 = 0;
        for (int i = 0; i < p2.size(); i++) {
            if (temp1.contains((Square) p2.get(i).getParent())) {
                verifier2++;
            }
        }

        if (verifier1 == 19) {
            return 1;
        }
        if (verifier2 == 19) {
            return 2;
        }

        return 0;

    }

    public int isWin4(ChessBoard chessBoard) {
        ArrayList<Chess> p1 = chessBoard.getP1();
        ArrayList<Chess> p2 = chessBoard.getP2();
        ArrayList<Chess> p3 = chessBoard.getP3();
        ArrayList<Chess> p4 = chessBoard.getP4();
        ArrayList<Square> temp1 = chessBoard.getTemp1();
        ArrayList<Square> temp2 = chessBoard.getTemp2();
        ArrayList<Square> temp3 = chessBoard.getTemp3();
        ArrayList<Square> temp4 = chessBoard.getTemp4();

        int verifier1 = 0;
        for (int i = 0; i < p1.size(); i++) {
            if (temp4.contains((Square) p1.get(i).getParent())) {//bug
                verifier1++;
            }
        }

        int verifier2 = 0;
        for (int i = 0; i < p2.size(); i++) {
            if (temp3.contains((Square) p2.get(i).getParent())) {
                verifier2++;
            }
        }

        int verifier3 = 0;
        for (int i = 0; i < p3.size(); i++) {
            if (temp2.contains((Square) p3.get(i).getParent())) {
                verifier3++;
            }
        }
        int verifier4 = 0;
        for (int i = 0; i < p4.size(); i++) {
            if (temp1.contains((Square) p4.get(i).getParent())) {
                verifier4++;
            }
        }

        if (verifier1 == 13) {
            return 1;
        }
        if (verifier2 == 13) {
            return 2;
        }
        if (verifier3 == 13) {
            return 4;
        }
        if (verifier4 == 13) {
            return 3;
        }

        return 0;

    }

    public ArrayList<Chess> getP1() {
        return p1;
    }

    public void setP1(ArrayList<Chess> p1) {
        this.p1 = p1;
    }

    public ArrayList<Chess> getP2() {
        return p2;
    }

    public void setP2(ArrayList<Chess> p2) {
        this.p2 = p2;
    }

    public ArrayList<Chess> getP3() {
        return p3;
    }

    public void setP3(ArrayList<Chess> p3) {
        this.p3 = p3;
    }

    public ArrayList<Chess> getP4() {
        return p4;
    }

    public void setP4(ArrayList<Chess> p4) {
        this.p4 = p4;
    }

    public ArrayList<Chess> getChessList() {
        return chessList;
    }

    public void setChessList(ArrayList<Chess> chessList) {
        this.chessList = chessList;
    }

    public boolean isIs2Player() {
        return is2Player;
    }

    public void setIs2Player(boolean is2Player) {
        this.is2Player = is2Player;
    }

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public void setLocArrayList(ArrayList<ChessBoardLoc> locArrayList) {
        this.locArrayList = locArrayList;
    }

    public ArrayList<Square> getTemp1() {
        return temp1;
    }

    public void setTemp1(ArrayList<Square> temp1) {
        this.temp1 = temp1;
    }

    public ArrayList<Square> getTemp2() {
        return temp2;
    }

    public void setTemp2(ArrayList<Square> temp2) {
        this.temp2 = temp2;
    }

    public ArrayList<Square> getTemp3() {
        return temp3;
    }

    public void setTemp3(ArrayList<Square> temp3) {
        this.temp3 = temp3;
    }

    public ArrayList<Square> getTemp4() {
        return temp4;
    }

    public void setTemp4(ArrayList<Square> temp4) {
        this.temp4 = temp4;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public static int getDim() {
        return dim;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public boolean isHint() {
        return hint;
    }

    public void setHint(boolean hint) {
        this.hint = hint;
    }

    @Override
    public void actionPerformed(ActionEvent e) {//按棋子执行的方法
        for (int i = 0; i < 16; i++) {
            for (int k = 0; k < 16; k++) {
                if (squares[i][k].isReady()) {
                    squares[i][k].setBorder(null);
                }
            }
        }
        isLegal(null, ((Chess) e.getSource()).getChessBoardLoc(), this, new ArrayList<ChessBoardLoc>(), true);
        if (whoesTurn((Chess) e.getSource(), who)) {
            for (int i = 0; i < chessList.size(); i++) {
                if (chessList.get(i).isSelected()) {
                    chessList.get(i).setSelected(false);
                    chessList.get(i).setBackground(chessList.get(i).getParent().getBackground());
                }
            }//取消前一个棋子的选中状态

            if (e.getSource() instanceof Chess) {
                Chess chess = (Chess) e.getSource();
                chess.setBackground(new Color(0x2B0F2D));
                chess.setSelected(true);
            }//变色
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {//按方块执行的方法
        if (e.getSource() instanceof Square) {
            Square square = (Square) e.getSource();
            Color color;

            if (locArrayList.contains(square.getLoc())) {
                return;//如果方格里有棋子
            } else {//如果没有棋子
                for (int j = 0; j < chessList.size(); j++) {//先前有没有棋子被选中（没有的话，点击方格没有用）
                    if (chessList.get(j).isSelected()) {//如果有
                        if (isLegal(square, chessList.get(j).getChessBoardLoc(), this, new ArrayList<ChessBoardLoc>(), false)) {
                            if (isInTarget(chessList.get(j), square)) {
                                undoTimes=0;
                                lastSelected = chessList.get(j);
                                chessList.get(j).setVisible(false);//把棋子删除
                                locArrayList.remove(chessList.get(j).getChessBoardLoc());//把棋子坐标删除
                                int id = chessList.get(j).getId();
                                if (id == 1) {
                                    p1.remove(chessList.get(j));
                                }
                                if (id == 2) {
                                    p2.remove(chessList.get(j));

                                }
                                if (id == 3) {

                                    p3.remove(chessList.get(j));
                                }
                                if (id == 4) {

                                    p4.remove(chessList.get(j));
                                }
                                chessList.get(j).getParent().removeAll();//把棋子从容器中删除
                                chessList.remove(j);//从list里删除

                                //抹除先前棋子

                                addChess(square.getLoc().getRow(), square.getLoc().getColumn(), id, this.is2Player, false);
                                //新建棋子
                                who++;
                                if (this.isIs2Player()) {
                                    this.gamePanel.getPlayer().setText("Player:" + ((who % 2) + 1));
                                    this.gamePanel.setWho(((who % 2) + 1));
                                } else {
                                    this.gamePanel.getPlayer().setText("Player:" + ((who % 4) + 1));
                                    this.gamePanel.setWho(((who % 4) + 1));

                                }
                                for (int i = 0; i < 16; i++) {
                                    for (int k = 0; k < 16; k++) {
                                        if (squares[i][k].isReady()) {
                                            squares[i][k].setBorder(null);
                                        }
                                    }
                                }
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                }
            }

        }


        int win;
        if (this.isIs2Player()) {
            win = isWin2(this);

        } else {
            win = isWin4(this);
        }
        if (win != 0) {
            JOptionPane.showMessageDialog(null, "Player " + win + " wins!!");
            this.isWin = true;
            for (int i = 0; i < chessList.size(); i++) {
                chessList.get(i).removeActionListener(this);
            }
        }
    }

    public ArrayList<ChessBoardLoc> getLocArrayList() {
        return locArrayList;
    }

    private boolean isInTarget(Chess chess, Square square) {
        if (isIs2Player()) {
            if (chess.getId() == 1) {
                if (temp2.contains(chess.getParent()) && !temp2.contains(square)) {
                    return false;
                }
            } else if (chess.getId() == 2) {
                if (temp1.contains(chess.getParent()) && !temp1.contains(square)) {
                    return false;
                }
            }
        } else {
            if (chess.getId() == 1) {
                if (temp4.contains(chess.getParent()) && !temp4.contains(square)) {
                    return false;
                }
            } else if (chess.getId() == 2) {
                if (temp3.contains(chess.getParent()) && !temp3.contains(square)) {
                    return false;
                }
            } else if (chess.getId() == 3) {
                if (temp2.contains(chess.getParent()) && !temp2.contains(square)) {
                    return false;
                }
            } else if (chess.getId() == 4) {
                if (temp1.contains(chess.getParent()) && !temp1.contains(square)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isLegal(Square square, ChessBoardLoc chessloc, ChessBoard chessBoard, ArrayList<ChessBoardLoc> stepped, boolean isButton) {
        ArrayList<ChessBoardLoc> available = new ArrayList<>();
        ArrayList<ChessBoardLoc> firstRound = new ArrayList<>();//第一圈，带棋子的
        ArrayList<ChessBoardLoc> firstRound0 = new ArrayList<>();//第一圈所有方格，仅用作连跳的递归判断

        firstRound0.add(new ChessBoardLoc(chessloc.getRow() + 1, chessloc.getColumn()));
        firstRound0.add(new ChessBoardLoc(chessloc.getRow() + 1, chessloc.getColumn() + 1));
        firstRound0.add(new ChessBoardLoc(chessloc.getRow() + 1, chessloc.getColumn() - 1));
        firstRound0.add(new ChessBoardLoc(chessloc.getRow() - 1, chessloc.getColumn()));
        firstRound0.add(new ChessBoardLoc(chessloc.getRow() - 1, chessloc.getColumn() + 1));
        firstRound0.add(new ChessBoardLoc(chessloc.getRow() - 1, chessloc.getColumn() - 1));
        firstRound0.add(new ChessBoardLoc(chessloc.getRow(), chessloc.getColumn() - 1));
        firstRound0.add(new ChessBoardLoc(chessloc.getRow(), chessloc.getColumn() + 1));


        if (locArrayList.contains(chessloc)) {//若是第一次跳
            available = (ArrayList<ChessBoardLoc>) firstRound0.clone();
            for (int j = 0; j < available.size(); j++) {//先去掉所有的available里带棋子的和不存在的
                for (int k = 0; k < chessBoard.getChessList().size(); k++) {
                    if (available.get(j).equals(chessBoard.getChessList().get(k).getChessBoardLoc())) {
                        firstRound.add(available.get(j));
                        available.remove(j);
                        j--;
                        break;
                    }
                }
            }

            for (int i = 0; i < available.size(); i++) {
                if (available.get(i).getRow() == -1 || available.get(i).getRow() == 16
                        || available.get(i).getColumn() == -1 || available.get(i).getColumn() == 16) {
                    available.remove(i);
                    i--;
                }
            }

            for (int j = 0; j < firstRound.size(); j++) {//对所有第一圈带棋子的格子来说，判断能不能被跳；
                ChessBoardLoc target = chessloc.relative(firstRound.get(j)).add(firstRound.get(j));//跳到的那个位置
                if (chessBoard.getLocArrayList().contains(target) == false
                        && target.isLegal()) {
                    available.add(target);
                    continue;
                }
            }

            for (int i = 0; i < stepped.size(); i++) {//如之前检验过，则不必再检验
                if (available.contains(stepped.get(i))) {
                    available.remove(stepped.get(i));
                }
            }

        } else {

            for (int j = 0; j < firstRound0.size(); j++) {//先把所有的firstRound0里带棋子的加到firstRound里
                if (chessBoard.locArrayList.contains(firstRound0.get(j))) {
                    firstRound.add(firstRound0.get(j));
                    continue;
                }
            }

            for (int j = 0; j < firstRound.size(); j++) {//对所有第一圈带棋子的格子来说，判断能不能被跳；
                ChessBoardLoc target = chessloc.relative(firstRound.get(j)).add(firstRound.get(j));//跳到的那个位置
                if (chessBoard.locArrayList.contains(target) == false
                        && target.isLegal()) {
                    available.add(target);
                    continue;
                }
            }

            for (int i = 0; i < stepped.size(); i++) {//如之前检验过，则不必再检验
                if (available.contains(stepped.get(i))) {
                    available.remove(stepped.get(i));
                }
            }

        }

        for (int i = 0; i < available.size(); i++) {
            stepped.add(available.get(i));
        }
        if (isHint()) {
            if (isButton) {

                for (int i = 0; i < available.size(); i++) {
                    for (int j = 0; j < 16; j++) {
                        for (int k = 0; k < 16; k++) {
                            if (squares[j][k].getLoc().equals(available.get(i))) {
                                squares[j][k].setBorder(BorderFactory.createLineBorder(new Color(0x1AFFCC),5));
                                squares[j][k].setReady(true);
                            }
                        }
                    }
                }
                for (int i = 0; i < available.size(); i++) {
                    if (firstRound0.contains(available.get(i)) == false) {
                        if (isLegal(square, available.get(i), chessBoard, stepped, isButton) == false) {
                            continue;
                        } else {
                            return true;
                        }
                    }
                }
            }
        }
        removeDuplicate(stepped);

        if (!isButton) {
            if (available.contains(square.getLoc())) {
                return true;
            } else {

                for (int i = 0; i < available.size(); i++) {
                    if (firstRound0.contains(available.get(i)) == false) {
                        if (isLegal(square, available.get(i), chessBoard, stepped, isButton) == false) {
                            continue;
                        } else {
                            return true;
                        }
                    }
                }
            }
            return false;

        }
        return false;
    }

    private boolean whoesTurn(Chess chess, int who) {
        if (this.isIs2Player()) {
            int whoNext = who % 2;
            switch (whoNext) {
                case 0:
                    if (p1.contains(chess)) {
                        return true;
                    }
                    break;
                case 1:
                    if (p2.contains(chess)) {
                        return true;

                    }
                    break;
            }
        } else {
            int whoNext = who % 4;

            switch (whoNext) {
                case 0:
                    if (p1.contains(chess)) {
                        return true;
                    }
                    break;
                case 1:
                    if (p2.contains(chess)) {
                        return true;
                    }
                    break;
                case 2:
                    if (p4.contains(chess)) {
                        return true;
                    }
                    break;
                case 3:
                    if (p3.contains(chess)) {
                        return true;
                    }
                    break;
            }

        }


        return false;
    }

    public void addChess(int i, int j, int id, boolean is2Player, boolean isOrientation) {
        if (is2Player) {
            if (id == 1) {
                Chess chess = new Chess(i, j, 1, new ImageIcon("src\\View\\Tie.png"));
                if (!isOrientation) {
                    this.nowSelected = chess;
                }
                chess.addActionListener(this);
                chessList.add(chess);
                p1.add(chess);
                squares[i][j].add(chess);
                locArrayList.add(chess.getChessBoardLoc());
                if (isOrientation) {
                    temp1.add((Square) chess.getParent());
                }
            } else if (id == 2) {
                Chess chess = new Chess(i, j, 2, new ImageIcon("src\\View\\Ming.png"));
                if (!isOrientation) {
                    this.nowSelected = chess;
                }
                chess.addActionListener(this);
                chessList.add(chess);
                p2.add(chess);
                squares[i][j].add(chess);
                locArrayList.add(chess.getChessBoardLoc());
                if (isOrientation) {
                    temp2.add((Square) chess.getParent());
                }
            }
        } else {
            if (id == 1) {
                Chess chess = new Chess(i, j, 1, new ImageIcon("src\\View\\Tie.png"));
                if (!isOrientation) {
                    this.nowSelected = chess;
                }
                chess.addActionListener(this);
                chessList.add(chess);
                p1.add(chess);
                squares[i][j].add(chess);
                locArrayList.add(chess.getChessBoardLoc());
                if (isOrientation) {
                    temp1.add((Square) chess.getParent());
                }
            } else if (id == 2) {
                Chess chess = new Chess(i, j, 2, new ImageIcon("src\\View\\Meng.png"));
                if (!isOrientation) {
                    this.nowSelected = chess;
                }
                chess.addActionListener(this);
                chessList.add(chess);
                p2.add(chess);
                squares[i][j].add(chess);
                locArrayList.add(chess.getChessBoardLoc());

                if (isOrientation) {
                    temp2.add((Square) chess.getParent());
                }
            } else if (id == 3) {
                Chess chess = new Chess(i, j, 3, new ImageIcon("src\\View\\Ming.png"));
                if (!isOrientation) {
                    this.nowSelected = chess;
                }
                chess.addActionListener(this);
                chessList.add(chess);
                p3.add(chess);
                squares[i][j].add(chess);
                locArrayList.add(chess.getChessBoardLoc());

                if (isOrientation) {
                    temp3.add((Square) chess.getParent());
                }
            } else if (id == 4) {
                Chess chess = new Chess(i, j, 4, new ImageIcon("src\\View\\Chao.png"));
                if (!isOrientation) {
                    this.nowSelected = chess;
                }
                chess.addActionListener(this);
                chessList.add(chess);
                p4.add(chess);
                squares[i][j].add(chess);
                locArrayList.add(chess.getChessBoardLoc());

                if (isOrientation) {
                    temp4.add((Square) chess.getParent());
                }
            }
        }
    }

    public Chess getLastSelected() {
        return lastSelected;
    }

    public void setLastSelected(Chess lastSelected) {
        this.lastSelected = lastSelected;
    }

    public Chess getNowSelected() {
        return nowSelected;
    }

    public void setNowSelected(Chess nowSelected) {
        this.nowSelected = nowSelected;
    }

    public int getUndoTimes() {
        return undoTimes;
    }

    public void setUndoTimes(int undoTimes) {
        this.undoTimes = undoTimes;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void removeDuplicate(ArrayList arlList) {
        HashSet h = new HashSet(arlList);
        arlList.clear();
        arlList.addAll(h);
    }
}
