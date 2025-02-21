package com.mohammadalikassem.charrejli.modules.parsers.lb.touch;


import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models.TouchBundle;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models.TouchBundleConsumption;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models.TouchNumberCredentials;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models.TouchNumberDetails;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Component
public class TouchParser {
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TouchNumberDetails getNumberDetails(TouchNumberCredentials credentials) throws IOException {
        var session = Jsoup.newSession();
        // This will auto redirect to the login page and callback to the usage page
        var loginPage = session
                .url("https://www.touch.com.lb/autoforms/portal/touch/mytouch/myusage")
                .method(Method.GET)
                .timeout(100000)
                .execute();

        var loginForm = loginPage
                .parse()
                .forms()
                .stream()
                .filter(el -> el.is("#login-form"))
                .findFirst()
                .get();

        Map<String, String> formData = new HashMap<>();

        for (Element input : loginForm.select("input[type=hidden]")) {
            String name = input.attr("name");
            String value = input.attr("value");
            formData.put(name, value);
        }
        String action = loginForm.attr("action");

        formData.put("username", credentials.username()); // Replace with actual username
        formData.put("password", credentials.password()); // Replace with actual password

        var res = session.url(action)
                .data(formData)
                .method(Method.POST)
                .timeout(10000)
                .execute();

        return this.parseHtmlPage(res.parse());

    }

    private TouchNumberDetails parseHtmlPage(Document doc) {
        Optional<Element> myUsageDiv = Optional.ofNullable(doc.selectFirst("div.myUsage"));

        var mobileNumber = myUsageDiv
                .map(element -> element.select("div.infoContainer > div.mobileNumber > p").stream().findFirst())
                .flatMap(Function.identity())
                .map(Element::text)
                .map(String::strip)
                .orElse("");

        var balance = myUsageDiv
                .map(element -> element.select("div.infoContainer > div.unbilledNumber > p").stream().findFirst())
                .flatMap(Function.identity())
                .map(Element::text)
                .map(t -> t.replaceAll("\\$", ""))
                .map(String::strip)
                .map(Double::parseDouble)
                .orElse(0.0);

        var lineExpiry = myUsageDiv
                .map(element -> element.select("div.infoContainer > div.lineExpiry > p").stream().findFirst())
                .flatMap(Function.identity())
                .map(Element::text)
                .map(String::strip)
                .map(t -> LocalDate.parse(t, dateFormatter))
                .orElse(LocalDate.now());

        ArrayList<TouchBundle> bundles = new ArrayList<>();

        myUsageDiv.ifPresent(element -> element.select("div.unbilledInfo").forEach(e -> {

            LocalDateTime bundleValidity = LocalDateTime.now();
            var bundleName = e.select("h5")
                    .stream()
                    .findFirst()
                    .map(Element::text)
                    .map(String::strip)
                    .orElse("");

            var bundleConsumptions = new ArrayList<TouchBundleConsumption>();

            for (var x : e.select("ul > li")) {
                if (x.text().strip().startsWith("Valid till")) {
                    bundleValidity = Optional.ofNullable(x.selectFirst("span.price"))
                            .map(Element::text)
                            .map(String::strip)
                            .map(s -> s.contains(":") ? LocalDateTime.parse(s, dateTimeFormatter)
                                    : LocalDate.parse(s, dateFormatter).atStartOfDay())
                            .orElse(LocalDateTime.now());

                } else {
                    var p = Optional.ofNullable(x.selectFirst("span.price"));
                    AtomicReference<String> bundleConsumptionName = new AtomicReference<>(x.text());
                    AtomicReference<String> text = new AtomicReference<>("0 / 0");
                    p.ifPresent(span -> {
                        text.set(span.text());
                        span.remove();
                        bundleConsumptionName.set(x.text().strip());
                    });

                    var used = 0.0;
                    var of = 0.0;
                    var usedUnit = "";
                    var ofUnit = "";
                    var splitted = text.get().split("/");

                    if (splitted.length == 2) {

                        StringBuilder numbers = new StringBuilder();
                        StringBuilder nonNumbers = new StringBuilder();

                        for (char ch : splitted[0].strip().toCharArray()) {

                            if (Character.isDigit(ch) || ch == '.') {
                                numbers.append(ch);
                            } else {
                                nonNumbers.append(ch);
                            }
                        }
                        used = Double.parseDouble(numbers.toString().strip());

                        usedUnit = nonNumbers.toString().strip();

                        numbers = new StringBuilder();
                        nonNumbers = new StringBuilder();

                        for (char ch : splitted[1].strip().toCharArray()) {
                            if (Character.isDigit(ch) || ch == '.') {
                                numbers.append(ch);
                            } else {
                                nonNumbers.append(ch);
                            }
                        }
                        of = Double.parseDouble(numbers.toString().strip());

                        ofUnit = nonNumbers.toString().strip();

                    }

                    var consumption = new TouchBundleConsumption(bundleConsumptionName.get(), used, of,
                            of == 0.0 ? 0.0 : used / of, usedUnit, ofUnit);

                    bundleConsumptions.add(consumption);
                }
            }

            bundles.add(new TouchBundle(bundleName, bundleValidity, bundleConsumptions));

        }));

        var n = new TouchNumberDetails(mobileNumber, balance, lineExpiry, bundles);

        return n;
    }
}
