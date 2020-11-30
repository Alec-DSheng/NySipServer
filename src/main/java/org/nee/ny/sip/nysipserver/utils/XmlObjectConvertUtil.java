package org.nee.ny.sip.nysipserver.utils;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
}
