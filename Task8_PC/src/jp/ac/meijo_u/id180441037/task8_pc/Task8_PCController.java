package jp.ac.meijo_u.id180441037.task8_pc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Task8_PCController {
	@FXML private TextField ipAdress;
	@FXML private TextField portNumber;
	@FXML private TextField name;
	@FXML private TextField message;
	@FXML private Button buttonConnect;
	@FXML private Button buttonDisconnect;
	@FXML private Button buttonSend;
	@FXML private TextArea chatArea;
	private ChatClientTask clientTask;
	private SendTask sendTask;
	
	@FXML
	public void handleButtonConnectAction(ActionEvent event) {
		int port = Integer.parseInt(portNumber.getText());
		clientTask = new ChatClientTask(ipAdress.getText(),port,name.getText());
		//テキストエリアのテキストプロパティとタスクのメッセージプロパティをbind
		chatArea.textProperty().bind(clientTask.messageProperty());
		
		Thread thread = new Thread(clientTask);
		thread.setDaemon(true);
		thread.start();
	}
	
	
	@FXML
	public void handleButtonDisconnectAction(ActionEvent event) {
			clientTask.cancel(true);
	}
		
	
	@FXML
	public void handleButtonSendAction(ActionEvent event) {
		sendTask = new SendTask(clientTask.getSocket(),name.getText(),message.getText());
	
		Thread thread = new Thread(sendTask);
		thread.setDaemon(true);
		thread.start();
		
	}
}
