package br.com.verdinhas.gafanhoto.url;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;

@Getter
public class Url {

	@Id
	public String id;

	private String url;

	private List<String> urlWords;

	private Date date;

	public Url(String url, List<String> urlWords, Date date) {
		this.url = url;
		this.urlWords = urlWords;
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Url other = (Url) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	
}
