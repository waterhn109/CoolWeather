package com.coolweather.app.util;

/**
 * Created by HY on 2016/8/18.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class XMLRootSearcher {


    public static String findRootElementName(String xml) {


        String result = null;



        Pattern regex = null;


        Matcher acceptor = null;



        try {



// http://www.w3.org/TR/2004/REC-xml11-20040204/#NT-Name



// Name ::= NameStartChar (NameChar)*



            String namePattern ="<(([:_A-Za-z\\xC0-\\xD6\\xD8-\\xF6\\xF8-\\u02FF"
                    +"\\u0370-\\u037D\\u037F-\\u1FFF\\u200C-\\u200D"
                    +"\\u2070-\\u218F\\u2C00-\\u2FEF\\u3001-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFFD"
                     +"])([:_A-Za-z0-9\\."
                     +"\\xB7\\xC0-\\xD6\\xD8-\\xF6\\xF8-\\u02FF\\u0370-\\u037D"
                     +"\\u0300-\\u036F\\u037F-\\u1FFF\\u200C-\\u200D"
                    +"\\u203F-\\u2040\\u2070-\\u218F\\u2C00-\\u2FEF\\u3001-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFFD"
                    +"\\-])*)";




            regex = Pattern.compile(namePattern, Pattern.CANON_EQ);




            acceptor = regex.matcher(xml);


        } catch (PatternSyntaxException pse) {



            pse.printStackTrace(System.err);




            return result;


        }



        if (acceptor.find()) {



            if (null != acceptor.group(1)) {




                result = acceptor.group(1);



            }


        }



        return result;

    }


}