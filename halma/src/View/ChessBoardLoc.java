package View;

import java.util.Objects;

public class ChessBoardLoc {
    private int row, column;


    public ChessBoardLoc(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public ChessBoardLoc() {
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoardLoc location = (ChessBoardLoc) o;
        return row == location.row && column == location.column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public ChessBoardLoc relative(ChessBoardLoc compare){//compare相对于对象的位置
        int x=compare.getRow()-this.getRow();
        int y=compare.getColumn()-this.getColumn();
        ChessBoardLoc rel =new ChessBoardLoc(x,y);
        return rel;
    }

    public ChessBoardLoc add(ChessBoardLoc add){//compare加上对象的位置
        int x=add.getRow()+this.getRow();
        int y=add.getColumn()+this.getColumn();
        ChessBoardLoc addResult =new ChessBoardLoc(x,y);
        return addResult;
    }

    public boolean isLegal(){
        if((this.getColumn()<=15&&this.getColumn()>=0)&&(this.getRow()<=15&&this.getColumn()>=0)){
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
