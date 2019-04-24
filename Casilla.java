package astar;

public class Casilla {

    private int costeTotal;
    private int costeHeuristica;
    public int costToKey;
    private int row;
    private int col;
    private boolean isKey;
    private Casilla parent;

    public int getCosteTotal() {
        return costeTotal;
    }

    public int getCostToKey() {
        return costToKey;
    }

    public void setCostToKey(int costToKey) {
        this.costToKey = costToKey;
    }

    public void setCosteTotal(int costeTotal) {
        this.costeTotal = costeTotal;
    }

    public void setCosteHeuristica(int costeHeuristica) {
        this.costeHeuristica = costeHeuristica;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    Casilla(int row, int col) {
        this.row = row;
        this.col = col;
    }

    int calculateHeuristic(Casilla casillaDestino) {
        return Math.abs(casillaDestino.getRow() - getRow()) + Math.abs(casillaDestino.getCol() - getCol());
    }


    @Override
    public boolean equals(Object arg0) {
        Casilla other = (Casilla) arg0;
        return this.getRow() == other.getRow() && this.getCol() == other.getCol();
    }

    @Override
    public String toString() {
        return "Casilla [row=" + row + ", col=" + col + "]";
    }

    int getCosteHeuristica(Casilla casillaDestino) {
        return calculateHeuristic(casillaDestino);
    }

    Casilla getParent() {
        return parent;
    }

    void setParent(Casilla parent) {
        this.parent = parent;
    }


    int getRow() {
        return row;
    }


    int getCol() {
        return col;
    }


}