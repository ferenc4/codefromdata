package codefromdata;

import codefromdata.impl.CodeGeneratorImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CodeGeneratorImplTest {
    @Test
    public void javaUsageCodeJsonStringTestForIntField() {
        String integerJson = "{\"intField1\":123}";
        String actualCode = CodeGenerator.defaultInstance().usageCodeFromJsonString(integerJson);
        String expectedCode = "SchemaDefinition instanceFromData = new SchemaDefinition();\n" +
                "instanceFromData.setIntField1(123);\n" +
                "ObjectFormatter of = new ObjectFormatter().writeValueAsString(instanceFromData);";
        assertEquals(expectedCode, actualCode);
    }

    @Test
    public void javaSchemaCodeJsonStringTestForIntField() {
        String integerJson = "{\"intField1\":123}";
        String actualCode = CodeGenerator.defaultInstance().schemaCodeFromJsonString(integerJson);
        String expectedCode = "class SchemaDefinition {\n" +
                "    private int intField1;\n" +
                "\n" +
                "    public void setIntField1(int intField1) {\n" +
                "        this.intField1 = intField1;\n" +
                "    }\n" +
                "\n" +
                "    public int getIntField1() {\n" +
                "        return intField1;\n" +
                "    }\n" +
                "}";

        assertEquals(expectedCode, actualCode);
    }
}
