package org.posobota.nette;

import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;

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

}
