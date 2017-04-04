package datawerks.common.storage.parsers.objects;

import org.supercsv.cellprocessor.ift.CellProcessor;

public interface ParseableObject {

	public CellProcessor[] getCellProcessors();
	
	public String[] getColNames();
	
}
