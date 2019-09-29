package com.champirata.champflix.dataextractor;

import java.util.Arrays;
import java.util.List;

import com.champirata.champflix.constant.Genre;
import com.champirata.champflix.constant.Grade;
import com.champirata.champflix.constant.Language;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dummy class that's supposed to fetch data from web services.
 */
public class SeriesReviewExtractor {

	@Getter 
	@Setter 
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SeriesReview {		
		
		private String title;	
		private double rating;
		private String source;
		private Genre genre;
		

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
			SeriesReview other = (SeriesReview) obj;
			if (title == null) {
				if (other.title != null)
					return false;
			} else if (!title.equals(other.title))
				return false;
			return true;
		}


		@Override
		public String toString() {
			return "Book [title=" + title + ", rating=" + rating + ", source="
					+ source + "]";
		}
		
	}
	
	public static List<SeriesReview> getRatingsFromIMDv(){
		SeriesReview s1 = new SeriesReview("Kadenang Ginto", 8.5,"IMDv",Genre.DRAMA);
		SeriesReview s2 = new SeriesReview("Los Bastardos", 7.2,"IMDv",Genre.DRAMA);
		SeriesReview s3 = new SeriesReview("Killer Bride", 9.9,"IMDv",Genre.DRAMA);
		SeriesReview s4 = new SeriesReview("The General's Daughter", 6.4,"IMDv",Genre.DRAMA);
		SeriesReview s5 = new SeriesReview("Nang Ngumiti ang Langit", 5.8,"IMDv",Genre.DRAMA);
		SeriesReview s6 = new SeriesReview("Prima Donnas", 7.5,"IMDv",Genre.DRAMA);
		SeriesReview s7 = new SeriesReview("The Better Woman", 4.2,"IMDv",Genre.DRAMA);
		SeriesReview s8 = new SeriesReview("Love You Two", 8.7,"IMDv",Genre.DRAMA);
		SeriesReview s9 = new SeriesReview("Dahil sa Pag-ibig", 3.5,"IMDv",Genre.DRAMA);
		SeriesReview s10 = new SeriesReview("Sahaya", 9.0,"IMDv",Genre.DRAMA);
		
		
		return Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10);
		
	}
	
	public static List<SeriesReview> getRatingsFromRottenPotatoes(){
		SeriesReview s1 = new SeriesReview("Kadenang Ginto", 6.5,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s2 = new SeriesReview("Los Bastardos", 4.2,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s3 = new SeriesReview("Killer Bride", 8.7,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s4 = new SeriesReview("The General's Daughter", 3.4,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s5 = new SeriesReview("Nang Ngumiti ang Langit", 7.8,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s6 = new SeriesReview("Prima Donnas", 8.5,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s7 = new SeriesReview("The Better Woman", 7.2,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s8 = new SeriesReview("Love You Two", 5.7,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s9 = new SeriesReview("Dahil sa Pag-ibig", 4.2,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s10 = new SeriesReview("Sahaya", 9.0,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s11 = new SeriesReview("Onanay", 8.5,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s12 = new SeriesReview("Lobo", 5.4,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s13 = new SeriesReview("Pangako sa Iyo", 10.0,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s14 = new SeriesReview("Forevermore", 8.1,"Rotten Potatoes",Genre.DRAMA);
		SeriesReview s15 = new SeriesReview("Ika-anim na Utos", 9.0,"Rotten Potatoes",Genre.DRAMA);
		
		
		return Arrays.asList(s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12, s13, s14, s15);
		
	}
	
	
}
