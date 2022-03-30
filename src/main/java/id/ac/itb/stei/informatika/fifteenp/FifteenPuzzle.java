package id.ac.itb.stei.informatika.fifteenp;

import id.ac.itb.stei.informatika.fifteenp.util.Direction;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrix;
import id.ac.itb.stei.informatika.fifteenp.util.FifteenMatrixBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class FifteenPuzzle {
    FifteenMatrix puzzle;
    PriorityQueue<FifteenMatrixNode> queue;
    ArrayList<FifteenMatrixNode> evaluatedStates;

    ArrayList<FifteenMatrix> solutionPathMatrix;
    ArrayList<Direction> solutionPathDir;

    public static void main(String[] args) {
        FifteenMatrixBuilder builder = new FifteenMatrixBuilder();
        builder.append(3).append(1).append(2).append(4);
        builder.append(null).append(5).append(7).append(8);
        builder.append(10).append(6).append(11).append(12);
        builder.append(9).append(13).append(14).append(15);

        FifteenMatrix puzzle = builder.build();
        FifteenPuzzle solver = new FifteenPuzzle(puzzle);

        double start = System.nanoTime();
        solver.solve();
        double end = System.nanoTime();

        ArrayList<FifteenMatrix> path = solver.getSolutionPathMatrix();
        ArrayList<Direction> pathDir = solver.getSolutionPathDir();

        System.out.println((end - start) * 1e-9 + " seconds.");

    }

    public static final FifteenMatrix SOLUTION
        = new FifteenMatrixBuilder()
            .append(1).append(2).append(3).append(4)
            .append(5).append(6).append(7).append(8)
            .append(9).append(10).append(11).append(12)
            .append(13).append(14).append(15).append(null)
            .build();

    public FifteenPuzzle(FifteenMatrix puzzle) {
        this.puzzle = puzzle;
        this.queue = new PriorityQueue<>(Comparator.comparingInt(FifteenMatrixNode::cost));
        this.evaluatedStates = new ArrayList<>();
        this.solutionPathMatrix = new ArrayList<>();
        this.solutionPathDir = new ArrayList<>();
    }

    public void solve() {
        this.checkSolvability();
        this.setup();

        FifteenMatrixNode state = this.queue.poll();
        while (!state.value().equals(FifteenPuzzle.SOLUTION)) {
            this.expand(state);
            state = this.queue.poll();
        }

        this.evaluatedStates.add(state);
        this.buildSolution();
    }

    private void checkSolvability() {
        int blankTileIndex = this.puzzle.blankTileIndex();
        int factor = (blankTileIndex / 4 + blankTileIndex % 4) % 2;
        boolean solvable = (this.puzzle.lowerSum() + factor) % 2 == 0;
        if (!solvable) {
            throw new IllegalArgumentException();
        }
    }

    private void setup() {
        this.queue.add(new FifteenMatrixNode(
                this.puzzle,
                null,
                null,
                1
        ));
    }

    private boolean has(FifteenMatrix state) {
        for (FifteenMatrixNode node: this.evaluatedStates) {
            if (node.value().equals(state)) {
                return true;
            }
        }

        for (FifteenMatrixNode node: this.queue) {
            if (node.value().equals(state)) {
                return true;
            }
        }

        return false;
    }

    private void expand(FifteenMatrixNode state) {

        for (Direction dir: Direction.DIRECTIONS) {
            try {
                FifteenMatrix newMatrix = state.value()
                        .moveBlankTile(dir);
                if (!this.has(newMatrix)) {
                    FifteenMatrixNode node = new FifteenMatrixNode(
                            newMatrix,
                            state,
                            dir,
                            state.depth() + 1
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

    private void buildSolution() {
        FifteenMatrixNode node = this.evaluatedStates.get(
                this.evaluatedStates.size() - 1
        );

        while (node.parent() != null) {
            this.solutionPathMatrix.add(node.value());
            this.solutionPathDir.add(node.dir());
            node = node.parent();
        }
        this.solutionPathMatrix.add(node.value());

        Collections.reverse(this.solutionPathMatrix);
        Collections.reverse(this.solutionPathDir);
    }

    public ArrayList<FifteenMatrix> getSolutionPathMatrix() {
        return this.solutionPathMatrix;
    }

    public ArrayList<Direction> getSolutionPathDir() {
        return this.solutionPathDir;
    }

    public int generatedStates() {
        return this.queue.size() + this.evaluatedStates.size();
    }
}

record FifteenMatrixNode(FifteenMatrix value,
                         FifteenMatrixNode parent,
                         Direction dir,
                         int depth) {
    public int cost() {
        return this.parent().depth() + this.value().mismatchedTiles();
    }

    public String toString() {
        return this.value.toString();
    }
}