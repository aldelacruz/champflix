package com.champirata.champflix.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champirata.champflix.constant.Genre;
import com.champirata.champflix.constant.Language;
import com.champirata.champflix.model.TVSeries;
import com.champirata.champflix.repository.SeriesRepository;


@Service
public class ChampflixSeriesDataService {
	
	@Autowired
	SeriesRepository seriesRepo;

	public List<TVSeries> getAllSeries() {
		List<TVSeries> series = new ArrayList<>();
		seriesRepo.findAll().forEach(series::add);
		return series;
	}
	
	public Map<Genre, List<TVSeries>> groupByGenreWithRating(double rating) {
		return getAllSeries().stream().filter(tvSeries -> tvSeries.getRating() >= rating)
				.sorted(Comparator.comparing(TVSeries::getRating).reversed())
				.collect(Collectors.groupingBy(TVSeries::getGenre));
	}
	
	
	//Slicing methods demo: Get data from 2 show review sites and display the top 10 highly rated drama TV series from both sites combined
	//DB equivalent: Select distinct (title) from tvseries where rating =>8 limit 0, 10;
	public List<TVSeries> getHighestRatedFromReviewSites(){
		
		return new ArrayList<TVSeries>();
	}
	
	//Matching methods demo:
	//(a) Is there at least one rated G that is highly rated?
	//(b) Do all the series have a rating of >7?
	//(c) Is there any TV series with a rating of lower that 5?
	public boolean match(List<TVSeries> series){
		
		return false;
	}

	//Finding methods demo
	//(a)At least one Tagalog series that is highly rated
	public TVSeries find(List<TVSeries> series) {
		Optional<TVSeries> TVseries = series.stream()
				.filter(s -> s.getLanguage().equals(Language.FILIPINO) && s.getRating() >= 8.0)
				.findAny();
		
		if(TVseries.isPresent()) {
			return TVseries.get();
		}
		return new TVSeries();
	}
	
	//Reduction operations - terminal operations
	//Reduce the stream of elements into a single value
	//For example: compute the sum of all numbers, or 
	//maxima or minima, eg, lowest rated series
	//First syntax: Optional<T> reduce(BinaryOperator<T> accumulator) -> returns an Optional, takes a binary operator as parameter, an accumulator that works with 2 stream elements at a time
	//Sample: Optional<Integer> sum = Arrays.stream(new Integer[]{5,1,3,6}).reduce(Integer::sum);
	//Sample parallelized: Optional<Integer> sum = Arrays.stream(new Integer[]{5,1,3,6}).parallel.reduce(Integer::sum); //w/o synchronization
	//Find the highest rated drama series
	public TVSeries highestRatedByGenre(Genre genre) {

		Optional<TVSeries> series = getAllSeries().stream().filter(s -> s.getGenre().equals(Genre.DRAMA))
				.reduce((s1, s2) -> s1.getRating() >= s2.getRating() ? s1 : s2);
		return series.isPresent() ? series.get() : new TVSeries();

		// IMPERATIVE:
		/*
		 * TVSeries result = null; List<TVSeries> series = getAllSeries(); for (TVSeries
		 * TVseries : series) { // Initialize result with first book having rating >=
		 * 4.5 if (result == null) { if (TVseries.getGenre().equals(Genre.DRAMA)) {
		 * result = TVseries; } continue; }
		 * 
		 * if (TVseries.getGenre().equals(Genre.DRAMA) && result.getRating() >
		 * TVseries.getRating()) { result = TVseries; } }
		 * 
		 * System.out.println("(Imperative) Highest rated Drama: " + result);
		 */
	}
	
	//Second syntax: T reduce(T identity, BinaryOperator<T> accumulator); if stream is empty, method will return identity (default value)
	//For example:strings.stream().reduce("",(s1, s2) -> s1 +s 2);
	
	//Third syntax: <U> U reduce(U identity, BiFunction<U,? super T, U> accumulator, BinaryOperator<U> combiner); return U for reduce that takes 3 parameters: an identifier, an accumulator function, and a combiner function
	//For example: strings.stream().reduce(()-> new StringBuilder(), (sb, s) -> sb.append(s), (sb1,sb2)->sb1.append(sb2));
	
	static void overloadedReductions() {
		System.out.println("\noverloadedReductions ... ");

		String[] grades = { "A", "A", "B", "C", "D", "E" };

		String concat1 = Arrays.stream(grades).reduce("", (s1, s2) -> s1 + s2);
		System.out.println("concat1: " + concat1);

		StringBuilder concat2 = Arrays.stream(grades).reduce(new StringBuilder(), (sb, s) -> sb.append(s),
				(sb1, sb2) -> sb1.append(sb2));
		System.out.println("concat2: " + concat2);

	}
	
	public static void main(String[] args) {
		overloadedReductions();
	}
	
	//Collect - collect elements into a container
	//Mutable reduction
	
	
	
	
	
	 /***** Here We Are Creating A 'Sequential Stream' & Displaying The Result *****/
	/*
	 * t1 = System.currentTimeMillis();
	 * System.out.println("Sequential Stream Count?= " + eList.stream().filter(e ->
	 * e.getSalary() > 15000).count());
	 * 
	 * t2 = System.currentTimeMillis();
	 * System.out.println("Sequential Stream Time Taken?= " + (t2-t1) + "\n");
	 */
    /***** Here We Are Creating A 'Parallel Stream' & Displaying The Result *****/
	/*
	 * t1 = System.currentTimeMillis();
	 * System.out.println("Parallel Stream Count?= " +
	 * eList.parallelStream().filter(e -> e.getSalary() > 15000).count());
	 * 
	 * t2 = System.currentTimeMillis();
	 * System.out.println("Parallel Stream Time Taken?= " + (t2-t1));
	 */
	
	/*
	 * Do remember, Parallel Streams must be used only with stateless,
	 * non-interfering, and associative operations i.e.
	 * 
	 * A stateless operation is an operation in which the state of one element does
	 * not affect another element A non-interfering operation is an operation in
	 * which data source is not affected An associative operation is an operation in
	 * which the result is not affected by the order of operands
	 */
	
}
