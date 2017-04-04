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
import org.jetbrains.annotations.Nullable;

public class GenerateValueClass extends EditorAction {

    public GenerateValueClass() {
        super(new EditorActionHandler() {
            @Override
            protected void doExecute(Editor editor, @Nullable Caret caret, DataContext dataContext) {
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
                for (PsiElement var : variables) {
                    System.out.println(var.toString());
                }
            }
        });
    }

    protected GenerateValueClass(EditorActionHandler defaultHandler) {
        super(defaultHandler);
    }
}
