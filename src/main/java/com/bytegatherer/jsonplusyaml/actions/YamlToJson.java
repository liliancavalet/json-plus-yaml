package com.bytegatherer.jsonplusyaml.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

public class YamlToJson extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getData(CommonDataKeys.EDITOR);

        if (editor == null) {
            Messages.showMessageDialog(project, "No text editor found.", "Error", Messages.getErrorIcon());
            return;
        }

        SelectionModel selectionModel = editor.getSelectionModel();
        String yamlInput = selectionModel.getSelectedText();

        if (yamlInput == null || yamlInput.isEmpty()) {
            Messages.showMessageDialog(project, "No text selected.", "Error", Messages.getErrorIcon());
            return;
        }

        try {
            Yaml yaml = new Yaml();
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            Map<String, Object> yamlMap = yaml.load(yamlInput);

            // Convert Map to JSON String
            String jsonOutput = objectMapper.writeValueAsString(yamlMap);

            // Replace selected text with the converted JSON
            Document document = editor.getDocument();
            WriteCommandAction.runWriteCommandAction(project, () -> {
                document.replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), jsonOutput);
            });
        } catch (Exception e) {
            Messages.showMessageDialog(project, "Conversion Error: " + e.getMessage(), "Error", Messages.getErrorIcon());
        }
    }
}