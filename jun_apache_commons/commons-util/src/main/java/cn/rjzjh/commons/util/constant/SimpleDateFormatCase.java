package cn.rjzjh.commons.util.constant;

import java.text.SimpleDateFormat;

public  enum SimpleDateFormatCase {
	YYYY_MM_DD(new SimpleDateFormat("yyyy-MM-dd")),
	YYYY_MM_DD_hhmmss(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")),
	YYYY_MM_DD_hhmmssSSS(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")),
	YYYYMMDD(new SimpleDateFormat("yyyyMMdd")),
	yyyyMMddHHmmssSSSS(new SimpleDateFormat("yyyyMMddHHmmssSSSS")),
	yyyyMMddHHmmss(new SimpleDateFormat("yyyyMMddHHmmss")),
	TyyyyMMddHHmmss(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz")),
	TyyyyMMddHHmmssNoZ(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
    private SimpleDateFormatCase(SimpleDateFormat instanc){
    	this.instanc = instanc;
    }
	private SimpleDateFormat instanc;
	public SimpleDateFormat getInstanc() {
		return instanc;
	}
}
