package datawerks.common.storage.parsers.processors;

import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

public class NameCellProcessor extends CellProcessorAdaptor {

	public NameCellProcessor() {
		super();
	}
	
	public NameCellProcessor(CellProcessor next) {
		super(next);
	}
	
	@Override
	public String execute(Object value, CsvContext context) {
		String s = (String)value;
		return s.toLowerCase();
	}

}
