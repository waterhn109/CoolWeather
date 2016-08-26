package com.coolweather.app.util;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;


import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;

import com.coolweather.app.model.Province;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class Utility {

	private static List<Xmldata> Xmldatas;
	/**
	 * 解析和处理服务器返回的省级数据
	 * xml解析
	 */
	public synchronized static boolean handleProvincesResponse(
			CoolWeatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {

			Xmldatas = GetXmldata.GetXmldata(response);
			Iterator<Xmldata> iterator = Xmldatas.iterator();
			while (iterator.hasNext()){
				Xmldata xmldata = iterator.next();
				Province province = new Province();
				province.setProvinceCode(xmldata.getPyName());
				province.setProvinceName(xmldata.getQuName());
				// 将解析出来的数据存储到Province表
				coolWeatherDB.saveProvince(province);


			}
			return true;
		}
		return false;
	}

	/**
	 * 解析和处理服务器返回的市级数据
	 */
	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
			String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			Xmldatas = GetXmldata.GetXmldata(response);
			Iterator<Xmldata> iterator = Xmldatas.iterator();
			while (iterator.hasNext()){
				Xmldata xmldata = iterator.next();
				City city = new City();
				city.setCityCode(xmldata.getPyName());
				city.setCityName(xmldata.getCityname());
				city.setProvinceId(provinceId);
				// 将解析出来的数据存储到City表
				coolWeatherDB.saveCity(city);



			}return true;
		}
		return false;
	}

	/**
	 * 解析和处理服务器返回的县级数据
	 */
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
			String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			Xmldatas = GetXmldata.GetXmldata(response);
			Iterator<Xmldata> iterator = Xmldatas.iterator();
			while (iterator.hasNext()){
				Xmldata xmldata = iterator.next();
				City city = new City();
				city.setCityCode(xmldata.getPyName());
				city.setCityName(xmldata.getCityname());
				city.setProvinceId(cityId);
				// 将解析出来的数据存储到City表
				coolWeatherDB.saveCity(city);



			}return true;

		}
		return false;
	}

	/**
	 * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。
	 */
	public static void handleWeatherResponse(Context context, String response) {
		String reg_charset = "/>";
		String[] strs = response.split(reg_charset);
		Xmldata xmldata = new Xmldata();
		for (int i = 0; i < 1; i++)
		{

			xmldata.setQuName(GetXmldata.substr("quName",strs[i]));
			xmldata.setPyName(GetXmldata.substr("pyName",strs[i]));
			xmldata.setCityname(GetXmldata.substr("cityname",strs[i]));
			xmldata.setStateDetailed(GetXmldata.substr("stateDetailed",strs[i]));
			xmldata.setTem1(GetXmldata.substr("tem1",strs[i]));
			xmldata.setTem2(GetXmldata.substr("tem2",strs[i]));
			xmldata.setWindState(GetXmldata.substr("windState",strs[i]));

			xmldata.setTemNow(GetXmldata.substr("temNow",strs[i]));
			xmldata.setWindDir(GetXmldata.substr("windDir",strs[i]));
			xmldata.setWindPower(GetXmldata.substr("windPower",strs[i]));
			xmldata.setHumidity(GetXmldata.substr("humidity",strs[i]));
			xmldata.setTime(GetXmldata.substr("time",strs[i]));
			xmldata.setCityY(GetXmldata.substr("cityY",strs[i]));
			xmldata.setCityX(GetXmldata.substr("cityX",strs[i]));
			xmldata.setCentername(GetXmldata.substr("centername",strs[i]));


		}

			String cityName = xmldata.getCityname();
			String weatherCode = xmldata.getPyName();
			String temp1 = xmldata.getTem1();
			String temp2 = xmldata.getTem2();
			String weatherDesp = xmldata.getStateDetailed();
			String publishTime = xmldata.getTime();
			saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
					weatherDesp, publishTime);

	}

	/**
	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中。
	 */
	public static void saveWeatherInfo(Context context, String cityName,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("weather_code", weatherCode);
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}

}