package tdg.map;

public class Map {
    // 10 x 10 map size
    int[][] map = new int[10][10];

    int maxCol, maxRow; // number of max col and row

    int startX, startY; // Coordonnées de la map de départ

    int endX, endY; // Coordonnées de la map d'arrivée

    public Map(int maxCol, int maxRow, int startX, int startY, int endX, int endY) {
        this.maxCol = maxCol;
        this.maxRow = maxRow;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
}
