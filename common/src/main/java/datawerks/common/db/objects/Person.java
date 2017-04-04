package datawerks.common.db.objects;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import datawerks.common.db.DBObject;

@Entity
public class Person implements DBObject {
	
	@Id
    private Integer id;
	 
	private String name;
	private Date time_of_start;
	
	protected Person() {}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getTime_of_start() {
		return time_of_start;
	}
	
	public void setTime_of_start(Date time_of_start) {
		this.time_of_start = time_of_start;
	}
	
}
