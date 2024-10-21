package com.randomEventAlert;

public enum Sound {
    HI_GUYS_MAIKERU_HERE("hi_guys_maikeru_here.wav");

    private final String resourceName;

    Sound(String resNam) {
        resourceName = resNam;
    }

    String getResourceName() {
        return resourceName;
    }
}
