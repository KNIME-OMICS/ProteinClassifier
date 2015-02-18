package uni.tubingen.inference.proteinclassifier;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "ProteinClassifier" Node.
 * Simple knime node to classifier proteins taking into account target_decoy analysis
 *
 * @author enrique
 */
public class ProteinClassifierNodeView extends NodeView<ProteinClassifierNodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link ProteinClassifierNodeModel})
     */
    protected ProteinClassifierNodeView(final ProteinClassifierNodeModel nodeModel) {
        super(nodeModel);
        // TODO: generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {
    	ProteinClassifierNodeModel nodeModel = 
                getNodeModel();
            assert nodeModel != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {
        // TODO: generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {
        // TODO: generated method stub
    }

}

