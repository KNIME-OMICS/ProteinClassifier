package uni.tubingen.inference.util;

import java.io.File;
import java.io.IOException;

import org.knime.base.data.append.column.AppendedColumnRow;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.RowIterator;
import org.knime.core.data.def.BooleanCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.defaultnodesettings.SettingsModelString;

/**
 * This is the model implementation of ProteinClassifier.
 * Simple knime node to classifier proteins taking into account target_decoy analysis
 *
 * @author enrique
 */
public class ProteinClassifierNodeModel extends NodeModel {
    
   
	   static String CFGKEY_PROTEIN             = "protein_list";
	   static String CFGKEY_PROBABILITIES  = "probability_list";
	   static String CFGKEY_LABEL_DECOY   = "decoy_label";
	
	    //fields to link execute variable with input variable...
		
		private final SettingsModelString protein_column   = new SettingsModelString(CFGKEY_PROTEIN, "Protein");
		private final SettingsModelString probability_column   = new SettingsModelString(CFGKEY_PROBABILITIES, "Probabilities");
		private final SettingsModelString decoy_label   = new SettingsModelString(CFGKEY_LABEL_DECOY, "Decoy_Label");
		
		static BufferedDataContainer container = null;


	/**
     * Constructor for the node model.
     */
    protected ProteinClassifierNodeModel() {
    
        // TODO: Specify the amount of input and output ports needed.
        super(1, 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception {

    	    
    	    
    	    int proba_idx  = inData[0].getDataTableSpec().findColumnIndex(probability_column.getStringValue());
        	int accsn_idx   = inData[0].getDataTableSpec().findColumnIndex(protein_column.getStringValue());
        	
        	if (accsn_idx < 0 || proba_idx  < 0 || proba_idx  == accsn_idx) {
        		throw new Exception("Illegal columns: "+ probability_column+" "+ protein_column+", re-configure the node!");
        	}
        	
        	DataTableSpec newSpec = new DataTableSpec(inData[0].getDataTableSpec(), make_output_spec());
        	BufferedDataContainer container = exec.createDataContainer(newSpec);
        	
        	RowIterator it = inData[0].iterator();
            
    	     RowIterator row_it = inData[0].iterator();
    	          
    	          while (row_it.hasNext()) {
    	        	 DataRow current_row = row_it.next();
    		    		String   accsn = ((StringCell) current_row .getCell(accsn_idx)).getStringValue();
    		    		boolean is_decoy = accsn.contains(decoy_label.getStringValue());
    		    		AppendedColumnRow new_r = new AppendedColumnRow(current_row, BooleanCell.get(! is_decoy));
    		    		container.addRowToTable(new_r);		    		
    	          }
    	
    	          container.close();
    	      return new BufferedDataTable[] { container.getTable() };
    }
          
         
        private DataTableSpec make_output_spec() {
    	
    	               DataColumnSpec cols[] = new DataColumnSpec[1];
    	               cols[0] = new DataColumnSpecCreator("Target", BooleanCell.TYPE).createSpec();
    	               
		        return new DataTableSpec(cols);
	       }
    
         

    /**
     * {@inheritDoc}
     */
    @Override
    protected void reset() {
        // TODO: generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {

        // TODO: generated method stub
    	 return new DataTableSpec[]{new DataTableSpec(inSpecs[0], make_output_spec())};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
    	probability_column.saveSettingsTo(settings);
        protein_column.saveSettingsTo(settings);
        decoy_label.saveSettingsTo(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	probability_column.loadSettingsFrom(settings);
        protein_column.loadSettingsFrom(settings);
        decoy_label.loadSettingsFrom(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    	probability_column.validateSettings(settings);
        protein_column.validateSettings(settings);
        decoy_label.validateSettings(settings);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        // TODO: generated method stub
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        // TODO: generated method stub
    }

}

