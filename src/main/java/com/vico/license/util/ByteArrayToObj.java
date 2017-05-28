package com.vico.license.util;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class ByteArrayToObj {
    private static final Logger logger = Logger.getLogger(ByteArrayToObj.class);

    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
            ObjectInputStream oi = new ObjectInputStream(bi);

            obj = oi.readObject();
            bi.close();
            oi.close();
        } catch (Exception e) {
            logger.error(e);
        }
        return obj;
    }
}
