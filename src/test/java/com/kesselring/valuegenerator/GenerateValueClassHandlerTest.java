package com.kesselring.valuegenerator;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.testFramework.EditorActionTestCase;

public class GenerateValueClassHandlerTest extends EditorActionTestCase {

    public void testPerson() throws Exception {
        ActionManager.getInstance().registerAction("GenerateValueClass", new GenerateValueClass());
        doFileTest("/person_before.java", "/person_after.java");
    }

    @Override
    protected String getActionId() {
        return "GenerateValueClass";
    }

    @Override
    protected String getTestDataPath() {
        return "testData";
    }

}
