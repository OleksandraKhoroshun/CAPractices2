import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class Board {

    private final int[][] blocks;
    private final int N;

    private int hashCode = -1;
    private int zeroRow = -1;
    private int zeroCol = -1;
    private Collection<Board> neighbors;

    public Board(int[][] tiles) {
        this.N = 3;
        this.blocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] >= 0 && tiles[i][j] < N * N) blocks[i][j] = tiles[i][j];
                else {
                    throw new IllegalArgumentException();
                }
            }
        }
        assert blocks.length > 0;
    }

    public int size() {
        return N;
    }

    public int hamming() {
        int hamming = 0;
        for (int row = 0; row < this.size(); row++) {
            for (int col = 0; col < this.size(); col++) {
                if (blocks[row][col] != 0 && blocks[row][col] != (row * N + col + 1)) hamming++;
            }
        }
        return hamming;
    }

    public int manhattan() {
        int res = 0;
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != (i * N + j + 1)) {
                    int expRow = (blocks[i][j] - 1) / N;
                    int expCol = (blocks[i][j] - 1) % N;
                    res += Math.abs(expRow - i) + Math.abs(expCol - j);
                }
            }
        }
        return res;
    }

    public boolean isGoal() {
        if (blocks[N-1][N-1] != 0) return false;

        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                if (blocks[i][j] != 0 && blocks[i][j] != (i * N + j + 1)) return false;
            }
        }
        return true;
    }

    public boolean isSolvable() {
        int count = 0;

        for (int i = 0; i < this.size() * this.size(); i++) {
            int currRow = i / this.size();
            int currCol = i % this.size();
            if (blocks[currRow][currCol] == 0) {
                this.zeroRow = currRow;
                this.zeroCol = currCol;
            }
            for (int j = i; j < this.size() * this.size(); j++) {
                int row = j / this.size();
                int col = j % this.size();

                if (blocks[row][col] != 0 && blocks[row][col] < blocks[currRow][currCol]) {
                    count++;
                }
            }
        }
        if (blocks.length % 2 != 0 && count % 2 != 0) return false;
        if (blocks.length % 2 == 0 && (count + this.zeroRow) % 2 == 0) return false;
        return true;
    }

    public boolean equals(Object y) {
        if (!(y instanceof Board)) return false;
        Board that = (Board) y;
        return this.blocks[N-1][N-1] == that.blocks[N-1][N-1] && this.size() == that.size() && Arrays.deepEquals(this.blocks, that.blocks);
    }

    public int hashCode() {
        if (this.hashCode != -1) return hashCode;
        this.hashCode = Arrays.deepHashCode(blocks);
        return this.hashCode;
    }

    public Collection<Board> neighbors() {
        if (neighbors != null) return neighbors;
        if (this.zeroRow == -1 && this.zeroCol == -1) findZeroTile();

        neighbors = new HashSet<>();

        if (zeroRow - 1 >= 0) generateNeighbor(zeroRow - 1, true);
        if (zeroCol - 1 >= 0) generateNeighbor(zeroCol - 1, false);
        if (zeroRow + 1 < this.size()) generateNeighbor(zeroRow + 1, true);
        if (zeroCol + 1 < this.size()) generateNeighbor(zeroCol + 1, false);

        return neighbors;
    }

    private void findZeroTile() {
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                if (blocks[i][j] == 0) {
                    this.zeroRow = i;
                    this.zeroCol = j;
                    break;
                }
            }
        }
    }

    private void generateNeighbor(int toPosition, boolean isRow) {
        int[][] copy = copy(this.blocks);
        if (isRow) swapEntries(copy, zeroRow, zeroCol, toPosition, zeroCol);
        else swapEntries(copy, zeroRow, zeroCol, zeroRow, toPosition);

        neighbors.add(new Board(copy));
    }

    private void swapEntries(int[][] array, int fromRow, int fromCol, int toRow, int toCol) {
        int i = array[fromRow][fromCol];
        array[fromRow][fromCol] = array[toRow][toCol];
        array[toRow][toCol] = i;
    }

    public String toString() {
        StringBuilder s = new StringBuilder(4 * N * N);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    private int[][] copy(int[][] arr) {
        int[][] copy = new int[arr.length][arr.length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                copy[i][j] = arr[i][j];
            }
        }
        return copy;
    }
    public static void main(String[] args)
    {
        Queue q = new Queue(4);
    }

}