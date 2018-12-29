package com.workspan.json.gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.workspan.entity.EntityManager;
import com.workspan.json.model.EntityGraph;

/**
 * Main class of the program. Reads the file, creates Java object 
 * from JSON file and delegates task of creating clone.
 * 
 * 
 * @author deekshasingh
 *
 */
public class EntityGsonReader {

	private static final Logger logger = Logger.getLogger(EntityGsonReader.class);

	public static void main(String[] args) throws IOException {
		
		validateArgument(args);
		logger.info("FileName is " + args[0]);
		File file = new File(args[0]);
		FileInputStream in = null;
		if (file.isFile() && file.canRead()) {
			try {
				// Open the stream.
				in = new FileInputStream(file);

				InputStreamReader isr = new InputStreamReader(in);

				//create JsonReader object
				JsonReader reader = new JsonReader(isr);

				//create objects
				GsonBuilder builder = new GsonBuilder(); 
				builder.setPrettyPrinting(); 

				Gson gson = builder.create(); 
				EntityGraph eg = gson.fromJson(reader, EntityGraph.class);

				reader.close();
				//clone the nodes
				EntityManager manager = new EntityManager(eg);
				manager.createClones(Integer.parseInt(args[1]));


				logger.info("Entity Object\n\n"+new Gson().toJson(manager.getEg()));

			} finally {
				in.close();
			}
		} else {
			logger.error("File not readable.");
		}

	}

	/**
	 * Validates the length of the arguments.
	 * @param args: Passed in Arguments: Arg[0]: filename, Arg[1]: Entity Id 
	 */
	private static void validateArgument(String[] args) {
		if(args.length<2) {
			logger.error("Proper Usage is: java -jar jarName filename entityId");
			System.exit(0);
		}
	}

}
