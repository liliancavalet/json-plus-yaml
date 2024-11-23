package com.bytegatherer.jsonplusyaml.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.bytegatherer.jsonplusyaml.exceptions.ConversionFailedException;
import org.junit.jupiter.api.Test;

class JsonYamlConverterTest {

  @Test
  void shouldConvertJsonToYaml() {
    String validJson = "{\"key\":\"value\"}";

    String yamlOutput = JsonYamlConverter.convertToYaml(validJson);

    assertTrue(yamlOutput.contains("key: \"value\""));
  }

  @Test
  void shouldThrowNotJsonExceptionWhenContentProvidedIsNotJson() {
    String invalidJson = "not valid json";

    Exception exception = assertThrows(ConversionFailedException.class, () ->
        JsonYamlConverter.convertToYaml(invalidJson)
    );

    assertEquals("Conversion Error: Provided content is not JSON", exception.getMessage());
  }

  @Test
  void shouldThrowConversionFailedExceptionWhenMalformedJson() {
    String invalidJson = "{ \"key\": }";

    Exception exception = assertThrows(ConversionFailedException.class, () ->
        JsonYamlConverter.convertToYaml(invalidJson)
    );

    assertTrue(exception.getMessage().contains("Conversion Error:"));
  }

  @Test
  void shouldConvertYamlToJson() {
    String validYaml = "key: value";

    String jsonOutput = JsonYamlConverter.convertToJson(validYaml);

    assertTrue(jsonOutput.contains("\"key\" : \"value\""));
  }

  @Test
  void shouldThrowNotYamlExceptionWhenJsonProvidedInsteadOfYaml() {
    String jsonInput = "{\"key\":\"value\"}";

    Exception exception = assertThrows(ConversionFailedException.class, () ->
        JsonYamlConverter.convertToJson(jsonInput)
    );

    assertEquals("Conversion Error: Provided content is not YAML", exception.getMessage());
  }

  @Test
  void shouldThrowNotYamlExceptionWhenYamlContentNotProvided() {
    String invalidYaml = "{\"key\":\"value\"}";

    Exception exception = assertThrows(ConversionFailedException.class, () ->
        JsonYamlConverter.convertToJson(invalidYaml)
    );

    assertTrue(exception.getMessage().contains("Provided content is not YAML"));
  }

  @Test
  void shouldThrowConversionFailedExceptionWhenMalformedYaml() {
    String invalidYaml = "key: [value";

    Exception exception = assertThrows(ConversionFailedException.class, () ->
        JsonYamlConverter.convertToJson(invalidYaml)
    );

    assertTrue(exception.getMessage().contains("Conversion Error:"));
  }
}
