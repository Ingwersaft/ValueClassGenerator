package com.kesselring.valuegenerator;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
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
public class GenerateValueClassHandlerTest {

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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(mockedDataContext.getData(anyString())).thenReturn(mockedProject);

        PowerMockito.mockStatic(PsiUtilBase.class);
        PowerMockito.when(PsiUtilBase.getPsiFileInEditor(any(Editor.class), any(Project.class))).thenReturn(mockedRootPsiFile);

        PsiElement[] mockedChilden = createMockedChildren();
//         when(mockedRootPsiFile.getChildren()).thenReturn(mockedChilden);
    }

    private PsiElement[] createMockedChildren() {
        return new PsiElement[0];
    }

    @Test(expected = NullPointerException.class)
    public void firstTest() {
        new GenerateValueClassHandler().doExecute(mockedEditor, mockedCaret, mockedDataContext);
    }

}
