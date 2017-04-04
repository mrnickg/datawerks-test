package datawerks.common.db;

import org.springframework.data.repository.CrudRepository;

public interface DatabaseInterface {
	
	public void set(DBObject object, CrudRepository repo);
}
