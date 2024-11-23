package com.bytegatherer.jsonplusyaml.helper;

import com.bytegatherer.jsonplusyaml.exceptions.ContentNotFoundException;
import com.bytegatherer.jsonplusyaml.exceptions.EditorInProjectNotFoundException;
import com.intellij.openapi.editor.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EditorValidatorTest {

  @Test
  void shouldThrowEditorInProjectNotFoundExceptionIfEditorDoesntExists() {
    assertThrows(EditorInProjectNotFoundException.class, () ->
            EditorValidator.throwsIfEditorDoesntExists(null),
        "No text editor found."
    );
  }

  @Test
  void shouldThrowContentNotFoundExceptionIfNullDocument() {
    assertThrows(ContentNotFoundException.class, () ->
            EditorValidator.getEditorContentIfAvailable(null),
        "No content in editor found."
    );
  }

  @Test
  void shouldGetEditorContentWhenNonNullDocument() {
    Document document = mock(Document.class);
    when(document.getText()).thenReturn("My document");

    String content = EditorValidator.getEditorContentIfAvailable(document);
    assertEquals("My document", content);
  }

  @Test
  void shouldThrowContentNotFoundExceptionIfContentEmpty() {
    assertThrows(ContentNotFoundException.class, () ->
            EditorValidator.throwsIfEditorContentIsEmpty(""),
        "No text selected to perform conversion."
    );
  }
}
