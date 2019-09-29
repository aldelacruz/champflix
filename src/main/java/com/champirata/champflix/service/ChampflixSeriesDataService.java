package com.champirata.champflix.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	private List<SeriesReview> getAllReviews() {
		List<SeriesReview> reviews = new ArrayList<>();
		reviews.addAll(SeriesReviewExtractor.getRatingsFromIMDv());
		reviews.addAll(SeriesReviewExtractor.getRatingsFromRottenPotatoes());
		return reviews;
	}

	private TVSeries getDefaultBlankSeries() {
		return new TVSeries(0, "Champflix is LIFE!", 10.00, Genre.MYSTERY, Language.FILIPINO, Grade.X,
				"No results found.", "champflix.JPG", "XXX", 0, 0);
	}

	// Slicing operations
	// distinct(), limit(), skip()
	public List<SeriesReview> getTopReviewedSeries(Genre genre) {
		List<SeriesReview> reviews = getAllReviews();
		return reviews.stream()
				// DB: Select distinct (title) from reviews where rating >= 6.0 limit 0, 5;
				// TODO: Print at most 5 DISTINCT reviews with a rating > 6.0
				// Your codes here
				.sorted(Comparator.comparing(SeriesReview::getRating).reversed()).collect(Collectors.toList());
	}

	// Matching operations
	public boolean isChildSafe(List<TVSeries> series) {
		// TODO: Provide a simple check if the list does not contain any series that is
		// rated SPG
		return true;
	}

	// Finding operations
	public TVSeries find(List<TVSeries> series, Language language) {
		// TODO: Find at least one Tagalog series that is rated > 6
		// return TVseries.isPresent() ? TVseries.get() : getDefaultBlankSeries();
		return null;
	}

	// reduce
	public TVSeries getHighestRatedByGenre(Genre genre) {
		// TODO: Refactor this imperative method to a declarative one using Streams
		TVSeries result = null;
		List<TVSeries> series = getAllSeries();
		for (TVSeries TVseries : series) {
			if (result == null) {
				if (TVseries.getGenre().equals(genre)) {
					result = TVseries;
				}
				continue;
			}
			if (TVseries.getGenre().equals(genre) && TVseries.getRating() > TVseries.getRating()) {
				result = TVseries;
			}
		}
		if (result == null) {
			result = getDefaultBlankSeries();
		}
		return result;
	}

	// collect using Collectors
	public Map<Genre, List<TVSeries>> groupByGenreWithRating(double rating) {
		// TODO: Refactor this imperative method to a declarative one using Streams,
		// sort each list in the map in desc rating manner
		List<TVSeries> series = getAllSeries();
		Map<Genre, List<TVSeries>> seriesByGenre = new HashMap<>();
		for (TVSeries tvSeries : series) {
			if (tvSeries.getRating() >= rating) {
				Genre genre = tvSeries.getGenre();
				List<TVSeries> seriesList = seriesByGenre.get(genre);
				if (seriesList == null) {
					seriesList = new ArrayList<>();
					seriesByGenre.put(genre, seriesList);
				}
				seriesList.add(tvSeries);
			}
		}
		return seriesByGenre;
	}

}
