package com.lionel.student_jpa.utils;

public class Generator {
    
    public static String generateId( String maxIdBeforeNow , String prefix )
	{
		String resultId = "";
			
		resultId += prefix;
		
		int id = Integer.parseInt(maxIdBeforeNow.substring(3)) + 1;
		
		switch( String.valueOf(id).length())
		{
		case 1 :
			resultId += "00"+id;
			break;
		case 2 : 
			resultId += "0"+id;
			break;
		default : 
			resultId += id;
		}
		
			
		return resultId;
	}

}
