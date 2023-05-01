package com.diningedge.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;

public class CsvUtitlity {
	public static void addData() {
		try {
			File file = new File(System.getProperty("user.dir") + "\\downloads\\product_list.csv");
			
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			List<String> lines = new ArrayList<>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

			for (int i = 0; i < lines.size(); i++) {
				System.out.println(lines.get(i));
				if(lines.get(lines.size()-1)!=null) {
					FileWriter outputfile = new FileWriter(file);
					@SuppressWarnings("resource")
					CSVWriter writer = new CSVWriter(outputfile);
					String[] header = { "Name", "Class", "Marks" };
			        writer.writeNext(header);
				}
			}
			//writer.close();

		} catch (Exception e) {
			
		}
		
	}

	public static void main(String... strings) {
		addData();
	}
}
