package com.ken;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.ken.Logger.log;
import static com.ken.Logger.logn;

public class Moonlight {

    public Moonlight(String ext) {

        String LIST_FILE_NAME = "gamelist.xml";

        Document doc;
        try {
            doc = new SAXReader().read(new File(LIST_FILE_NAME));
        } catch (DocumentException e) {
            e.printStackTrace();
            doc = null;
        }
        if (doc == null) {
            logn(LIST_FILE_NAME + " does not exist!");
            return;
        }

        boolean isMade = new File("new").mkdir();
        if (!isMade) {
            logn("can't mkdir!");
        }

        Element root = doc.getRootElement();
        List<Node> nodes = root.selectNodes("game");
        for (Node node : nodes) {
            String fileName = node.selectSingleNode("path").getText();
            if (!fileName.contains(".zip")) {
                logn("not a rom");
                continue;
            }
            String gameNumber = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.lastIndexOf("."));
            String gameName = node.selectSingleNode("name").getText();

            try {
                log("unzipping " + fileName + "...");
                String unzippedFileName = Zip.unzip(fileName);
                logn("done!");

                boolean isDeleted = new File(fileName).delete();

                if (fileName.contains("chinese"))
                    gameName += "(c)";
                log("repacking " + unzippedFileName + " to " + gameName + "...");
                Zip.zipFile(unzippedFileName, gameName + ext, "new/" + gameNumber);
                logn("done!");

                log("deleting " + unzippedFileName + "...");
                isDeleted = new File(unzippedFileName).delete();
                if (isDeleted)
                    logn("done!");
                else logn("fail!!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}