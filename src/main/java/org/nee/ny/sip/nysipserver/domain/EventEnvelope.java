package org.nee.ny.sip.nysipserver.domain;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @Author: alec
 * Description:
 * @date: 14:47 2020-12-02
 */
@ToString
@EqualsAndHashCode
public class EventEnvelope<T> {

    private String eventId;

    private String type;

    private Date createTime;

    private T payload;

    public EventEnvelope () {}

    public EventEnvelope (String type, T payload) {
        this.payload = payload;
        this.eventId = UUID.randomUUID().toString();
        this.type = type;
        this.payload = payload;
    }


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            EventEnvelope<?> that = (EventEnvelope)o;
            return Objects.equals(this.eventId, that.eventId) && Objects.equals(this.type, that.type) && Objects.equals(this.createTime, that.createTime) && Objects.equals(this.payload, that.payload);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{ this.eventId, this.type, this.createTime, this.payload});
    }

    public String toString() {
        return "EventEnvelope{ eventId='" + this.eventId + '\'' + ", type='" + this.type + '\'' + ", createTime=" + this.createTime + ", payload=" + this.payload + '}';
    }
}
