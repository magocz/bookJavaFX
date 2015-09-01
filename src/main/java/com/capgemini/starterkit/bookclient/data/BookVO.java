package com.capgemini.starterkit.bookclient.data;


public class BookVO {
	private Long id;
	private String title;
	/*
	 * REV: lepiej przechowywac autorow jako kolekcje
	 */
	private String authors;

	public BookVO(){
	}

	public BookVO(Long id, String title, String authors){
		this.setId(id);
		this.setTitle(title);
		this.setAuthors(authors);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}
}
