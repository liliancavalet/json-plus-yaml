package com.bytegatherer.jsonplusyaml.helper;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bytegatherer.jsonplusyaml.exceptions.FileExtensionException;
import com.bytegatherer.jsonplusyaml.exceptions.FileInProjectNotFoundException;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FileAndEditorOperationsTest {

  @Mock
  VirtualFile virtualFile;

  @Mock
  Document document;

  @Mock
  FileDocumentManager fileDocumentManager;

  @Test
  void shouldSuccessfullyChangeVirtualFileExtension() throws IOException {
    when(virtualFile.getNameWithoutExtension()).thenReturn("testFile");

    FileAndEditorOperations.changeFileExtensionWithUndoRedo(virtualFile, "yaml");

    verify(virtualFile, times(1)).rename(eq("json-plus-yaml"), eq("testFile.yaml"));
  }

  @Test
  void shouldThrowFileExtensionExceptionWhenCantChangeVirtualFileExtension() throws IOException {
    when(virtualFile.getNameWithoutExtension()).thenReturn("testFile");
    doThrow(new RuntimeException("Rename failed")).when(virtualFile)
        .rename(anyString(), anyString());

    assertThrows(FileExtensionException.class, () ->
            FileAndEditorOperations.changeFileExtensionWithUndoRedo(virtualFile, "yaml"),
        "Couldn't convert file extension."
    );
  }

  @Test
  void shouldSuccessfullyChangeDocumentExtension() throws IOException {
    try (MockedStatic<FileDocumentManager> mockedStatic = mockStatic(FileDocumentManager.class)) {
      mockedStatic.when(FileDocumentManager::getInstance).thenReturn(fileDocumentManager);
      when(fileDocumentManager.getFile(document)).thenReturn(virtualFile);
      when(virtualFile.getNameWithoutExtension()).thenReturn("testFile");

      FileAndEditorOperations.changeFileExtensionWithUndoRedo(document, "yaml");

      verify(virtualFile, times(1)).rename(eq("json-plus-yaml"), eq("testFile.yaml"));
    }
  }

  @Test
  void shouldThrowFileInProjectNotFoundExceptionWhenNoFileFound() {
    assertThrows(FileInProjectNotFoundException.class, () ->
            FileAndEditorOperations.changeFileExtensionWithUndoRedo(document, "yaml"),
        "File not found."
    );
  }

  @Test
  void shouldThrowFileExtensionExceptionWhenCantChangeDocumentExtension() throws IOException {
    try (MockedStatic<FileDocumentManager> mockedStatic = mockStatic(FileDocumentManager.class)) {
      mockedStatic.when(FileDocumentManager::getInstance).thenReturn(fileDocumentManager);
      when(fileDocumentManager.getFile(document)).thenReturn(virtualFile);
      when(virtualFile.getNameWithoutExtension()).thenReturn("testFile");
      doThrow(new RuntimeException("Rename failed")).when(virtualFile)
          .rename(anyString(), anyString());

      assertThrows(FileExtensionException.class, () ->
              FileAndEditorOperations.changeFileExtensionWithUndoRedo(document, "yaml"),
          "Couldn't convert file extension."
      );
    }
  }
}
