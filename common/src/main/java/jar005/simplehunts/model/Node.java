package jar005.simplehunts.model;

public class Node {
    private String attributeName;
    private Node trueBranchNode;
    private Node falseBranchNode;
    private boolean leaf;
    private boolean value;

    public Node() {}

    public Node(String attributeName) {
        this();
        this.attributeName = attributeName;
    }

    public boolean isLeaf() {
        return leaf;
    }

    public boolean getValue() {
        return value;
    }

    public void makeLeaf(boolean leafValue) {
        this.leaf = true;
        this.value = leafValue;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Node getTrueBranchNode() {
        return trueBranchNode;
    }

    public void setTrueBranch(Node trueEdgeNode) {
        this.trueBranchNode = trueEdgeNode;
    }

    public Node getFalseBranchNode() {
        return falseBranchNode;
    }

    public void setFalseBranch(Node falseEdgeNode) {
        this.falseBranchNode = falseEdgeNode;
    }
}
