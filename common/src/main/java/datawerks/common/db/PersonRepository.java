package datawerks.common.db;

import org.springframework.data.repository.CrudRepository;

import datawerks.common.db.objects.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

}
