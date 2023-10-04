package com.cms.docuploading.Entity;







import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@jakarta.persistence.Entity
public class Document {
	@jakarta.persistence.Id
	@GenericGenerator(name="docnumber", strategy="com.cms.docuploading.DocIdGenerator.DocumentIdGenerator")
	@jakarta.persistence.GeneratedValue(generator="docnumber")
    private String docID;
    private String docTitle;
    private String docType;
    private String docPath;
    private boolean compressed;
    private String userID;
    private String userGroup;
    private String userOrg;
    private String docTags;
    private String location;
    @CreationTimestamp()
    private LocalDate createdDate;
    @UpdateTimestamp()
	@jakarta.persistence.Column(insertable=false)
    private LocalDate lastUpdatedDate;
}
