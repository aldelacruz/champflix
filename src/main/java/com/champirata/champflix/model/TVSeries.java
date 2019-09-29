package com.champirata.champflix.model;

import javax.persistence.Entity;

import com.champirata.champflix.constant.Genre;
import com.champirata.champflix.constant.Grade;
import com.champirata.champflix.constant.Language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;


@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TVSeries extends Show {
	

	
	public TVSeries(int id, String title, double rating, Genre genre, Language language, Grade grade, String summary,
			String image, String channel, Integer episodes, Integer seasons) {
		super(id, title, rating, genre, language, grade, summary, image);
		this.channel = channel;
		this.episodes = episodes;
		this.seasons = seasons;
	}


	@Column(nullable=false)
	private String channel;
	
	@Column(nullable=true)
	private Integer episodes;
	
	@Column(nullable=true)
	private Integer seasons;
	

}
