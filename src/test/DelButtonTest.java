package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DelButtonTest {
	
	private ObservableList<String> items;

	@Before
	public void setUp(){
		items = FXCollections.observableArrayList("mucky","bucky","ducky");
		
	}
	@Test
	public void test() {
		assertTrue(items.contains("mucky"));
		items.remove("mucky");
		assertFalse(items.contains("mucky"));
		for(String i : items){
			System.out.println(i);
		}
	}

}
