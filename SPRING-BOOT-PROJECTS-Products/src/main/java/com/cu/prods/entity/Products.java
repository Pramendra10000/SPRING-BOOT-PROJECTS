package com.cu.prods.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Products {
	

	@Id
  private long id;
  private String title;
  private String description;
  
  
  
public Products() {
	super();
	// TODO Auto-generated constructor stub
}



public Products(long id, String title, String description) {
	super();
	this.id = id;
	this.title = title;
	this.description = description;
}



public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}

@Override
public String toString() {
	return "Products [id=" + id + ", title=" + title + ", description=" + description + "]";
}
  
	
	
}
