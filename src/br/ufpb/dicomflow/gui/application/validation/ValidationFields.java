package br.ufpb.dicomflow.gui.application.validation;

import java.util.ArrayList;
import java.util.List;

import br.ufpb.dicomflow.gui.application.SceneLoader;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class ValidationFields {

	private static final String STILE_BORDER_VALIDATION = "-fx-border-color: #FF0000";


	public static boolean checkRqueridFields(Node... itemToCheck) {

		final Tooltip toolTip = new Tooltip("Este Campo é requerido.");

		// used to determinate how many fields failed in validation
		List<Node> failedFields = new ArrayList<>();
		toolTip.setStyle("-fx-background-color: linear-gradient(#FF6B6B , #FFA6A6 ); -fx-font-weight: bold;");
		SceneLoader.getInstance().hackTooltipStartTiming(toolTip);

		for (Node n : arrayToList(itemToCheck)) {

			// Validate TextFields
			if(n instanceof TextField) {
				TextField textField = (TextField) n;
				textField.textProperty().addListener(
						(ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
							removeToolTipAndBorderColor(textField, toolTip);
						});
				if(textField.getText() == null || textField.getText().trim().equals("")) {
					failedFields.add(n);
					addToolTipAndBorderColor(textField, toolTip);
				} else {
					removeToolTipAndBorderColor(textField, toolTip);
				}
			}

			else if(n instanceof PasswordField) {
				PasswordField passwordField = (PasswordField) n;
				passwordField.textProperty().addListener(
						(ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
							removeToolTipAndBorderColor(passwordField, toolTip);
						});
				if (passwordField.getText() == null || passwordField.getText().trim().equals("")) {
					failedFields.add(n);
					addToolTipAndBorderColor(passwordField, toolTip);
				} else {
					removeToolTipAndBorderColor(passwordField, toolTip);
				}
			}

			// Validate Combo Box
			else if (n instanceof ComboBox) {
				ComboBox comboBox = (ComboBox) n;
				comboBox.valueProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
					removeToolTipAndBorderColor(comboBox, toolTip);
				});
				if (comboBox.getValue() == null) {
					failedFields.add(n);
					addToolTipAndBorderColor(comboBox, toolTip);
				} else {
					removeToolTipAndBorderColor(comboBox, toolTip);
				}
			}

			// Validate TextArea
			else if (n instanceof TextArea) {
				TextArea textArea = (TextArea) n;
				textArea.textProperty().addListener(
						(ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
							removeToolTipAndBorderColor(textArea, toolTip);
						});
				if (textArea.getText() == null || textArea.getText().trim().equals("")) {
					failedFields.add(n);
					addToolTipAndBorderColor(textArea, toolTip);
				} else {
					removeToolTipAndBorderColor(textArea, toolTip);
				}
			}

		}

		return failedFields.isEmpty();
	}

	public static boolean checkEmptyFields(Node itemToCheck) {


			// Validate TextFields
			if(itemToCheck instanceof TextField) {
				TextField textField = (TextField) itemToCheck;
				return textField.getText() == null || textField.getText().trim().equals("");
			}

			// Validate PasswordFields
			else if(itemToCheck instanceof PasswordField) {
				PasswordField passwordField = (PasswordField) itemToCheck;
				return passwordField.getText() == null || passwordField.getText().trim().equals("");

			}

			else if (itemToCheck instanceof ComboBox) {
				ComboBox comboBox = (ComboBox) itemToCheck;
				return comboBox.getValue() == null;
			}

			// Validate TextArea
			else if (itemToCheck instanceof TextArea) {
				TextArea textArea = (TextArea) itemToCheck;
				return textArea.getText() == null || textArea.getText().trim().equals("");
			}

			return false;
	}

	/**
	 * *******ADD AND REMOVE STYLES********
	 */
	private static void addToolTipAndBorderColor(Node n, Tooltip t) {
		Tooltip.install(n, t);
		n.setStyle(STILE_BORDER_VALIDATION);
	}

	private static void removeToolTipAndBorderColor(Node n, Tooltip t) {
		Tooltip.uninstall(n, t);
		n.setStyle(null);
	}

	/**
	 * ***********ARRAY TO LIST UTILITY************
	 */
	private static List<Node> arrayToList(Node[] n) {
		List<Node> listItems = new ArrayList<>();
		for (Node n1 : n) {
			listItems.add(n1);
		}
		return listItems;
	}

	public static boolean checkMailFormat(TextField mail) {

		String mailRegex = "[A-Za-z0-9\\._-]+@[A-Za-z0-9]+(\\.[A-Za-z]+)*";

		final Tooltip toolTip = new Tooltip("E-mail com formato inválido");

		List<Node> failedFields = new ArrayList<>();
		toolTip.setStyle("-fx-background-color: linear-gradient(#FF6B6B , #FFA6A6 ); -fx-font-weight: bold;");
		SceneLoader.getInstance().hackTooltipStartTiming(toolTip);

		mail.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					removeToolTipAndBorderColor(mail, toolTip);
				});
		if (mail.getText() == null || !mail.getText().matches(mailRegex)) {
			failedFields.add(mail);
			addToolTipAndBorderColor(mail, toolTip);
		} else {
			removeToolTipAndBorderColor(mail, toolTip);
		}

		return failedFields.isEmpty();
	}

	public static boolean checkAliasFormat(TextField alias) {

		String aliasRegex = "[A-Za-z0-9]+";

		final Tooltip toolTip = new Tooltip("Apeliado com formato inválido");

		List<Node> failedFields = new ArrayList<>();
		toolTip.setStyle("-fx-background-color: linear-gradient(#FF6B6B , #FFA6A6 ); -fx-font-weight: bold;");
		SceneLoader.getInstance().hackTooltipStartTiming(toolTip);

		alias.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					removeToolTipAndBorderColor(alias, toolTip);
				});
		if (alias.getText() == null || !alias.getText().matches(aliasRegex)) {
			failedFields.add(alias);
			addToolTipAndBorderColor(alias, toolTip);
		} else {
			removeToolTipAndBorderColor(alias, toolTip);
		}

		return failedFields.isEmpty();
	}

	public static boolean checkCountryFormat(TextField country) {

		String countryRegex = "[A-Za-z][A-Za-z]";

		final Tooltip toolTip = new Tooltip("País com formato inválido");

		List<Node> failedFields = new ArrayList<>();
		toolTip.setStyle("-fx-background-color: linear-gradient(#FF6B6B , #FFA6A6 ); -fx-font-weight: bold;");
		SceneLoader.getInstance().hackTooltipStartTiming(toolTip);

		country.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					removeToolTipAndBorderColor(country, toolTip);
				});
		if (country.getText() == null || !country.getText().matches(countryRegex)) {
			failedFields.add(country);
			addToolTipAndBorderColor(country, toolTip);
		} else {
			removeToolTipAndBorderColor(country, toolTip);
		}

		return failedFields.isEmpty();
	}

	public static boolean checkPasswordEquals(PasswordField password, PasswordField repassword) {

		final Tooltip toolTip = new Tooltip("Não confere com a senha informada");

		List<Node> failedFields = new ArrayList<>();
		toolTip.setStyle("-fx-background-color: linear-gradient(#FF6B6B , #FFA6A6 ); -fx-font-weight: bold;");
		SceneLoader.getInstance().hackTooltipStartTiming(toolTip);

		repassword.textProperty()
				.addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
					removeToolTipAndBorderColor(repassword, toolTip);
				});
		if (password.getText() == null || repassword.getText() == null
				|| !password.getText().equals(repassword.getText())) {
			failedFields.add(repassword);
			addToolTipAndBorderColor(repassword, toolTip);
		} else {
			removeToolTipAndBorderColor(repassword, toolTip);
		}

		return failedFields.isEmpty();
	}
}
