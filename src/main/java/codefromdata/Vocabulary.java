package codefromdata;

import codefromdata.impl.JavaVocabularyImpl;

public interface Vocabulary {
    static Vocabulary getDefaultJavaVocabulary() {
        return new JavaVocabularyImpl();
    }

    LanguageVersion getLanguageVersion();

    default boolean isTypeOf(Vocabulary vocabulary) {
        return getLanguageVersion().equals(vocabulary.getLanguageVersion());
    }

    String setterFunctionNameForVariable(String variable);

    String getterFunctionNameForVariable(String variable);
}
