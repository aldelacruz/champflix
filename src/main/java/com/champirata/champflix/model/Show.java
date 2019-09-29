package com.champirata.champflix.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.champirata.champflix.constant.Genre;
import com.champirata.champflix.constant.Grade;
import com.champirata.champflix.constant.Language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Show {
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(nullable=false)	
	private int id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private double rating;
	
	@Column(nullable=false)
	private Genre genre;
	
	@Column(nullable=false)
	private Language language;
	
	@Column(nullable=false)
	private Grade grade;
	
	@Column(nullable=true)
	private String summary;
	
	@Column(nullable=true)
	private String image;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Show other = (Show) obj;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Show [id=" + id + ", title=" + title + ", rating=" + rating + ", genre=" + genre + ", language="
				+ language + ", grade=" + grade + "]";
	}
	
	
	

}
