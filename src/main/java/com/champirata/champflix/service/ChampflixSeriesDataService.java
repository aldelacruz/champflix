package com.champirata.champflix.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.champirata.champflix.constant.Genre;
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
	
	

}
