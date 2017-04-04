package datawerks.common.storage.parsers.objects;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

import datawerks.common.storage.parsers.processors.UTCDateCellProcessor;
import datawerks.common.storage.parsers.processors.NameCellProcessor;

public class CSVPerson implements ParseableObject {

	final static String[] names = new String[] {
			"id",
			"name",
			"time_of_start",
			"Obs",
			"custom1",
			"custom2"
	};
	
	final static CellProcessor[] processors = new CellProcessor[] { 
            new UniqueHashCode(new ParseInt()), // ID (must be unique)
            new NotNull(new NameCellProcessor()), // Name
            new NotNull(new UTCDateCellProcessor()), // Start Time
            new Optional(), // Observations
            new Optional(),
            new Optional()
    };
	
	private int id;
	private String name;
	private Date time_of_start;
	private String Obs;
	private String custom1;
	private String custom2;
	
	public CSVPerson() {}

	@Override
	public CellProcessor[] getCellProcessors() {
		return processors;
	}

	@Override
	public String[] getColNames() {
		return names;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getTime_of_start() {
		return time_of_start;
	}
	
	public void setTime_of_start(Date time_of_start) {
		this.time_of_start = time_of_start;
	}
	
	public String getObs() {
		return Obs;
	}
	
	public void setObs( String Obs) {
		this.Obs = Obs;
	}
	
	public String getCustom1() {
		return custom1;
	}
	
	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}
	
	public String getCustom2() {
		return custom2;
	}
	
	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}
	
	public String getDateFormatted() {
		return time_of_start.toString();
	}
}
