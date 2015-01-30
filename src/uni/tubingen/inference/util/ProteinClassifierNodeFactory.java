package uni.tubingen.inference.util;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "ProteinClassifier" Node.
 * Simple knime node to classifier proteins taking into account target_decoy analysis
 *
 * @author enrique
 */
public class ProteinClassifierNodeFactory 
        extends NodeFactory<ProteinClassifierNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ProteinClassifierNodeModel createNodeModel() {
        return new ProteinClassifierNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<ProteinClassifierNodeModel> createNodeView(final int viewIndex,
            final ProteinClassifierNodeModel nodeModel) {
        return new ProteinClassifierNodeView(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new ProteinClassifierNodeDialog();
    }

}

