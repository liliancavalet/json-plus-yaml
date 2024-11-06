package com.bytegatherer.jsonplusyaml.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;

public class JsonToYaml extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = FileEditorManager.getInstance(Objects.requireNonNull(event.getProject())).getSelectedTextEditor();

        if (editor == null) {
            Messages.showMessageDialog(project, "No text editor found.", "Error", Messages.getErrorIcon());
            return;
        }

        SelectionModel selectionModel = editor.getSelectionModel();
        String jsonInput = selectionModel.getSelectedText();

        if (jsonInput == null || jsonInput.isEmpty()) {
            Messages.showMessageDialog(project, "No text selected.", "Error", Messages.getErrorIcon());
            return;
        }

        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            YAMLMapper yamlMapper = new YAMLMapper();
            Map<String, Object> jsonMap = jsonMapper.readValue(jsonInput, Map.class);

            // Convert Map to YAML String
            String yamlOutput = yamlMapper.writeValueAsString(jsonMap);

            // Replace selected text with the converted YAML
            Document document = editor.getDocument();
            WriteCommandAction.runWriteCommandAction(project, () -> {
                document.replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), yamlOutput);
            });
        } catch (Exception e) {
            Messages.showMessageDialog(project, "Conversion Error: " + e.getMessage(), "Error", Messages.getErrorIcon());
        }
    }
}
