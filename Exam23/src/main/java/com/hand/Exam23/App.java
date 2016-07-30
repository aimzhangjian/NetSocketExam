package com.hand.Exam23;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import net.sf.json.JSONObject;

/**
 * Hello world!
 * 
 */
public class App {

	public static void main(String[] args) {

		String address = "http://hq.sinajs.cn/list=sz300170";
		String file = "exam23we.txt";
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
					File fileEnd = new File(file);

					FileOutputStream outputStream = new FileOutputStream(
							fileEnd);
					String uri = fileEnd.getAbsolutePath().toString();

					byte[] buffer = new byte[1024];
					int len = 0;
					if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
						// 回调onFinish()

						while ((len = inStream.read(buffer)) != -1) {
							String str = new String(buffer, "GBK");
							System.out.println(str);
							toJson(str);

							outputStream.write(buffer, 0, len);

						}
					}
					outputStream.close();
					inStream.close();
					httpConn.disconnect();

				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		}).start();

	}

	public static void toJson(String str) {
		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder documentBuild;

			documentBuild = documentFactory.newDocumentBuilder();

			// 创建文档
			Document document = documentBuild.newDocument();
			// 创建一个根元素
			Element root = document.createElement("xml");

			Element stock = document.createElement("stock");

			

			gupiao g = new gupiao();
			int index = str.indexOf("\"");
			str = str.substring(index + 1);

			index = str.indexOf(",");
			String name = str.substring(0, index);
			g.setName(name);

			// 创建元素
			Element lan1 = document.createElement("name");
			// 设置元素文本值
			lan1.setTextContent(name);
			stock.appendChild(lan1);  
			
			str = str.substring(index + 1);

			index = str.indexOf(",");
			double open = Double.parseDouble(str.substring(0, index));

			g.setOpen(open);

			// 创建元素
			Element lan2 = document.createElement("open");
			// 设置元素文本值
			lan2.setTextContent(str.substring(0, index));
 
			stock.appendChild(lan2);
			str = str.substring(index + 1);

			index = str.indexOf(",");
			double close = Double.parseDouble(str.substring(0, index));
			g.setClose(close);

			// 创建元素
			Element lan3 = document.createElement("close");
			// 设置元素文本值
			lan3.setTextContent(str.substring(0, index));

			stock.appendChild(lan3);
			
			str = str.substring(index + 1);

			index = str.indexOf(",");
			double current = Double.parseDouble(str.substring(0, index));
			g.setCurrent(current);

			// 创建元素
			Element lan4 = document.createElement("current");
			// 设置元素文本值
			lan4.setTextContent(str.substring(0, index));

			stock.appendChild(lan4);
			
			str = str.substring(index + 1);

			index = str.indexOf(",");
			double high = Double.parseDouble(str.substring(0, index));
			g.setHigh(high);

			// 创建元素
			Element lan5 = document.createElement("high");
			// 设置元素文本值
			lan5.setTextContent(str.substring(0, index));

			stock.appendChild(lan5);
			
			str = str.substring(index + 1);

			index = str.indexOf(",");
			double low = Double.parseDouble(str.substring(0, index));
			g.setLow(low);

			// 创建元素
			Element lan6 = document.createElement("low");
			// 设置元素文本值
			lan6.setTextContent(str.substring(0, index));
			stock.appendChild(lan6);
			
			root.appendChild(stock);
			document.appendChild(root);
			
			
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			StringWriter writer = new StringWriter();
			transformer.transform(new DOMSource(document), new StreamResult(
					writer));
			System.out.println(writer.toString());
			
			File f=new File("testcreat.xml");
			if(!f.exists()){
				f.createNewFile();
			}
			transformer.transform(new DOMSource(document),new StreamResult(f));
			
			FileOutputStream outStream=new FileOutputStream("testcreatoutSteam.xml");
			BufferedOutputStream buout=new BufferedOutputStream(outStream);
			transformer.transform(new DOMSource(document),new StreamResult(buout));
			
			
			transformer.transform(new DOMSource(document),new StreamResult("testcreat1.xml"));
			
			
			
			
			
			
			
			JSONObject map = JSONObject.fromObject(g);
			String json = map.toString();
			System.out.println(json);
			FileWriter fwrit;
			try {
				fwrit = new FileWriter("myjson.json");
				BufferedWriter wr = new BufferedWriter(fwrit);
				wr.write(json);

				wr.flush();
				wr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
