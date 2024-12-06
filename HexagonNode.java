/**
 * Code Written by Jackson L. Davis
 *
 * A HexagonNode is a node that is part of a HexagonBoard.
 * It is connected to six other HexagonNodes.
 */
public class HexagonNode<I> {

    /** The contents of the node. */
    private I item;

    /** The next nodes. */
    private HexagonNode<I> nextUpLeft;
    private HexagonNode<I> nextUpRight;
    private HexagonNode<I> nextMidLeft;
    private HexagonNode<I> nextMidRight;
    private HexagonNode<I> nextDownLeft;
    private HexagonNode<I> nextDownRight;

    /**
     * Constructor method for a new HexagonNode.
     * @param x item to be placed in the node
     */
    public HexagonNode(I x) {
        setItem(x);
    }

    /**
     * Constructor method without the parameter.
     */
    public HexagonNode() {
        setItem(null);
    }

    /**
     * @return the contents of the node
     */
    public I item() {
        return this.item;
    }

    /**
     * Set item equal to x
     * @param x the item to replace the node's current item
     */
    public void setItem(I x) {
        this.item = x;
    }

    /**
     * Getter methods for the next nodes.
     * @return the next node in the given direction
     */
    public HexagonNode<I> nextUpLeft() {
        return this.nextUpLeft;
    }
    public HexagonNode<I> nextUpRight() {
        return this.nextUpRight;
    }
    public HexagonNode<I> nextMidLeft() {
        return this.nextMidLeft;
    }
    public HexagonNode<I> nextMidRight() {
        return this.nextMidRight;
    }
    public HexagonNode<I> nextDownLeft() {
        return this.nextDownLeft;
    }
    public HexagonNode<I> nextDownRight() {
        return this.nextDownRight;
    }

    /**
     * Set next node equal to x, note that x's next nodes DO NOT CHANGE
     * @param x the node to replace the node in the given direction with
     */
    public void setNextUpLeft(HexagonNode<I> x) {
        this.nextUpLeft = x;
    }
    public void setNextUpRight(HexagonNode<I> x) {
        this.nextUpRight = x;
    }
    public void setNextMidLeft(HexagonNode<I> x) {
        this.nextMidLeft = x;
    }
    public void setNextMidRight(HexagonNode<I> x) {
        this.nextMidRight = x;
    }
    public void setNextDownLeft(HexagonNode<I> x) {
        this.nextDownLeft = x;
    }
    public void setNextDownRight(HexagonNode<I> x) {
        this.nextDownRight = x;
    }

