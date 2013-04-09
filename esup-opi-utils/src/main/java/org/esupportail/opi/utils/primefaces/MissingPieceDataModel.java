package org.esupportail.opi.utils.primefaces;

import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.primefaces.model.SelectableDataModel;

public class MissingPieceDataModel extends ListDataModel<MissingPiece> implements SelectableDataModel<MissingPiece> {   
	  
	    public MissingPieceDataModel() {  
	    }  
	  
	    public MissingPieceDataModel(List<MissingPiece> data) {  
	        super(data);  
	    }  
	      
	    @Override  
	    public MissingPiece getRowData(String rowKey) {  
	          
	        List<MissingPiece> missingPieces = (List<MissingPiece>) getWrappedData();  
	          
	        for(MissingPiece missingPiece : missingPieces) {  
	            if(missingPiece.getId().equals(rowKey))  
	                return missingPiece;  
	        }  
	          
	        return null;  
	    }  
	  
	    @Override  
	    public Object getRowKey(MissingPiece missingPiece) {  
	        return missingPiece.getId();  
	    }  
 
}
