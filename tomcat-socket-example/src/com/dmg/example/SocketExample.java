package com.dmg.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketExample {
	
	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		Socket socket = new Socket("127.0.0.1", 80);
		OutputStream os = socket.getOutputStream();
		boolean autoFlush = true;
		PrintWriter out = new PrintWriter(os, autoFlush);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// 发送http请求
		out.print("GET /index.jsp HTTP/1.1");
		out.print("Host: localhost:8080");
		out.print("Connection: Close");
		out.print("");
		
		// 读取响应
		boolean loop = true;
		StringBuilder sb = new StringBuilder(8096);
		while (loop) {
			if (in.ready()) {
				int i = 0;
				while (i != -1) {
					i = in.read();
					sb.append( (char) i);
				}
				loop = false;
			}
			Thread.currentThread().sleep(50);
		}
		
		// 展示响应信息
		System.out.println(sb.toString());
		socket.close();
	}
}