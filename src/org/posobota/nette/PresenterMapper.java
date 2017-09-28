package org.posobota.nette;

import org.jetbrains.annotations.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PresenterMapper
{
    private static String prefix = "App\\";
    private static String modulePart = "*Module\\";
    private static String presenterPart = "Presenters\\*Presenter";

    @Nullable
    public static String classToPresenterName(String className)
    {
        String modulePartRegex = modulePart.replace("\\", "\\\\").replace("*", "(\\w+)");
        Pattern pattern = Pattern.compile("^\\\\?" + prefix.replace("\\", "\\\\")
                + "((?:" + modulePartRegex + ")*)"
                + presenterPart.replace("\\", "\\\\").replace("*", "(\\w+)") + "\\z");
        Matcher matcher = pattern.matcher(className);
        if (!matcher.matches()) {
            return null;
        }
        return Pattern.compile(modulePartRegex).matcher(matcher.group(1)).replaceAll("$1:") + matcher.group(3);
    }

    @Nullable
    public static String presenterNameToClass(String presenterName)
    {
        presenterName = presenterName.startsWith(":") ? presenterName.substring(1) : presenterName;
        switch (presenterName) {
            case "Homepage":
                return "App\\Presenters\\HomepagePresenter";
            case "Blog:ArticleDetail":
                return "App\\BlogModule\\Presenters\\ArticleDetailPresenter";
            case "Blog:ArticleList":
                return "App\\BlogModule\\Presenters\\ArticleListPresenter";
        }
        return null;
    }

}
