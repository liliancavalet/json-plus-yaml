package com.bytegatherer.jsonplusyaml.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.bytegatherer.jsonplusyaml.exceptions.ContentNotFoundException;
import com.bytegatherer.jsonplusyaml.exceptions.FileInProjectNotFoundException;
import com.intellij.openapi.editor.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileValidatorTest {

  @Mock
  Document document;

  @Test
  void shouldThrowFileInProjectNotFoundExceptionIfVirtualFileDoesntExists() {
    Exception exception = assertThrows(FileInProjectNotFoundException.class, () ->
        FileValidator.throwsIfVirtualFileDoesntExists(null)
    );
    assertEquals("No file found.", exception.getMessage());
  }

  @Test
  void shouldReturnDocumentAsTextIfAvailable() {
    when(document.getText()).thenReturn("File content");

    String content = FileValidator.getFileContentIfAvailable(document);

    assertEquals("File content", content);
  }

  @Test
  void shouldThrowContentNotFoundExceptionIfFileContentNotAvailable() {
    Exception exception = assertThrows(ContentNotFoundException.class, () ->
        FileValidator.getFileContentIfAvailable(null)
    );
    assertEquals("No content in file found.", exception.getMessage());
  }

  @Test
  void shouldThrowContentNotFoundExceptionIfFileContentIsEmpty() {
    Exception exception = assertThrows(ContentNotFoundException.class, () ->
        FileValidator.throwsIfFileContentIsEmpty("")
    );
    assertEquals("No content in file to perform conversion.", exception.getMessage());
  }
}
