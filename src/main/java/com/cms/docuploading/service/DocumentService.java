package com.cms.docuploading.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cms.docuploading.Entity.Document;
import com.cms.docuploading.Repository.DocumentRepository;

@Service
public class DocumentService {
	
	
	@Autowired
	DocumentRepository repo;
	
	

public String uploadService(MultipartFile file,String userID,String userOrg,String location ) throws IOException
	{
		
		 String name=file.getOriginalFilename();
		 
		 
		 
		 String fileName=userID+"-"+name;
		 
         
         
         Document d=new Document();
         
         d.setDocTitle(fileName);
         d.setDocType(file.getContentType());
         
         d.setUserID(userID);
         
         d.setUserOrg(userOrg);
         d.setLocation(location);
         
         
         String doclocation=d.getLocation()+"."+d.getUserOrg();
         
         
         String[] docpath = doclocation.split("\\.");
         
         d.setUserGroup(docpath[6]);
         
 		System.out.println(docpath.length);
 		System.out.println(docpath[0]);
 		System.out.println(docpath[1]);
 		System.out.println(docpath[2]);
 		System.out.println(docpath[3]);
 		System.out.println(docpath[4]);
 		System.out.println(docpath[5]);
 		System.out.println(docpath[6]);
 		

 		String storageDirectoryPath ="D:\\"+docpath[0]+"\\"+docpath[4]+"\\"+docpath[1]+"\\"+docpath[2]+"\\"+docpath[3]+"\\"+docpath[5]+"\\"+docpath[6];
 		
 		System.out.println(storageDirectoryPath);
 		
 		
 		 Path storageDirectory = Paths.get(storageDirectoryPath);
 	       
 	       if(!Files.exists(storageDirectory)){ 
 	           
 	               Files.createDirectories(storageDirectory); //create the directory in the given storage directory path
 	           
 	       }
 		

         
         
          Path destination = Paths.get(storageDirectory.toString() + "\\" + fileName);

      
           Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
       
       
       
       
           String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/getFile/")
	                .path(d.getDocTitle())
	                .toUriString();
           
           
                   d.setDocPath(fileDownloadUri);
                   
           	   Document doc = repo.save(d);
           	   
           	   if(doc!=null)
			       return "uploaded successfully "+doc.getDocID();
           	   else
           		   return "failed to upload document";

	}
	
	
	
	
	

	public  byte[] downloadService(String fileName, String location,String userOrg) throws IOException {
		
		String doclocation=location+"."+userOrg;
		
		String[] docpath = doclocation.split("\\.");
		

		System.out.println("Entered into download service: ");
		System.out.println(docpath.length);
 		System.out.println(docpath[0]);
 		System.out.println(docpath[1]);
 		System.out.println(docpath[2]);
 		System.out.println(docpath[3]);
 		System.out.println(docpath[4]);
 		System.out.println(docpath[5]);
 		System.out.println(docpath[6]);
		
		String storageDirectoryPath ="D:\\"+docpath[0]+"\\"+docpath[4]+"\\"+docpath[1]+"\\"+docpath[2]+"\\"+docpath[3]+"\\"+docpath[5]+"\\"+docpath[6];
		
		System.out.println(storageDirectoryPath);
		
		 Path storageDirectory = Paths.get(storageDirectoryPath);
	       
	      
		 Path p =   Paths.get(storageDirectory+"\\"+fileName);// retrieve the doc by its name
        
		System.out.println(p);
		
		return  Files.readAllBytes(p);
		
       // return IOUtils.toByteArray(destination.toUri());
        
    }
	
	
	
	public List<Document> getAllDocuments(String userID)
	{
		System.out.println(userID);
		return repo.findByuserID(userID);
		
	}

	
	public List<Document> getAllDocumentsByDepartment(String userGroup)
	{
		System.out.println(userGroup);
		return repo.findByuserGroup(userGroup);
		
	}
	
	
	
	
	
	
public String updateDocService(MultipartFile file, String docID,String userRole) {
		
		System.out.println("Entered into update DocService");
		
		String fileName=file.getOriginalFilename();
		
		
		Document d=repo.findBydocID(docID);
		
		String docTitle = d.getDocTitle();
		
		 
		 String doclocation=d.getLocation()+"."+d.getUserOrg();
		 
		 
		 String[] docpath = doclocation.split("\\.");
		 
		 
		 String storageDirectoryPath ="D:\\"+docpath[0]+"\\"+docpath[4]+"\\"+docpath[1]+"\\"+docpath[2]+"\\"+docpath[3]+"\\"+docpath[5]+"\\"+docpath[6];
			
			System.out.println(storageDirectoryPath);
			
			 Path storageDirectory = Paths.get(storageDirectoryPath);
		       
		      
			 Path p =   Paths.get(storageDirectory+"\\"+docTitle);
		 
		                try {
							    Files.deleteIfExists(p);
						    }
		                
		                catch (IOException e) {
							return "FileNotExists";
						}
		
		
		                d.setDocTitle(fileName);
		                d.setDocType(file.getContentType());
		                
		               
		                
		                if(!Files.exists(storageDirectory))
		                { 
		      	           
		  	               try 
		  	               {
							   Files.createDirectories(storageDirectory);
						   } 
		  	               
		  	               catch (IOException e) 
		  	               {
							e.printStackTrace();
		  	           
		  	               }
		                }   
		  		

		          
		          
		           Path destination = Paths.get(storageDirectory.toString() + "\\" + fileName);

		       
		            try 
		            {
						Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
					} 
		            
		            catch (IOException e)
		            {
						e.printStackTrace();
					}
		        
		        
		        
		        
		            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
		 	                                 .path("/getFile/")
		 	                                 .path(d.getDocTitle())
		 	                                 .toUriString();
		            
		            
		                    d.setDocPath(fileDownloadUri);
		                    
		            	   repo.save(d);
		                
		                
		               
		return "success";
	}
	
	
	
	
	
public String deleteDocumentByAdmin(String docID, String userRole) {
		
		System.out.println("Entered into delete DocService");
		
		if(userRole.equalsIgnoreCase("Admin"))
		{
		Document doc=repo.findBydocID(docID);
		
		String docTitle = doc.getDocTitle();
		 
		 
		 String doclocation=doc.getLocation()+"."+doc.getUserOrg();
		 
		 
		 String[] docpath = doclocation.split("\\.");
		 
		 
		 String storageDirectoryPath ="D:\\"+docpath[0]+"\\"+docpath[4]+"\\"+docpath[1]+"\\"+docpath[2]+"\\"+docpath[3]+"\\"+docpath[5]+"\\"+docpath[6];
			
			System.out.println(storageDirectoryPath);
			
			 Path storageDirectory = Paths.get(storageDirectoryPath);
		       
		      
			 Path p =   Paths.get(storageDirectory+"\\"+docTitle);
		 
		                try 
		                {
							    Files.deleteIfExists(p);
						} 
		                
		                catch (IOException e)
		                {
							return "FileNotExists";
						}
		
		
		repo.deleteById(docID);
		
		return "deleted successfully "+docID;
		
		}
		
		else return "You Don't have Authorizaion";
	}


}
