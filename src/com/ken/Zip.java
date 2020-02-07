package com.ken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void zipFile(String srcFile, String newName, String zipFileName) throws IOException {
        FileOutputStream fileWriter = null;
        ZipOutputStream outputZip = null;

        fileWriter = new FileOutputStream(zipFileName);
        outputZip = new ZipOutputStream(fileWriter);

        byte[] buf = new byte[1024];
        int len;
        FileInputStream fileReader = new FileInputStream(srcFile);
        outputZip.putNextEntry(new ZipEntry(newName));
        while ((len = fileReader.read(buf)) > 0) {
            outputZip.write(buf, 0, len);
        }

        outputZip.flush();
        outputZip.close();
        fileWriter.flush();
        fileWriter.close();
        fileReader.close();
    }

    public static void unzip(String srcZipFile) throws IOException {
        ZipInputStream zip = null;
        FileInputStream fileReader = null;

        fileReader = new FileInputStream(srcZipFile);
        zip = new ZipInputStream(fileReader);

        int len;
        byte[] buffer = new byte[1024];

        ZipEntry entry = null;
        while ((entry = zip.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                File file = new File(entry.getName());
                FileOutputStream fileWriter = new FileOutputStream(file);
                while ((len = zip.read(buffer)) > 0) {
                    fileWriter.write(buffer, 0, len);
                }
                fileWriter.flush();
                fileWriter.close();
            }
        }
        zip.close();
        fileReader.close();
    }
}
