package org.posobota.nette;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;


public class PresenterNameCompletionContributor extends CompletionContributor
{

    public PresenterNameCompletionContributor()
    {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withParent(PresenterUtils.getPresenterNamePattern()), new PresenterNameCompletionProvider());
    }
}
