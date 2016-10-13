package br.ufpb.dicomflow.gui.components;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class CustomTreeItem extends HBox {

	private Label boxText;
	private Button boxButton;
	private Node graphic;

	public CustomTreeItem(Label txt) {
		super();

		this.boxText = txt;

		this.getChildren().add(boxText);
		this.setAlignment(Pos.CENTER_LEFT);
		
	}

	public CustomTreeItem(Label txt, Button bt, Node graphic) {
		super(5);

		this.boxText = txt;
		this.boxButton = bt;

		this.getChildren().addAll(graphic, boxText, boxButton);
		this.setAlignment(Pos.CENTER_LEFT);
	}
	
	public CustomTreeItem(Label txt, Node graphic) {
		super(5);

		this.boxText = txt;

		this.getChildren().addAll(graphic, boxText);
		this.setAlignment(Pos.CENTER_LEFT);
	}

	public Label getBoxText() {
		return boxText;
	}

	public void setBoxText(Label boxText) {
		this.boxText = boxText;
	}

	public Button getBoxButton() {
		return boxButton;
	}

	public void setBoxButton(Button boxButton) {
		this.boxButton = boxButton;
	}

	public Node getGraphic() {
		return graphic;
	}

	public void setGraphic(Node graphic) {
		this.graphic = graphic;
	}
	
	

}
