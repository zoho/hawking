//$Id$
package com.zoho.hawking.language.english.tensepredictor;

import com.zoho.hawking.HawkingTimeParser;
import com.zoho.hawking.language.english.tensepredictor.dependencies.Aux;
import com.zoho.hawking.language.english.tensepredictor.dependencies.Root;
import com.zoho.hawking.utils.Constants;
import com.zoho.hawking.utils.CoreNlpUtils;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class SentenceTensePredictor {

    private static final Logger LOGGER = Logger.getLogger(HawkingTimeParser.class.getName());

    private static final Tense PRESENT_SIMPLE =
            new Tense(Tenses.PRESENT_SIMPLE.name(), TenseClass.PRESENT.name(), TenseClass.PRESENT.name());
    private static final Tense PRESENT_CONTINUOUS =
            new Tense(Tenses.PRESENT_CONTINUOUS.name(), TenseClass.PRESENT.name(), TenseClass.PRESENT.name());
    private static final Tense PRESENT_PERFECT =
            new Tense(Tenses.PRESENT_PERFECT.name(), TenseClass.PRESENT.name(), TenseClass.PAST.name());
    private static final Tense PRESENT_PERFECT_CONTINUOUS =
            new Tense(Tenses.PRESENT_PERFECT_CONTINUOUS.name(), TenseClass.PRESENT.name(), TenseClass.PAST.name());
    private static final Tense PAST_SIMPLE =
            new Tense(Tenses.PAST_SIMPLE.name(), TenseClass.PAST.name(), TenseClass.PAST.name());
    private static final Tense PAST_CONTINUOUS =
            new Tense(Tenses.PAST_CONTINUOUS.name(), TenseClass.PAST.name(), TenseClass.PAST.name());
    private static final Tense PAST_PERFECT =
            new Tense(Tenses.PAST_PERFECT.name(), TenseClass.PAST.name(), TenseClass.PAST.name());
    private static final Tense PAST_PERFECT_CONTINUOUS =
            new Tense(Tenses.PAST_PERFECT_CONTINUOUS.name(), TenseClass.PAST.name(), TenseClass.PAST.name());
    private static final Tense FUTURE_SIMPLE =
            new Tense(Tenses.FUTURE_SIMPLE.name(), TenseClass.FUTURE.name(), TenseClass.FUTURE.name());
    private static final Tense FUTURE_CONTINUOUS =
            new Tense(Tenses.FUTURE_CONTINUOUS.name(), TenseClass.FUTURE.name(), TenseClass.FUTURE.name());
    private static final Tense FUTURE_PERFECT =
            new Tense(Tenses.FUTURE_PERFECT.name(), TenseClass.FUTURE.name(), TenseClass.FUTURE.name());
    private static final Tense FUTURE_PERFECT_CONTINUOUS =
            new Tense(Tenses.FUTURE_PERFECT_CONTINUOUS.name(), TenseClass.FUTURE.name(), TenseClass.FUTURE.name());
    private static final Tense DEFAULT_TENSE = new Tense("");

    public static Tense getSentenceTense(String sentence) {
        long st = System.currentTimeMillis();
        GrammaticalStructure grammaticalStructure = CoreNlpUtils.getParsedDependency(sentence).get(0);
        LOGGER.info("Time for parsing is " + (System.currentTimeMillis() - st));
        Root root = null;
        Root tmpRoot = null;
        String rootWord = null;
        ArrayList<Aux> aux = new ArrayList<>();
        for (TypedDependency dependency : grammaticalStructure.allTypedDependencies()) {
            if (dependency.reln().toString().equals("root")) {
                String[] dep = dependency.dep().toString().split("/");
                Pair<String, String> pairTwo = Pair.of(dep[0], dep[1]);
                if (Arrays.asList(Constants.ALL_VERB_POS).contains(dep[1])) {
                    root = new Root("root", pairTwo); //No I18N
                } else {
                    rootWord = dep[0];
                }
                tmpRoot = new Root("root", pairTwo); //No I18N
            } else if (root == null && dependency.gov().word().equals(rootWord)) {
                String[] dep = dependency.dep().toString().split("/");
                if (Arrays.asList(Constants.ALL_VERB_POS).contains(dep[1])) {
                    Pair<String, String> pairTwo = Pair.of(dep[0], dep[1]);
                    root = new Root("root", pairTwo); //No I18N
                }
            } else if (dependency.reln().toString().equals("aux") || dependency.reln().toString().equals("auxpass") ||
                    dependency.reln().toString().equals("cop")) {
                String[] gov = dependency.gov().toString().split("/");
                String[] dep = dependency.dep().toString().split("/");

                Pair<String, String> pairOne = Pair.of(gov[0], gov[1]);
                Pair<String, String> pairTwo = Pair.of(dep[0], dep[1]);
                aux.add(new Aux(pairOne, pairTwo));
            } else if (dependency.reln().toString().equals("dep")) {
                String[] gov = dependency.gov().toString().split("/");
                String[] dep = dependency.dep().toString().split("/");
                if (gov[1].equals("VBZ") && dep[1].equals("VBN")) {
                    Pair<String, String> pairTwo = Pair.of(dep[0], dep[1]);
                    root = new Root("root", pairTwo); //No I18N
                }
            }

        }
        if (root == null) {
            root = tmpRoot;
        }
        return getTense(root, aux);
    }

    private static Tense getTense(Root root, ArrayList<Aux> aux) {
        String rootWord = root.dep().getLeft();
        Tense tense = DEFAULT_TENSE;
        ArrayList<Aux> tmpList = new ArrayList<>();
        for (Aux tmpAux : aux) {
            if (!tmpAux.gov().getLeft().equals(rootWord)) {
                tmpList.add(tmpAux);
            }
        }
        aux.removeAll(tmpList);
        int auxSize = aux.size();
        switch (auxSize) {
            case 0:
                tense = getTense(root);
                break;
            case 1:
                tense = getTense(aux.get(0));
                break;
            case 2:
                tense = getTense(aux.get(0), aux.get(1));
                break;
            case 3:
                tense = getTense(aux.get(0), aux.get(1), aux.get(2));
                break;
        }

        return tense;
    }

    private static Tense getTense(Root root) {
        Tense tense = DEFAULT_TENSE;
        String pos = root.dep().getRight();
        if (Arrays.asList(Constants.PRESENT_POS).contains(pos)) {
            tense = PRESENT_SIMPLE;
        } else if (Arrays.asList(Constants.PAST_POS).contains(pos)) {
            tense = PAST_SIMPLE;
        }

        return tense;
    }

    private static Tense getTense(Aux aux) {
        Tense tense = DEFAULT_TENSE;
        String right = aux.gov().getRight();
        String left = aux.dep().getRight();
        switch (right) {
            case "VB":
                switch (left) {
                    case "VBP":
                    case "VBZ":
                        tense = PRESENT_SIMPLE;
                        break;
                    case "VBD":
                        tense = PAST_SIMPLE;
                        break;
                    case "MD":
                        if (Arrays.asList(Constants.MODAL_PRESENT).contains(aux.dep().getLeft())) {
                            tense = PRESENT_SIMPLE;
                        } else {
                            tense = FUTURE_SIMPLE;
                        }
                        break;
                }
                break;
            case "NN":
            case "JJ":
                if ("MD".equals(left)) {
                    tense = PRESENT_SIMPLE;
                }
                break;
            case "VBN":
                switch (left) {
                    case "VBP":
                    case "VBZ":
                        tense = PRESENT_PERFECT;
                        break;
                    case "VBD":
                        tense = PAST_PERFECT;
                        break;
                }
                break;
            case "VBD":
                if ("VBD".equals(left)) {
                    tense = PAST_PERFECT;
                }
                break;
            case "VBG":
                switch (left) {
                    case "VBP":
                    case "VBZ":
                        tense = PRESENT_CONTINUOUS;
                        break;
                    case "VBD":
                        tense = PAST_CONTINUOUS;
                        break;
                }
                break;
        }

        return tense;
    }

    private static Tense getTense(Aux aux1, Aux aux2) {
        Tense tense = DEFAULT_TENSE;
        String right1 = aux1.gov().getRight();
        String left1 = aux1.dep().getRight();
        String right2 = aux2.gov().getRight();
        String left2 = aux2.dep().getRight();
        if (Arrays.asList(Constants.MODAL_PAST).contains((aux1.dep().getLeft() + " " + aux2.dep().getLeft()))) {
            tense = PAST_PERFECT;
        } else if (right1.equals("VBN") && left1.equals("VBZ")) {
            switch (right2 + "/" + left2) {
                case "VBG/VBN":
                    tense = PRESENT_PERFECT_CONTINUOUS;
                    break;
                case "VBN/VBN":
                    tense = PRESENT_PERFECT;
                    break;
            }
        } else if (right1.equals("VBG") && left1.equals("VBP")) {
            if ("VBG/VBN".equals(right2 + "/" + left2)) {
                tense = PRESENT_PERFECT_CONTINUOUS;
            }
        } else if (right1.equals("VBG") && left1.equals("VBZ")) {
            if ("VBG/VBN".equals(right2 + "/" + left2)) {
                tense = PRESENT_PERFECT_CONTINUOUS;
            }
        } else if (right1.equals("VBN") && left1.equals("VBP")) {
            if ("VBG/VBN".equals(right2 + "/" + left2)) {
                tense = PRESENT_PERFECT_CONTINUOUS;
            }
        } else if (right1.equals("VBN") && left1.equals("VB")) {
            if ("VBG/VBN".equals(right2 + "/" + left2)) {
                tense = PRESENT_PERFECT_CONTINUOUS;
            }
        } else if (right1.equals("VBG") && left1.equals("VBD")) {
            if ("VBG/VBN".equals(right2 + "/" + left2)) {
                tense = PAST_PERFECT_CONTINUOUS;
            }
        } else if (right1.equals("VBD") && left1.equals("MD")) {
            if ("VBD/VB".equals(right2 + "/" + left2)) {
                tense = FUTURE_PERFECT;
            }
        } else if (right1.equals("VBN") && left1.equals("MD")) {
            switch (right2 + "/" + left2) {
                case "VBN/VB":
                case "VBN/VBP":
                    tense = FUTURE_PERFECT;
                    break;
            }
        } else if (right1.equals("VB") && left1.equals("MD")) {
            if ("VB/VB".equals(right2 + "/" + left2)) {
                tense = FUTURE_PERFECT;
            }
        } else if (right1.equals("VBG") && left1.equals("MD")) {
            if ("VBG/VB".equals(right2 + "/" + left2)) {
                tense = FUTURE_CONTINUOUS;
            }
        } else if (right1.equals("JJ")) {
            if (left1.equals("VBZ")) {
                if ("JJ/VBN".equals(right2 + "/" + left2)) {
                    tense = PRESENT_PERFECT;
                }
            }
        } else if (right1.equals("NN")) {
            if (left1.equals("VBZ")) {
                if ("NN/VBN".equals(right2 + "/" + left2)) {
                    tense = PRESENT_PERFECT_CONTINUOUS;
                }
            }
        }
        return tense;
    }

    private static Tense getTense(Aux aux1, Aux aux2, Aux aux3) {
        Tense tense = DEFAULT_TENSE;
        String right1 = aux1.gov().getRight();
        String left1 = aux1.dep().getRight();
        String right2 = aux2.gov().getRight();
        String left2 = aux2.dep().getRight();
        String right3 = aux3.gov().getRight();
        String left3 = aux3.dep().getRight();
        if (Arrays.asList(Constants.MODAL_PAST_PERFECT).contains((aux1.dep().getLeft() + " " + aux2.dep().getLeft() + " " + aux3.dep().getLeft()))) {
            tense = PAST_PERFECT;
        } else if (right1.equals("VBG") && left1.equals("MD")) {
            if (right2.equals("VBG") && left2.equals("VB")) {
                if ("VBG/VBN".equals(right3 + "/" + left3)) {
                    tense = FUTURE_PERFECT_CONTINUOUS;
                }
            }
        }

        return tense;
    }

}