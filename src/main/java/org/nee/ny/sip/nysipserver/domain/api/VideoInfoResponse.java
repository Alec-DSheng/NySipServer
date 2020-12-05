package org.nee.ny.sip.nysipserver.domain.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: alec
 * Description:
 * @date: 11:28 2020-12-05
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class VideoInfoResponse {

    private String streamCode;

    private String flvUrl;

    private String hlsUrl;

    public VideoInfoResponse(String mediaUrl, String streamCode) {
        this.flvUrl = String.format("http://%s/rtp/%s.flv",mediaUrl,streamCode);
        this.hlsUrl = String.format("http://%s/rtp/hls/%s.hls", mediaUrl, streamCode);
        this.streamCode = streamCode;
    }
}
