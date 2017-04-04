package datawerks.common.storage.parsers.processors;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.supercsv.cellprocessor.CellProcessorAdaptor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.cellprocessor.ift.DateCellProcessor;
import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.util.CsvContext;

public class UTCDateCellProcessor extends CellProcessorAdaptor implements DateCellProcessor {

	SimpleDateFormat format;
	DateTimeFormatter formatter;
	
	public UTCDateCellProcessor() {
		super();
		init();
	}
	
	public UTCDateCellProcessor(CellProcessor next) {
		super(next);
		init();
	}
	
	private void init() {
		formatter = DateTimeFormat.forPattern("dd-MM-YY HH:mm:ss");
	}
	
	@Override
	public Date execute(Object value, CsvContext context) {
		String s = (String)value;
		Date finalDate;
		try {
			
			DateTime dt = formatter.withZone(DateTimeZone.forID("Europe/Berlin")).parseDateTime(s);
			DateTime dtUtc = dt.withZone(DateTimeZone.UTC);
			LocalDateTime ldt = dtUtc.toLocalDateTime();
			finalDate = ldt.toDate();

		}
		catch (Exception e) {
			throw new SuperCsvCellProcessorException("Cannot parse date for "+value, context, this);
		}
		
		return finalDate;
		
	}

}
