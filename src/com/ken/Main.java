package com.ken;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new GbkRepack();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void moonlight(String[] args) {
        String ext;
        if (args.length > 0)
            ext = "." + args[0];
        else ext = "";

        new Moonlight(ext);
    }
}
