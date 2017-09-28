package org.posobota.nette;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.ElementPatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.Nullable;

public class PresenterNameCompletionContributor extends CompletionContributor
{

    public PresenterNameCompletionContributor()
    {
        ElementPattern<PsiElement> methodReferencePattern = new ElementPattern<PsiElement>()
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
                PhpIndex index = PhpIndex.getInstance(ref.getProject());
                if (ref.getClassReference() == null || !PresenterUtils.isPresenter(ref.getClassReference().getType(), index)) {
                    return false;
                }
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
        };
        ElementPattern<PsiElement> firstParameterPattern = new ElementPattern<PsiElement>()
        {
            @Override
            public boolean accepts(@Nullable Object o)
            {
                if (!(o instanceof StringLiteralExpression) || !(((StringLiteralExpression) o).getParent() instanceof ParameterList)) {
                    return false;
                }
                return ((ParameterList) ((StringLiteralExpression) o).getParent()).getParameters()[0] == o;
            }

            @Override
            public boolean accepts(@Nullable Object o, ProcessingContext processingContext)
            {
                return accepts(o);
            }

            @Override
            public ElementPatternCondition<PsiElement> getCondition()
            {
                return null;
            }
        };

        extend(CompletionType.BASIC, PlatformPatterns.psiElement()
                        .withParent(StringLiteralExpression.class)
                        .withSuperParent(2, ParameterList.class)
                        .withParent(firstParameterPattern)
                        .withSuperParent(3, MethodReference.class)
                        .withSuperParent(3, methodReferencePattern)
                , new PresenterNameCompletionProvider());
    }
}
