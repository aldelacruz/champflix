package com.champirata.champflix.repository;

import org.springframework.data.repository.CrudRepository;

import com.champirata.champflix.model.TVSeries;


public interface SeriesRepository  extends CrudRepository<TVSeries, Long>{

}
