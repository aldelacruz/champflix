package com.champirata.champflix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Practice {
	static List<Integer> myList = new ArrayList<>();

	/** Employee class **/
	static class Employee {
		int id;
		String name;

		public Employee(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	}

	public static void main(String[] args) {
		System.out.println("Hello World!");
	}

}
