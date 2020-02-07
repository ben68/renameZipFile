package com.ken;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    private static String LIST_FILE_NAME = "gamelist.xml";

    public static void main(String[] args) {
        String ext;
        if (args.length > 0)
            ext = "." + args[0];
        else ext = "";

        ext=".nes";

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
                Zip.unzip(fileName);
                logn("done!");

                boolean isDeleted = new File(fileName).delete();

                String unzipName = "用此系统及游戏获利者死全家" + gameNumber;
                if (fileName.contains("chinese"))
                    gameName += "(c)";
                log("repacking " + unzipName + " to " + gameName + "...");
                Zip.zipFile(unzipName + ext, gameName + ext, "new/" + gameNumber + ".zip");
                logn("done!");

                log("deleting " + unzipName + "...");
                isDeleted = new File(unzipName + ext).delete();
                if (isDeleted)
                    logn("done!");
                else logn("fail!!");
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }

    private static void log(String msg) {
        System.out.print(msg);
    }

    private static void logn(String msg) {
        System.out.println(msg);
    }
}
