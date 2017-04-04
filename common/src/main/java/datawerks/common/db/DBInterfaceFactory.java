package datawerks.common.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(DatabaseProperties.class)
public class DBInterfaceFactory {

	private static final String MY_SQL = "mysql";
	
	private DatabaseInterface dbInterface;
	
	private DatabaseProperties databaseProperties;
	
	@Autowired
	public DBInterfaceFactory(DatabaseProperties databaseProperties) {
		this.databaseProperties = databaseProperties;
		init();
	}
	
	private void init() {
		switch (databaseProperties.getInstance()) {
			case MY_SQL: 
				dbInterface = new MySQLDbInterface();
		}	
	}
	
	public DatabaseInterface getInterface() {
		return dbInterface;
	}
	
}
