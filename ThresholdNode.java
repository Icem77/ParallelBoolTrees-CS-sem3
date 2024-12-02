package cp2024.solution;

public abstract class ThresholdNode extends SimpleNode {
    protected Integer threshold;

    public ThresholdNode(Node parentNode, Integer nargs, Integer threshold) {
        super(parentNode, nargs);
        this.threshold = threshold;
    }
}
