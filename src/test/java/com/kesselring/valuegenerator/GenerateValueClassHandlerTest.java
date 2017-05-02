package com.kesselring.valuegenerator;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.testFramework.LightIdeaTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsiUtilBase.class)
public class GenerateValueClassHandlerTest extends LightIdeaTestCase {

    @Mock
    private Editor mockedEditor;
    @Mock
    private Caret mockedCaret;
    @Mock
    private DataContext mockedDataContext;
    @Mock
    private Project mockedProject;
    @Mock
    private PsiFile mockedRootPsiFile;

    private String exampleClass = "import java.awt.*;\n" +
            "\n" +
            "public class Person {\n" +
            "\n" +
            "   String name;\n" +
            "   String password;\n" +
            "   String surname;\n" +
            "   Integer age;\n" +
            "   SystemColor systemColor;\n" +
            "}\n";

    @Before
    public void setup() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        when(mockedDataContext.getData(anyString())).thenReturn(mockedProject);

        PowerMockito.mockStatic(PsiUtilBase.class);
        PowerMockito.when(PsiUtilBase.getPsiFileInEditor(any(Editor.class), any(Project.class))).thenReturn(mockedRootPsiFile);

        PsiElement[] mockedChilden = createMockedChildren();

        PsiClass classFromText = getJavaFacade().getElementFactory().createClassFromText(exampleClass, null);
        when(mockedRootPsiFile.getChildren()).thenReturn(classFromText.getChildren());
    }

    private PsiElement[] createMockedChildren() {
        return new PsiElement[0];
    }

    @Test(expected = NullPointerException.class)
    public void firstTest() {
        new GenerateValueClassHandler().doExecute(mockedEditor, mockedCaret, mockedDataContext);
    }
}
