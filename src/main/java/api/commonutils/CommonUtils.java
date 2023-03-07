package api.commonutils;

import java.io.File;

import flicker.base.BaseTestPage;

public class CommonUtils extends BaseTestPage {

	public static File fetchSchemaFile(String fileName) {
		File file = null;
		
		try {
			file = new File(applicationProps.getProperty("schema_base_location").toString() + fileName);
		} catch (Exception e) {
			System.out.println("Failed to load schemafile :" + fileName);
			e.printStackTrace();
		}

		return file;

	}
}
