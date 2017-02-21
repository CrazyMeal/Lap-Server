package fr.lap.db;

import javax.persistence.Persistence;

public class DbScriptGenerator {
	public static void main(String[] args) {
		Persistence.generateSchema("samplePU", null);
	}
}
