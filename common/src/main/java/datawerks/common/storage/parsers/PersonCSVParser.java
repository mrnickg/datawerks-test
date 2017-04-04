package datawerks.common.storage.parsers;

import datawerks.common.storage.parsers.objects.CSVPerson;

public class PersonCSVParser extends AbstractCSVParser {

	public PersonCSVParser() {
		object = new CSVPerson();
	}
}
