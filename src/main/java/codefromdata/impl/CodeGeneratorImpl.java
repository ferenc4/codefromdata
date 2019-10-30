package codefromdata.impl;

import codefromdata.CodeGenerator;
import codefromdata.Vocabulary;
import codefromdata.exceptions.NotImplementedException;
import com.google.gson.*;

import java.util.*;

public class CodeGeneratorImpl implements CodeGenerator {

    private final String topLevelSchemaName;
    private final Vocabulary vocabulary;

    public CodeGeneratorImpl(String topLevelSchemaName, Vocabulary vocabulary) {
        this.topLevelSchemaName = topLevelSchemaName;
        this.vocabulary = vocabulary;
    }

    public String usageCodeFromJsonString(String jsonString) {
        Map<JsonPath, JsonPrimitive> leafNodes = getLeafNodes(jsonString);
        Map<JsonPath, StringBuilder> nodes = new HashMap<>();
        for (Map.Entry<JsonPath, JsonPrimitive> node : leafNodes.entrySet()) {
            StringBuilder sb = new StringBuilder();
            JsonPath path = node.getKey().deepCopy();
            while (!path.isEmpty()) {
                JsonPath.Identifier last = path.popLast();
                JsonPath.Identifier.Type type = last.getType();
                if (type.equals(JsonPath.Identifier.Type.OBJECT_ID)) {
                    sb.append(vocabulary.setterFunctionNameForVariable(last.getStrId()));
                    sb.append("(");
                    sb.append(node.getValue());
                    sb.append(")");
                }
                nodes.put(path.deepCopy(), sb);
            }
        }
        return "leafNodes";
    }

    private Map<JsonPath, JsonPrimitive> getLeafNodes(String jsonString) {
        JsonElement element = new JsonParser().parse(jsonString);
        Stack<JsonPath> toProcess = new Stack<>();
        Map<JsonPath, JsonPrimitive> leafNodes = new TreeMap<>();
        do {
            JsonPath parentPath = new JsonPath();
            JsonObject jObject = element.getAsJsonObject();
            Set<String> fieldNames = jObject.keySet();
            for (String fieldName : fieldNames) {
                JsonElement jsonElement = jObject.get(fieldName);
                if (jsonElement.isJsonArray()) {
                    for (int i = 0; i < jsonElement.getAsJsonArray().size(); i++) {
                        JsonPath arrayEntry = parentPath.deepCopy().add(fieldName).add(i);
                        toProcess.push(arrayEntry);
                    }
                } else if (jsonElement.isJsonObject()) {
                    toProcess.push(parentPath.deepCopy().add(fieldName));
                } else if (jsonElement.isJsonPrimitive()) {
                    leafNodes.put(parentPath.deepCopy().add(fieldName), jsonElement.getAsJsonPrimitive());
                } else if (jsonElement.isJsonNull()) {
                    throw new NotImplementedException("Null json values are not yet supported.");
                } else {
                    throw new IllegalStateException(String.format("Invalid state for json element <%s>.",
                            jsonElement.toString()));
                }
            }
        } while (!toProcess.isEmpty());
        return leafNodes;
    }

    @Override
    public String schemaCodeFromJsonString(String json) {
        return "";
    }
}