    /**
     * @return string representation of the node and its next nodes
     */
    public String toString() {
        String result = "";
        if (this.item != null) {
            result = this.item.toString();
        }
        else {
            result = "(no item assigned to this node)";
        }
        result += "\n";
        if (this.nextUpLeft != null) {
            if (this.nextUpLeft.item() != null) {
                result += "Next Up Left: " + this.nextUpLeft.item().toString() + "\n";
            }
            else {
                result += "Next Up Left: (no item assigned to this node)\n";
            }
        }
        else {
            // do not print a direction that has null instead of a node
        }
        if (this.nextUpRight != null) {
            if (this.nextUpRight.item() != null) {
                result += "Next Up Right: " + this.nextUpRight.item().toString() + "\n";
            }
            else {
                result += "Next Up Right: (no item assigned to this node)\n";
            }
        } else {}
        if (this.nextMidLeft != null) {
            if (this.nextMidLeft.item() != null) {
                result += "Next Mid Left: " + this.nextMidLeft.item().toString() + "\n";
            }
            else {
                result += "Next Mid Left: (no item assigned to this node)\n";
            }
        } else {}
        if (this.nextMidRight != null) {
            if (this.nextMidRight.item() != null) {
                result += "Next Mid Right: " + this.nextMidRight.item().toString() + "\n";
            }
            else {
                result += "Next Mid Right: (no item assigned to this node)\n";
            }
        } else {}
        if (this.nextDownLeft != null) {
            if (this.nextDownLeft.item() != null) {
                result += "Next Down Left: " + this.nextDownLeft.item().toString() + "\n";
            }
            else {
                result += "Next Down Left: (no item assigned to this node)\n";
            }
        } else {}
        if (this.nextDownRight != null) {
            if (this.nextDownRight.item() != null) {
                result += "Next Down Right: " + this.nextDownRight.item().toString() + "\n";
            }
            else {
                result += "Next Down Right: (no item assigned to this node)\n";
            }
        } else {}
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Testing HexagonNode.java");
        int uErrors = 0; // unintentional errors

        // test HexagonNode() constructor
        try {
            HexagonNode<String> centre = new HexagonNode<>("First Node!");

            // test toString()
            System.out.println(centre);

            // test item()
            if (!centre.item().equals("First Node!")) {
                System.out.println("Error: item() returned " + centre.item() + " instead of \"First Node!\"");
                uErrors += 1;
            } else {}

            // test setItem()
            centre.setItem("Centre");
            System.out.println(centre);
            if (!centre.item().equals("Centre")) {
                System.out.println("Error: setItem() did not correctly set node's item," +
                        "item() returns " + centre.item() + " instead of \"Centre\"");
                uErrors += 1;
            } else {}

            // test next node methods
            if (centre.nextUpLeft() != null) {
                System.out.println("Error: nextUpLeft() should return null, but returns " + centre.nextUpLeft());
                uErrors += 1;
            } else {}
            if (centre.nextUpRight() != null) {
                System.out.println("Error: nextUpRight() should return null, but returns " + centre.nextUpRight());
                uErrors += 1;
            } else {}
            if (centre.nextMidLeft() != null) {
                System.out.println("Error: nextMidLeft() should return null, but returns " + centre.nextMidLeft());
                uErrors += 1;
            } else {}
            if (centre.nextMidRight() != null) {
                System.out.println("Error: nextMidRight() should return null, but returns " + centre.nextMidRight());
                uErrors += 1;
            } else {}
            if (centre.nextDownLeft() != null) {
                System.out.println("Error: nextDownLeft() should return null, but returns " + centre.nextDownLeft());
                uErrors += 1;
            } else {}
            if (centre.nextDownRight() != null) {
                System.out.println("Error: nextDownRight() should return null, but returns " + centre.nextDownRight());
                uErrors += 1;
            } else {}

            // test set next node methods (I have to make more nodes first)
            HexagonNode<String> upLeft = new HexagonNode<>("Java");
            HexagonNode<String> upRight = new HexagonNode<>("HTML");
            HexagonNode<String> midLeft = new HexagonNode<>("Python");
            HexagonNode<String> midRight = new HexagonNode<>("CSS");
            HexagonNode<String> downLeft = new HexagonNode<>("C#");
            HexagonNode<String> downRight = new HexagonNode<>("JavaScript");

            centre.setNextUpLeft(upLeft);
            System.out.println(centre);
            centre.setNextUpRight(upRight);
            System.out.println(centre);
            centre.setNextMidLeft(midLeft);
            System.out.println(centre);
            centre.setNextMidRight(midRight);
            System.out.println(centre);
            centre.setNextDownLeft(downLeft);
            System.out.println(centre);
            centre.setNextDownRight(downRight);
            System.out.println(centre);

            if (centre.nextUpLeft() != upLeft) {
                System.out.println("Error: setNextUpLeft() did not set up next node correctly, currently returns "
                        + centre.nextUpLeft());
                uErrors += 1;
            } else {}
            if (centre.nextUpRight() != upRight) {
                System.out.println("Error: setNextUpRight() did not set up next node correctly, currently returns "
                        + centre.nextUpRight());
                uErrors += 1;
            } else {}
            if (centre.nextMidLeft() != midLeft) {
                System.out.println("Error: setNextMidLeft() did not set up next node correctly, currently returns "
                        + centre.nextMidLeft());
                uErrors += 1;
            } else {}
            if (centre.nextMidRight() != midRight) {
                System.out.println("Error: setNextMidRight() did not set up next node correctly, currently "
                        + centre.nextMidRight());
                uErrors += 1;
            } else {}
            if (centre.nextDownLeft() != downLeft) {
                System.out.println("Error: setNextDownLeft() did not set up next node correctly, currently "
                        + centre.nextDownLeft());
                uErrors += 1;
            } else {}
            if (centre.nextDownRight() != downRight) {
                System.out.println("Error: setNextDownRight() did not set up next node correctly, currently "
                        + centre.nextDownRight());
                uErrors += 1;
            } else {}

            // set up more connections
            upLeft.setNextMidRight(upRight);
            upLeft.setNextDownLeft(midLeft);
            upLeft.setNextDownRight(centre);

            upRight.setNextMidLeft(upLeft);
            upRight.setNextDownLeft(centre);
            upRight.setNextDownRight(midRight);

            midLeft.setNextUpRight(upLeft);
            midLeft.setNextMidRight(centre);
            midLeft.setNextDownRight(downLeft);

            midRight.setNextUpLeft(upRight);
            midRight.setNextMidLeft(centre);
            midRight.setNextDownLeft(downRight);

            downLeft.setNextUpLeft(midLeft);
            downLeft.setNextUpRight(centre);
            downLeft.setNextMidRight(downRight);

            downRight.setNextUpLeft(centre);
            downRight.setNextUpRight(midRight);
            downRight.setNextMidLeft(downLeft);

            System.out.println(upLeft);
            System.out.println(upRight);
            System.out.println(midLeft);
            System.out.println(midRight);
            System.out.println(downLeft);
            System.out.println(downRight);
        }
        catch (Exception e) {
            System.out.println("Error: HexagonNode() constructor or other HexagonNode method threw an exception.");
            System.out.println("Unintentional Error: " + e.getMessage());
            uErrors += 1;
        }

        try {
            HexagonNode<Integer> nullNode = new HexagonNode<>();
            System.out.println(nullNode);
        }
        catch (Exception e) {
            System.out.println("Error: HexagonNode() constructor or other HexagonNode method threw an exception.");
            System.out.println("Unintentional Error: " + e.getMessage());
            uErrors += 1;
        }

        System.out.println("Testing complete with " + uErrors + " unintentional errors.");
    }
}
