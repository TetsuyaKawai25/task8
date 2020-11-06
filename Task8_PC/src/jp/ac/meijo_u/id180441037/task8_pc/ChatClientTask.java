package jp.ac.meijo_u.id180441037.task8_pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import javafx.concurrent.Task;

public class ChatClientTask extends Task<Void>{
	private String server;
	private int port;
	private String name;
	private boolean judge = true;
	
	protected ChatClientTask (String server, int port ,String name ) {
		this.server = server;
		this.port = port;
		this.name = name;
	}
	
	private Socket socket = null;
	private OutputStream os = null;
	private	DataOutputStream dos = null;
	@Override
	protected Void call() throws Exception{
		//	ソケットを作成
		socket = new Socket();
		//	指定されたホスト名（IPアドレス）とポート番号でサーバに接続する
		socket.connect(new InetSocketAddress(server, port));
		// 接続されたソケットの出力ストリームを取得し，データ出力ストリームを連結
		os = socket.getOutputStream();
		dos = new DataOutputStream(os);
		// 参加時の定型文を送信
		dos.writeUTF(createJoinMessage());
		dos.flush();
		
		//	 接続されたソケットの入力ストリームを取得し，データ入力ストリームを連結
		InputStream is = socket.getInputStream();
		DataInputStream dis = new DataInputStream(is);
		
		while(judge) {
			//	 データの受信
			String message = dis.readUTF();
			//	 受信したデータを表示
			updateMessage(message);
		}
		
		// 離脱時の定型文を送信
		dos.writeUTF(createScessionMessage());
		dos.flush();
		
		return null;
	}
	
	protected Socket getSocket() {
		return this.socket;
	}
	
	protected void changeFalse() {
		this.judge = false;
	}
	
	protected DataOutputStream getDOS() {
		return this.dos;
	}
	
	protected String createJoinMessage() {
		String message;
		message = this.name + "が参加しました.";
		return message;
	}
	
	protected String createScessionMessage() {
		String message;
		message = this.name + "が離脱しました.";
		return message;
	}
}
