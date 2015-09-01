package com.capgemini.starterkit.bookclient.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import com.capgemini.starterkit.bookclient.data.BookVO;
import com.capgemini.starterkit.bookclient.dataModel.BookSearchModel;
import com.capgemini.starterkit.bookclient.getData.DataProviderImpl;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class BookSearchController {

	@FXML
	ResourceBundle resources;

	@FXML
	URL location;

	@FXML
	TextField titleField;

	@FXML
	TextField newTitle;

	@FXML
	TextField newAuthors;

	@FXML
	Button searchButton;

	@FXML
	Button addBookButton;

	@FXML
	TableView<BookVO> resultTable;

	@FXML
	TableColumn<BookVO, Long> idColumn;

	@FXML
	TableColumn<BookVO, String> titleColumn;

	@FXML
	TableColumn<BookVO, String> authorsColumn;

	private final BookSearchModel model = new BookSearchModel();

	public BookSearchController() {
	}

	@FXML
	private void initialize() {
		initializeResultTable();
		titleField.textProperty().bindBidirectional(model.titleProperty());
		resultTable.itemsProperty().bind(model.resultProperty());
		searchButton.disableProperty().bind(titleField.textProperty().isEmpty());
	}

	private void initializeResultTable() {
		idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
		titleColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTitle()));
		authorsColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAuthors()));

		resultTable.setPlaceholder(new Label(resources.getString("table.emptyText")));
	}

	@FXML
	public void searchButtonAction(ActionEvent event) {
		searchButtonAction();
	}

	private void searchButtonAction() {
		Task<Collection<BookVO>> backgroundTask = new Task<Collection<BookVO>>() {
			@Override
			protected Collection<BookVO> call() throws Exception {
				return DataProviderImpl.getBooksByTitle(model.getTitle());
			}
		};
		backgroundTask.stateProperty().addListener(new ChangeListener<Worker.State>() {
			@Override
			public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
				if (newValue == State.SUCCEEDED) {
					if (backgroundTask.getValue() == null) {
						showAlert("The application was unable to connect to the server!");
						return;
					}
					model.setResult(new ArrayList<BookVO>(backgroundTask.getValue()));
					resultTable.getSortOrder().clear();
				}
			}
		});
		new Thread(backgroundTask).start();
	}

	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error!");
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	@FXML
	public void addBook() throws Exception {
		if (newAuthors.getText().equals("") || newTitle.getText().equals("")) {
			showAlert("Wszystkie pola musza byc uzupelnione!");
			return;
		}
		Task<Void> backgroundTask = new Task<Void>() {
			@Override
			public Void call() throws Exception {
				saveBook();
				return null;
			}
		};
		new Thread(backgroundTask).start();
	}

	private void saveBook() throws Exception {
		BookVO bookVO = DataProviderImpl.saveBook(new BookVO(null, newTitle.getText(), newAuthors.getText()));
		if (bookVO == null) {
			showAlert("Zapis ksiazki nie byl mozliwy!");
			return;
		}
		addBookVO(bookVO);
		clearTextFields();
	}

	private void addBookVO(BookVO bookVO) {
		if (titleField.getText() != null) {
			if (bookVO.getTitle().startsWith(titleField.getText())) {
				model.getResult().add(bookVO);
			}
		}

	}

	private void clearTextFields() {
		newTitle.setText("");
		newAuthors.setText("");
	}

}
