package org.posobota.nette;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;

public class PresenterNameCompletionContributor extends CompletionContributor
{

    public PresenterNameCompletionContributor()
    {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withParent(StringLiteralExpression.class), new PresenterNameCompletionProvider());
    }
}
