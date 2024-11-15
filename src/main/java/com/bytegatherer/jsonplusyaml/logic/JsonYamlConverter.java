package com.bytegatherer.jsonplusyaml.logic;

import com.bytegatherer.jsonplusyaml.exceptions.ConversionFailedException;
import com.bytegatherer.jsonplusyaml.exceptions.NotJsonException;
import com.bytegatherer.jsonplusyaml.exceptions.NotYamlException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class JsonYamlConverter {

    public static String convertToYaml(String jsonInput) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            YAMLMapper yamlMapper = new YAMLMapper();
            throwsIfNotJson(jsonInput);
            Map<String, Object> jsonMap = jsonMapper.readValue(jsonInput, new TypeReference<Map<String, Object>>() {
            });

            // Convert Map to YAML String
            return yamlMapper.writeValueAsString(jsonMap);
        } catch (Exception e) {
            throw new ConversionFailedException("Conversion Error: " + e.getMessage(), e.getCause());
        }
    }

    private static void throwsIfNotJson(String jsonInput) {
        if (!isJson(jsonInput)) {
            throw new NotJsonException("Provided content is not JSON");
        }
    }

    public static String convertToJson(String yamlInput) {
        try {
            Yaml yaml = new Yaml();
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            Map<String, Object> yamlMap = getYamlIfCorrect(yaml, yamlInput);

            // Convert Map to JSON String
            return objectMapper.writeValueAsString(yamlMap);
        } catch (Exception e) {
            throw new ConversionFailedException("Conversion Error: " + e.getMessage(), e.getCause());
        }
    }

    private static Map<String, Object> getYamlIfCorrect(Yaml yaml, String yamlInput) {

        if (isJson(yamlInput)) {
            throw new NotYamlException("Provided content is not YAML");
        }

        try {
            return yaml.load(yamlInput);
        } catch (Exception e) {
            throw new NotYamlException("Provided content is not YAML", e.getCause());
        }
    }

    private static boolean isJson(String input) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.readTree(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
