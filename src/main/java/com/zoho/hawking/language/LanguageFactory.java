package com.zoho.hawking.language;

import com.zoho.hawking.language.english.EnglishLanguage;

public class LanguageFactory {

    public static AbstractLanguage getLanguageImpl(String lang){
        if(lang == null || lang.isEmpty()){
            return null;
        }
        if(lang.equals("eng")){
            return new EnglishLanguage();
        }
        return null;
    }
}
