package org.posobota.nette;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.UsageSearchContext;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;

public class PresenterNameReferenceSearcher extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters>
{

    @Override
    public void processQuery(@NotNull ReferencesSearch.SearchParameters searchParameters, @NotNull Processor<PsiReference> processor)
    {
        if (!(searchParameters.getElementToSearch() instanceof PhpClass)) {
            return;
        }
        PhpClass presenter = (PhpClass) searchParameters.getElementToSearch();
        if (!PresenterUtils.isPresenter(presenter.getType(), PhpIndex.getInstance(presenter.getProject()))) {
            return;
        }
        String presenterNameFqn = PresenterMapper.classToPresenterName(presenter.getFQN());
        if (presenterNameFqn == null) {
            return;
        }
        PsiSearchHelper.SERVICE.getInstance(presenter.getProject())
                .processElementsWithWord((psiElement, i) -> {
                    if (!PresenterUtils.getPresenterNamePattern().accepts(psiElement)) {
                        return true;
                    }
                    processor.process(new PresenterNameReference(psiElement, presenter.getFQN()));
                    return true;
                }, searchParameters.getScopeDeterminedByUser(), presenterNameFqn, UsageSearchContext.IN_STRINGS, true);
    }
}
