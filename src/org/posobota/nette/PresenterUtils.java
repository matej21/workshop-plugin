package org.posobota.nette;

import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.ElementPatternCondition;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

class PresenterUtils
{

    static boolean isPresenter(PhpType type, PhpIndex index)
    {
        Collection<PhpClass> presenters = index.getClassesByFQN("Nette\\Application\\UI\\Presenter");
        if (presenters.isEmpty()) {
            return false;
        }
        PhpClass presenter = presenters.iterator().next();
        return presenter.getType().isConvertibleFrom(type, index);
    }

    static ElementPattern<StringLiteralExpression> getPresenterNamePattern()
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

        return PlatformPatterns.psiElement(StringLiteralExpression.class)
                .withSuperParent(1, ParameterList.class)
                .withSuperParent(2, MethodReference.class)
                .and(firstParameterPattern)
                .withSuperParent(2, methodReferencePattern);
    }

}
