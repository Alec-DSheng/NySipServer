package org.nee.ny.sip.nysipserver.domain;

import lombok.*;

/**
 * @Author: alec
 * Description: 流媒体服务实例
 * @date: 16:05 2020-12-28
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ZlMediaKitServer {

   private String ip;
   private String mediaServerId;
   private String secret;
   private String httpPort;
   private String rtspPort;
   private String rtmpSslPort;
   private String httpCharSet;
   private String rtpProxyPort;
   private String httpSslPort;
   private String rtmpPort;
   private String ffmpegCmd;
   private Long registerTime;
}
