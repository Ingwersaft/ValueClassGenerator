import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiVariable;
import com.intellij.psi.util.PsiElementFilter;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiUtilBase;
import com.kesselring.valuegenerator.Type;
import com.kesselring.valuegenerator.Variable;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateValueClass extends EditorAction {

    public GenerateValueClass() {
        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
                System.out.println("doExecute called: editor = [" + editor + "], caret = [" + caret + "], " +
                        "dataContext = [" + dataContext + "]");
                super.doExecute(editor, caret, dataContext);
                Project project = (Project) dataContext.getData(DataKeys.PROJECT.getName());
                PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);

                PsiElement[] variables = PsiTreeUtil.collectElements(psiFile, new PsiElementFilter() {
                    public boolean isAccepted(PsiElement e) {
                        if (e instanceof PsiVariable) {
                            return true;
                        }
                        return false;
                    }
                });
                try {
                    List<Variable> variableList = Stream.of(variables)
                            .map(psiElement -> (PsiVariable) psiElement)
                            .map(psiVariable -> new Variable(
                                    new Type(psiVariable.getType().getCanonicalText()),
                                    new Variable.Name(psiVariable.getName())))
                            .peek(variable -> System.out.println(variable))
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    System.out.println(e.getClass());
                    e.printStackTrace();
                }
            }
        });
    }

    protected GenerateValueClass(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }
}
