package com.ken;

import chconvertor.Convertor;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import static com.ken.Logger.log;
import static com.ken.Logger.logn;

public class GbkRepack {

    public GbkRepack() throws IOException {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().endsWith(".zip");
            }
        };
        File[] files = new File(".").listFiles(fileFilter);
        if (files == null)
            return;

        boolean isMade = new File("new").mkdir();
        if (!isMade) {
            logn("can't mkdir!");
        }

        for (File file : files) {

            String fileName = file.getName();
            log(fileName + " is repacking...");

            String unzippedFileName = Zip.unzip(fileName);
            file.delete();

            String fileNameT = Convertor.simplifiedToTraditional(unzippedFileName);
            Zip.zipFile(unzippedFileName, fileNameT, "new/" + fileNameT.substring(0, fileNameT.lastIndexOf(".")));
            new File(unzippedFileName).delete();

            logn("done!");
        }
    }
}
