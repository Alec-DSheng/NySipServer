package org.nee.ny.sip.nysipserver.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.sip.RequestEvent;
import javax.sip.message.Request;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

/**
 * @Author: alec
 * Description:
 * @date: 10:00 2020-11-30
 */
@Slf4j
public class XmlObjectConvertUtil {

    public static Object xmlConvertObject (String xml, Object obj) {
        try {
            log.info("xml {}", xml);
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
}