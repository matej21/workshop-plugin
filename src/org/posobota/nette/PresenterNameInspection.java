package org.posobota.nette;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.inspections.PhpInspection;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;

public class PresenterNameInspection extends PhpInspection
{

    @NotNull
    @Override
    public String getShortName()
    {
        return "NettePresenterName";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder problemsHolder, boolean b)
    {
        return new PhpElementVisitor()
        {
            @Override
            public void visitPhpStringLiteralExpression(StringLiteralExpression expression)
            {
                super.visitPhpStringLiteralExpression(expression);
                if (!PresenterUtils.getPresenterNamePattern().accepts(expression)) {
                    return;
                }
                String presenterName = expression.getContents();
                String presenterClass = PresenterMapper.presenterNameToClass(presenterName);
                if (PhpIndex.getInstance(expression.getProject()).getAnyByFQN(presenterClass).size() > 0) {
                    return;
                }
                problemsHolder.registerProblem(expression, "Target presenter " + presenterClass + " not found");
            }
        };
    }
}
