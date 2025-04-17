
package model.PieceColor;

public enum PieceColor {
    WHITE(1), BLACK(2);
    private final int value;

    PieceColor(int i) {
        this.value=i;
    }
    public int getValue() {
        return value;
    }


}