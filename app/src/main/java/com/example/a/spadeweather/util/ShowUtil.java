package com.example.a.spadeweather.util;

import com.example.a.spadeweather.R;

import java.util.Calendar;


public class ShowUtil {
    public static String showTime(String updateTime){
        String month=updateTime.substring(5,7);
        String day=updateTime.substring(8,10);
        String moment=updateTime.split(" ")[1];
        String time=month+"-"+day+" "+moment;
        return time;
    }
    public static int showWeatherImage(String weatherImage){
        switch (weatherImage){
            case "100":
                return R.mipmap.wea_100;
            case "100n":
                return R.mipmap.wea_100n;
            case "101":
                return R.mipmap.wea_101;
            case "102":
                return R.mipmap.wea_102;
            case "103":
                return R.mipmap.wea_103;
            case "103n":
                return R.mipmap.wea_103n;
            case "104":
                return R.mipmap.wea_104;
            case "104n":
                return R.mipmap.wea_104n;
            case "200":
                return R.mipmap.wea_200;
            case "201":
                return R.mipmap.wea_201;
            case "202":
                return R.mipmap.wea_202;
            case "203":
                return R.mipmap.wea_203;
            case "204":
                return R.mipmap.wea_204;
            case "205":
                return R.mipmap.wea_205;
            case "206":
                return R.mipmap.wea_206;
            case "207":
                return R.mipmap.wea_207;
            case "208":
                return R.mipmap.wea_208;
            case "209":
                return R.mipmap.wea_209;
            case "210":
                return R.mipmap.wea_210;
            case "211":
                return R.mipmap.wea_211;
            case "212":
                return R.mipmap.wea_212;
            case "213":
                return R.mipmap.wea_213;
            case "300":
                return R.mipmap.wea_300;
            case "300n":
                return R.mipmap.wea_300n;
            case "301":
                return R.mipmap.wea_301;
            case "301n":
                return R.mipmap.wea_301n;
            case "302":
                return R.mipmap.wea_302;
            case "303":
                return R.mipmap.wea_303;
            case "304":
                return R.mipmap.wea_304;
            case "305":
                return R.mipmap.wea_305;
            case "306":
                return R.mipmap.wea_306;
            case "307":
                return R.mipmap.wea_307;
            case "308":
                return R.mipmap.wea_308;
            case "309":
                return R.mipmap.wea_309;
            case "310":
                return R.mipmap.wea_310;
            case "311":
                return R.mipmap.wea_311;
            case "312":
                return R.mipmap.wea_312;
            case "313":
                return R.mipmap.wea_313;
            case "314":
                return R.mipmap.wea_314;
            case "315":
                return R.mipmap.wea_315;
            case "316":
                return R.mipmap.wea_316;
            case "317":
                return R.mipmap.wea_317;
            case "318":
                return R.mipmap.wea_318;
            case "399":
                return R.mipmap.wea_399;
            case "400":
                return R.mipmap.wea_400;
            case "401":
                return R.mipmap.wea_401;
            case "402":
                return R.mipmap.wea_402;
            case "403":
                return R.mipmap.wea_403;
            case "404":
                return R.mipmap.wea_404;
            case "405":
                return R.mipmap.wea_405;
            case "406":
                return R.mipmap.wea_406;
            case "407":
                return R.mipmap.wea_407;
            case "407n":
                return R.mipmap.wea_407n;
            case "408":
                return R.mipmap.wea_408;
            case "409":
                return R.mipmap.wea_409;
            case "410":
                return R.mipmap.wea_410;
            case "499":
                return R.mipmap.wea_499;
            case "500":
                return R.mipmap.wea_500;
            case "501":
                return R.mipmap.wea_501;
            case "502":
                return R.mipmap.wea_502;
            case "503":
                return R.mipmap.wea_503;
            case "504":
                return R.mipmap.wea_504;
            case "507":
                return R.mipmap.wea_507;
            case "508":
                return R.mipmap.wea_508;
            case "509":
                return R.mipmap.wea_509;
            case "510":
                return R.mipmap.wea_510;
            case "511":
                return R.mipmap.wea_511;
            case "512":
                return R.mipmap.wea_512;
            case "513":
                return R.mipmap.wea_513;
            case "514":
                return R.mipmap.wea_514;
            case "515":
                return R.mipmap.wea_515;
            case "900":
                return R.mipmap.wea_900;
            case "901":
                return R.mipmap.wea_901;
            case "999":
                return R.mipmap.wea_999;
            default:

        }
        return 0;
    }
    public static String showDailyTime(String dailyUpdateTime){
        Calendar now=Calendar.getInstance();
        int nowDay=now.get(Calendar.DAY_OF_MONTH);
        String nowDayString=Integer.toString(nowDay);
        char c1=dailyUpdateTime.charAt(dailyUpdateTime.length()-1);
        char c2=nowDayString.charAt(nowDayString.length()-1);
        switch (c1-c2){
            case 0:
                return "今天";
            case 1:
                return "明天";
            case 2:
                return "后天";
            default:
        }
        return null;
    }
    public static String showHourlyTime(String hourlyTime){
        String time=hourlyTime.split(" ")[1];
        return time;
    }
}
