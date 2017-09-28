package org.posobota.nette;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;

public class PresenterNameReferenceSearcher extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters>
{

    @Override
    public void processQuery(@NotNull ReferencesSearch.SearchParameters searchParameters, @NotNull Processor<PsiReference> processor)
    {

    }
}
