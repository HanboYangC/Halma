package Controller;


import View.ChessBoard;
import frame.Windows;
import frame.initialFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class InitialFrameControl extends JFrame {
    public InitialFrameControl() {
        initialFrame start=new initialFrame("Halma",800,700);
        start.setVisible(true);
        start.getButton2().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
                Windows windows=new Windows("Halma",1000,800,true);
                windows.setVisible(true);
            }
        });
        start.getButton4().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                start.setVisible(false);
                Windows windows=new Windows("Halma",1000,800,false);
                windows.setVisible(true);
            }
        });
        start.getButton5().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser("Save");
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        JFrame old=(JFrame)((JButton)e.getSource()).getParent().getParent().getParent().getParent().getParent();
                        ChessBoard chessBoard = loadFile(fileChooser.getSelectedFile(),
                                old, e);
                        if(chessBoard==null){
                            return;
                        }
                        Windows newWin=new Windows("Halma", 1000, 800, chessBoard.isIs2Player(), chessBoard);
                        newWin.setVisible(true);
                        chessBoard.setGamePanel(newWin.getGamePanel());
                        int who =chessBoard.getWho();
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

            private ChessBoard loadFile(File file, JFrame windows, ActionEvent e) throws IOException {
                //没有删除旧棋子，且新棋子没有加监听器
                FileInputStream inputStream = new FileInputStream(file);
                //FileInputStream inputStream = new FileInputStream(file);

                //先判断file 是否合法
                int verifier = inputStream.read();
                int who=inputStream.read()-48;
                inputStream.read();
                inputStream.read();
                byte[] b = new byte[16 * 16 + 16 * 2];
                inputStream.read(b);
                if (verifier == '#') {
                    for (int j = 0; j < b.length; j++) {
                        if (b[j] != '1' && b[j] != '2' && b[j] != '0'&& b[j] != 13 && b[j] != 10) {//判断存档是否含空
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
                        JOptionPane.showMessageDialog(null,"存档不正确");
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
                    chessBoard.setWho(who+1);


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
                        int i =count/16;
                        int j =count%16;

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

                    if(chessBoard.getP1().size()!=19||chessBoard.getP2().size()!=19){
                        JOptionPane.showMessageDialog(null, "存档不正确");
                        return null;
                    }
                    if(chessBoard.isWin2(chessBoard)!=0){
                        JOptionPane.showMessageDialog(null, "开局Player"+chessBoard.isWin2(chessBoard)+"已胜利");
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
                    chessBoard.setWho(who+3);


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
                    if(chessBoard.getP1().size()!=13||chessBoard.getP2().size()!=13
                            ||chessBoard.getP3().size()!=13||chessBoard.getP4().size()!=13){
                        JOptionPane.showMessageDialog(null, "棋子数量不正确");
                        return null;
                    }
                    if(chessBoard.isWin4(chessBoard)!=0){
                        JOptionPane.showMessageDialog(null, "开局Player"+chessBoard.isWin4(chessBoard)+"已胜利");
                        return null;
                    }
                    inputStream2.close();
                    windows.setVisible(false);

                    return chessBoard;
                } else {
                    JOptionPane.showMessageDialog(null, "存档信息不正确");
                    return null;
                }
            }

        });
    }

}
