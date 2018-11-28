package com.kluhpas.kummerproject;

public class userConfig {
    private String name_Config;
    private boolean color_0;
    private boolean color_1;
    private boolean color_2;
    private boolean color_3;
    private boolean color_4;
    private boolean sound_0;
    private boolean sound_1;
    private boolean sound_2;
    private boolean sound_3;
    private boolean picture_0;
    private boolean picture_1;
    private boolean picture_2;
    private boolean picture_3;
    private boolean picture_4;
    private int delayMin;
    private int delayMax;
    private int showTime;
    private int workTime;
    private int restTime;
    private int sets;
    private boolean switch_timer_settings;

    protected userConfig(String name_Config, boolean color_0, boolean color_1, boolean color_2, boolean color_3, boolean color_4, boolean sound_0, boolean sound_1, boolean sound_2, boolean sound_3, boolean picture_0, boolean picture_1, boolean picture_2, boolean picture_3, boolean picture_4, int delayMin, int delayMax, int showTime, int workTime, int restTime, int sets, boolean switch_timer_settings) {
        this.name_Config = name_Config;
        this.color_0 = color_0;
        this.color_1 = color_1;
        this.color_2 = color_2;
        this.color_3 = color_3;
        this.color_4 = color_4;
        this.sound_0 = sound_0;
        this.sound_1 = sound_1;
        this.sound_2 = sound_2;
        this.sound_3 = sound_3;
        this.picture_0 = picture_0;
        this.picture_1 = picture_1;
        this.picture_2 = picture_2;
        this.picture_3 = picture_3;
        this.picture_4 = picture_4;
        this.delayMin = delayMin;
        this.delayMax = delayMax;
        this.showTime = showTime;
        this.workTime = workTime;
        this.restTime = restTime;
        this.sets = sets;
        this.switch_timer_settings = switch_timer_settings;
    }

    protected String getName_Config() {
        return name_Config;
    }

    protected boolean isColor_0() {
        return color_0;
    }

    protected boolean isColor_1() {
        return color_1;
    }

    protected boolean isColor_2() {
        return color_2;
    }

    protected boolean isColor_3() {
        return color_3;
    }

    protected boolean isColor_4() {
        return color_4;
    }

    protected boolean isSound_0() {
        return sound_0;
    }

    protected boolean isSound_1() {
        return sound_1;
    }

    protected boolean isSound_2() {
        return sound_2;
    }

    protected boolean isSound_3() {
        return sound_3;
    }

    protected boolean isPicture_0() {
        return picture_0;
    }

    protected boolean isPicture_1() {
        return picture_1;
    }

    protected boolean isPicture_2() {
        return picture_2;
    }

    protected boolean isPicture_3() {
        return picture_3;
    }

    protected boolean isPicture_4() {
        return picture_4;
    }

    protected int getDelayMin() {
        return delayMin;
    }

    protected int getDelayMax() {
        return delayMax;
    }

    protected int getShowTime() {
        return showTime;
    }

    protected int getWorkTime() {
        return workTime;
    }

    protected int getRestTime() {
        return restTime;
    }

    protected int getSets() {
        return sets;
    }

    protected boolean isSwitch_timer_settings() {
        return switch_timer_settings;
    }

    protected String getAllParamConcat () {
        String tmp = this.name_Config + ";" + this.color_0 + ";" + this.color_1 + ";" + this.color_2 + ";" + this.color_3 + ";" + this.color_4 + ";" + this.sound_0 + ";" + this.sound_1 + ";" + this.sound_2 + ";" + this.sound_3 + ";" + this.picture_0 + ";" + this.picture_1 + ";" + this.picture_2 + ";" + this.picture_3 + ";" + this.picture_4 + ";" + this.delayMin + ";" + this.delayMax + ";" + this.showTime + ";" + this.workTime + ";" + this.restTime + ";" + this.sets + ";" + this.switch_timer_settings;
        return tmp;
    }
}
