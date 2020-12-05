package org.nee.ny.sip.nysipserver.transaction.command.Invite;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.nee.ny.sip.nysipserver.transaction.command.CommandParams;

/**
 * @Author
 * Description:
 * @date: 10:24 2020-12-04
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PlayCommandParams extends CommandParams {

    private String channelId;

    private String streamCode;
}
