package com.hand.Exam21;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Hello world!
 * 
 */
public class App {
	public static void main(String[] args) {
		String address = "http://files.saas.hand-china.com/java/target.pdf";
		String file = "post.pdf";
		getByteDate(address, file);
	}

	public static void getByteDate(final String address, final String file) {
		new Thread(new Runnable() {

			public void run() {
				try {
					/**
					 * 
					 * HttpURLConnection post请求
					 */

					URL url = new URL(address);

					HttpURLConnection httpConn = (HttpURLConnection) url
							.openConnection();

					// get请求方式
					httpConn.setRequestMethod("GET");

					httpConn.setConnectTimeout(8000);
					httpConn.setReadTimeout(8000);
					// 取得输入流，并使用Reader读取
					InputStream inStream = httpConn.getInputStream();
					BufferedInputStream inbuf = new BufferedInputStream(inStream);

					File fileEnd = new File(file);
                     
					
					FileOutputStream outputStream = new FileOutputStream(
							fileEnd);
					BufferedOutputStream oubuf = new BufferedOutputStream(outputStream);
					
					String uri = fileEnd.getAbsolutePath().toString();

					byte[] buffer = new byte[1024];
					int len = 0;
					if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						// 回调onFinish()

						while ((len = inbuf.read(buffer)) != -1) {
						
							oubuf.write(buffer, 0, len);
							
						}
					}
					
					inStream.close();
					inbuf.close();
					oubuf.flush();
					oubuf.close();
					outputStream.close();
					httpConn.disconnect();

				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		}).start();

	}

}
