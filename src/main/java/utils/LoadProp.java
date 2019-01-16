package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProp {
	public static final Properties  prop=new Properties(); 
	
	public static void loadProperties() throws FileNotFoundException, IOException
	{
	
		
		prop.load(new FileInputStream("src\\main\\resources\\com\\AppleCarData\\env.properties"));
	}

}
