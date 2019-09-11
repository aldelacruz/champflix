package com.champirata.champflix.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.champirata.champflix.constant.Genre;
import com.champirata.champflix.constant.Language;
import com.champirata.champflix.model.TVSeries;
import com.champirata.champflix.service.ChampflixSeriesDataService;


@RestController
public class SeriesController {
	
	@Autowired
	ChampflixSeriesDataService champflixSeriesService;
		
	@GetMapping("/tvseries")
	public ModelAndView getAllTVSeries() {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("allSeries");
        mav.addObject("seriesList", champflixSeriesService.getAllSeries());
		return mav;
	}
	
	@GetMapping("/tvseries-genre/{rating}")
	public   ModelAndView groupByGenreWithRating(@PathVariable double rating) {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("bycategory");
        mav.addObject("shows", champflixSeriesService.groupByGenreWithRating(rating));
		return mav;
	}
	
	//for slice
	@GetMapping("/top-reviewed-by-genre/{genre}")
	public ModelAndView getTopReviewedSeries(@PathVariable String genre) {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("ratingstable");
        mav.addObject("shows", champflixSeriesService.getTopReviewedSeries(Genre.DRAMA));
        return mav;
	}
	
	//for reduce
	@GetMapping("/highest-by-genre/{genre}")
	public   ModelAndView highestRatedByGenre(@PathVariable String genre) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("allSeries");
		List<TVSeries> series = new ArrayList<>();
		series.add(champflixSeriesService.getHighestRatedByGenre(Genre.DRAMA));
		mav.addObject("seriesList", series);
		return mav;
		
	}
	
	//for find
	@GetMapping("/highly-rated-by-language/{language}")
	public ModelAndView find(@PathVariable String language) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("allSeries");
		List<TVSeries> series = new ArrayList<>();
		series.add(champflixSeriesService.find(champflixSeriesService.getAllSeries(), Language.FILIPINO));
		mav.addObject("seriesList", series);
		return mav;
	}
	
	//for match
	@GetMapping("/match")
	public boolean match() {
		return champflixSeriesService.match(champflixSeriesService.getAllSeries());
	}
}
