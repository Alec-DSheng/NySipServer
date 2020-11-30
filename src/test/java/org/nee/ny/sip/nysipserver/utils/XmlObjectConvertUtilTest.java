package org.nee.ny.sip.nysipserver.utils;

import org.junit.jupiter.api.Test;
import org.nee.ny.sip.nysipserver.event.message.MessageRequestAbstract;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @Author: alec
 * Description:
 * @date: 10:30 2020-11-30
 */
public class XmlObjectConvertUtilTest {

    private final String xml = "<?xml version=\"1.0\"?>\n" +
            "<Notify>\n" +
            "<CmdType>Keepalive</CmdType>\n" +
            "<SN>37</SN>\n" +
            "<DeviceID>34020000001110000004</DeviceID>\n" +
            "<Status>OK</Status>\n" +
            "</Notify>";


    @Test
    public void convertObject() {
//        MessageRequestAbstract messageRequestAbstract = new MessageRequestAbstract();
//        messageRequestAbstract = (MessageRequestAbstract) XmlObjectConvertUtil
//                .xmlConvertObject(xml, messageRequestAbstract);
//
//        assertThat(messageRequestAbstract).isNotNull();
//        assertThat(messageRequestAbstract.getDeviceId()).isEqualTo("34020000001110000004");

    }
}
