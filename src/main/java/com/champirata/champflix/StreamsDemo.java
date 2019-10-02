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
		
		//TODO: This is for the intro to parallel streams, just run this to test performance of for-loop, and sequential and parallel streams 
		
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
		
		//TODO: Run this to test for laziness, ie, the intermediate functions only perform the operations when the terminal operation is evaluated, otherwise they just return a stream of elements

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
		
		//TODO: Refactor this method so that only the second overloaded reduce is used, ie, without the combiner,
		//hint: use an intermediate operation before reduce
		
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

		//TODO: Show third overloaded method
		
		//TODO: Refactor the third overloaded method so it can perform mutable reduction

	}

	// if accumulator mutates, use collect(). Otherwise, use reduce()
	private static void mutableReduction() {
		
		//TODO: Show how mutable reduction is done with collect
		
		//TODO: Know the difference between the accumulators of reduce and collect
		
		
		
	}
	
	
	private static void collectToCollection(List<EmployeeReview> employees) {
		
		//TODO: Collect employee reviews to a List with employees with score > 7.0
		
		//TODO: Collect it to a Set
		
		//TODO: Collect it to a TreeSet
		
	}
	
	private static void collectToMap(List<EmployeeReview> employees) {
		
		//TODO: Collect the reviews from the Manager to a simple Map with Employee Id as key and the EmployeeReview as value
		
		//TODO:Collect the combined reviews from the Manager and the TL combined to a Map with Employee Id as key and EmployeeReview as value, choose the review with the higher score as map's value
		
		//TODO: Collect the combined reviews to a treeMap using a Comparator that compares according to higher score
		
		//TODO: Collect to a map with salaryGrade as key and a list of EmployeeReviews grouped according to Employee salary grade
		
		//TODO: Collect to a Map with SalaryGrade as key and another Map as value, the value containing the reviewer as key, and a list of the EmployeeReview that the reviewer did as value
		
		//TODO: Collect to Map with Employee name as key, and the average score from the TL and Manager as value
		
		//TODO: Partition the averaged EmployeeReviews to those who will get CIRP (ave > 8) and those who will not (ave < 8)
		
				
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
