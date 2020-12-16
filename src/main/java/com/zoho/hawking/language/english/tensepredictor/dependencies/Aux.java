//$Id$
package com.zoho.hawking.language.english.tensepredictor.dependencies;

import org.apache.commons.lang3.tuple.Pair;

public class Aux extends Dependencies<Pair<String, String>, Pair<String, String>> {

    public Aux(Pair<String, String> one, Pair<String, String> two) {
        super(one, two);
    }

    public Pair<String, String> gov() {
        return one;
    }

    public Pair<String, String> dep() {
        return two;
    }

}
