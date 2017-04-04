package datawerks.common.storage.parsers;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.supercsv.exception.SuperCsvCellProcessorException;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import datawerks.common.storage.parsers.objects.ParseableObject;

public abstract class AbstractCSVParser implements FileParser<ParseableObject> {

	protected ParseableObject object;
	protected ArrayList<String> errors;
	
	public List<ParseableObject> parse(String url) throws IOException {
		ICsvBeanReader beanReader = null;
		List<ParseableObject> objects = new ArrayList<ParseableObject>();
		errors = new ArrayList<String>();
		
		try {
			beanReader = new CsvBeanReader(new FileReader(url), CsvPreference.STANDARD_PREFERENCE);

			while ( readNext(beanReader, objects, errors) ) {}
			
		} 
		finally {
			if (beanReader != null) {
				beanReader.close();
			}
		}
		
		return objects;
	}

	protected boolean readNext(ICsvBeanReader reader, List<ParseableObject> objects, ArrayList<String> errors) throws IOException {
		boolean proceed = true;
		try {
			ParseableObject o = reader.read(object.getClass(), object.getColNames(), object.getCellProcessors());
			if (o != null) {
				objects.add(o);
			}
			else {
				proceed = false;
			}
		}
		catch (SuperCsvCellProcessorException ex){
		}
		catch (IOException e) {
			throw e;
		}
		
		return proceed; 
	}
	
	public boolean hasErrors() {
		return errors.size() > 0;
	}
	
	public ArrayList<String> getErrors() {
		return errors;
	}
}
