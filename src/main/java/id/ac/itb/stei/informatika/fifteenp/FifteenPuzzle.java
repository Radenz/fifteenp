package id.ac.itb.stei.informatika.fifteenp;

import id.ac.itb.stei.informatika.fifteenp.util.Direction;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrixBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * {@code FifteenPuzzle} represents a 15-puzzle solver
 * that holds a single {@code FifteenMatrix} as a puzzle.
 */
public class FifteenPuzzle {
    private FifteenMatrix puzzle;
    private PriorityQueue<FifteenMatrixNode> queue;
    private ArrayList<FifteenMatrixNode> evaluatedStates;

    private ArrayList<FifteenMatrix> solutionPathMatrix;
    private ArrayList<Direction> solutionPathDir;

    /**
     * The solution of the 15-puzzle as a
     * {@code FifteenMatrix} object.
     */
    public static final FifteenMatrix SOLUTION
        = new FifteenMatrixBuilder()
            .append(1).append(2).append(3).append(4)
            .append(5).append(6).append(7).append(8)
            .append(9).append(10).append(11).append(12)
            .append(13).append(14).append(15).append(null)
            .build();

    /**
     * The integer representation of the 15-puzzle
     * solution.
     */
    public static final long SOLUTION_ID
            = FifteenPuzzle.SOLUTION.identity();

    /**
     * Creates a new {@code FifteenPuzzle} to solve a
     * specific puzzle based on specified {@code FifteenMatrix}
     * object as its puzzle.
     * @param puzzle the puzzle to solve
     */
    public FifteenPuzzle(FifteenMatrix puzzle) {
        this.puzzle = puzzle;
        this.queue = new PriorityQueue<>(Comparator.comparingInt(FifteenMatrixNode::cost));
        this.evaluatedStates = new ArrayList<>();
        this.solutionPathMatrix = new ArrayList<>();
        this.solutionPathDir = new ArrayList<>();
    }

    /**
     * Solves the given puzzle using branch and bound
     * algorithm.
     */
    public void solve() {
        this.checkSolvability();
        this.setup();

        FifteenMatrixNode state = this.queue.poll();
        while (state.matrixId() != FifteenPuzzle.SOLUTION_ID) {
            this.expand(state);
            state = this.queue.poll();
        }

        this.evaluatedStates.add(state);
        this.buildSolution();
    }

    /**
     * Checks if the given puzzle is solvable.
     * @throws IllegalArgumentException if the given
     *         puzzle is unsolvable
     */
    private void checkSolvability() throws IllegalArgumentException {
        boolean solvable = this.puzzle.lowerSum() % 2 == 0;
        if (!solvable) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Sets the puzzle as the first state of the
     * solving process using branch and bound
     * algorithm.
     */
    private void setup() {
        this.queue.add(new FifteenMatrixNode(
                this.puzzle.identity(),
                null,
                null,
                1,
                1
        ));
    }

    /**
     * Checks if the solver has already enqueued or
     * expanded a specific matrix represented by
     * the specified long integer.
     * @param stateMatrixId long integer representing
     *                      the matrix to check
     * @return true if the matrix has already been
     *         enqueued or expanded
     */
    private boolean has(long stateMatrixId) {
        for (FifteenMatrixNode node: this.evaluatedStates) {
            if (node.matrixId() == stateMatrixId) {
                return true;
            }
        }

        for (FifteenMatrixNode node: this.queue) {
            if (node.matrixId() == stateMatrixId) {
                return true;
            }
        }

        return false;
    }

    /**
     * Expands a specified state. This method will enqueue
     * all child states of the specified state and move
     * the specified state to the evaluated states list.
     * @param state the state to expand
     */
    private void expand(FifteenMatrixNode state) {
        for (Direction dir: Direction.DIRECTIONS) {
            if (dir.flip() == state.dir()) {
                continue;
            }
            try {
                FifteenMatrix newMatrix = FifteenMatrix.from(state.matrixId())
                        .moveBlankTile(dir);
                long newMatrixId = newMatrix.identity();
                if (!this.has(newMatrixId)) {
                    FifteenMatrixNode node = new FifteenMatrixNode(
                            newMatrixId,
                            state,
                            dir,
                            state.depth() + 1,
                            state.depth() + newMatrix.mismatchedTiles()
                    );
                    this.queue.offer(
                        node
                    );

                }
            } catch (IndexOutOfBoundsException ignored) {

            }
        }

        this.evaluatedStates.add(state);
    }

    /**
     * Builds the solution path of this {@code FifteenPuzzle}.
     */
    private void buildSolution() {
        FifteenMatrixNode node = this.evaluatedStates.get(
                this.evaluatedStates.size() - 1
        );

        while (node.parent() != null) {
            this.solutionPathMatrix.add(FifteenMatrix.from(
                    node.matrixId()
            ));
            this.solutionPathDir.add(node.dir());
            node = node.parent();
        }
        this.solutionPathMatrix.add(FifteenMatrix.from(
                node.matrixId()
        ));

        Collections.reverse(this.solutionPathMatrix);
        Collections.reverse(this.solutionPathDir);
    }

    /**
     * Retrieves solution path to reach the goal state
     * of this {@code FifteenPuzzle} as a list of
     * {@code FifteenMatrix}.
     * @return the solution path to the goal state as a
     *         list of {@code FifteenMatrix}
     */
    public ArrayList<FifteenMatrix> getSolutionPathMatrix() {
        return this.solutionPathMatrix;
    }

    /**
     * Retrieves solution path to reach the goal state
     * of this {@code FifteenPuzzle} as a list of
     * {@code Direction}.
     * @return the solution path to the goal state as a
     *         list of {@code Direction}
     */
    public ArrayList<Direction> getSolutionPathDir() {
        return this.solutionPathDir;
    }

    /**
     * Counts the amount of generated states to solve this
     * {@code FifteenPuzzle}.
     * @return the amount of generated states
     */
    public int generatedStates() {
        return this.queue.size() + this.evaluatedStates.size();
    }
}

/**
 * {@code FifteenMatrixNode} represents a state or
 * tree node in the branch and bound algorithm while
 * solving the 15-puzzle.
 * @param matrixId  the long integer representation of
 *                  the {@code FifteenMatrix} this state holds
 * @param parent    parent state of this state
 * @param dir       the direction that the blank tile was moved
 *                  to reach this state
 * @param depth     depth of this state
 * @param cost      cost to expand this state
 */
record FifteenMatrixNode(long matrixId,
                         FifteenMatrixNode parent,
                         Direction dir,
                         int depth,
                         int cost) {
}