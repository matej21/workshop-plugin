package org.posobota.nette;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;

public class PresenterNameReferenceContributor extends PsiReferenceContributor
{

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar psiReferenceRegistrar)
    {
        psiReferenceRegistrar.registerReferenceProvider(PlatformPatterns.psiElement().and(PresenterUtils.getPresenterNamePattern()), new PresenterNameReferenceProvider());
    }

}
