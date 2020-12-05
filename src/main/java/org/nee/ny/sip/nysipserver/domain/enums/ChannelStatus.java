package org.nee.ny.sip.nysipserver.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * @Author: alec
 * Description:
 * @date: 16:09 2020-12-03
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum  ChannelStatus {

    ON(1, "ON"),
    OFF(0, "OFF"),
    OFFLINE(0, "OFFLINE"),
    ONLINE(1, "ONLINE");

    private Integer code;

    private String name;

    public static Optional<Integer> codeByName(String name) {
        for (ChannelStatus keymap : ChannelStatus.values()) {
            if (keymap.getName().equals(name)) {
                return Optional.of(keymap.code);
            }
        }
        return Optional.empty();
    }

}
