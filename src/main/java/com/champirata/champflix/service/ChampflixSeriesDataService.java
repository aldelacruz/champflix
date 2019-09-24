package com.champirata.champflix.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.champirata.champflix.constant.Genre;
import com.champirata.champflix.constant.Grade;
import com.champirata.champflix.constant.Language;
import com.champirata.champflix.dataextractor.SeriesReviewExtractor;
import com.champirata.champflix.dataextractor.SeriesReviewExtractor.SeriesReview;
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

	// Slicing operations
	// distinct(), limit(), skip()
	public List<SeriesReview> getTopReviewedSeries(Genre genre) {
		List<SeriesReview> reviews = new ArrayList<>();
		reviews.addAll(SeriesReviewExtractor.getRatingsFromIMDv(genre));
		reviews.addAll(SeriesReviewExtractor.getRatingsFromRottenPotatoes(genre));
		// Print at most 5 DISTINCT TV shows with a rating > 6.0
		// DB: Select distinct (title) from reviews where rating >= 6.0 limit 0, 5;
		return reviews.stream()// .filter(r -> r.getRating() >= 6.0)
				// .distinct()//based on .equals
				// .peek(r -> System.out.println(r.getTitle()))// a method that peeks into the
				// current element beig processed
				// .limit(5)// a short circuit operation, aborts the pipeline after 5th element
				// .skip(5)//skips the first 5 elements, also looks at the first 5 elements,
				// just doesn't pass it down
				// .limit(3)you can have another limit after skippine elements
				// .map(r -> r.getTitle()) //just ike filter but returns a stream of diff Object
				// .forEach(System.out::println);
				.sorted(Comparator.comparing(SeriesReview::getRating).reversed())
				.collect(Collectors.toList());
	}

	// Matching operations
	// anyMatch(), allMatch(), nonMatch();//intermediate operations, returns
	// boolean, all short circuit operations
	// Matching methods demo:
	// (a) Is there at least one rated G that is highly rated?
	// (b) Do all the series have a rating of >7?
	// (c) Is there any TV series with a rating of lower that 5?
	public boolean match(List<TVSeries> series) {

		return series.stream().anyMatch(s -> s.getGrade().equals(Grade.SPG));
	}

	// Finding methods demo
	// (a)At least one Tagalog series that is highly rated
	public TVSeries find(List<TVSeries> series, Language language) {
		Optional<TVSeries> TVseries = series.stream()
				.filter(s -> s.getLanguage().equals(language) && s.getRating() >= 5.0).findAny();
		//.findFirst();
		System.out.println("Series:"+TVseries.get());
		return TVseries.isPresent() ? TVseries.get() : new TVSeries();
	}

	// Reduction operations - terminal operations
	// Immutable reduction
	// Reduce the stream of elements into a single value
	// For example: compute the sum of all numbers, or
	// maxima or minima, eg, lowest rated series
	// First syntax: Optional<T> reduce(BinaryOperator<T> accumulator) -> returns an
	// Optional, takes a binary operator as parameter, an accumulator that works
	// with 2 stream elements at a time
	// Sample: Optional<Integer> sum = Arrays.stream(new
	// Integer[]{5,1,3,6}).reduce(Integer::sum);
	// Sample parallelized: Optional<Integer> sum = Arrays.stream(new
	// Integer[]{5,1,3,6}).parallel.reduce(Integer::sum); //w/o synchronization
	// Find the highest rated drama series
	public TVSeries getHighestRatedByGenre(Genre genre) {

		Optional<TVSeries> series = getAllSeries().stream().filter(s -> s.getGenre().equals(genre))
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

	// Second syntax: T reduce(T identity, BinaryOperator<T> accumulator); if stream
	// is empty, method will return identity (default value)
	// For example:strings.stream().reduce("",(s1, s2) -> s1 +s 2);

	// Third syntax: <U> U reduce(U identity, BiFunction<U,? super T, U>
	// accumulator, BinaryOperator<U> combiner); return U for reduce that takes 3
	// parameters: an identifier, an accumulator function, and a combiner function
	// For example: strings.stream().reduce(()-> new StringBuilder(), (sb, s) ->
	// sb.append(s), (sb1,sb2)->sb1.append(sb2));

	
	public Map collectToMap(List<SeriesReviewExtractor.SeriesReview> reviews) {
		
		/*
		 * List<SeriesReview> reviews = new ArrayList<>();
		 * reviews.addAll(SeriesReviewExtractor.getRatingsFromIMDv(genre));
		 * reviews.addAll(SeriesReviewExtractor.getRatingsFromRottenPotatoes(genre));
		 */
		//HasMap
		//return reviews.stream().collect(Collectors.toMap(r-> r.getTitle(), r->r, (r1, r2) -> r1.getRating()>r2.getRating()?r1:r2));
		
		
		//returns tree map sorted by key
		return reviews.stream().collect(Collectors.toMap(SeriesReview::getTitle, Function.identity(), (r1, r2) -> r1.getRating()>r2.getRating()?r1:r2, () -> new TreeMap()));
	}
	
	
	
	static void overloadedReductions() {
		System.out.println("\noverloadedReductions ... ");

		String[] grades = { "A", "A", "B", "C", "D", "E" };

		// Stream API design principle: API does not differentiate between sequential
		// and parallel streams
		String concat1 = Arrays.stream(grades).reduce("", (s1, s2) -> s1 + s2);
		System.out.println("concat1: " + concat1);

		// Single instance of container is used
		// sb is not threadsafe
		// combiner redundantly combines
		// combiner cannot be null
		// tip: test for correctness: always test output with parallel stream
		StringBuilder concat2 = Arrays.stream(grades).reduce(new StringBuilder(), (sb, s) -> sb.append(s),
				(sb1, sb2) -> sb1.append(sb2));
		System.out.println("concat2: " + concat2);

	}

	static void collect() {

		String[] grades = { "A", "A", "B", "C", "D", "E" };
		//accumulator and combiner are BiConsumer, they don't return anything, they just mutate the container
		StringBuilder concat3 = Arrays.stream(grades).parallel().collect(() -> new StringBuilder(), (sb, s) -> sb.append(s),
				(sb1, sb2) -> sb1.append(sb2));
		StringBuilder concat4 = Arrays.stream(grades).parallel().collect(() -> new StringBuilder(), (sb, s) -> new StringBuilder().append(sb).append(s),
				(sb1, sb2) -> sb1.append(sb2));		
		//Simplified version
		//Collectors is a helper class that performs predefined reductions 
		String concat5 = Arrays.stream(grades).parallel().collect(Collectors.joining());
		System.out.println("concat3: " + concat3);
		System.out.println("concat4: " + concat4);
		System.out.println("concat5: " + concat5);
		
		
	}
	
	
	
	 public static void main(String[] args) {
		 //overloadedReductions();
		 collect();
	 }
}
