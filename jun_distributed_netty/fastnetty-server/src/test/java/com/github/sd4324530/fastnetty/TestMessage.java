package com.github.sd4324530.fastnetty;

import com.github.sd4324530.fastnetty.core.message.InputMessage;
import com.github.sd4324530.fastnetty.core.utils.HexBytesUtils;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * @author peiyu
 */
public class TestMessage implements InputMessage {

    private Date checkTime;

    private String cardId;

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Override
    public TestMessage fromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byte soi = byteBuffer.get();
        char adr = byteBuffer.getChar();
        byte cid1 = byteBuffer.get();
        byte cid2 = byteBuffer.get();
        byte length = byteBuffer.get();
        byte[] data = new byte[length];
        byteBuffer.get(data);

        this.checkTime = new Date();
        this.cardId = HexBytesUtils.byteArrayToHexString(data);
        return this;
    }

    @Override
    public String toString() {
        return "TestMessage{" +
                "checkTime=" + checkTime +
                ", cardId='" + cardId + '\'' +
                '}';
    }
}
