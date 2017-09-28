package org.posobota.nette;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.jetbrains.php.PhpIndex;
import org.jetbrains.annotations.NotNull;

public class PresenterNameReference extends PsiPolyVariantReferenceBase<PsiElement>
{

    private String presenterClassName;

    public PresenterNameReference(PsiElement psiElement, String presenterClassName)
    {
        super(psiElement);
        this.presenterClassName = presenterClassName;
        int end = psiElement.getText().lastIndexOf(":");
        int start = psiElement.getText().lastIndexOf(":", end - 1);
        this.setRangeInElement(new TextRange(start + 1, end));
    }


    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean b)
    {
        return PsiElementResolveResult.createResults(PhpIndex.getInstance(getElement().getProject()).getClassesByFQN(presenterClassName));
    }


    @NotNull
    @Override
    public Object[] getVariants()
    {
        return new Object[0];
    }
}
