package Controller;

import View.Chess;
import View.ChessBoard;
import View.ChessBoardLoc;
import View.Square;
import frame.Windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class GamePanel extends JPanel {
    private int who;
    private JLabel player;
    private JButton save;
    private JButton load;
    private JButton close;
    private JButton reLoad;
    private JButton back;
    private JButton wannaHint;
    private JButton undo;
    private boolean is2;
    private boolean hint;


    public GamePanel(boolean is2) {
        this.setBackground(new Color(0x9B7B73));
        this.setBorder(BorderFactory.createEtchedBorder());
        this.setVisible(true);
        SpringLayout lay = new SpringLayout();
        this.setLayout(lay);
        this.is2 = is2;

        player = new JLabel("Player: null");
        player.setFont(new Font("宋体", Font.ITALIC, 30));
        //player.setFont(zi);
        player.setBorder(BorderFactory.createEtchedBorder());
        this.add(player);
        lay.putConstraint(SpringLayout.NORTH, player, 5, SpringLayout.NORTH, this);
        lay.putConstraint(SpringLayout.WEST, player, 5, SpringLayout.WEST, this);
        lay.putConstraint(SpringLayout.EAST, player, 5, SpringLayout.EAST, this);

        save = new JButton("保存游戏");
        save.setBackground(new Color(0xFFCABC));

        this.add(save);
        lay.putConstraint(SpringLayout.NORTH, save, 50, SpringLayout.SOUTH, player);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("Save");
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    saveFile(e, file);
                }
            }

            private void saveFile(ActionEvent e, File file0) {
                ArrayList<Chess> chesses =
                        ((ChessBoard) (((JButton) e.getSource()).//按钮
                                getParent().getParent().//contentPanel
                                getComponent(1))).getChessList();
                ChessBoard chessBoard=(ChessBoard) (((JButton) e.getSource()).//按钮
                        getParent().getParent().//contentPanel
                        getComponent(1));
                try {
                    File file = new File(file0.getPath() + ".txt");
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    boolean newFile = file.createNewFile();
                    GamePanel gamePanel = ((GamePanel) (((JButton) e.getSource()).getParent()));
                    if (gamePanel.is2) {
                        fileOutputStream.write(35);//#2
                        fileOutputStream.write(((Integer) (chessBoard.getWho()+1)).toString().getBytes());
                        fileOutputStream.write("\r\n".getBytes());
                    } else {
                        fileOutputStream.write(36);//$4
                        fileOutputStream.write(((Integer) (chessBoard.getWho()+1)).toString().getBytes());
                        fileOutputStream.write("\r\n".getBytes());
                    }
                    for (int i = 0; i < 16; i++) {
                        byte[] b = new byte[16];
                        for (int j = 0; j < b.length; j++) {
                            b[j] = '0';
                        }
                        for (int j = 0; j < chesses.size(); j++) {
                            Chess chess = chesses.get(j);
                            if (chess.getChessBoardLoc().getRow() == i) {
                                b[chess.getChessBoardLoc().getColumn()] = (byte) (chess.getId() + 48);
                            }
                        }
                        fileOutputStream.write(b);
                        fileOutputStream.write("\r\n".getBytes());
                    }
                    fileOutputStream.close();
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Wrong");
                    return;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

        load = new JButton("加载游戏");
        load.setBackground(new Color(0xFFCABC));
        this.add(load);
        lay.putConstraint(SpringLayout.NORTH, load, 50, SpringLayout.SOUTH, save);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int re = JOptionPane.showConfirmDialog(null, "您确定要加载其它游戏存档吗？");
                if (re != JOptionPane.OK_OPTION) {
                    return;
                }
                JFileChooser fileChooser = new JFileChooser("Save");
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        Windows old = (Windows) (((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent().getParent());
                        ChessBoard chessBoard = loadFile(fileChooser.getSelectedFile(),
                                old, e);
                        if (chessBoard == null) {
                            return;
                        }
                        Windows newWin = new Windows("Halma", 1000, 800, chessBoard.isIs2Player(), chessBoard);
                        newWin.setVisible(true);
                        chessBoard.setGamePanel(newWin.getGamePanel());
                        int who = chessBoard.getWho();
                        if (chessBoard.isIs2Player()) {
                            chessBoard.getGamePanel().getPlayer().setText("Player:" + ((who % 2) + 1));
                        } else {
                            chessBoard.getGamePanel().getPlayer().setText("Player:" + ((who % 4) + 1));
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            private ChessBoard loadFile(File file, Windows windows, ActionEvent e) throws IOException {
                //没有删除旧棋子，且新棋子没有加监听器
                FileInputStream inputStream = new FileInputStream(file);
                //FileInputStream inputStream = new FileInputStream(file);

                //先判断file 是否合法
                int verifier = inputStream.read();
                int who = inputStream.read() - 48;
                inputStream.read();
                inputStream.read();
                byte[] b = new byte[16 * 16 + 16 * 2];
                inputStream.read(b);
                if (verifier == '#') {
                    for (int j = 0; j < b.length; j++) {
                        if (b[j] != '1' && b[j] != '2' && b[j] != '0' && b[j] != 13 && b[j] != 10) {//判断存档是否含空
                            JOptionPane.showMessageDialog(null, "存档不正确");
                            return null;
                        }//判断基本语义是否正确
                    }
                } else if (verifier == '$') {
                    for (int j = 0; j < b.length; j++) {
                        if (b[j] != '1' && b[j] != '2' && b[j] != '3' &&
                                b[j] != '4' && b[j] != '0' && b[j] != 13 && b[j] != 10) {//判断存档是否含空
                            JOptionPane.showMessageDialog(null, "存档不正确");
                            return null;
                        }
                    }
                }
                inputStream.close();
                FileInputStream inputStream1 = new FileInputStream(file);
                inputStream1.read();
                inputStream1.read();
                inputStream1.read();
                inputStream1.read();

                for (int i = 0; i < 16; i++) {
                    byte[] eachRow = new byte[18];
                    inputStream1.read(eachRow);
                    if (eachRow[16] != 13 || eachRow[17] != 10) {
                        JOptionPane.showMessageDialog(null, "存档不正确");
                        return null;
                    }//棋子个数不正确
                }

                inputStream1.close();

                FileInputStream inputStream2 = new FileInputStream(file);

                inputStream2.read();
                inputStream2.read();
                inputStream2.read();
                inputStream2.read();

                if (verifier == '#') {
                    ChessBoard chessBoard = new ChessBoard(true, new GamePanel(true));
                    chessBoard.getChessList().removeAll(chessBoard.getChessList());
                    chessBoard.getP1().removeAll(chessBoard.getP1());
                    chessBoard.getP2().removeAll(chessBoard.getP2());
                    chessBoard.getP3().removeAll(chessBoard.getP3());
                    chessBoard.getP4().removeAll(chessBoard.getP4());
                    chessBoard.getLocArrayList().removeAll(chessBoard.getLocArrayList());
                    chessBoard.setWho(who + 1);


                    for (int i = 0; i < 16; i++) {
                        for (int j = 0; j < 16; j++) {
                            if (chessBoard.getSquares()[i][j].getComponentCount() != 0) {
                                chessBoard.getSquares()[i][j].remove(0);
                            }
                        }
                    }

                    int len = 0;
                    int count = -1;
                    while ((len = inputStream2.read()) != -1) {
                        if (len == 13 || len == 10) {
                            continue;
                        }
                        count++;
                        int i = count / 16;
                        int j = count % 16;

                        if (len == '0') {
                            continue;
                        }
                        if (len == '1') {
                            chessBoard.addChess(i, j, 1, true, false);
                        }
                        if (len == '2') {
                            chessBoard.addChess(i, j, 2, true, false);
                        }
                    }

                    if (chessBoard.getP1().size() != 19 || chessBoard.getP2().size() != 19) {
                        JOptionPane.showMessageDialog(null, "存档不正确");
                        return null;
                    }
                    if (chessBoard.isWin2(chessBoard) != 0) {
                        JOptionPane.showMessageDialog(null, "开局Player" + chessBoard.isWin2(chessBoard) + "已胜利");
                        return null;

                    }
                    inputStream2.close();
                    windows.setVisible(false);
                    return chessBoard;


                } else if (verifier == '$') {
                    ChessBoard chessBoard = new ChessBoard(false, new GamePanel(false));
                    chessBoard.getChessList().removeAll(chessBoard.getChessList());
                    chessBoard.getP1().removeAll(chessBoard.getP1());
                    chessBoard.getP2().removeAll(chessBoard.getP2());
                    chessBoard.getP3().removeAll(chessBoard.getP3());
                    chessBoard.getP4().removeAll(chessBoard.getP4());
                    chessBoard.getLocArrayList().removeAll(chessBoard.getLocArrayList());
                    chessBoard.setWho(who + 3);


                    for (int i = 0; i < 16; i++) {
                        for (int j = 0; j < 16; j++) {
                            if (chessBoard.getSquares()[i][j].getComponentCount() != 0) {
                                chessBoard.getSquares()[i][j].remove(0);
                            }
                        }
                    }
                    int len = 0;
                    int count = -1;
                    while ((len = inputStream2.read()) != -1) {
                        if (len == 13 || len == 10) {
                            continue;
                        }
                        count++;//读到换行符会停止循环
                        int i;
                        int j;
                        i = count / 16;
                        j = (count % 16);

                        if (len == '0') {
                            continue;
                        } else if (len == '1') {
                            chessBoard.addChess(i, j, 1, false, false);
                        } else if (len == '2') {
                            chessBoard.addChess(i, j, 2, false, false);
                        } else if (len == '3') {
                            chessBoard.addChess(i, j, 3, false, false);
                        } else if (len == '4') {
                            chessBoard.addChess(i, j, 4, false, false);
                        }
                    }
                    if (chessBoard.getP1().size() != 13 || chessBoard.getP2().size() != 13
                            || chessBoard.getP3().size() != 13 || chessBoard.getP4().size() != 13) {
                        JOptionPane.showMessageDialog(null, "存档不正确");
                        return null;
                    }
                    if (chessBoard.isWin4(chessBoard) != 0) {
                        JOptionPane.showMessageDialog(null, "开局Player" + chessBoard.isWin4(chessBoard) + "已胜利");
                        return null;
                    }
                    inputStream2.close();
                    windows.setVisible(false);

                    return chessBoard;
                } else {
                    JOptionPane.showMessageDialog(null, "存档不正确");
                    return null;
                }
            }
        });

        close = new JButton("关闭游戏");
        close.setBackground(new Color(0xFFCABC));
        this.add(close);
        lay.putConstraint(SpringLayout.NORTH, close, 50, SpringLayout.SOUTH, load);
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        reLoad = new JButton("重新开始");
        reLoad.setBackground(new Color(0xFFCABC));
        this.add(reLoad);
        lay.putConstraint(SpringLayout.NORTH, reLoad, 50, SpringLayout.SOUTH, close);
        reLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "是否开启二人局？如果是请单击确定。如果想开启四人局，请单击否");
                if (result == JOptionPane.CANCEL_OPTION) {
                    return;
                } else if (result == JOptionPane.YES_OPTION) {
                    Windows frame = ((Windows) (((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent().getParent()));
                    Windows windows = new Windows(frame.getTitle(), frame.getWidth(), frame.getHeight(), true);
                    windows.setVisible(true);
                    frame.setVisible(false);
                } else if (result == JOptionPane.NO_OPTION) {
                    Windows frame = ((Windows) (((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent().getParent()));
                    Windows windows = new Windows(frame.getTitle(), frame.getWidth(), frame.getHeight(), false);
                    windows.setVisible(true);
                    frame.setVisible(false);
                }

            }
        });

        back = new JButton("返回初始界面");
        back.setBackground(new Color(0xFFCABC));
        this.add(back);
        lay.putConstraint(SpringLayout.NORTH, back, 50, SpringLayout.SOUTH, reLoad);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Windows old = (Windows) (((JButton) e.getSource()).getParent().getParent().getParent().getParent().getParent().getParent());
                old.setVisible(false);
                new InitialFrameControl();

            }
        });

        wannaHint = new JButton("提示开关");
        wannaHint.setBackground(new Color(0xFFCABC));
        this.add(wannaHint);
        lay.putConstraint(SpringLayout.NORTH, wannaHint, 50, SpringLayout.SOUTH, back);
        wannaHint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel gamePanel = (GamePanel) ((JButton) e.getSource()).getParent();
                gamePanel.setHint(!gamePanel.isHint());
                ((ChessBoard) (((JButton) e.getSource()).//按钮
                        getParent().getParent().//contentPanel
                        getComponent(1))).setHint(gamePanel.isHint());
            }
        });

        undo = new JButton("悔棋");
        undo.setBackground(new Color(0xFFCABC));
        this.add(undo);
        lay.putConstraint(SpringLayout.NORTH, undo, 50, SpringLayout.SOUTH, wannaHint);
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ChessBoard chessBoard = ((ChessBoard) (((JButton) e.getSource()).//按钮
                        getParent().getParent().//contentPanel
                        getComponent(1)));
                Chess lastSelected = chessBoard.getLastSelected();
                Chess nowSelected = chessBoard.getNowSelected();
                GamePanel gamePanel = ((GamePanel) ((JButton) e.getSource()).getParent());
                if (nowSelected == null) {
                    return;
                }

                if (chessBoard.getUndoTimes() == 0) {
                    chessBoard.setUndoTimes(1);
                    chessBoard.addChess(lastSelected.getChessBoardLoc().getRow(), lastSelected.getChessBoardLoc().getColumn(),
                            lastSelected.getId(), chessBoard.isIs2Player(), false);

                    nowSelected.setVisible(false);//把棋子删除
                    chessBoard.getLocArrayList().remove(nowSelected.getChessBoardLoc());//把棋子坐标删除
                    int id = nowSelected.getId();
                    if (id == 1) {
                        chessBoard.getP1().remove(nowSelected);
                    }
                    if (id == 2) {
                        chessBoard.getP2().remove(nowSelected);

                    }
                    if (id == 3) {

                        chessBoard.getP3().remove(nowSelected);
                    }
                    if (id == 4) {

                        chessBoard.getP4().remove(nowSelected);
                    }
                    nowSelected.getParent().removeAll();//把棋子从容器中删除
                    chessBoard.getChessList().remove(nowSelected);//从list里删除

                    who=chessBoard.getWho()-1;
                    chessBoard.setWho((chessBoard.getWho()) -1);

                    if (chessBoard.isIs2Player()) {
                        gamePanel.getPlayer().setText("Player:" + ((who % 2) + 1));
                        gamePanel.setWho(((who % 2) + 1));
                    } else {
                        gamePanel.getPlayer().setText("Player:" + ((who % 4) + 1));
                        gamePanel.setWho(((who % 4) + 1));
                    }
                    ;
                }
            }

        });


    }

    public void setPlayer(JLabel player) {
        this.player = player;
    }

    public JLabel getPlayer() {
        return player;
    }

    public int getWho() {
        return who;
    }

    public void setWho(int who) {
        this.who = who;
    }

    public boolean isHint() {
        return hint;
    }

    public void setHint(boolean hint) {
        this.hint = hint;
    }
}
