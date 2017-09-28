package org.posobota.nette;

import org.jetbrains.annotations.Nullable;


public class PresenterMapper
{

    @Nullable
    public static String classToPresenterName(String className)
    {
        switch (className) {
            case "\\App\\Presenters\\HomepagePresenter":
                return "Homepage";
            case "\\App\\BlogModule\\Presenters\\ArticleDetailPresenter":
                return "Blog:ArticleDetail";
            case "\\App\\BlogModule\\Presenters\\ArticleListPresenter":
                return "Blog:ArticleList";
        }
        return null;
    }
}
