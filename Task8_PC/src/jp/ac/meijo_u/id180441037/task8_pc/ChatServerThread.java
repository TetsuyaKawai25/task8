package jp.ac.meijo_u.id180441037.task8_pc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServerThread extends Thread{
	private Socket socket = null;
	private OutputStream os = null;
	private DataOutputStream dos = null;
	private InputStream is = null;
	private DataInputStream dis = null;
	protected static ArrayList<ChatServerThread> threadList ;
	private String messageBackground = "";
	
	protected ChatServerThread (Socket socket, ArrayList<ChatServerThread> list) {
		setSocket(socket);
		setThreadList(list);
	}
	
	protected void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	protected Socket getSocket() {
		return this.socket;
	}
	
	protected void setMessageBackground(String message) {
		this.messageBackground += message + "\n";
	}
	
	protected String getMessageBackground(){
		return this.messageBackground;
	}
	
	protected void setThreadList(ArrayList<ChatServerThread> list) {
		threadList = list;
	}
	
	@Override
	public void run() {
		try{			
			while (true) {
				is = socket.getInputStream();
				dis = new DataInputStream(is);
				
				//	 データの受信
				String message = dis.readUTF();
				
					//	脱退用の処理を書いたがうまくいかなかった
					/*	if(message.equals("end")) {
							for(i=0;i<threadList.size();i++) {
								if(threadList.get(i).socket == this.socket) {
									threadList.remove(threadList.get(i));
								}
							}
						}
					*/
				
				for(int i=0;i<threadList.size();i++) {
					//	データを今までのメッセージに付け足していく
					threadList.get(i).setMessageBackground(message);
					//	静的なスレッドリストをすべて読み込んで参加者の各ソケットに対してメッセージを送信
					os = threadList.get(i).getSocket().getOutputStream();
					dos = new DataOutputStream(os);
					dos.writeUTF(threadList.get(i).getMessageBackground());
					dos.flush();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			//	 ソケットをクローズする
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {}
			}
		}
	}
}
