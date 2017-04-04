package datawerks.common.db;

import org.springframework.data.repository.CrudRepository;

public class MySQLDbInterface implements DatabaseInterface {

	@Override
	public void set(DBObject object, CrudRepository repo) {
		repo.save(object);
	}

}
