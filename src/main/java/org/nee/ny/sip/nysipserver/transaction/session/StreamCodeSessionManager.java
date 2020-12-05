package org.nee.ny.sip.nysipserver.transaction.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Author: alec
 * Description: 管理SessionStreamCode
 * @date: 15:09 2020-12-04
 */
@Component
@Slf4j
public class StreamCodeSessionManager {

    /**
     * 生成唯一随机码
     * 基于内存生成。系统初始化时 生成 0 - 9999 个随机整数
     *
     * */

    private final static Set<Integer>  USED_STREAM_CODE = new HashSet<>();

    private final static Integer LEN = 10000;

    public String createStreamCode() {
        Integer randomValue;
        if (LEN == USED_STREAM_CODE.size()) {
            throw new NoSuchElementException("stream code 已分配完");
        }
        synchronized (this) {
            do {
                randomValue = new Random().nextInt(LEN);
                log.info("random {}, ss {}, is {}", randomValue, USED_STREAM_CODE, USED_STREAM_CODE.contains(randomValue));
            } while (USED_STREAM_CODE.contains(randomValue));
            USED_STREAM_CODE.add(randomValue);
        }

      int code =  Optional.of(randomValue).orElseThrow(() -> new NoSuchElementException("streamCode not enabled"));
      return String.format("%04d", code);
    }

    /**
     * 释放唯一随机码
     * */
    public void freeStreamCode(String streamCode) {
        int code = Integer.parseInt(streamCode);
        synchronized (this) {
            USED_STREAM_CODE.remove(code);
        }
    }

}
