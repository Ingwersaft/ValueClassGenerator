package com.kesselring.valuegenerator;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.testFramework.EditorActionTestCase;

public class GenerateValueClassHandlerTest extends EditorActionTestCase {

    public void testPerson() throws Exception {
        setupAction();
        doFileTest("/person_before.java", "/person_after.java");
    }

    public void testMultifieldPerson() throws Exception {
        setupAction();
        doFileTest("/person_multifield_before.java", "/person_after.java");
    }

    public void testWithPrimitivesPerson() throws Exception {
        setupAction();
        doFileTest("/person_withprimitives_before.java", "/person_after.java");
    }

    private void setupAction() {
        try {
            ActionManager.getInstance().registerAction("GenerateValueClass", new GenerateValueClass());
        } catch (Throwable e) {
            e.printStackTrace();
        }
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
