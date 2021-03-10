package cn.springmvc.mybatis.common.utils.fmt.xml;

import java.io.IOException;
import java.io.Writer;

import com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler;

/**
 * 
 * @author Vincent.Wang
 *
 */
@SuppressWarnings("restriction")
public class CDDATAEscapeHandler implements CharacterEscapeHandler {

    @Override
    public void escape(char[] chars, int start, int length, boolean isAttVal, Writer writer) throws IOException {
        writer.write(chars, start, length);
    }

}
