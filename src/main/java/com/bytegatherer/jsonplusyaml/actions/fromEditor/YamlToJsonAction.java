package com.bytegatherer.jsonplusyaml.actions.fromEditor;

import com.bytegatherer.jsonplusyaml.logic.JsonYamlConverter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.bytegatherer.jsonplusyaml.helper.EditorValidator.*;

public class YamlToJsonAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        try {
            Editor editor = FileEditorManager.getInstance(Objects.requireNonNull(event.getProject())).getSelectedTextEditor();
            throwsIfEditorDoesntExists(editor);

            Document document = editor.getDocument();
            String yamlInput = getEditorContentIfAvailable(document);
            throwsIfEditorContentIsEmpty(yamlInput);

            String jsonOutput = JsonYamlConverter.convertToJson(yamlInput);

            WriteCommandAction.runWriteCommandAction(project, () -> document.setText(jsonOutput));
        } catch (Exception e) {
            Messages.showMessageDialog(project, e.getMessage(), "Error", Messages.getErrorIcon());
        }
    }
}