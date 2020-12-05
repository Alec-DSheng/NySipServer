package org.nee.ny.sip.nysipserver.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 10:00 2020-11-30
 */
@Slf4j
public class XmlObjectConvertUtil {

    private final static String RN = "\r\n";

    public static Object xmlConvertObject (String xml, Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader sr = new StringReader(xml);
            return unmarshaller.unmarshal(sr);
        } catch (JAXBException e) {
            log.error("解析数据错误",e);
        }
        return null;
    }

    public static String objectConvertXml(Object obj) {
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false); // 格式化XML输出，有分行和缩进
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            marshaller.marshal(obj, baos);
            return  new String(baos.toByteArray());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getText(Element em, String tag){
        if (null == em)
        {
            return null;
        }
        Element e = em.element(tag);
        return null == e ? null : e.getText();
    }

    public static Element getRootElement(byte[] content) throws DocumentException {
        SAXReader reader = new SAXReader();
        reader.setEncoding("gbk");
        Document xml = reader.read(new ByteArrayInputStream(content));
        return xml.getRootElement();
    }

    /**
     * 截取streamCode
     * */
    public static Map<String, String> convertStreamCode(String content) {
        if (!StringUtils.hasLength(content)) {
            return null;
        }
        Map<String, String> data = new HashMap<>();
        String [] values = content.split(RN);
        for (String value: values) {
            String[] fields = value.split("=");
            data.put(fields[0], fields[1]);
        }
        return data;
    }
}
