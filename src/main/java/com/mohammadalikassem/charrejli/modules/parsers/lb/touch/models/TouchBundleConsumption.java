package com.mohammadalikassem.charrejli.modules.parsers.lb.touch.models;

public record TouchBundleConsumption(
                String name,
                Double used,
                Double of,
                Double percentage,
                String usedUnit,
                String ofUnit) {
}