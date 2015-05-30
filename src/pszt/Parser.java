/**
 * 
 */
package pszt;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * @author JA!
 *
 */
public class Parser{
	final FileChooser fileChooser = new FileChooser();
	private List<String> wszystkieLinie;
	private String[] podzieloneLinie;
	private List<String[]> tablicaPodzielonychLinii = new ArrayList<String[]>();
	static int licznik = 0;
	File file;
	Charset cp1252 = Charset.forName("CP1252");
	final String delimiter1 = ":";
	final String delimiter2 = "v";		//nie uzywac v w nazwach. chyba, ze komus sie chce przekopywac tablice znakow
	
	Parser(){
		
		
	}
	
	
	
	boolean wybierzPlik(Stage stage) {
		file = fileChooser.showOpenDialog(stage);
	     if (file != null) {
	        return true;
	     }
	     return false; //nie chce mi sie wyjatkow rzucac pff

		
	}



	public boolean parsujPlik() {
		try {
			wszystkieLinie = Files.readAllLines(Paths.get(file.getPath()), cp1252);
			this.splitujLinie();
			return true;
		}
		catch(IOException e){
			e.printStackTrace();
			
			return false;
		}
		
		
	}
	
	public void wyswietlListy() {
		System.out.println("Zawartosc list do parsowania:");
		for(String s: wszystkieLinie){
			System.out.println(s);
		}
		System.out.println("podzielone:");
		for(String[] s: tablicaPodzielonychLinii){
			System.out.println("KOLEJNY ARRAY");
			for(String ss : s)
			{
				System.out.println(ss);
			}
		}
		
		
	}


	private void splitujLinie() {
		String[] tempString;
		List<String> tempString2 = new ArrayList<String>();
		for(String s: wszystkieLinie){
			for(String ss : s.split(delimiter1))
			{
				int licznik2 = 0;
				System.out.println("Pierwszy split:" + ss);
				tempString = ss.split(delimiter2);
				for(String zs : tempString)
				{
					if(zs.startsWith(" ")){
						System.out.println("BEZ PIERWSZEGO ZNAKU " + zs.substring(1));
						tempString2.add(zs.substring(1));
						
					}
					else{
						tempString2.add(zs);
					}
						
					
				}
				tablicaPodzielonychLinii.add(tempString2.toArray(new String[tempString2.size()]));
				tempString2.clear();
			}
			licznik++;
		}
		licznik = 0;
		
	}
}



