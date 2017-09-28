package org.posobota.nette;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIcons;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class PresenterNameCompletionProvider extends CompletionProvider<CompletionParameters>
{

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet)
    {
        PsiElement el = completionParameters.getPosition();

        PhpIndex index = PhpIndex.getInstance(el.getProject());
        Collection<PhpClass> presenters = index.getClassesByFQN("Nette\\Application\\UI\\Presenter");
        if (presenters.isEmpty()) {
            return;
        }
        PhpClass presenter = presenters.iterator().next();

        index.getAllClassNames(completionResultSet.getPrefixMatcher())
                .stream()
                .flatMap(s -> index.getClassesByName(s).stream())
                .filter(cls -> presenter.getType().isConvertibleFrom(cls.getType(), index))
                .forEach(cls -> {
                    String presenterName = PresenterMapper.classToPresenterName(cls.getFQN());
                    if (presenterName == null) {
                        return;
                    }
                    completionResultSet.addElement(LookupElementBuilder.create(":" + presenterName + ":")
                            .withLookupString(presenterName.contains(":") ? presenterName.substring(presenterName.lastIndexOf(":") + 1) : presenterName)
                            .withPresentableText(presenterName)
                            .withTypeText(cls.getType().toStringResolved(), PhpIcons.CLASS, false)
                    );
                });
    }

}
