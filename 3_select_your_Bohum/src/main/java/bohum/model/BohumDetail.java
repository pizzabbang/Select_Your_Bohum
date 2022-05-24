package bohum.model;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

public class BohumDetail {
	
	public ArrayList<BohumDataBean> getHohumDetail(String age) {
		ArrayList<BohumDataBean> bohumTestInfoArr = new ArrayList<BohumDataBean>();
		try {
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1160100/service/GetMedicalReimbursementInsuranceInfoService/getInsuranceInfo"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=wKQumq5LX0aGJN19E3mLdne0GDiEtPtVpVY3tDVBkOYPc21sBxDu%2B4lUggPaO0ETQboYKIVcYuGsd5lxtqhYoQ%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝占� �뜝�룞�삕*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*�뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�샇*/
			urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*�뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�뜝占�(xml/json)*/
			urlBuilder.append("&" + URLEncoder.encode("age","UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")); /*�뜝�룞�삕�뜝�룞�삕*/       
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;
	        InputStreamReader in = new InputStreamReader((InputStream) conn.getContent(), "UTF-8");
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(in);
	        }  else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			System.out.println(sb.toString());

			String myData = sb.toString();
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(myData);

			JSONObject jsonObj = (JSONObject)obj;
			JSONObject jsonResponse = (JSONObject)jsonObj.get("response");
			JSONObject jsonBody = (JSONObject)jsonResponse.get("body");
			JSONObject jsonItems = (JSONObject)jsonBody.get("items");
			JSONArray jsonItem = (JSONArray)jsonItems.get("item");
			System.out.println("basDt \t cmpyCd \t cmpyNm \t ptrn \t mog \t prdNm \t age \t mlInsRt \t fmlInsRt \t");
			for(int i=0;i<jsonItem.size();i++) {
				JSONObject item = (JSONObject)jsonItem.get(i);
				System.out.print(item.get("basDt")+"\t");
				System.out.print(item.get("cmpyCd")+"\t");
				System.out.print(item.get("cmpyNm")+"\t");
				System.out.print(item.get("ptrn")+"\t");
				System.out.print(item.get("mog")+"\t");
				System.out.print(item.get("prdNm")+"\t");
				System.out.print(item.get("age")+"\t");
				System.out.print(item.get("mlInsRt")+"\t");
				System.out.println(item.get("fmlInsRt")+"\t");
				
				String basDt = (String)item.get("basDt");
				String cmpyCd = (String)item.get("cmpyCd");
				String cmpyNm = (String)item.get("cmpyNm");
				String ptrn = (String)item.get("ptrn");
				String mog = (String)item.get("mog");
				String prdNm = (String)item.get("prdNm");
				String mlInsRt = (String)item.get("mlInsRt");
				String fmlInsRt = (String)item.get("fmlInsRt");
				
				BohumDataBean bohumTestBean = new BohumDataBean(basDt, cmpyCd, cmpyNm, ptrn, mog, prdNm, age, mlInsRt, fmlInsRt);
				bohumTestInfoArr.add(bohumTestBean);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return bohumTestInfoArr;
	}//getHohumDetail
	
	
	public ArrayList<BohumDataBean> getHohumDetail(String cmpyNm ,String prdNm,String age) {
		ArrayList<BohumDataBean> bohumTestInfoArr = new ArrayList<BohumDataBean>();
		try {
			StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1160100/service/GetMedicalReimbursementInsuranceInfoService/getInsuranceInfo"); /*URL*/
			urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=wKQumq5LX0aGJN19E3mLdne0GDiEtPtVpVY3tDVBkOYPc21sBxDu%2B4lUggPaO0ETQboYKIVcYuGsd5lxtqhYoQ%3D%3D");
			urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8")); /*�뜝�룞�삕 �뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�뜝占� �뜝�룞�삕*/
			urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*�뜝�룞�삕�뜝�룞�삕�뜝�룞�삕 �뜝�룞�삕�샇*/
			urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*�뜝�룞�삕�뜝�룞�삕�뜝�룞�삕�뜝占�(xml/json)*/
			urlBuilder.append("&" + URLEncoder.encode("cmpyNm","UTF-8") + "=" + URLEncoder.encode(cmpyNm, "UTF-8")); /*�뜝�룞�삕�뜝�룞�삕*/       
			urlBuilder.append("&" + URLEncoder.encode("prdNm","UTF-8") + "=" + URLEncoder.encode(prdNm, "UTF-8")); /*�뜝�룞�삕�뜝�룞�삕*/       
			urlBuilder.append("&" + URLEncoder.encode("age","UTF-8") + "=" + URLEncoder.encode(age, "UTF-8")); /*�뜝�룞�삕�뜝�룞�삕*/       
			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;
	        InputStreamReader in = new InputStreamReader((InputStream) conn.getContent(), "UTF-8");
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(in);
	        }  else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			System.out.println(sb.toString());

			String myData = sb.toString();
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject)parser.parse(myData);

			JSONObject jsonObj = (JSONObject)obj;
			JSONObject jsonResponse = (JSONObject)jsonObj.get("response");
			JSONObject jsonBody = (JSONObject)jsonResponse.get("body");
			JSONObject jsonItems = (JSONObject)jsonBody.get("items");
			JSONArray jsonItem = (JSONArray)jsonItems.get("item");
			System.out.println("basDt \t cmpyCd \t cmpyNm \t ptrn \t mog \t prdNm \t age \t mlInsRt \t fmlInsRt \t");
			for(int i=0;i<jsonItem.size();i++) {
				JSONObject item = (JSONObject)jsonItem.get(i);
				System.out.print(item.get("basDt")+"\t");
				System.out.print(item.get("cmpyCd")+"\t");
				System.out.print(item.get("cmpyNm")+"\t");
				System.out.print(item.get("ptrn")+"\t");
				System.out.print(item.get("mog")+"\t");
				System.out.print(item.get("prdNm")+"\t");
				System.out.print(item.get("age")+"\t");
				System.out.print(item.get("mlInsRt")+"\t");
				System.out.println(item.get("fmlInsRt")+"\t");
				
				String basDt = (String)item.get("basDt");
				String cmpyCd = (String)item.get("cmpyCd");
				String ptrn = (String)item.get("ptrn");
				String mog = (String)item.get("mog");
				String mlInsRt = (String)item.get("mlInsRt");
				String fmlInsRt = (String)item.get("fmlInsRt");
				
				BohumDataBean bohumTestBean = new BohumDataBean(basDt, cmpyCd, cmpyNm, ptrn, mog, prdNm, age, mlInsRt, fmlInsRt);
				bohumTestInfoArr.add(bohumTestBean);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return bohumTestInfoArr;
	}//getHohumDetail
}//BohumDetail
