package org.nee.ny.sip.nysipserver.service.kit;

/**
 * @Author: alec
 * Description:
 * @date: 16:27 2020-12-28
 */
public interface KitSubject<T> {

    void addObserver(T t);

    void removeObject(T t);

    void notifyObserver();
}
