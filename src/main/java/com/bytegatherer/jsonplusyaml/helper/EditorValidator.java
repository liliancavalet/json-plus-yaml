package com.bytegatherer.jsonplusyaml.helper;

import com.bytegatherer.jsonplusyaml.exceptions.ContentNotFoundException;
import com.bytegatherer.jsonplusyaml.exceptions.EditorInProjectNotFoundException;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;

public class EditorValidator {

    public static void throwsIfEditorDoesntExists(Editor editor) {
        if (editor == null) {
            throw new EditorInProjectNotFoundException("No text editor found.");
        }
    }

    public static String getEditorContentIfAvailable(Document document) {
        if (document != null) {
            return document.getText();
        } else {
            throw new ContentNotFoundException("No content in editor found.");
        }
    }

    public static void throwsIfEditorContentIsEmpty(String content) {
        if (content.isEmpty()) {
            throw new ContentNotFoundException("No text selected to perform conversion.");
        }
    }
}
