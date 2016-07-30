package com.hand.Exam22;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		ServerSocket servSocket;
		try {
			servSocket = new ServerSocket(12345);
			Socket socket = servSocket.accept();

			FileInputStream instrem = new FileInputStream("post.pdf");
			byte byt[] = new byte[1024];
			int leng;
			while ((leng=instrem.read(byt))!=-1) {
				socket.getOutputStream().write(byt,0,leng);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
