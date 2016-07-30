package com.hand.Exam22;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Clin {
	public static void main(String arg[]) {
		try {
			BufferedReader reader;
			Socket socket;
			socket = new Socket("127.0.0.1", 12345);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			
			
			BufferedInputStream inbuf = new BufferedInputStream(socket.getInputStream());
			
			
			FileOutputStream outfil = new FileOutputStream("post2.pdf");
			BufferedOutputStream oubuf = new BufferedOutputStream(outfil);
			
			byte byt[] = new byte[1024];
			
			while ((inbuf.read(byt)) != -1) {
				oubuf.write(byt);
			}
			
			inbuf.close();
			oubuf.close();
			outfil.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
