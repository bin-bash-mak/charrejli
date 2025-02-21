package com.mohammadalikassem.charrejli.endpoints;

import com.mohammadalikassem.charrejli.modules.parsers.lb.alfa.AlfaParser;
import com.mohammadalikassem.charrejli.modules.parsers.lb.alfa.models.AlfaCredentials;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.TouchParser;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models.TouchNumberCredentials;
import com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models.TouchNumberDetails;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@BrowserCallable
@AnonymousAllowed
public class HelloEndpoint {
    @Autowired
    TouchParser touchParser;
    @Autowired
    AlfaParser alfaParser;

    /**
     * A simple endpoint method that returns a greeting
     * to a person whose name is given as a parameter.
     * <p>
     * Both the parameter and the return value are
     * automatically considered to be Nonnull, due to
     * existence of <code>package-info.java</code>
     * in the same package that defines a
     * <code>@org.springframework.lang.NonNullApi</code>
     * for the current package.
     * <p>
     * Note that you can override the default Nonnull
     * behavior by annotating the parameter with
     * <code>@dev.hilla.Nullable</code>.
     *
     * @param name that assumed to be nonnull
     * @return a nonnull greeting
     */
    public int sayHello(String name) {
        if (name.isEmpty()) {
//            return "Hello stranger";
        } else {
//            return "Hello " + name;
        }
        return 1;
    }

    public TouchNumberDetails tempGetTouchNumberDetails(String username, String password) {
        try {
            return this.touchParser.getNumberDetails(new TouchNumberCredentials(username, password));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String tempGetAlfaNumberDetails(String username, String password) throws IOException {
        return this.alfaParser.getNumberDetails(new AlfaCredentials(username, password));
    }
}
