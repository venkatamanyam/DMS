package com.cms.docuploading.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cms.docuploading.Entity.Document;
import com.cms.docuploading.Repository.DocumentRepository;
import com.cms.docuploading.service.DocumentService;

@RestController
@CrossOrigin("http://localhost:3000/")
public class DocumentController {

	
	@Autowired
	DocumentService service;
	
	
	
	
	
	@PostMapping(value="/upload/{userID}/{userOrg}/{location}", consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.ALL_VALUE})
    public ResponseEntity<String> uploadDocument(@RequestPart("file") MultipartFile file,@PathVariable(name = "userID") String userID,@PathVariable(name = "userOrg") String userOrg,@PathVariable(name = "location") String location)throws IOException
    {   
    	 
    	  
    	 String msg= service.uploadService(file,userID,userOrg,location) ;
    	 
    	 
    	 
                 
        if(msg.contains("uploaded"))
         return new ResponseEntity<String>(msg,HttpStatus.OK);
        else
        	return new ResponseEntity<String>(msg,HttpStatus.BAD_REQUEST);
    	  
   }
	
	
	
	
	@GetMapping(
            value = "getFile/{fileName}/{userID}/{location}/{userOrg}",
            produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.ALL_VALUE, MediaType.IMAGE_GIF_VALUE,MediaType.IMAGE_PNG_VALUE}
    )
    public ResponseEntity<Object> downloadDocument(@PathVariable(name = "fileName") String fileName,@PathVariable(name = "userID") String userID,@PathVariable(name = "location") String location,@PathVariable(name = "userOrg") String userOrg) throws IOException {
       
		
		System.out.println("Entered into Download");
		
		System.out.println(fileName);
		System.out.println(userID);
		System.out.println(location);
		System.out.println(userOrg);
		
		List<Document> docslist= service.getAllDocuments(userID);
		          
		          System.out.println(docslist.size());
		               
		if(docslist.size()>0 || (location.length()!=0  && userOrg.length()!=0))
		{       
		    String doclocation= docslist.get(0).getLocation() + docslist.get(0).getUserOrg();
		
		    String mediaType = "";
		    
	        String[] extension = fileName.split("\\.");
	        

	        if(extension[1].equalsIgnoreCase("pdf") || extension[1].equalsIgnoreCase("zip"))
	        {
	            mediaType="application/";
	        }
	        else
	        {
	            mediaType="image/";
	        }
               if(docslist.size()>0)
               { 
            	   
            	   return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(mediaType+extension[1]))
	                
            			   .body(service.downloadService(fileName,location,userOrg));
            	}
               
               
               
               else if(location.length()>0 && userOrg.length()>0)
               { 
            	   
            	   return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(mediaType+extension[1]))
	                
            			   .body(service.downloadService(fileName,location,userOrg));
            	   
               
               }
               
               
               else
    		   {
    		          return new ResponseEntity<Object>("Failure",HttpStatus.BAD_REQUEST);
    		            	   
    		   }
	        
	        
		   }
		               
		               
		               
		   else
		   {
		          return new ResponseEntity<Object>("Failure",HttpStatus.BAD_REQUEST);
		            	   
		   }
		
		
		
		
		
		
    }
	
	
	@GetMapping(value="/getDocs/{userID}")
	public  ResponseEntity<List<Document>> getAllDocs(@PathVariable("userID") String userID)
	{
		
	             List<Document> l = service.getAllDocuments(userID);
	             
	             return new ResponseEntity<List<Document>>(l,HttpStatus.OK);
	             
		
	}
	
	
	@GetMapping(value="/getDocsByDept/{userGroup}")
	public ResponseEntity<List<Document>> getAllDocumentsByDepartmnt(@PathVariable("userGroup")String userGroup)
	{
		List<Document> l = service.getAllDocumentsByDepartment(userGroup);
        
        return new ResponseEntity<List<Document>>(l,HttpStatus.OK);
		
	}
	
	
	@PutMapping(value="/updatedoc/{docID}/{userRole}", consumes= {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE,MediaType.ALL_VALUE})
    public ResponseEntity<String> uploadDocument(@RequestPart("file") MultipartFile file,@PathVariable(name = "docID") String docID,@PathVariable(name = "userRole") String userRole)throws IOException
    {   String msg="";
		
		
    	 if(userRole.equalsIgnoreCase("Employee") || userRole.equalsIgnoreCase("Admin"))
    	  
    	  msg = service.updateDocService(file,docID,userRole) ;
    	 
    	
        
        return new ResponseEntity<String>(msg,HttpStatus.OK);
    	  
   }
	
	
	
	
	
	 @DeleteMapping(value="/deletefile/{docID}/{userRole}")
	    public ResponseEntity<String> deleteFileByAdmin(@PathVariable("docID") String docID,@PathVariable("userRole") String userRole){
		    
		    System.out.println("*******************");
			System.out.println("From Delete Document controller: "+userRole);
			System.out.println("*******************");
				
				 
			String msg = service.deleteDocumentByAdmin(docID,userRole);
				
		      if(msg.contains("deleted"))
		    		return new ResponseEntity<String>(msg,HttpStatus.OK);
		      else
		        	return new ResponseEntity<String>(msg,HttpStatus.BAD_REQUEST);     

		 
		 
		 
		 
		 
		 
	    
	 }
	
	
}
