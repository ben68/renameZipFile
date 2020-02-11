package com.ken;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class Zip {

    public static void zipFile(String srcFile, String newName, String zipFileName) throws IOException {
        FileOutputStream fileWriter = new FileOutputStream(zipFileName + ".zip");
        ZipOutputStream outputZip = new ZipOutputStream(fileWriter);

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

    public static String unzip(String srcZipFile) throws IOException {
        String fileName = null;

        ZipFile zip = new ZipFile(new File(srcZipFile));

        Enumeration<? extends ZipEntry> iterator = zip.entries();
        while (iterator.hasMoreElements()) {
            ZipEntry entry = iterator.nextElement();
            fileName = entry.getName();
            if (entry.isDirectory() || fileName.contains("/"))
                continue;

            FileOutputStream fileWriter = new FileOutputStream(new File(fileName));
            InputStream input = zip.getInputStream(entry);

            int len;
            byte[] buffer = new byte[1024];
            while ((len = input.read(buffer)) > 0) {
                fileWriter.write(buffer, 0, len);
            }

            input.close();
            fileWriter.flush();
            fileWriter.close();
        }

        zip.close();

        return fileName;
    }
}
