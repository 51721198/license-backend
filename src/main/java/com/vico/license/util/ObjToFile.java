package com.vico.license.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjToFile {
    private static final Logger logger = Logger.getLogger(ObjToFile.class);

    public static boolean object2File(Object object) {
        boolean processflag = true;
        String path = ClassPathResourceURI.getResourceURI("/").getPath();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(path + File.separator + FileNames.PRIVATEKEY_NAME));
            oos.writeObject(object);
            oos.flush();
        } catch (Exception e) {
            logger.error(e + "生成私钥有问题!");
            processflag = false;
            return processflag;
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return processflag;
    }
}
