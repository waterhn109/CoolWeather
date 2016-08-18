package com.coolweather.app.util;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.List;

/**
 * Created by HY on 2016/8/18.
 */
public class GetXmldata {

    private static List<Xmldata>  Xmldatas;

    public static List<Xmldata> GetXmldata(String response){

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(response));
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                Xmldata xmldata = new Xmldata();
                xmldata.setUpcity(XMLRootSearcher.findRootElementName(response));
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    // 开始解析某个结点
                    case XmlPullParser.START_TAG: {
                        if ("cityX".equals(nodeName)) {
                            xmldata.setCityX(xmlPullParser.nextText());
                        } else if ("cityY".equals(nodeName)) {
                            xmldata.setCityY(xmlPullParser.nextText());
                        } else if ("cityname".equals(nodeName)) {
                            xmldata.setCityname(xmlPullParser.nextText());
                        } else if ("centername".equals(nodeName)) {
                            xmldata.setCentername(xmlPullParser.nextText());
                        } else if ("pyName".equals(nodeName)) {
                            xmldata.setPyName(xmlPullParser.nextText());
                        } else if ("stateDetailed".equals(nodeName)) {
                            xmldata.setStateDetailed(xmlPullParser.nextText());
                        } else if ("tem1".equals(nodeName)) {
                            xmldata.setTem1(xmlPullParser.nextText());
                        } else if ("tem2".equals(nodeName)) {
                            xmldata.setTem2(xmlPullParser.nextText());
                        } else if ("temNow".equals(nodeName)) {
                            xmldata.setTemNow(xmlPullParser.nextText());
                        } else if ("windState".equals(nodeName)) {
                            xmldata.setWindState(xmlPullParser.nextText());
                        } else if ("windDir".equals(nodeName)) {
                            xmldata.setWindDir(xmlPullParser.nextText());
                        } else if ("windPower".equals(nodeName)) {
                            xmldata.setWindPower(xmlPullParser.nextText());
                        } else if ("humidity".equals(nodeName)) {
                            xmldata.setHumidity(xmlPullParser.nextText());
                        } else if ("url".equals(nodeName)) {
                            xmldata.setUrl(xmlPullParser.nextText());
                        }
                        else if ("quName".equals(nodeName)) {
                            xmldata.setQuName(xmlPullParser.nextText());
                        }
                        else if ("time".equals(nodeName)) {
                            xmldata.setTime(xmlPullParser.nextText());
                        }
                        break;
                    }
                    // 完成解析某个结点
//                    case XmlPullParser.END_TAG: {
//                        if ("app".equals(nodeName)) {
//                            Log.d("MainActivity", "id is " + id);
//                            Log.d("MainActivity", "name is " + name);
//                            Log.d("MainActivity", "version is " + version);
//                        }
//                        break;
//                    }
                    default:
                        break;
                }
                Xmldatas.add(xmldata);
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Xmldatas;
    }
}
