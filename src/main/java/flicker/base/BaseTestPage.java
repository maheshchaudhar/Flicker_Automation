package flicker.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class BaseTestPage {
	
	public static Properties applicationProps = new Properties();
	
	public static Logger logger = Logger.getRootLogger();

	static {
		//BasicConfigurator.configure();
		InputStream propIO = null;
		try {
			 propIO = new FileInputStream("application.properties");
			
		} catch (FileNotFoundException e) {
			System.out.println("application.properties => File not found");
			e.printStackTrace();
		}
		
		try {
			applicationProps.load(propIO);
			
		} catch (IOException e) {
			System.out.println("Failed to load properties");
			e.printStackTrace();
		}
		
		
		//Load Log 4j Configuration
		Properties log4j = new Properties();
		try {
			log4j.load( new FileInputStream("log4j.properties"));
			PropertyConfigurator.configure(log4j);
		} catch (IOException e) {
			System.out.println("Failed to load log 4j properties file.");	
			e.printStackTrace();
		}
	
	
	}
	

}
