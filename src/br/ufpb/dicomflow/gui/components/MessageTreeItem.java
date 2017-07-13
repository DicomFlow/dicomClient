package br.ufpb.dicomflow.gui.components;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class MessageTreeItem extends HBox {

	private Label fromText;
	private Label subjectText;
	private ImageView statusImage;
	private Integer idMessage;

	private Label idMessageText;
	private ImageView downloadImage;
	private ImageView replyImage;

	private Label nameText;
	private Label genderText;
	private Label birthdateText;
	private ImageView patientImage;

	private Label typeText;
	private Label descriptionText;
	private Label datetimeText;
	private Label sizeText;
	private ImageView studyImage;




	public MessageTreeItem(Label fromText, Label subjectText, ImageView statusImage, Integer idMessage) {
		super(5);

		this.fromText = fromText;
		this.subjectText = subjectText;
		this.statusImage = statusImage;
		this.idMessage = idMessage;

		this.getChildren().addAll(this.fromText, this.subjectText, this.statusImage);
		this.setAlignment(Pos.CENTER_LEFT);
	}

	public MessageTreeItem(Label idMessageText, ImageView downloadButton, ImageView replyImage) {
		super(5);

		this.idMessageText = idMessageText;
		this.downloadImage = downloadButton;
		this.replyImage = replyImage;

		this.getChildren().addAll(this.idMessageText, this.downloadImage, this.replyImage);
		this.setAlignment(Pos.CENTER_LEFT);
	}

	public MessageTreeItem(Label nameText, Label genderText, Label birthdateText, ImageView patientImage) {
		super(5);

		this.nameText = nameText;
		this.genderText = genderText;
		this.birthdateText = birthdateText;
		this.patientImage = patientImage;

		this.getChildren().addAll(this.patientImage, this.nameText, this.genderText, this.birthdateText );
		this.setAlignment(Pos.CENTER_LEFT);
	}

	public MessageTreeItem(Label typeText, Label descriptionText, Label datetimeText, Label sizeText, ImageView studyImage) {
		super(5);

		this.typeText = typeText;
		this.descriptionText = descriptionText;
		this.datetimeText = datetimeText;
		this.sizeText = sizeText;
		this.studyImage = studyImage;

		this.getChildren().addAll(this.studyImage, this.typeText, this.descriptionText, this.datetimeText, this.sizeText);
		this.setAlignment(Pos.CENTER_LEFT);
	}

	public MessageTreeItem(Label descriptionText, ImageView studyImage) {
		super(5);

		this.descriptionText = descriptionText;
		this.studyImage = studyImage;

		this.getChildren().addAll(this.studyImage, this.descriptionText);
		this.setAlignment(Pos.CENTER_LEFT);
	}



	public Label getFromText() {
		return fromText;
	}

	public void setFromText(Label fromText) {
		this.fromText = fromText;
	}

	public Label getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(Label subjectText) {
		this.subjectText = subjectText;
	}

	public ImageView getDownloadImage() {
		return downloadImage;
	}

	public void setDownloadImage(ImageView downloadImage) {
		this.downloadImage = downloadImage;
	}

	public ImageView getStatusImage() {
		return statusImage;
	}

	public void setStatusImage(ImageView statusImage) {
		this.statusImage = statusImage;
	}

	public ImageView getReplyImage() {
		return replyImage;
	}

	public void setReplyImage(ImageView replyImage) {
		this.replyImage = replyImage;
	}

	public Label getIdMessageText() {
		return idMessageText;
	}

	public void setIdMessageText(Label idMessageText) {
		this.idMessageText = idMessageText;
	}

	public Label getNameText() {
		return nameText;
	}

	public void setNameText(Label nameText) {
		this.nameText = nameText;
	}

	public Label getGenderText() {
		return genderText;
	}

	public void setGenderText(Label genderText) {
		this.genderText = genderText;
	}

	public Label getBirthdateText() {
		return birthdateText;
	}

	public void setBirthdateText(Label birthdateText) {
		this.birthdateText = birthdateText;
	}

	public Label getTypeText() {
		return typeText;
	}

	public void setTypeText(Label typeText) {
		this.typeText = typeText;
	}

	public Label getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(Label descriptionText) {
		this.descriptionText = descriptionText;
	}

	public Label getDatetimeText() {
		return datetimeText;
	}

	public void setDatetimeText(Label datetimeText) {
		this.datetimeText = datetimeText;
	}

	public Label getSizeText() {
		return sizeText;
	}

	public void setSizeText(Label sizeText) {
		this.sizeText = sizeText;
	}

	public ImageView getPatientImage() {
		return patientImage;
	}

	public void setPatientImage(ImageView patientImage) {
		this.patientImage = patientImage;
	}

	public ImageView getStudyImage() {
		return studyImage;
	}

	public void setStudyImage(ImageView studyImage) {
		this.studyImage = studyImage;
	}

	public Integer getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Integer idMessage) {
		this.idMessage = idMessage;
	}

}
