package com.coolweather.app.util;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;



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
				city.setPublishTime(xmldata.getTime());
				city.setTemp1(xmldata.getTem1());
				city.setTemp2(xmldata.getTem2());
				city.setWeatherDesp(xmldata.getStateDetailed());
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
	public static void handleWeatherResponse(Context context, String response,String countyCode) {
		String reg_charset = "/>";
		String[] strs = response.split(reg_charset);
		Xmldata xmldata = new Xmldata();
		//取得根元素
		xmldata.setUpcity(GetXmldata.upcity(response));
		//选择最小的
		for (int i = 0; i < strs.length-1; i++)
		{
			if (GetXmldata.substr("pyName",strs[i]).equals(countyCode)) {
				xmldata.setQuName(GetXmldata.substr("quName", strs[i]));
				xmldata.setPyName(GetXmldata.substr("pyName", strs[i]));
				xmldata.setCityname(GetXmldata.substr("cityname", strs[i]));
				xmldata.setStateDetailed(GetXmldata.substr("stateDetailed", strs[i]));
				xmldata.setTem1(GetXmldata.substr("tem1", strs[i]));
				xmldata.setTem2(GetXmldata.substr("tem2", strs[i]));
				xmldata.setWindState(GetXmldata.substr("windState", strs[i]));

				xmldata.setTemNow(GetXmldata.substr("temNow", strs[i]));
				xmldata.setWindDir(GetXmldata.substr("windDir", strs[i]));
				xmldata.setWindPower(GetXmldata.substr("windPower", strs[i]));
				xmldata.setHumidity(GetXmldata.substr("humidity", strs[i]));
				xmldata.setTime(GetXmldata.substr("time", strs[i]));
				xmldata.setCityY(GetXmldata.substr("cityY", strs[i]));
				xmldata.setCityX(GetXmldata.substr("cityX", strs[i]));
				xmldata.setCentername(GetXmldata.substr("centername", strs[i]));

			}
		}

			String cityName = xmldata.getCityname();
			String weatherCode = xmldata.getPyName();
			String province_code = xmldata.getUpcity();
			String temp1 = xmldata.getTem1();
			String temp2 = xmldata.getTem2();
			String weatherDesp = xmldata.getStateDetailed();
			String publishTime = xmldata.getTime();
			saveWeatherInfo(context, cityName,province_code, weatherCode, temp1, temp2,
					weatherDesp, publishTime);

	}

	/**
	 * 将服务器返回的所有天气信息存储到SharedPreferences文件中。
	 */
	public static void saveWeatherInfo(Context context, String cityName,
									   String province_code,
			String weatherCode, String temp1, String temp2, String weatherDesp,
			String publishTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences.Editor editor = PreferenceManager
				.getDefaultSharedPreferences(context).edit();
		editor.putBoolean("city_selected", true);
		editor.putString("city_name", cityName);
		editor.putString("province_code", province_code);
		editor.putString("weather_code", weatherCode);
		editor.putString("remark", "");
		//自己的特色
		if (province_code.equals("shanghai")){
			editor.putString("remark", "魔都");
		}else if(province_code.equals("beijing")){
			editor.putString("remark", "帝都");
		}else if(cityName.equals("广州")){
			editor.putString("remark", "小曾曾打工的地方");
		}else if(province_code.equals("梁平")){
			editor.putString("remark", "GRD");
		}else if(cityName.equals("南京")){
			editor.putString("remark", "大蓝鲸");
		}else if(cityName.equals("商丘")){
			editor.putString("remark", "老家");
		}else if(cityName.equals("宿州")){
			editor.putString("remark", "老家2");
		}else if(cityName.equals("大理")){
			editor.putString("remark", "苍山洱海");
		}else if(cityName.equals("海口")){
			editor.putString("remark", "小曾曾老家");
		}else if(cityName.equals("曲靖")){
			editor.putString("remark", "鸭子老家");
		}else if(cityName.equals("南宁")){
			editor.putString("remark", "农哥老家");
		}else if(cityName.equals("驻马店")){
			editor.putString("remark", "驻马店没有眼泪，大雪纷飞");
		}else if(cityName.equals("昆明")){
			editor.putString("remark", "老司机带我去");
		}else if(cityName.equals("西宁")){
			editor.putString("remark", "沙哥老家");
		}else if(cityName.equals("泰州")){
			editor.putString("remark", "鸡蛋老家");
		}else if(cityName.equals("成都")){
			editor.putString("remark", "各种好");
		}else if(cityName.equals("长沙")){
			editor.putString("remark", "美女多");
		}else if(cityName.equals("兰州")){
			editor.putString("remark", "青海人开的拉面馆都是垃圾");
		}else if(cityName.equals("张掖")){
			editor.putString("remark", "第一次喝果啤");
		}else if(cityName.equals("敦煌")){
			editor.putString("remark", "人少的时候去完全不一样");
		} else if(cityName.equals("沈阳")){
			editor.putString("remark", "烧烤大哥和剥蒜小妹");
		}else if(cityName.equals("哈尔滨")){
			editor.putString("remark", "一起哈啤");
		}else if(cityName.equals("阜阳")){
			editor.putString("remark", "有几个熟人");
		}else if(province_code.equals("xinjiang")){
			editor.putString("remark", "新疆是个好地方");
		}else if(cityName.equals("林芝")){
			editor.putString("remark", "文艺屌丝必去地之二");
		} else {
			if(weatherDesp.contains("晴")){editor.putString("remark", "天气不错");}
			else if (weatherDesp.contains("雨")){
			editor.putString("remark", "记得带雨伞啊");}
			else if (weatherDesp.contains("雪")){
				editor.putString("remark", "忧郁的一片天 飘着纷飞的雪");}
		}
		editor.putString("temp1", temp1);
		editor.putString("temp2", temp2);
		editor.putString("weather_desp", weatherDesp);
		editor.putString("publish_time", publishTime);
		editor.putString("current_date", sdf.format(new Date()));
		editor.commit();
	}

}