package org.posobota.nette;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;

public class PresenterNameReferenceProvider extends PsiReferenceProvider
{

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext)
    {
        assert psiElement instanceof StringLiteralExpression;
        String presenterName = ((StringLiteralExpression) psiElement).getContents();
        presenterName = PresenterUtils.destinationToPresenter(presenterName);
        if (presenterName == null) {
            return new PsiReference[0];
        }
        String presenterClassName = PresenterMapper.presenterNameToClass(presenterName);

        return new PsiReference[]{new PresenterNameReference(psiElement, presenterClassName)};
    }

}
