package uni.tubingen.inference.util;

import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.StringValue;
import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentColumnNameSelection;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.DialogComponentString;
import org.knime.core.node.defaultnodesettings.DialogComponentStringSelection;
import org.knime.core.node.defaultnodesettings.SettingsModelDouble;
import org.knime.core.node.defaultnodesettings.SettingsModelDoubleBounded;
import org.knime.core.node.defaultnodesettings.SettingsModelString;
import org.knime.core.node.util.ColumnFilter;


/**
 * <code>NodeDialog</code> for the "ProteinClassifier" Node.
 * Simple knime node to classifier proteins taking into account target_decoy analysis
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author enrique
 */
public class ProteinClassifierNodeDialog extends DefaultNodeSettingsPane {

    /**
     * New pane for configuring the ProteinClassifier node.
     */
    protected ProteinClassifierNodeDialog() {
 	      super ();
	        
	        //fields to match with coming table...
	     
          final SettingsModelString accsn_protein    = new SettingsModelString(ProteinClassifierNodeModel.CFGKEY_PROTEIN, "Protein");
          final SettingsModelString probabilities    = new SettingsModelString(ProteinClassifierNodeModel.CFGKEY_PROBABILITIES, "Probabilities");
          final SettingsModelString decoy_label   = new SettingsModelString(ProteinClassifierNodeModel.CFGKEY_LABEL_DECOY, "Decoy_Label");
         
          final String[] items = new String[] {
          		"REVERSED",
          		"_rev",
          		"decoy",
          		"_ram"
          	};

          addDialogComponent(new DialogComponentColumnNameSelection(accsn_protein, "Proteins Column", 0, true, StringValue.class));
          addDialogComponent(new DialogComponentColumnNameSelection(probabilities, "Probabilities Column", 0, true, 
          		           
        		            new ColumnFilter() {

  		                	@Override
  							public boolean includeColumn(DataColumnSpec colSpec) {
  								
  		                		if (colSpec.getType().isCollectionType() && colSpec.getType().getCollectionElementType().isCompatible(StringValue.class))
  									return true;
  								
  								if (colSpec.getType().isCompatible(StringValue.class)) 
  									return true;
  								
  								return false;
  							}

  							@Override
  							public String allFilteredMsg() {
  								return "No suitable columns (string or List/Set column) to select!";
  							}
          			
          		}));
          
           addDialogComponent(new DialogComponentStringSelection(new SettingsModelString(ProteinClassifierNodeModel.CFGKEY_LABEL_DECOY, items[0]), "Decoy_Label", items));
    }
}

