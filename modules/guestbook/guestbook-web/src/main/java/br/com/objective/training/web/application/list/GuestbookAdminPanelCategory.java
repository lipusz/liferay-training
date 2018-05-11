package br.com.objective.training.web.application.list;

import br.com.objective.training.web.constants.GuestbookAdminPanelCategoryKeys;
import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import org.osgi.service.component.annotations.Component;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author lucas
 */
@Component(
        immediate = true,
        property = {
                "panel.category.key=" + PanelCategoryKeys.SITE_ADMINISTRATION,
                "panel.category.order:Integer=100"
        },
        service = PanelCategory.class
)
public class GuestbookAdminPanelCategory extends BasePanelCategory {

    @Override
    public String getKey() {
        return GuestbookAdminPanelCategoryKeys.CONTROL_PANEL_CATEGORY;
    }

    @Override
    public String getLabel(Locale locale) {
        ResourceBundle resourceBundle = ResourceBundleUtil
                .getBundle("content.Language", locale, getClass());

        return LanguageUtil.get(resourceBundle, "category.custom.label");
    }

}