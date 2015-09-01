package com.capgemini.starterkit.bookclient.dataModel;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.starterkit.bookclient.data.BookVO;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class BookSearchModel {
	private final StringProperty title = new SimpleStringProperty();

	private final ListProperty<BookVO> result = new SimpleListProperty<>(
			FXCollections.observableList(new ArrayList<>()));

	public StringProperty titleProperty() {
		return title;
	}

	public String getTitle() {
		return title.getValue();
	}

	public void setTitle(String title){
		this.title.set(title);
	}

	public final List<BookVO> getResult() {
		return result.get();
	}

	public final void setResult(List<BookVO> value) {
		result.setAll(value);
	}

	public ListProperty<BookVO> resultProperty() {
		return result;
	}
}
