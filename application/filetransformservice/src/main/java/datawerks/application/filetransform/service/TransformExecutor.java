package main.java.datawerks.application.filetransform.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import datawerks.common.db.DatabaseInterface;
import datawerks.common.db.PersonRepository;
import datawerks.common.db.objects.Person;
import datawerks.common.db.objects.PersonFactory;
import datawerks.common.logging.Log;
import datawerks.common.storage.parsers.PersonCSVParser;
import datawerks.common.storage.parsers.objects.CSVPerson;
import datawerks.common.storage.parsers.objects.ParseableObject;

public class TransformExecutor implements Runnable {

	private String message;
	private DatabaseInterface dbInterface;
	private PersonRepository repo;
	
	public TransformExecutor(String message, DatabaseInterface dbInterface, PersonRepository repo) {
		this.message = message;
		this.dbInterface = dbInterface;
		this.repo = repo;
	}
	
	@Override
	public void run() {
		Log.info("Transform Executor starting processing", this);
		PersonCSVParser parser = new PersonCSVParser();
		List<ParseableObject> people = new ArrayList<ParseableObject>();
		try {
			URI u = new URI(message);	
			people = parser.parse(u.getPath());
			if (parser.hasErrors()) {
				Log.error("Failed to parse the following objects:", this);
				for (String s : parser.getErrors()) {
					Log.debug(s, this);
				}
			}

		}
		catch (IOException e) {
			Log.error("Problem loading file at location: "+ message, this);
			e.printStackTrace();
		}
		catch (URISyntaxException e) {
			Log.error("Problem loading file at location: "+ message, this);
			e.printStackTrace();
		}
		
		Log.info("Transform Executor finished parsing input file", this);
		Log.info("Transform Executor persisting "+people.size()+" results to database", this);
		
		for(ParseableObject p : people) {
			Person person = PersonFactory.getPerson((CSVPerson)p);
			dbInterface.set(person, repo);
		}
		
		
		Log.info("Transfrom Executor finishing", this);
	}

}
