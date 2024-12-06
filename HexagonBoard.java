/**
 * Code Written by Jackson L. Davis
 *
 * A HexagonBoard is a data structure that resembles a hexagon board like this:
 *   O - O
 *  / \ / \
 * O - O - O
 *  \ / \ /
 *   O - O
 * This is a HexagonBoard of size 1.
 * Each O is a HexagonNode.
 *     O - O - O
 *    / \ / \ / \
 *   O - O - O - O
 *  / \ / \ / \ / \
 * O - O - O - O - O
 *  \ / \ / \ / \ /
 *   O - O - O - O
 *    \ / \ / \ /
 *     O - O - O
 * This is a HexagonBoard of size 2.
 * Here are the rows and columns of the HexagonBoard
 *      0 - 0 - 0           0   1   2
 *                         /   /   /
 *    1 - 1 - 1 - 1       0   1   2   3
 *                       /   /   /   /
 *  2 - 2 - 2 - 2 - 2   0   1   2   3   4
 *                       \   \   \   \
 *    3 - 3 - 3 - 3       0   1   2   3
 *                         \   \   \
 *      4 - 4 - 4           0   1   2
 *
 * Some other useful information about HexagonBoards
 * Number of rows = 2 * size + 1
 * Number of columns = 2 * size + 1
 * Index of last row = 2 * size
 * Index of last column = 2 * size
 *
 * Row lengths for HexagonBoard of size n:
 * (the index of the last entry in each row is this minus 1)
 *  Row 0: n + 1
 *  Row 1: n + 2
 *  Row 2: n + 3
 *  ...
 *  Row n-2: n + (n-1)
 *  Row n-1: n + n
 *  Row n: n + (n+1)
 *  Row n+1 : n + n
 *  Row n+2: n + (n-1)
 *  ...
 *  Row 2n-2: n + 3
 *  Row 2n-1: n + 2
 *  Row 2n: n + 1
 *
 * Column lengths for HexagonBoard of size n
 * (the index of the last entry in each column is this minus 1)
 *  Col 0: 2n + 1
 *  Col 1: 2n + 1
 *  ...
 *  Col n-1: 2n + 1
 *  Col n: 2n + 1
 *  Col n+1: 2n - 1
 *  Col n+2: 2n - 3
 *  ...
 *  Col 2n-2: 5
 *  Col 2n-1: 3
 *  Col 2n: 1
 */
public class HexagonBoard<I> {
    private int size;
    private HexagonNode<I> centreNode; // the centre node of the HexagonBoard

    private HexagonNode<I> currentNode; // this is like a cursor that is on a node
    // indexes that will make locating the current node easier for printing
    private int row;
    private int col;

