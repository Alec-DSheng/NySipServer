package org.nee.ny.sip.nysipserver.utils;

import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.rmi.UnknownHostException;
import java.util.*;

/**
 * @Author: alec
 * Description:
 * @date: 20:27 2020-11-27
 */
@Slf4j
public class IntentAddressUtil {


    public static List<Inet4Address> getLocalIp4AddressFromNetworkInterface() throws SocketException {
        List<Inet4Address> ipAddressList = new ArrayList<>();
        Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
        if (Objects.isNull(enumeration)) {
            return ipAddressList;
        }
        while (enumeration.hasMoreElements()) {
            NetworkInterface network = (NetworkInterface) enumeration.nextElement();

            if (!isValidInterface(network)) {
                continue;
            }
            Enumeration intenetEnums = network.getInetAddresses();
            while (intenetEnums.hasMoreElements()) {
                InetAddress address = (InetAddress) intenetEnums.nextElement();
                if (isValidAddress(address)) {
                    ipAddressList.add((Inet4Address) address);
                }
            }
        }
        return ipAddressList;
    }

    /**
     * 过滤回环网卡,点对点网卡,非活动网卡,虚拟网卡,并要求网卡名字是eth或ens开头
     * */
    private static boolean isValidInterface(NetworkInterface network) throws SocketException {
        if (network.isLoopback() || network.isPointToPoint() || network.isVirtual()) {
            return false;
        }
        String name = network.getName();
        if (!name.startsWith("eth") && !name.startsWith("en")) {
            return false;
        }
        return network.isUp();
    }

    private static boolean isValidAddress(InetAddress inetAddress) {
        if (!inetAddress.isSiteLocalAddress()) {
            return false;
        }
        if (inetAddress.isLoopbackAddress()) {
            return false;
        }
        return inetAddress instanceof Inet4Address;
    }

    private static Optional<Inet4Address> getIpBySocket() throws SocketException, java.net.UnknownHostException {
        final DatagramSocket socket = new DatagramSocket();
        socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
        if (socket.getLocalAddress() instanceof Inet4Address) {
            return Optional.of((Inet4Address)socket.getLocalAddress());
        }
        return Optional.empty();
    }

    public static Optional<Inet4Address> getLocalIp4Address () throws UnknownHostException {
        try {
            final List<Inet4Address> ipAddressList = getLocalIp4AddressFromNetworkInterface();
            if (ipAddressList.size() != 1) {
                final Optional<Inet4Address> inet4Address = getIpBySocket();
                Inet4Address inet = inet4Address.orElseGet(() ->
                        ipAddressList.isEmpty() ? null : ipAddressList.get(0));
                return Optional.ofNullable(inet);
            }
            return Optional.of(ipAddressList.get(0));
        } catch (Exception e) {
            throw new UnknownHostException("获取本机地址错误");
        }
    }
}
