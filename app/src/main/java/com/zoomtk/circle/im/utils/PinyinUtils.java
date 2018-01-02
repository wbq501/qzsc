package com.zoomtk.circle.im.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {
	public static String getPinYin(String inputString) {
		if (inputString == null) {
			return null;
		}
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.UPPERCASE);

		char[] input = inputString.trim().toCharArray();
		StringBuffer output = new StringBuffer("");

		try {
			for (char anInput : input) {
				String[] temp = PinyinHelper.toHanyuPinyinStringArray(anInput, format);
				if (temp == null) {
					output.append(Character.toString(anInput).toUpperCase());
				} else {
					output.append(temp[0]);
				}
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output.toString();
	}
}
