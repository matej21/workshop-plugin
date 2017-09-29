package org.posobota.nette;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
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
            }
        };
    }
}
