package com.serp.finance.entity;

import java.time.Instant;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="fee_categories")
public class FeeCategory {

	@Id
	private ObjectId id;
	private String categoryName;
	private String description;
	private Set<String> batchId;
	@CreatedDate
	private Instant createdDate;
	@LastModifiedDate
	private Instant modifiedDate;
	
	@PersistenceConstructor
	public FeeCategory(String categoryName, String description, Set<String> batchId) {
		super();
		this.categoryName = categoryName;
		this.description = description;
		this.batchId = batchId;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getBatchId() {
		return batchId;
	}

	public void setBatchId(Set<String> batchId) {
		this.batchId = batchId;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public Instant getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Instant modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "FeeCategory [id=" + id + ", categoryName=" + categoryName + ", description=" + description
				+ ", batchId=" + batchId + ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + "]";
	}
	
	
	

	
	
}
