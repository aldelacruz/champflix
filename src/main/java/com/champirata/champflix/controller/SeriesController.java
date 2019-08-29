package com.champirata.champflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.champirata.champflix.service.ChampflixSeriesDataService;


@RestController
public class SeriesController {
	
	@Autowired
	ChampflixSeriesDataService champflixSeriesService;
	
	/*
	 * @GetMapping("/tvseries") public List<TVSeries> getAllTVSeries() { return
	 * champflixSeriesService.getAllSeries(); }
	 */
	
	@GetMapping("/tvseries")
	public ModelAndView getAllTVSeries() {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("allSeries");
        mav.addObject("seriesList", champflixSeriesService.getAllSeries());
		return mav;
	}
	
	/*
	 * @GetMapping("/tvseries-genre/{rating}") public Map<Genre, List<TVSeries>>
	 * groupByGenreWithRating(@PathVariable double rating) { return
	 * champflixSeriesService.groupByGenreWithRating(rating); }
	 */
	
	@GetMapping("/tvseries-genre/{rating}")
	public   ModelAndView groupByGenreWithRating(@PathVariable double rating) {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("bycategory");
        mav.addObject("seriesList", champflixSeriesService.groupByGenreWithRating(rating));
		return mav;
	}
	
	

}
