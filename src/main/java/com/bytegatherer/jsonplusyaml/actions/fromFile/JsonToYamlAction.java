package com.bytegatherer.jsonplusyaml.actions.fromFile;

import com.bytegatherer.jsonplusyaml.logic.JsonYamlConverter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import static com.bytegatherer.jsonplusyaml.helper.FileValidator.*;
import static com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE;

public class JsonToYamlAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();

        try {
            VirtualFile selectedFile = event.getDataContext().getData(VIRTUAL_FILE);
            throwsIfVirtualFileDoesntExists(selectedFile);

            Document document = FileDocumentManager.getInstance().getDocument(selectedFile);
            String jsonInput = getFileContentIfAvailable(document);
            throwsIfFileContentIsEmpty(jsonInput);

            String yamlOutput = JsonYamlConverter.convertToYaml(jsonInput);

            WriteCommandAction.runWriteCommandAction(project, () -> document.setText(yamlOutput));
        } catch (Exception e) {
            Messages.showMessageDialog(project, e.getMessage(), "Error", Messages.getErrorIcon());
        }
    }
}
