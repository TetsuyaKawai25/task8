package jp.ac.meijo_u.id180441037.task8_pc;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	/**	 最大接続数（100クライアント） */
	private static final int MAX_CONNECTION = 100;
	private static ServerSocket serverSocket;
	private static ArrayList<ChatServerThread> threadList = new ArrayList<ChatServerThread>();
	
	public static void main(String[] args) {
		//	 コマンドライン引数からポート番号を取得
		int port = Integer.parseInt(args[0]);
		Socket socket = null;
		try {
			serverSocket = new ServerSocket();
			serverSocket.setReuseAddress(true);
			//	 指定ポート番号にバインド
			serverSocket.bind(new InetSocketAddress(port), MAX_CONNECTION);
			System.out.println("ポート" + port + "番をリッスン中...");
			
			
			while (true) {
				//	 クライアントからの接続要求を待機（クライアント接続するまでブロッキング）
				socket = serverSocket.accept();
				//接続されたクライアント情報の表示
				showClientInformation(socket);
				
				//	サブクラスのインスタンスを作成
				ChatServerThread thread = new ChatServerThread(socket,threadList);
				//	作成したインスタンスのstart()メソッドを呼び出す
				thread.start();
				//	作成したスレッドをリストに追加
				threadList.add(thread);
			}
			
		} catch (Exception e) {
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
	
	/**
	 * 接続したクライアントの情報を表示する
	 * @param socket 接続済みソケットオブジェクト
	 */
	private static void showClientInformation(Socket socket) throws IOException {
		// クライアントのIPアドレスを取得
		InetAddress address = socket.getInetAddress();
		// クライアントのポート番号を取得
		int port = socket.getPort();
		
		System.out.println("クライアント[" + address.toString() + ":" + port +
				"] が接続しました." );
	}
}
