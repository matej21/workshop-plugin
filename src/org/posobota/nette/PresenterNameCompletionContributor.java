package org.posobota.nette;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.ElementPatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.Nullable;

public class PresenterNameCompletionContributor extends CompletionContributor
{

    public PresenterNameCompletionContributor()
    {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement().withParent(StringLiteralExpression.class).withSuperParent(3, new ElementPattern<PsiElement>()
        {
            @Override
            public boolean accepts(@Nullable Object o)
            {
                if (!(o instanceof MethodReference)) {
                    return false;
                }
                MethodReference ref = (MethodReference) o;
                if (ref.getName() == null || !(ref.getName().equals("link") || ref.getName().equals("redirect"))) {
                    return false;
                }
                //todo zkontrolovat, ze se to vola na presenteru
                return true;
            }

            @Override
            public boolean accepts(@Nullable Object o, ProcessingContext processingContext)
            {
                return (accepts(o));
            }

            @Override
            public ElementPatternCondition<PsiElement> getCondition()
            {
                return null;
            }
        }), new PresenterNameCompletionProvider());
    }
}
