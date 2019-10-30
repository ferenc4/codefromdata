package codefromdata.impl;

import codefromdata.exceptions.NotImplementedException;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonPath extends Comparable<JsonPath> {
    private ArrayList<Identifier> jsonPath = new ArrayList<>();

    public Identifier getLast() {
        return jsonPath.get(jsonPath.size() - 1);
    }

    public Identifier popLast() {
        return jsonPath.remove(jsonPath.size() - 1);
    }

    public int length() {
        return jsonPath.size();
    }

    public boolean isEmpty() {
        return jsonPath.isEmpty();
    }


    public JsonPath deepCopy() {
        JsonPath jsonPathObj = new JsonPath();
        jsonPathObj.jsonPath = new ArrayList<>(this.jsonPath);
        return jsonPathObj;
    }

    public JsonPath add(String element) {
        jsonPath.add(new Identifier(element));
        return this;
    }

    public JsonPath add(int element) {
        jsonPath.add(new Identifier(element));
        return this;
    }

    public JsonPath addAll(Collection<String> elements) {
        jsonPath.addAll(elements.stream().map(Identifier::new).collect(Collectors.toList()));
        return this;
    }

    public JsonPath add(Collection<Integer> elements) {
        jsonPath.addAll(elements.stream().map(Identifier::new).collect(Collectors.toList()));
        return this;
    }

    public JsonElement find(JsonElement root) {
        JsonElement currentElement = root;
        for (Identifier fieldId : jsonPath) {
            if (Identifier.Type.OBJECT_ID.equals(fieldId.getType())) {
                currentElement = currentElement.getAsJsonObject().get(fieldId.getStrId());
            } else if (Identifier.Type.ARRAY_ID.equals(fieldId.getType())) {
                currentElement = currentElement.getAsJsonArray().get(fieldId.getIntId());
            } else {
                throw new NotImplementedException("Scenario not implemented");
            }
        }
        return currentElement;
    }

    @Override
    public int compareTo(JsonPath jsonPath) {
        if (this.length() != jsonPath.length()) return Integer.compare(this.length(), jsonPath.length());
        for (int i = 0; i < this.length(); i++) {
            this.jsonPath.get(i).
        }
        return 0;
    }

    static class Identifier extends Comparable<Identifier> {
        private final Integer intId;
        private final String strId;
        private final Type type;

        enum Type {
            OBJECT_ID,
            ARRAY_ID
        }

        Identifier(String strId) {
            this.strId = strId;
            this.intId = null;
            this.type = Type.OBJECT_ID;
        }

        Identifier(Integer intId) {
            this.strId = null;
            this.intId = intId;
            this.type = Type.ARRAY_ID;
        }

        Integer getIntId() {
            return intId;
        }

        String getStrId() {
            return strId;
        }

        Type getType() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Identifier that = (Identifier) o;
            return Objects.equals(intId, that.intId) &&
                    Objects.equals(strId, that.strId) &&
                    type == that.type;
        }

        @Override
        public int hashCode() {
            return Objects.hash(intId, strId, type);
        }
    }
}
