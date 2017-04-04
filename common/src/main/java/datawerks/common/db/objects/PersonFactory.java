package datawerks.common.db.objects;

import datawerks.common.storage.parsers.objects.CSVPerson;

public class PersonFactory {
	
	public static Person getPerson(CSVPerson p) {
		Person person = new Person();
		person.setId(new Integer(p.getId()));
		person.setName(p.getName());
		person.setTime_of_start(p.getTime_of_start());
		
		return person;
	}
}
