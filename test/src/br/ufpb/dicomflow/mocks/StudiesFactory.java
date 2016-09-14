package br.ufpb.dicomflow.mocks;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;

public class StudiesFactory {
	
	public static List<Study> createStudies() {
		ArrayList<Study> result = new ArrayList<Study>();
		
		Study s1 = new Study();
		s1.setId(1l);
		Patient p1 = new Patient();
		p1.setId(1l);
		p1.setPatientBirthDate("01/01/1980");
		p1.setPatientName("João da Silva");
		s1.setPatient(p1);
		result.add(s1);
		
		TitledPane t1 = new TitledPane("João Da Silva", new Button("B1"));
    	
        TitledPane t2 = new TitledPane("T2", new Button("B2"));
        
        TitledPane t3 = new TitledPane("T3", new Button("B3"));
        
				
		return result;
	}

}
