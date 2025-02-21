package com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

public record TouchBundle(String name, LocalDateTime validity, ArrayList<TouchBundleConsumption> consumptions) {
}
