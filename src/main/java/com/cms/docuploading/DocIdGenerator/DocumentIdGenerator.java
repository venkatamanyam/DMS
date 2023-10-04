package com.cms.docuploading.DocIdGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.internal.SessionImpl;

public class DocumentIdGenerator implements IdentifierGenerator {

	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {

		String prefix="DOC";
		String suffix="";
		try
		{
			
			SessionImplementor sessionImplementor = (SessionImplementor) session;
			
			Connection con = sessionImplementor.getJdbcConnectionAccess().obtainConnection();

			Statement st =  con.createStatement();
			ResultSet rs = st.executeQuery("select count(docid) from document");
			
			boolean b = rs.next();
			
			System.out.println("????????????????????????");
			
			System.out.println(b+"  vvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
				if(b)
				{int i=rs.getInt(1)+301;
				
				System.out.println("????????????????????????");
				suffix=String.valueOf(i);
				
				System.out.println(suffix);
			
				System.out.println("????????????????????????");
				}
			
			
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		
		
		
		return prefix+suffix;
	}

}
