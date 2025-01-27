package com.mdci.backend.controller;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "pagination")
public class PaginationProperties {

    private int defaultPage;
    private int defaultSize;
    private int maxSize;

    // Getters et Setters
    public int getDefaultPage() {
        return defaultPage;
    }

    public void setDefaultPage(int defaultPage) {
        this.defaultPage = defaultPage;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(int defaultSize) {
        this.defaultSize = defaultSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