    /**
     * Constructor method for a new HexagonBoard,
     * this method sets up all HexagonNodes and their connections to neighbouring nodes.
     * @param sz the size of the HexagonBoard
     * @precond sz >= 0
     */
    public HexagonBoard(int sz) {
        if (sz < 0) {
            throw new RuntimeException("HexagonBoard must have size at least 0");
        }
        else {
            this.size = sz;
            this.centreNode = new HexagonNode<I>();
            this.currentNode = this.centreNode; // cursor starts on the centre node

            // make each layer of the HexagonBoard
            for (int i = 1; i <= this.size; i++) {

                // set up upRight corner node
                for (int c = 1; c < i; c++) {
                    this.currentNode = this.currentNode.nextUpRight();
                }
                this.currentNode.setNextUpRight(new HexagonNode<>());
                this.currentNode.nextUpRight().setNextDownLeft(this.currentNode);
                this.currentNode = this.currentNode.nextUpRight();

                // set up edge nodes between upRight and midRight corners
                for (int e = 1; e < i; e++) {
                    this.currentNode.setNextDownRight(new HexagonNode<>());
                    this.currentNode.nextDownRight().setNextUpLeft(this.currentNode);
                    this.currentNode.nextDownLeft().setNextMidRight(this.currentNode.nextDownRight());
                    this.currentNode.nextDownRight().setNextMidLeft(this.currentNode.nextDownLeft());
                    this.currentNode.nextDownLeft().nextDownRight().setNextUpRight(this.currentNode.nextDownRight());
                    this.currentNode.nextDownRight().setNextDownLeft(this.currentNode.nextDownLeft().nextDownRight());
                    this.currentNode = this.currentNode.nextDownRight();
                }

                // set up midRight corner node
                this.currentNode = this.centreNode;
                for (int c = 1; c < i; c++) {
                    this.currentNode = this.currentNode.nextMidRight();
                }
                this.currentNode.setNextMidRight(new HexagonNode<>());
                this.currentNode.nextMidRight().setNextMidLeft(this.currentNode);
                this.currentNode.nextUpRight().setNextDownRight(this.currentNode.nextMidRight());
                this.currentNode.nextMidRight().setNextUpLeft(this.currentNode.nextUpRight());
                this.currentNode = this.currentNode.nextMidRight();

                // set up edge nodes between midRight and downRight corners
                for (int e = 1; e < i; e++) {
                    this.currentNode.setNextDownLeft(new HexagonNode<>());
                    this.currentNode.nextDownLeft().setNextUpRight(this.currentNode);
                    this.currentNode.nextMidLeft().setNextDownRight(this.currentNode.nextDownLeft());
                    this.currentNode.nextDownLeft().setNextUpLeft(this.currentNode.nextMidLeft());
                    this.currentNode.nextMidLeft().nextDownLeft().setNextMidRight(this.currentNode.nextDownLeft());
                    this.currentNode.nextDownLeft().setNextMidLeft(this.currentNode.nextMidLeft().nextDownLeft());
                    this.currentNode = this.currentNode.nextDownLeft();
                }

                // set up downRight corner node
                this.currentNode = this.centreNode;
                for (int c = 1; c < i; c++) {
                    this.currentNode = this.currentNode.nextDownRight();
                }
                this.currentNode.setNextDownRight(new HexagonNode<>());
                this.currentNode.nextDownRight().setNextUpLeft(this.currentNode);
                this.currentNode.nextMidRight().setNextDownLeft(this.currentNode.nextDownRight());
                this.currentNode.nextDownRight().setNextUpRight(this.currentNode.nextMidRight());
                this.currentNode = this.currentNode.nextDownRight();

                // set up edge nodes between downRight and downLeft corners
                for (int e = 1; e < i; e++) {
                    this.currentNode.setNextMidLeft(new HexagonNode<>());
                    this.currentNode.nextMidLeft().setNextMidRight(this.currentNode);
                    this.currentNode.nextUpLeft().setNextDownLeft(this.currentNode.nextMidLeft());
                    this.currentNode.nextMidLeft().setNextUpRight(this.currentNode.nextUpLeft());
                    this.currentNode.nextUpLeft().nextMidLeft().setNextDownRight(this.currentNode.nextMidLeft());
                    this.currentNode.nextMidLeft().setNextUpLeft(this.currentNode.nextUpLeft().nextMidLeft());
                    this.currentNode = this.currentNode.nextMidLeft();
                }

                // set up downLeft corner node
                this.currentNode = this.centreNode;
                for (int c = 1; c < i; c++) {
                    this.currentNode = this.currentNode.nextDownLeft();
                }
                this.currentNode.setNextDownLeft(new HexagonNode<>());
                this.currentNode.nextDownLeft().setNextUpRight(this.currentNode);
                this.currentNode.nextDownRight().setNextMidLeft(this.currentNode.nextDownLeft());
                this.currentNode.nextDownLeft().setNextMidRight(this.currentNode.nextDownRight());
                this.currentNode = this.currentNode.nextDownLeft();

                // set up edge nodes between downLeft and midLeft corners
                for (int e = 1; e < i; e++) {
                    this.currentNode.setNextUpLeft(new HexagonNode<>());
                    this.currentNode.nextUpLeft().setNextDownRight(this.currentNode);
                    this.currentNode.nextUpRight().setNextMidLeft(this.currentNode.nextUpLeft());
                    this.currentNode.nextUpLeft().setNextMidRight(this.currentNode.nextUpRight());
                    this.currentNode.nextUpRight().nextUpLeft().setNextDownLeft(this.currentNode.nextUpLeft());
                    this.currentNode.nextUpLeft().setNextUpRight(this.currentNode.nextUpRight().nextUpLeft());
                    this.currentNode = this.currentNode.nextUpLeft();
                }

                // set up midLeft corner node
                this.currentNode = this.centreNode;
                for (int c = 1; c < i; c++) {
                    this.currentNode = this.currentNode.nextMidLeft();
                }
                this.currentNode.setNextMidLeft(new HexagonNode<>());
                this.currentNode.nextMidLeft().setNextMidRight(this.currentNode);
                this.currentNode.nextDownLeft().setNextUpLeft(this.currentNode.nextMidLeft());
                this.currentNode.nextMidLeft().setNextDownRight(this.currentNode.nextDownLeft());
                this.currentNode = this.currentNode.nextMidLeft();

                // set up edge nodes between midLeft and upLeft corners
                for (int e = 1; e < i; e++) {
                    this.currentNode.setNextUpRight(new HexagonNode<>());
                    this.currentNode.nextUpRight().setNextDownLeft(this.currentNode);
                    this.currentNode.nextMidRight().setNextUpLeft(this.currentNode.nextUpRight());
                    this.currentNode.nextUpRight().setNextDownRight(this.currentNode.nextMidRight());
                    this.currentNode.nextMidRight().nextUpRight().setNextMidLeft(this.currentNode.nextUpRight());
                    this.currentNode.nextUpRight().setNextMidRight(this.currentNode.nextMidRight().nextUpRight());
                    this.currentNode = this.currentNode.nextUpRight();
                }

                // set up upLeft corner node
                this.currentNode = this.centreNode;
                for (int c = 1; c < i; c++) {
                    this.currentNode = this.currentNode.nextUpLeft();
                }
                this.currentNode.setNextUpLeft(new HexagonNode<>());
                this.currentNode.nextUpLeft().setNextDownRight(this.currentNode);
                this.currentNode.nextMidLeft().setNextUpRight(this.currentNode.nextUpLeft());
                this.currentNode.nextUpLeft().setNextDownLeft(this.currentNode.nextMidLeft());
                this.currentNode = this.currentNode.nextUpLeft();

                // set up edge nodes between upLeft and upRight corners
                for (int e = 1; e < i; e++) {
                    this.currentNode.setNextMidRight(new HexagonNode<>());
                    this.currentNode.nextMidRight().setNextMidLeft(this.currentNode);
                    this.currentNode.nextDownRight().setNextUpRight(this.currentNode.nextMidRight());
                    this.currentNode.nextMidRight().setNextDownLeft(this.currentNode.nextDownRight());
                    this.currentNode.nextDownRight().nextMidRight().setNextUpLeft(this.currentNode.nextMidRight());
                    this.currentNode.nextMidRight().setNextDownRight(this.currentNode.nextDownRight().nextMidRight());
                    this.currentNode = this.currentNode.nextMidRight();
                }

                // set up last connection between upRight corner and last edge between upLeft and upRight corners
                this.currentNode.setNextMidRight(this.currentNode.nextDownRight().nextUpRight());
                this.currentNode.nextDownRight().nextUpRight().setNextMidLeft(this.currentNode);

                // reset current node to centre node
                this.currentNode = this.centreNode;
            }

            // set up row and column
            this.row = this.size;
            this.col = this.size;
        }
    }

