package com.keylina.core;

import static com.codeborne.selenide.Selenide.*;
import com.codeborne.selenide.SelenideElement;
import com.keylina.core.AppParams;
public class PageObject {

    String url;
    
    public void open(String url) {
        url = com.keylina.core.AppParams.getValueFromAppParams(url);
        open(url);
    }




}
