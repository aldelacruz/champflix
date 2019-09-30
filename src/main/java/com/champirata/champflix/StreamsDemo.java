package com.champirata.champflix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsDemo {
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
		
		public int getId() {
			return this.id;
		}
	}
	
	static void streamPerformanceTest() {
		for (int i = 0; i < 5000000; i++)
			myList.add(i);
		int result = 0;
		long loopStartTime = System.currentTimeMillis();
		
		for (int i : myList) {
			if (i % 2 == 0)
				result += i;
		}
		
		long loopEndTime = System.currentTimeMillis();
		System.out.println(result);
		System.out.println("Loop total Time = " + (loopEndTime - loopStartTime));
		
		long streamStartTime = System.currentTimeMillis();
		System.out.println(myList.stream().filter(value -> value % 2 == 0).mapToInt(Integer::intValue).sum());
		
		long streamEndTime = System.currentTimeMillis();
		System.out.println("Stream total Time = " + (streamEndTime - streamStartTime));
		
		long parallelStreamStartTime = System.currentTimeMillis();
		System.out.println(myList.parallelStream().filter(value -> value % 2 == 0).mapToInt(Integer::intValue).sum());
		
		long parallelStreamEndTime = System.currentTimeMillis();
		System.out.println("Parallel Stream total Time = " + (parallelStreamEndTime - parallelStreamStartTime));
	}
	
	static void streamLazinessTest() throws InterruptedException {

		List<Employee> employees = new ArrayList<>();
		/** Creating the employee list **/
		for (int i = 1; i < 10; i++) {
			employees.add(new StreamsDemo.Employee(i, "name_" + i));
		}
		/**
		 * Only Intermediate Operations; it will just create another stream and will
		 * not perform any operations
		 **/
		Stream<String> employeeNameStreams = employees.parallelStream().filter(employee -> employee.id % 2 == 0)
				.map(employee -> {
					System.out.println("In Map - " + employee.getName());
					return employee.getName();
				});
		/** Adding some delay to make sure nothing has happen till now **/
		Thread.sleep(2000);
		System.out.println("2 sec");
		/**
		 * Terminal operation on the stream and it will invoke the Intermediate
		 * Operations filter and map
		 **/
		employeeNameStreams.collect(Collectors.toList());
	}
	
	
	static void streamShortCircuitTest() {
		
		List < Employee > employees = new ArrayList < > ();
		  for (int i = 1; i < 10000000; i++) {
		   employees.add(new StreamsDemo.Employee(i, "name_" + i));
		  }
		  /** Only Intermediate Operations; it will just create another streams and 
		   * will not perform any operations **/
		  Stream < String > employeeNameStreams = employees.stream().filter(e -> e.getId() % 2 == 0)
		   .map(employee -> {
		    System.out.println("In Map - " + employee.getName());
		    return employee.getName();
		   });
		  long streamStartTime = System.currentTimeMillis();
		  /** Terminal operation with short-circuit operation: limit **/
		  employeeNameStreams.limit(100).collect(Collectors.toList());
		  System.out.println(System.currentTimeMillis() - streamStartTime);
		
	}
	
	// Reduce quiz
		static void reductionQuiz() {
			String[] grades = { "A", "A", "B", "C" };
			StringBuilder concat = Arrays.stream(grades).reduce(new StringBuilder(), (sb1, s) -> sb1.append(s),
					(sb1, sb2) -> sb1.append(sb2));
			System.out.println(concat.toString());
		}

	
	public static void main(String[] args) {
		System.out.println("Hello World!");
		
		streamShortCircuitTest();
	}
	 

}
