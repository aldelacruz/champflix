package com.champirata.champflix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class StreamsDemo {
	static List<Integer> myList = new ArrayList<>();

	/** Employee class **/
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	static class EmployeeReview  implements Comparable<EmployeeReview> {
		int id;
		String name;
		SalaryGrade salaryGrade;
		double score;
		String reviewer;

		public EmployeeReview(int id, String name) {
			this.id = id;
			this.name = name;
		}	

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + id;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EmployeeReview other = (EmployeeReview) obj;
			if (id != other.id)
				return false;
			return true;
		}

		@Override
		public int compareTo(EmployeeReview er) {
			return Integer.valueOf(id).compareTo(er.getId());
		}

		@Override
		public String toString() {
			return "EmployeeReview [id=" + id + ", name=" + name + ", salaryGrade=" + salaryGrade + ", score=" + score + ", reviewedBy=" + reviewer + "]";
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

		List<EmployeeReview> employees = new ArrayList<>();
		/** Creating the employee list **/
		for (int i = 1; i < 10; i++) {
			employees.add(new StreamsDemo.EmployeeReview(i, "name_" + i));
		}
		/**
		 * Only Intermediate Operations; it will just create another stream and will not
		 * perform any operations
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

		List<EmployeeReview> employees = new ArrayList<>();
		for (int i = 1; i < 10000000; i++) {
			employees.add(new StreamsDemo.EmployeeReview(i, "name_" + i));
		}
		/**
		 * Only Intermediate Operations; it will just create another streams and will
		 * not perform any operations
		 **/
		Stream<String> employeeNameStreams = employees.stream().filter(e -> e.getId() % 2 == 0).map(employee -> {
			System.out.println("In Map - " + employee.getName());
			return employee.getName();
		});
		long streamStartTime = System.currentTimeMillis();
		/** Terminal operation with short-circuit operation: limit **/
		employeeNameStreams.limit(100).collect(Collectors.toList());
		System.out.println(System.currentTimeMillis() - streamStartTime);

	}

	// Functional programming example
	static void functionalProgrammingSample() {
		// lambda expressions
		MyFunction<String, String> myFunction = (s1, s2) -> s1 + s2;
		System.out.println(myFunction.apply("Hello ", "Streams World!"));

		// methodReference: ClassName::staticMethod
		MyFunction<Integer, Integer> myFunction2 = Integer::sum;
		System.out.println(myFunction2.apply(5, 4));

		// constructorReference: ClassName::new
		MyEmployeeSupplier<Integer, String, EmployeeReview> myEmployeeSupplier = EmployeeReview::new;
		System.out.println(myEmployeeSupplier.get(10, "Amor Powers"));

	}

	// Reduce quiz
	static void reductionQuiz() {
		String[] grades = { "A", "A", "B", "C" };
		StringBuilder concat = Arrays.stream(grades).reduce(new StringBuilder(), (sb1, s) -> sb1.append(s),
				(sb1, sb2) -> sb1.append(sb2));
		System.out.println(concat.toString());
	}


	// Overloaded reduction operations
	private void overloadedReductions() {
		String[] grades = { "A", "A", "B", "C", "D", "E" };
		// takes a BinaryOperator and returns an Optional of the value, if any.
		Optional<String> concat1 = Arrays.stream(grades).reduce((s1, s2) -> s1 + s2);

		System.out.println("First overloaded method: " + concat1);

		// takes 2 parameters: an identity and a BinaryOperator, returns the result if
		// any or the identity if none
		String concat2 = Arrays.stream(grades).reduce("", (s1, s2) -> s1 + s2);

		System.out.println("Second overloaded method: " + concat2);

		

	}

	// if accumulator mutates, use collect(). Otherwise, use reduce()
	private static void mutableReduction() {
		
		
	}
	
	
	private static void collectToCollection(List<EmployeeReview> employees) {
		
		
		
	}
	
	private static void collectToMap(List<EmployeeReview> employees) {
		
		
		
		
	}
	
	
	


	@FunctionalInterface
	private interface MyFunction<T, R> {

		R apply(T t, T t1);
	}

	@FunctionalInterface
	private interface MyEmployeeSupplier<N, T, R> {

		R get(N id, T name);
	}

	private enum SalaryGrade {
		A1, A2, A3, A4, A5
	}
	
	private static List<EmployeeReview> getRatingsFromManager(){
		
		EmployeeReview e1 = new EmployeeReview(1, "Ivan Goff", SalaryGrade.A1, 7.9, "Manager");
		EmployeeReview e2 = new EmployeeReview(2, "Blaine Spooner", SalaryGrade.A1, 5.4, "Manager");
		EmployeeReview e3 = new EmployeeReview(3, "Massimo Carver", SalaryGrade.A1, 5.8, "Manager");
		EmployeeReview e4 = new EmployeeReview(4, "Ruairi Townsend", SalaryGrade.A1, 6.3, "Manager");
		EmployeeReview e5 = new EmployeeReview(5, "Cleo O'Neill", SalaryGrade.A2, 8.2, "Manager");
		EmployeeReview e6 = new EmployeeReview(6, "Cheyenne Ferreira", SalaryGrade.A2, 7.7, "Manager");
		EmployeeReview e7 = new EmployeeReview(7, "Murtaza Galloway", SalaryGrade.A2, 9.2, "Manager");
		EmployeeReview e8 = new EmployeeReview(8, "Dillon Southern", SalaryGrade.A3, 6.1, "Manager");
		EmployeeReview e9 = new EmployeeReview(9, "Kirstin Muir", SalaryGrade.A3, 9.4, "Manager");
		EmployeeReview e10 = new EmployeeReview(10, "Ewan Redman", SalaryGrade.A3, 8.0, "Manager");
		return Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
	}
	
	private static List<EmployeeReview> getRatingsFromTeamLeader(){
		EmployeeReview e1 = new EmployeeReview(1, "Ivan Goff", SalaryGrade.A1, 8.9, "TL");
		EmployeeReview e2 = new EmployeeReview(2, "Blaine Spooner", SalaryGrade.A1, 6.2, "TL");
		EmployeeReview e3 = new EmployeeReview(3, "Massimo Carver", SalaryGrade.A1, 5.7, "TL");
		EmployeeReview e4 = new EmployeeReview(4, "Ruairi Townsend", SalaryGrade.A1, 7.4, "TL");
		EmployeeReview e5 = new EmployeeReview(5, "Cleo O'Neill", SalaryGrade.A2, 7.9, "TL");
		EmployeeReview e6 = new EmployeeReview(6, "Cheyenne Ferreira", SalaryGrade.A2, 6.0, "TL");
		EmployeeReview e7 = new EmployeeReview(7, "Murtaza Galloway", SalaryGrade.A2, 10.0, "TL");
		EmployeeReview e8 = new EmployeeReview(8, "Dillon Southern", SalaryGrade.A3, 5.8, "TL");
		EmployeeReview e9 = new EmployeeReview(9, "Kirstin Muir", SalaryGrade.A3, 8.3, "TL");
		EmployeeReview e10 = new EmployeeReview(10, "Ewan Redman", SalaryGrade.A3, 9.0, "TL");
		return Arrays.asList(e1, e2, e3, e4, e5, e6, e7, e8, e9, e10);
	}
	
	private static List<EmployeeReview> getRatingsFromSuperiors(){
		List<EmployeeReview> allRatedEmployees = new ArrayList<EmployeeReview>(getRatingsFromTeamLeader());
		allRatedEmployees.addAll(getRatingsFromManager());
		return allRatedEmployees;
	}
	
	
	public static void main(String[] args) {
		
		collectToMap(getRatingsFromSuperiors());
		
	}
	
	
	

}
