package com.vico.license.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZIPFiles {
    private static final Logger logger = Logger.getLogger(ZIPFiles.class);

    public static boolean compatFiles() {
        boolean processflag = true;
        ZipOutputStream out = null;
        FileInputStream fis = null;
        String path = ClassPathResourceURI.getResourceURI("/").getPath();
        byte[] buffer = new byte[1024];
        File RSAKeyprivate = new File(path + File.separator + FileNames.PRIVATEKEY_NAME);
        File license = new File(path + File.separator + FileNames.LICENSE_FILE_NAME);
        ArrayList<File> list = new ArrayList<File>();
        list.add(RSAKeyprivate);
        list.add(license);

        String strZipName = FileNames.ZIPFILE_NAME;

        try {
            out = new ZipOutputStream(new FileOutputStream(path + strZipName));

            for (File file : list) {
                fis = new FileInputStream(file);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len = 0;
                while ((len = fis.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                }
            }
        } catch (Exception e) {
            logger.error(e + "生成ZIP出现问题!");
            processflag = false;
            return processflag;
        } finally {
            try {
                out.closeEntry();
                fis.close();
                out.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return processflag;
    }
}

