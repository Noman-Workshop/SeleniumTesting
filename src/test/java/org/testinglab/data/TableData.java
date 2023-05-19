package org.testinglab.data;

import java.util.ArrayList;
import java.util.List;

public class TableData {
	
	public String caption;
	public String tableId;
	
	public static class Person {
		
		public String name;
		public String age;
		
		public Person(String name, String age) {
			this.name = name;
			this.age = age;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Person)) return false;
			
			Person person = (Person) o;
			
			if (!name.equals(person.name)) return false;
			return age.equals(person.age);
		}
	}
	
	public List<Person> tableData;
	
	// table contains name and age of people
	public TableData() {
		this.caption = "People";
		this.tableId = "people-table";
		
		tableData = new ArrayList<>();
		tableData.add(new Person("John", "20"));
		tableData.add(new Person("Jane", "30"));
		tableData.add(new Person("Jack", "40"));
		tableData.add(new Person("Jill", "50"));
	}
}
