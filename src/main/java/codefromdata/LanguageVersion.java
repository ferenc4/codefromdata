package codefromdata;

import java.util.Objects;

public class LanguageVersion {
    private String language;
    private String version;

    public LanguageVersion(String language, String version) {
        this.language = language;
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LanguageVersion that = (LanguageVersion) o;
        return Objects.equals(language, that.language) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, version);
    }
}
