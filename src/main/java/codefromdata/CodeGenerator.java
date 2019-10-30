package codefromdata;

import codefromdata.impl.CodeGeneratorImpl;
import codefromdata.impl.JavaVocabularyImpl;

public interface CodeGenerator {
    static CodeGenerator defaultInstance() {
        return newBuilder().build();
    }

    static CodeGenerator.Builder newBuilder() {
        return new Builder();
    }

    String usageCodeFromJsonString(String json);

    String schemaCodeFromJsonString(String json);

    class Builder {
        static final String TOP_LEVEL_SCHEMA_CLASS = "TopLevelSchemaClass";
        private String topLevelSchemaName;
        private Vocabulary vocabulary;

        public Builder setTopLevelSchemaName(String topLevelSchemaName) {
            this.topLevelSchemaName = topLevelSchemaName;
            return this;
        }

        public Builder setVocabulary(Vocabulary vocabulary) {
            this.vocabulary = vocabulary;
            return this;
        }

        private Builder setEmptyToDefaults() {
            if (isEmpty(topLevelSchemaName)) topLevelSchemaName = TOP_LEVEL_SCHEMA_CLASS;
            if (vocabulary == null) vocabulary = new JavaVocabularyImpl();
            return this;
        }

        private boolean isEmpty(String str) {
            return str == null || str.isEmpty();
        }

        public CodeGenerator build() {
            setEmptyToDefaults();
            return new CodeGeneratorImpl(topLevelSchemaName, vocabulary);
        }
    }
}
