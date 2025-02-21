package com.mohammadalikassem.charrejli.modules.parsers.lb.alfa;

import com.mohammadalikassem.charrejli.modules.parsers.lb.alfa.models.AlfaCredentials;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.Instant;

@Component
public class AlfaParser {
    public String getNumberDetails(AlfaCredentials credentials) throws IOException {
        var sess = Jsoup.newSession();

        Connection.Response loginResponse = sess.url("https://www.alfa.com.lb/en/account/login")
                .method(Connection.Method.GET)
                .execute();
        String requestVerificationToken = loginResponse.parse()
                .select("input[name=__RequestVerificationToken]").val();

        @SuppressWarnings("unused")
        Connection.Response response = sess.url("https://www.alfa.com.lb/en/account/login")
                .data("__RequestVerificationToken", requestVerificationToken)
                .data("Username", credentials.username()) // Replace with actual mobile number
                .data("Password", credentials.password()) // Replace with actual password
                .data("RememberMe", "false")
                // .cookies(loginResponse.cookies())
                .method(Connection.Method.POST)
                .timeout(100000)
                .execute();

        @SuppressWarnings("unused")
        Connection.Response myServicesResponse = sess
                .url("https://www.alfa.com.lb/en/account/manage-services/getmyservices?_="
                        + Instant.now().toEpochMilli())
                .ignoreContentType(true)
                .header("X-Requested-With", "XMLHttpRequest")
                // .cookies(response.cookies())
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .timeout(100000)
                .execute();
        return myServicesResponse.body();
    }
}