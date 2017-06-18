package com.alibaba.middleware.race.sync.server2.unitTest;

import com.alibaba.middleware.race.sync.server.FileUtil;
import com.alibaba.middleware.race.sync.server2.RecordField;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

import static com.alibaba.middleware.race.sync.server2.RecordField.fieldIndexMap;

/**
 * Created by yche on 6/17/17.
 */
public class RecordFieldTest {
    public static void main(String[] args) throws IOException {
        MappedByteBuffer mappedByteBuffer;
        FileChannel fileChannel = new RandomAccessFile("/tmp/1.txt", "r").getChannel();
        mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, 1024 * 1024);
        mappedByteBuffer.load();

        // first time init
        if (!RecordField.isInit())
            new RecordField(mappedByteBuffer).initFieldIndexMap();
        FileUtil.unmap(mappedByteBuffer);

        // check it
        for (Map.Entry<ByteBuffer, Integer> entry : fieldIndexMap.entrySet()) {
            System.out.println(new String(entry.getKey().array()) + ", " + entry.getValue());
        }

        System.out.println("\nfield num:" + RecordField.FILED_NUM);
    }
}