package com.zoomtk.circle.utils;
import com.zoomtk.circle.Config.URLConst;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AjaxParamUtil {
	/**
	 * 对所有ajax请求进行最后的封装，加上相应的签名等数据
	 * @param map
	 * @return
	 */
	private static String ts = "timestamp";
	private static String fmt = "format";
	private static String ak = "app_key";
	private static String as = "app_secret";
	private static String ver = "v";
	private static String smd = "sign_method";
	private static String strField = "fields";


    /**
     * 传入的参数一定是服务器需要的参数
     * 以及一个fields(参数1,参数2,参数3,参数4)
     * @param param
     * @return
     */
	public static Map<String, String> getSuitParam(Map<String, String> param) {
		Map<String, String> reMap = new HashMap<String, String>();
		StringBuffer sbf = new StringBuffer();
		long currentTimestamp = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		String timeStamp = sdf.format(new Date(currentTimestamp));
		reMap.put(ts, timeStamp);
		reMap.put(fmt, "json");
		reMap.put(as, URLConst.SERVER_app_secret);
		reMap.put(ver, "1.0");
		reMap.putAll(param);
		reMap.put(smd, "md5");
		reMap.put(ak,URLConst.SERVER_app_key);
		StringBuffer reBf = new StringBuffer(sbf);
        List<Map.Entry<String, String>> infoIds =
                new ArrayList<Map.Entry<String, String>> (reMap.entrySet());

        Collections.sort (infoIds, new Comparator<Map.Entry<String, String>> () {
            public int compare (Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                //return (o2.getValue() - o1.getValue());
                return (o1.getKey ()).toString ().compareTo (o2.getKey ());
            }
        });

        reBf.append (URLConst.SERVER_app_secret);
        for (int i = 0; i < infoIds.size () ; i++) {
            if(!infoIds.get (i).getKey ().equals (as)){
                reBf.append (infoIds.get (i).getKey ());
                reBf.append(infoIds.get (i).getValue ());
            }
        }
        reBf.append (URLConst.SERVER_app_secret);


        String strSig = encryption("1", reBf.toString());
		reMap.put ("sign", strSig);
		return reMap;
	}

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String toHexString(byte[] b) {
		// String to byte
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (byte aB : b) {
			sb.append(HEX_DIGITS[(aB & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[aB & 0x0f]);
		}
		return sb.toString();
	}


    public static String encryption(String type,String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
			for (byte aB : b) {
				i = aB;
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

            re_md5 = buf.toString();
            if ("0".equals(type)) {
                return re_md5.toLowerCase();
            }else{
                return re_md5.toUpperCase ();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }


    /**
	 * 获得md5码
	 * 
	 * @param type
	 *            : 0,小写；1，大写
	 * @return
	 */
	public static String makeMD5(String type, String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			String re = toHexString(messageDigest);
			if ("0".equals(type)) {
				return re.toLowerCase();
			}
			return re;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return "";
	}

}
