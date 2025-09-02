package com.SwagLab.utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.util.Date;

public class CookieUtils {
    private CookieUtils() {
    }

    private static final String COOKIE_FILE_PATH = "cookies.data";

    public static void saveCookies(WebDriver driver) throws IOException {
        File file = new File(COOKIE_FILE_PATH);
        file.delete();
        file.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        for (Cookie ck : driver.manage().getCookies()) {
            writer.write(ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" +
                    ck.getPath() + ";" + ck.getExpiry() + ";" + ck.isSecure());
            writer.newLine();
        }
        writer.close();
    }

    public static void loadCookies(WebDriver driver, String baseUrl) throws IOException {
        File file = new File(COOKIE_FILE_PATH);
        if (!file.exists()) return;

        driver.get(baseUrl); // Required before setting cookies

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] token = line.split(";");
            String name = token[0];
            String value = token[1];
            String domain = token[2];
            String path = token[3];
            Date expiry = token[4].equals("null") ? null : new Date(token[4]);
            boolean isSecure = Boolean.parseBoolean(token[5]);

            Cookie cookie = new Cookie(name, value, domain, path, expiry, isSecure);
            driver.manage().addCookie(cookie);
        }
        reader.close();
        BrowserActions.refreshBrowser(driver);
    }
}
