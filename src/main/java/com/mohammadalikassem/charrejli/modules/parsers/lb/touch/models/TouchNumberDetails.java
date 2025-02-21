package com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models;

import java.time.LocalDate;
import java.util.ArrayList;

public record TouchNumberDetails(String number,
                Double balance,
                LocalDate expiry, ArrayList<TouchBundle> bundles) {
}
