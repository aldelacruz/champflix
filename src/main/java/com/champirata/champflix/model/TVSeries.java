package com.champirata.champflix.model;

import javax.persistence.Entity;
import javax.persistence.Column;



@Entity
public class TVSeries extends Show {
	
			
	@Column(nullable=false)
	private String channel;
	
	@Column(nullable=true)
	private Integer episodes;
	
	@Column(nullable=true)
	private Integer seasons;
	

	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public Integer getEpisodes() {
		return episodes;
	}
	public void setEpisodes(Integer episodes) {
		this.episodes = episodes;
	}
	public Integer getSeasons() {
		return seasons;
	}
	public void setSeasons(Integer seasons) {
		this.seasons = seasons;
	}
	
	
	
	

}
