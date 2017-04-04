package datawerks.common.db;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("database")
public class DatabaseProperties {

    private String instance = "NONE";

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

}