package com.dmg.example;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 这个服务器可以处理对指定目录中的静态资源的请求，该目录包括public静态变量WEB_ROOT指明的目录及所有子目录
 * 
 * @author tanzhe
 *
 */
public class HttpServer {
	/**
	 *  web根目录，存放静态资源的目录
	 */
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	
	/**
	 *  停止命令
	 */
	private static final String SHUTDOWN = "/SHUTDOWN";
	
	/**
	 *  停止命令是否已收到
	 */
	private boolean shutdown = false;
	
	public static void main(String[] args) {
		HttpServer httpServer = new HttpServer();
		httpServer.await();
	}
	
	public void await() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (!shutdown) {
			Socket socket = null;
			InputStream in = null;
			OutputStream os = null;
			
			try {
				socket = serverSocket.accept();
				in = socket.getInputStream();
				os = socket.getOutputStream(); 
				
				// 创建一个请求对象并解析
				Request request = new Request(in);
				request.parse();
				
				// 创建一个响应对象
				Response response = new Response(os);
				response.setRequest(request);
				response.sendStaticResources();
				
				// 关闭socket
				socket.close();
				
				// 检查是否是关闭请求
				shutdown = request.getUri().equals(SHUTDOWN);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
