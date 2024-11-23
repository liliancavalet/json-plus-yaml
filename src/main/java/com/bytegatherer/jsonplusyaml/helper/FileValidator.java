package com.bytegatherer.jsonplusyaml.helper;

import com.bytegatherer.jsonplusyaml.exceptions.ContentNotFoundException;
import com.bytegatherer.jsonplusyaml.exceptions.FileInProjectNotFoundException;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.vfs.VirtualFile;

public class FileValidator {

    public static void throwsIfVirtualFileDoesntExists(VirtualFile virtualFile) {
        if (virtualFile == null) {
            throw new FileInProjectNotFoundException("No file found.");
        }
    }

    public static String getFileContentIfAvailable(Document document) {
        if (document != null) {
            return document.getText();
        } else {
            throw new ContentNotFoundException("No content in file found.");
        }
    }

    public static void throwsIfFileContentIsEmpty(String content) {
        if (content.isEmpty()) {
            throw new ContentNotFoundException("No content in file to perform conversion.");
        }
    }
}