    /**
     * @return the size of the HexagonBoard
     */
    public int size() {
        return this.size;
    }

    /**
     * @return the centre node on the board
     */
    public HexagonNode<I> centreNode() {
        return this.centreNode;
    }

    /**
     * @return the item stored in the centre node
     */
    public I centreNodeItem() {
        return this.centreNode.item();
    }

    /**
     * Set the centre node's item equal to x.
     * @param x the item to be placed in the centre node
     */
    public void setCentreNodeItem(I x) {
        this.centreNode.setItem(x);
    }

    /**
     * @return the current node the cursor is on
     */
    public HexagonNode<I> currentNode() {
        return this.currentNode;
    }

    /**
     * @return the item stored in the current node
     */
    public I currentNodeItem() {
        return this.currentNode.item();
    }

    /**
     * Set the current node's item equal to x.
     * @param x item to be placed in the current node
     */
    public void setCurrentNodeItem(I x) {
        this.currentNode.setItem(x);
    }

    /**
     * @return the row index of the current node
     */
    public int row() {
        return this.row;
    }

    /**
     * @return the column index of the current node
     */
    public int col() {
        return this.col;
    }

    /**
     * Set the current node to the centre node.
     */
    public void goCentre() {
        this.currentNode = this.centreNode;
        this.row = this.size;
        this.col = this.size;
    }

