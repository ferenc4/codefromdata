package codefromdata.impl;

import codefromdata.LanguageVersion;
import codefromdata.Vocabulary;

public class JavaVocabularyImpl implements Vocabulary {

    private static final String SET = "set";
    private static final String GET = "get";

    @Override
    public LanguageVersion getLanguageVersion() {
        return new LanguageVersion("Java", "11");
    }

    public String setterFunctionNameForVariable(String variable) {
        return setterPrefix() + toTitleCase(variable);
    }

    public String getterFunctionNameForVariable(String variable) {
        return getterPrefix() + toTitleCase(variable);
    }

    private String toTitleCase(String variable) {
        StringBuilder output = new StringBuilder();
        if (variable.length() > 1) {
            output.append(variable.substring(0, 1).toUpperCase());
            output.append(variable.substring(1));
        } else {
            output.append(variable.substring(0, 1).toUpperCase());
        }
        return output.toString();
    }

    private String setterPrefix() {
        return SET;
    }

    private String getterPrefix() {
        return GET;
    }
}
