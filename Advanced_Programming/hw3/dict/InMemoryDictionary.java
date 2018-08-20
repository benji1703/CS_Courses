package dict;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeMap;



/**
 * Implements a persistent dictionary that can be held entirely in memory.
 * When flushed, it writes the entire dictionary back to a file.
 * 
 * The file format has one keyword per line:
 * <pre>word:def1:def2:def3,...</pre>
 * 
 * Note that an empty definition list is allowed (in which case the entry would have the form: <pre>word:</pre> 
 * 
 * @author talm / @edited benjamin.arbibe
 *
 */
public class InMemoryDictionary extends TreeMap<String,String> implements PersistentDictionary  {

	private static final long serialVersionUID = 1L; // (because we're extending a serializable class)
	private File file;		// Creating the file 

	public InMemoryDictionary(File dictFile) {

		this.file = dictFile;
	}


	@Override	
	public void open() throws IOException {

		String word;	
		String def;
		int indexOf;

		FileReader fr = null;
		BufferedReader br = null;

		if(!this.file.exists()) {	// Checking if file don't exist
			this.file.createNewFile();
		}

		try {

			fr = new FileReader(this.file);
			br = new BufferedReader(fr);

			String newLine = br.readLine();			// Read First line

			while (newLine != null) {

				indexOf = newLine.indexOf(':'); 	// Return the index of the first occurrence of ':'

				// Get the Word part of the line into 'word'
				word = newLine.substring(0, indexOf);

				// Get the Definition part of the line into 'def'
				def = newLine.substring(indexOf + 1, newLine.length());

				this.put(word, def); 				// Save it in the TreeMap

				newLine = br.readLine(); 			// Advance to next line	
			}
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			br.close();		// Closing the BufferedWriter will close the FileWriter
		}

	}



	@Override
	public void close() throws IOException {


		FileWriter fw = null;
		BufferedWriter bw = null;
		
		if(!this.file.exists()) {
			this.file.createNewFile();
		}
		
		try {

			fw = new FileWriter(this.file);					
			bw = new BufferedWriter(fw);
			
			for (String key : this.keySet()) {
				bw.write(key + ":" + this.get(key) + "\n");	// Save it to the map
			}
		
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		finally {
			bw.close();		// Closing the BufferedWriter will close the FileWriter
		}
	}

}
