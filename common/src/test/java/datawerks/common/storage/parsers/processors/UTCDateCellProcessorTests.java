package datawerks.common.storage.parsers.processors;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;


public class UTCDateCellProcessorTests {

	private UTCDateCellProcessor processor = new UTCDateCellProcessor();
	
	@Test
	public void shouldConvertECTtoUTC() throws Exception {
		String s1 = "12-06-1980 12:00:12";
		String s2 = "31-12-9999 12:00:12";
		String s3 = "12-6-99 12:00:12";
		String s4 = "12-6-89 12:00:12";
		
		Date d1 = processor.execute(s1, null);
		Date d2 = processor.execute(s2, null);
		Date d3 = processor.execute(s3, null);
		Date d4 = processor.execute(s4, null);
		
		Calendar c1 = Calendar.getInstance(TimeZone.getTimeZone("CET"));
		c1.setTime(d1);
		assertEquals(10, c1.get(Calendar.HOUR_OF_DAY));
		assertEquals(Calendar.JUNE, c1.get(Calendar.MONTH));
		
		c1.setTime(d2);
		assertEquals(9999, c1.get(Calendar.YEAR));
		assertEquals(11, c1.get(Calendar.HOUR_OF_DAY));
		
		c1.setTime(d3);
		assertEquals(1999, c1.get(Calendar.YEAR));
		assertEquals(10, c1.get(Calendar.HOUR_OF_DAY));
		
		c1.setTime(d4);
		assertEquals(1989, c1.get(Calendar.YEAR));
		assertEquals(10, c1.get(Calendar.HOUR_OF_DAY));
		
	}
}
