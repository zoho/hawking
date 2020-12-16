//$Id$
package com.zoho.hawking.language.english.tensepredictor.dependencies;

import org.apache.commons.lang3.tuple.Pair;

public class Root extends Dependencies<String, Pair<String, String>> {

    public Root(final String one, final Pair<String, String> two) {
        super(one, two);
    }

    public Pair<String, String> dep() {
        return two;
    }
}
