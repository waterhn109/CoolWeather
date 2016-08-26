package com.coolweather.app.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by HY on 2016/8/18.
 */
public class GetXmldata {

    private static List<Xmldata> Xmldatas = new ArrayList<Xmldata>();

    public static String substr(String str1,String str) {



        //String str = "<p>sad2f</p>";//两个字符串中间
        String regex = str1+"=\""+"(.[^\"]*.)"+"\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);//匹配类
        while (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    public static List<Xmldata> GetXmldata(String response){

        String reg_charset = "/>";
        String[] strs = response.split(reg_charset);
        Xmldatas.clear();
        for (int i = 0; i < strs.length-1; i++)
        {
            Xmldata xmldata = new Xmldata();
            xmldata.setQuName(substr("quName",strs[i]));
            xmldata.setPyName(substr("pyName",strs[i]));
            xmldata.setCityname(substr("cityname",strs[i]));
            xmldata.setStateDetailed(substr("stateDetailed",strs[i]));
            xmldata.setTem1(substr("tem1",strs[i]));
            xmldata.setTem2(substr("tem2",strs[i]));
            xmldata.setWindState(substr("windState",strs[i]));

            xmldata.setTemNow(substr("temNow",strs[i]));
            xmldata.setWindDir(substr("windDir",strs[i]));
            xmldata.setWindPower(substr("windPower",strs[i]));
            xmldata.setHumidity(substr("humidity",strs[i]));
            xmldata.setTime(substr("time",strs[i]));
            xmldata.setCityY(substr("cityY",strs[i]));
            xmldata.setCityX(substr("cityX",strs[i]));
            xmldata.setCentername(substr("centername",strs[i]));
            xmldata.setUrl(substr("url",strs[i]));

            Xmldatas.add(xmldata);

        }
        return Xmldatas;
    }
}
