package jp.ac.meijo_u.id180441037.task8_pc;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javafx.concurrent.Task;

public class SendTask extends Task<Void>{
	private String name;
	private Socket socket = null;
	private String message;
	
	protected SendTask (Socket socket,String name, String message) {
		this.socket = socket;
		this.name = name;
		this.message = message;
	}
	
	@Override
	protected Void call() throws Exception{
		// 接続されたソケットの出力ストリームを取得し，データ出力ストリームを連結
		OutputStream os = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(os);
		dos.writeUTF(name+"\t:"+message);
		dos.flush();
		return null;
	}
}
