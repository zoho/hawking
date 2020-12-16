//$Id$
package com.zoho.hawking.datetimeparser.utils;

import com.zoho.hawking.utils.Constants;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class RelationShipMatching {
    private Map<String, String> strTochar;
    private Map<String, String> mapInversed;
    private Map<String, String> componentMapOne;
    private Map<String, String> componentMapTwo;
    private String regexRemoveSpecialChar = "A-Za-z"; //No I18N
    private String parsedTextOne;
    private String parsedTextTwo;
    private boolean canExtract = true;

    public RelationShipMatching(Map<String, String> componentMapOne, Map<String, String> componentMapTwo,
                                String parsedTextOne, String parsedTextTwo) {
        this.parsedTextOne = parsedTextOne;
        this.parsedTextTwo = parsedTextTwo;
        this.componentMapOne = componentMapOne;
        this.componentMapTwo = componentMapTwo;
        setMaps();
    }

    private void setMaps() {
        strTochar = new HashMap<>();
        String[] keys;
        int counter1 = Collections.frequency(new ArrayList<String>(componentMapOne.values()), null);
        int counter2 = Collections.frequency(new ArrayList<String>(componentMapTwo.values()), null);
        if (counter1 == counter2) {
            keys = Constants.TAGS_TO_PARSE;
            HashMap<String, String> parsedComponentOne = TagParser.tagParser(parsedTextOne);
            HashMap<String, String> parsedComponentTwo = TagParser.tagParser(parsedTextTwo);
            Set<String> componentSetOne = parsedComponentOne.keySet();
            Set<String> componentSetTwo = parsedComponentTwo.keySet();
            Set<String> intersection;
            if (componentSetOne.size() > componentSetTwo.size()) {
                intersection = new HashSet<>(componentSetOne);
                intersection.retainAll(componentSetTwo);
            } else {
                intersection = new HashSet<>(componentSetTwo);
                intersection.retainAll(componentSetOne);
            }
            String[] elementsToRemove = {Constants.PREFIX_TAG, Constants.POSTFIX_TAG, Constants.SET_PREFIX_TAG};
            intersection.removeAll(new ArrayList<>(Arrays.asList(elementsToRemove)));
            if (intersection.size() > 0) {
                this.componentMapOne = specialComponentMap(parsedComponentOne);
                this.componentMapTwo = specialComponentMap(parsedComponentTwo);
            } else {
                this.canExtract = false;
            }

        } else {
            keys = componentMapOne.keySet().toArray(new String[componentMapOne.size()]);
        }
        if (canExtract) {
            for (int i = 0; i < keys.length; i++) {
                if ((97 + i) > 122) {
                    regexRemoveSpecialChar += (char) (97 + i);
                }
                strTochar.put(keys[i], Character.toString((char) (97 + i)));
            }
            mapInversed = strTochar.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
        }
    }

    /**
     * @return the strTochar
     */
    public Map<String, String> getStrTochar() {
        return strTochar;
    }

    /**
     * @return the mapInversed
     */
    public Map<String, String> getMapInversed() {
        return mapInversed;
    }

    /**
     * @return the componentMapOne
     */
    public Map<String, String> getComponentMapOne() {
        return componentMapOne;
    }

    /**
     * @return the componentMapTwo
     */
    public Map<String, String> getComponentMapTwo() {
        return componentMapTwo;
    }

    /**
     * @return the parsedTextOne
     */
    public String getParsedTextOne() {
        return parsedTextOne;
    }


    /**
     * @return the parsedTextTwo
     */
    public String getParsedTextTwo() {
        return parsedTextTwo;
    }

    /**
     * @return the regexRemoveSpecialChar
     */
    public String getRegexRemoveSpecialChar() {
        return "[^" + regexRemoveSpecialChar + "]";
    }

    private Map<String, String> specialComponentMap(Map<String, String> map) {
        Map<String, String> tmpMap = new HashMap<>();
        for (Entry<String, String> entrySet : map.entrySet()) {
            tmpMap.put(entrySet.getKey(), "<" + entrySet.getKey() + ">" + entrySet.getValue() + "</" + entrySet.getKey() + ">");
        }
        return tmpMap;
    }

    public boolean getCanExtract() {
        return canExtract;
    }

}