    /**
     * Check if the current node has a next node in the specified direction.
     * @return true if the current node has a next node in the specified direction, false otherwise
     */
    public boolean hasUpLeft() {
        return this.currentNode.nextUpLeft() != null;
    }
    public boolean hasUpRight() {
        return this.currentNode.nextUpRight() != null;
    }
    public boolean hasMidLeft() {
        return this.currentNode.nextMidLeft() != null;
    }
    public boolean hasMidRight() {
        return this.currentNode.nextMidRight() != null;
    }
    public boolean hasDownLeft() {
        return this.currentNode.nextDownLeft() != null;
    }
    public boolean hasDownRight() {
        return this.currentNode.nextDownRight() != null;
    }

    /**
     * Set the current node to the next node in the given direction.
     * @postcond the current node is set to the next node if the next node is not null,
     *           row and column change depending on the move
     * @return true if the move was successful, false if there was no node to go to
     */
    public boolean goUpLeft() {
        if (this.hasUpLeft()) {
            this.currentNode = this.currentNode.nextUpLeft();
            if (this.row <= this.size) {
                this.col -= 1;
            } else {}
            this.row -= 1;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean goUpRight() {
        if (this.hasUpRight()) {
            this.currentNode = this.currentNode.nextUpRight();
            if (this.row > this.size) {
                this.col += 1;
            } else {}
            this.row -= 1;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean goMidLeft() {
        if (this.hasMidLeft()) {
            this.currentNode = this.currentNode.nextMidLeft();
            this.col -= 1;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean goMidRight() {
        if (this.hasMidRight()) {
            this.currentNode = this.currentNode.nextMidRight();
            this.col += 1;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean goDownLeft() {
        if (this.hasDownLeft()) {
            this.currentNode = this.currentNode.nextDownLeft();
            if (this.row >= this.size) {
                this.col -= 1;
            } else {}
            this.row += 1;
            return true;
        }
        else {
            return false;
        }
    }
    public boolean goDownRight() {
        if (this.hasDownRight()) {
            this.currentNode = this.currentNode.nextDownRight();
            if (this.row < this.size) {
                this.col += 1;
            }
            this.row += 1;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * @return string representation of the HexagonBoard, the current node is marked with a * instead of an O
     */
    public String toString() {
        String result = "";
        if (size == 0) {
            result += "*\n";
        }
        else {
            for (int i = 0; i <= (2 * this.size); i++) {
                if (i < this.size) {
                    // top half of the board
                    // add spaces before O - O - O
                    for (int s = 0; s < (2 * (this.size - i)); s++) {
                        result += " ";
                    }
                    // add O - O - O
                    for (int j = 0; j <= (this.size + i); j++) {
                        if (i != this.row || j != this.col) {
                            result += "O";
                        }
                        else {
                            result += "*";
                        }
                        if (j < (this.size + i)) {
                            result += " - ";
                        }
                        else {
                            result += "\n";
                        }
                    }
                    // add spaces before / \ / \ / \
                    for (int t = 0; t < (2 * (this.size - i) - 1); t++) {
                        result += " ";
                    }
                    // add / \ / \ / \
                    for (int k = 0; k <= (this.size + i); k++) {
                        result += "/ \\";
                        if (k < (this.size + i)) {
                            result += " ";
                        }
                        else {
                            result += "\n";
                        }
                    }
                }
                else if (i == this.size) {
                    // middle row of the board
                    // add O - O - O
                    for (int j = 0; j <= (this.size + i); j++) {
                        if (i != this.row || j != this.col) {
                            result += "O";
                        }
                        else {
                            result += "*";
                        }
                        if (j < (this.size + i)) {
                            result += " - ";
                        }
                        else {
                            result += "\n";
                        }
                    }
                }
                else {
                    // bottom half of the board
                    // add spaces before \ / \ / \ /
                    for (int t = 0; t < (2 * (i - this.size) - 1); t++) {
                        result += " ";
                    }
                    // add \ / \ / \ /
                    for (int k = 0; k <= (this.size + (2 * this.size - i)); k++) {
                        result += "\\ /";
                        if (k < (this.size + (2 * this.size - i))) {
                            result += " ";
                        }
                        else {
                            result += "\n";
                        }
                    }
                    // add spaces before O - O - O
                    for (int s = 0; s < (2 * (i - this.size)); s++) {
                        result += " ";
                    }
                    // add O - O - O
                    for (int j = 0; j <= (this.size + (2 * this.size - i)); j++) {
                        if (i != this.row || j != this.col) {
                            result += "O";
                        }
                        else {
                            result += "*";
                        }
                        if (j < (this.size + (2 * this.size - i))) {
                            result += " - ";
                        }
                        else {
                            result += "\n";
                        }
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Testing HexagonBoard.java");
        int uErrors = 0; // unintentional errors

        // test HexagonBoard constructor with a board of size -1
        try {
            HexagonBoard<String> invalidBoard = new HexagonBoard<>(-1);
            System.out.println("Error: HexagonBoard() constructor did not throw an exception for a board of size -1");
            uErrors += 1;
        }
        catch (Exception e) {
            System.out.println("HexagonBoard() constructor correctly caught exception for invalid input.");
        }

        // test HexagonBoard constructor with a board of size 0
        try {
            HexagonBoard<String> nothingBoard = new HexagonBoard<>(0);

            // test toString()
            System.out.println(nothingBoard);

            // test size()
            if (nothingBoard.size() != 0) {
                System.out.println("Error: size() should return 0, but returned " + nothingBoard.size());
                uErrors += 1;
            } else {}

            // test to see if centreNode and currentNode are the same
            if (nothingBoard.centreNode() != nothingBoard.currentNode()) {
                System.out.println("Error: centreNode() and currentNode() do not return the same node.");
                uErrors += 1;
            } else {}

            // test if centreNode's item is null
            if (nothingBoard.centreNodeItem() != null) {
                System.out.println("Error: centreNodeItem() should return null, but returned " + nothingBoard.centreNodeItem());
                uErrors += 1;
            }

            // test if currentNode's item is null
            if (nothingBoard.currentNodeItem() != null) {
                System.out.println("Error: currentNodeItem() should return null, but returned " + nothingBoard.currentNodeItem());
                uErrors += 1;
            }

            // test setCentreNodeItem()
            nothingBoard.setCentreNodeItem("Only Node");
            System.out.println(nothingBoard.centreNode());
            if (!nothingBoard.centreNodeItem().equals("Only Node")) {
                System.out.println("Error: setCentreNodeItem() did not properly change node's item.");
                uErrors += 1;
            } else {}
            if (!nothingBoard.currentNodeItem().equals("Only Node")) {
                System.out.println("Error: setCentreNodeItem() did not properly change node's item.");
                uErrors += 1;
            } else {}

            // test setCurrentNodeItem()
            nothingBoard.setCurrentNodeItem("Just one node");
            System.out.println(nothingBoard.currentNode());
            if (!nothingBoard.centreNodeItem().equals("Just one node")) {
                System.out.println("Error: setCurrentNodeItem() did not properly change node's item.");
                uErrors += 1;
            } else {}
            if (!nothingBoard.currentNodeItem().equals("Just one node")) {
                System.out.println("Error: setCurrentNodeItem() did not properly change node's item.");
                uErrors += 1;
            } else {}

            // test row() and col()
            if (nothingBoard.row() != 0) {
                System.out.println("Error: row() should return 0, but returned " + nothingBoard.row());
                uErrors += 1;
            } else {}
            if (nothingBoard.col() != 0) {
                System.out.println("Error: col() should return 0, but returned " + nothingBoard.col());
                uErrors += 1;
            } else {}

            // test hasUpLeft(), hasMidLeft(), and hasDownLeft()
            if (nothingBoard.hasUpLeft()) {
                System.out.println("Error: hasUpLeft() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}
            if (nothingBoard.hasMidLeft()) {
                System.out.println("Error: hasMidLeft() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}
            if (nothingBoard.hasDownLeft()) {
                System.out.println("Error: hasDownLeft() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}

            // test hasUpRight(), hasMidRight(), and hasDownRight()
            if (nothingBoard.hasUpRight()) {
                System.out.println("Error: hasUpRight() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}
            if (nothingBoard.hasMidRight()) {
                System.out.println("Error: hasMidRight() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}
            if (nothingBoard.hasDownRight()) {
                System.out.println("Error: hasDownRight() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}

            // test goUpLeft(), goMidLeft(), and goDownLeft()
            if (nothingBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}
            if (nothingBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}
            if (nothingBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}

            // make sure row and col did not change
            if (nothingBoard.row() != 0) {
                System.out.println("Error: row() should return 0, but returned " + nothingBoard.row());
                uErrors += 1;
            } else {}
            if (nothingBoard.col() != 0) {
                System.out.println("Error: col() should return 0, but returned " + nothingBoard.col());
                uErrors += 1;
            } else {}

            // test goUpRight(), goMidRight(), and goDownRight()
            if (nothingBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}
            if (nothingBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}
            if (nothingBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when there was no node to go to.");
                uErrors += 1;
            } else {}

            // make sure row and col did not change
            if (nothingBoard.row() != 0) {
                System.out.println("Error: row() should return 0, but returned " + nothingBoard.row());
                uErrors += 1;
            } else {}
            if (nothingBoard.col() != 0) {
                System.out.println("Error: col() should return 0, but returned " + nothingBoard.col());
                uErrors += 1;
            } else {}
        }
        catch (Exception e) {
            System.out.println("Error: HexagonBoard() constructor or other HexagonBoard method threw an exception for a board of size 0.");
            System.out.println("Unintentional Error: " + e.getMessage());
            uErrors += 1;
        }

        // test HexagonBoard constructor with a board of size 1
        try {
            HexagonBoard<Integer> numBoard = new HexagonBoard<>(1);

            // test toString()
            System.out.println(numBoard);

            // test size()
            if (numBoard.size() != 1) {
                System.out.println("Error: size() should return 1, but returned " + numBoard.size());
                uErrors += 1;
            } else {}

            // test centreNodeItem(), centreNode(), currentNodeItem(), and currentNode() with everything null
            System.out.println(numBoard.centreNodeItem());
            System.out.println(numBoard.centreNode());
            System.out.println(numBoard.currentNodeItem());
            System.out.println(numBoard.currentNode());

            // change node items and reprint
            numBoard.setCentreNodeItem(1);
            numBoard.centreNode().nextUpLeft().setItem(5);
            numBoard.centreNode().nextUpRight().setItem(7);
            numBoard.centreNode().nextMidLeft().setItem(100);
            numBoard.centreNode().nextMidRight().setItem(256);
            numBoard.centreNode().nextDownLeft().setItem(24);
            numBoard.centreNode().nextDownRight().setItem(29);
            System.out.println(numBoard.centreNode());

            // test hasUpLeft(), hasUpRight(), hasMidLeft(), hasMidRight(), hasDownLeft(), and hasDownRight()
            if (!numBoard.hasUpLeft()) {
                System.out.println("Error: hasUpLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (!numBoard.hasUpRight()) {
                System.out.println("Error: hasUpRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (!numBoard.hasMidLeft()) {
                System.out.println("Error: hasMidLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (!numBoard.hasMidRight()) {
                System.out.println("Error: hasMidRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (!numBoard.hasDownLeft()) {
                System.out.println("Error: hasDownLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (!numBoard.hasDownRight()) {
                System.out.println("Error: hasDownRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}

            // test goUpLeft(), goUpRight(), goMidLeft(), goMidRight(), goDownLeft(), and goDownRight(),
            // move around the board, and take every path both ways while checking the nodes along the way

            // test goUpLeft()
            if (!numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 0) {
                System.out.println("Error: row() should have returned 0, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 0) {
                System.out.println("Error: col() should have returned 0, but returned " + numBoard.col());
                uErrors += 1;
            } else {}
            // try moving back the way you came
            if (!numBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 1) {
                System.out.println("Error: row() should have returned 1, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 1) {
                System.out.println("Error: col() should have returned 1, but returned " + numBoard.col());
                uErrors += 1;
            } else {}

            // test goUpRight()
            if (!numBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 0) {
                System.out.println("Error: row() should have returned 0, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 1) {
                System.out.println("Error: col() should have returned 1, but returned " + numBoard.col());
                uErrors += 1;
            } else {}
            // try moving back the way you came
            if (!numBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 1) {
                System.out.println("Error: row() should have returned 1, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 1) {
                System.out.println("Error: col() should have returned 1, but returned " + numBoard.col());
                uErrors += 1;
            } else {}

            // test goMidLeft()
            if (!numBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 1) {
                System.out.println("Error: row() should have returned 1, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 0) {
                System.out.println("Error: col() should have returned 0, but returned " + numBoard.col());
                uErrors += 1;
            } else {}
            // try moving back the way you came
            if (!numBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 1) {
                System.out.println("Error: row() should have returned 1, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 1) {
                System.out.println("Error: col() should have returned 1, but returned " + numBoard.col());
                uErrors += 1;
            } else {}

            // test goMidRight()
            if (!numBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 1) {
                System.out.println("Error: row() should have returned 1, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 2) {
                System.out.println("Error: col() should have returned 2, but returned " + numBoard.col());
                uErrors += 1;
            } else {}
            // try moving back the way you came
            if (!numBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 1) {
                System.out.println("Error: row() should have returned 1, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 1) {
                System.out.println("Error: col() should have returned 1, but returned " + numBoard.col());
                uErrors += 1;
            } else {}

            // test goDownLeft()
            if (!numBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 2) {
                System.out.println("Error: row() should have returned 2, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 0) {
                System.out.println("Error: col() should have returned 0, but returned " + numBoard.col());
                uErrors += 1;
            } else {}
            // try moving back the way you came
            if (!numBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 1) {
                System.out.println("Error: row() should have returned 1, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 1) {
                System.out.println("Error: col() should have returned 1, but returned " + numBoard.col());
                uErrors += 1;
            } else {}

            // test goDownRight()
            if (!numBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 2) {
                System.out.println("Error: row() should have returned 2, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 1) {
                System.out.println("Error: col() should have returned 1, but returned " + numBoard.col());
                uErrors += 1;
            } else {}
            // try moving back the way you came
            if (!numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);
            System.out.println(numBoard.currentNode());
            if (numBoard.row() != 1) {
                System.out.println("Error: row() should have returned 1, but returned " + numBoard.row());
                uErrors += 1;
            } else{}
            if (numBoard.col() != 1) {
                System.out.println("Error: col() should have returned 1, but returned " + numBoard.col());
                uErrors += 1;
            } else {}

            // move the cursor around the edge clockwise, and try going out of bounds
            if (!numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (numBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (numBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (numBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (numBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (numBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            if (numBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            if (numBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            // move the cursor around the edge counterclockwise
            if (!numBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            if (!numBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
                uErrors += 1;
            } else {}
            System.out.println(numBoard);

            // test goCentre()
            numBoard.goCentre();
            if (numBoard.currentNode() != numBoard.centreNode()) {
                System.out.println("Error: goCentre() did not change the current node to the centre node");
                uErrors += 1;
            }
            System.out.println(numBoard);
        }
        catch (Exception e) {
            System.out.println("Error: HexagonBoard() constructor or other HexagonBoard method threw an exception for a board of size 1.");
            System.out.println("Unintentional Error: " + e.getMessage());
            uErrors += 1;
        }

        // test HexagonBoard constructor with a board of size 2
        try {
            HexagonBoard<Double> doubleBoard = new HexagonBoard<>(2);

            // test toString()
            System.out.println(doubleBoard);

            // test size()
            if (doubleBoard.size() != 2) {
                System.out.println("Error: size() should return 2, but returned " + doubleBoard.size());
                uErrors += 1;
            } else {}

            // test all connections between nodes,
            // NOTE 1: I print the board after every move, and toString() uses row() and col()
            // to determine where to place the cursor (*) on the board, so if the board printed placed
            // the cursor in the correct spot, then row() and col() must have returned the correct numbers also
            // NOTE 2: the go() methods use the has() methods, so if the go() methods return the correct value,
            // then the has() methods must have returned the correct value also
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // test horizontal connections in all rows
            // row 0
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // row 1
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // row 2
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // row 3
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // row 4
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // test \ connections
            // 0
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // 1
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // 2
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // 3
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // 4
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // test / connections
            // 0
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // 1
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // 2
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // 3
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // 4
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);
            if (!doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned false when it should have returned true");
            } else {}
            System.out.println(doubleBoard);

            // try going out of bounds
            if (doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
            } else {}
            doubleBoard.goMidRight();
            System.out.println(doubleBoard);

            if (doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
            } else {}
            doubleBoard.goMidRight();
            System.out.println(doubleBoard);

            if (doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when it should have returned false");
            } else {}
            doubleBoard.goDownRight();
            System.out.println(doubleBoard);

            if (doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when it should have returned false");
            } else {}
            doubleBoard.goDownRight();
            System.out.println(doubleBoard);

            if (doubleBoard.goUpRight()) {
                System.out.println("Error: goUpRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when it should have returned false");
            } else {}
            doubleBoard.goDownLeft();
            System.out.println(doubleBoard);

            if (doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when it should have returned false");
            } else {}
            doubleBoard.goDownLeft();
            System.out.println(doubleBoard);

            if (doubleBoard.goMidRight()) {
                System.out.println("Error: goMidRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when it should have returned false");
            } else {}
            doubleBoard.goMidLeft();
            System.out.println(doubleBoard);

            if (doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when it should have returned false");
            } else {}
            doubleBoard.goMidLeft();
            System.out.println(doubleBoard);

            if (doubleBoard.goDownRight()) {
                System.out.println("Error: goDownRight() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
            } else {}
            doubleBoard.goUpLeft();
            System.out.println(doubleBoard);

            if (doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
            } else {}
            doubleBoard.goUpLeft();
            System.out.println(doubleBoard);

            if (doubleBoard.goDownLeft()) {
                System.out.println("Error: goDownLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
            } else {}
            doubleBoard.goUpRight();
            System.out.println(doubleBoard);

            if (doubleBoard.goMidLeft()) {
                System.out.println("Error: goMidLeft() returned true when it should have returned false");
            } else {}
            if (doubleBoard.goUpLeft()) {
                System.out.println("Error: goUpLeft() returned true when it should have returned false");
            } else {}
            doubleBoard.goUpRight();
            System.out.println(doubleBoard);

            // test goCentre()
            doubleBoard.goCentre();
            if (doubleBoard.currentNode() != doubleBoard.centreNode()) {
                System.out.println("Error: goCentre() did not set the current node to the centre node");
                uErrors += 1;
            } else {}
            System.out.println(doubleBoard);
        }
        catch (Exception e) {
            System.out.println("Error: HexagonBoard() constructor or other HexagonBoard method threw an exception for a board of size 2.");
            System.out.println("Unintentional Error: " + e.getMessage());
            uErrors += 1;
        }

        // test HexagonBoard constructor with a board of size 5
        try {
            HexagonBoard<Integer> bigBoard = new HexagonBoard<>(5);

            // test toString()
            System.out.println(bigBoard);

            // test size()
            if (bigBoard.size() != 5) {
                System.out.println("Error: size() should return 5, but returned " + bigBoard.size());
                uErrors += 1;
            } else {}
        }
        catch (Exception e) {
            System.out.println("Error: HexagonBoard() constructor or other HexagonBoard method threw an exception for a board of size 5.");
            System.out.println("Unintentional Error: " + e.getMessage());
            uErrors += 1;
        }

        System.out.println("Testing complete with " + uErrors + " unintentional errors.");
    }
}
