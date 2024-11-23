package com.bytegatherer.jsonplusyaml.helper;

import com.bytegatherer.jsonplusyaml.exceptions.FileExtensionException;
import com.bytegatherer.jsonplusyaml.exceptions.FileInProjectNotFoundException;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;

public class FileAndEditorOperations {

    public static final String REQUESTOR = "json-plus-yaml";

    public static void changeFileExtensionWithUndoRedo(VirtualFile file, String extension){
        try{
            String currentName = file.getNameWithoutExtension();
            String newName = String.format("%s.%s", currentName, extension);
            file.rename(REQUESTOR, newName);
        } catch(Exception e){
            throw new FileExtensionException("Couldn't convert file extension.");
        }
    }

    public static void changeFileExtensionWithUndoRedo(Document document, String extension){
        try{
            VirtualFile file = FileDocumentManager.getInstance().getFile(document);
            String currentName = file.getNameWithoutExtension();
            String newName = String.format("%s.%s", currentName, extension);
            file.rename(REQUESTOR, newName);
        } catch(NullPointerException e){
            throw new FileInProjectNotFoundException("File not found.");
        } catch(Exception e){
            throw new FileExtensionException("Couldn't convert file extension.");
        }
    }
}
