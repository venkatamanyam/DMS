package com.cms.docuploading.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.docuploading.Entity.Document;

public interface DocumentRepository extends JpaRepository<Document, String> {

	List<Document> findByuserID(String userID);

	Document findBydocID(String docID);

	List<Document> findByuserGroup(String userGroup);

}
