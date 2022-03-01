import java.util.*;

import ua.princeton.lib.*;

public class Solver {
    private int moves = 0;
    private SearchNode finaln;
    private Stack<Board> boards;

    public Solver(Board initial) {
        if (!initial.isSolvable()) {
            return;
            //throw new IllegalArgumentException("Unsolvable");
        }

        PriorityQueue<SearchNode> minPQ = new PriorityQueue<SearchNode>(initial.size() + 10);

        Set<Board> prevs = new HashSet<Board>(50);
        Board deqb = initial;
        Board prev = null;
        SearchNode deqn = new SearchNode(initial, 0, null);
        Iterable<Board> boards;

        while (!deqb.isGoal()) {
            boards = deqb.neighbors();
            moves++;

            for (Board board : boards) {
                if (!board.equals(prev) && !prevs.contains(board)) {
                    minPQ.add(new SearchNode(board, moves, deqn));
                }
            }

            prevs.add(prev);
            prev = deqb;
            deqn = minPQ.poll();
            deqb = deqn.curr;
        }
        finaln = deqn;
    }

    public int moves() {
        if (boards != null) return boards.size() - 1;
        solution();
        return boards.size() - 1;
    }

    public Iterable<Board> solution() {
        if (boards != null) return boards;
        boards = new Stack<Board>();
        SearchNode pointer = finaln;
        while (pointer != null) {
            boards.push(pointer.curr);
            pointer = pointer.prev;
        }
        return boards;
    }

    private class SearchNode implements Comparable<SearchNode> {
        private final int priority;
        private final SearchNode prev;
        private final Board curr;


        public SearchNode(Board curr, int moves, SearchNode prev) {
            this.curr = curr;
            this.prev = prev;
            this.priority = moves + curr.manhattan();
        }

        @Override
        public int compareTo(SearchNode that) {
            int cmp = this.priority - that.priority;
            return Integer.compare(cmp, 0);
        }

    }

    public static void main(String[] args) {
        // створюємо початкову дошку з файлу
        //In in = new In(args[0]);
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.nextInt();
        Board initial = new Board(blocks);

        // розв’язати
        Solver solver = new Solver(initial);

        // надрукувати рішення
        if (!initial.isSolvable())
            StdOut.println("Дошка не має розв’язку");
        else {
            StdOut.println("Мінімальна кількість кроків = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}