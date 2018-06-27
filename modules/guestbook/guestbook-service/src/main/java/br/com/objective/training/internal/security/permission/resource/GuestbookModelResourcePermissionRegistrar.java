package br.com.objective.training.internal.security.permission.resource;

import br.com.objective.training.constants.GuestbookAdminPortletKeys;
import br.com.objective.training.constants.GuestbookConstants;
import br.com.objective.training.model.Guestbook;
import br.com.objective.training.service.GuestbookLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.HashMapDictionary;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.Dictionary;

@Component(immediate = true)
public class GuestbookModelResourcePermissionRegistrar {

    @Activate
    public void activate(BundleContext bundleContext) {

        Dictionary<String, Object> properties = new HashMapDictionary<>();

        properties.put("model.class.name", Guestbook.class.getName());

        _serviceRegistration = bundleContext.registerService(
                ModelResourcePermission.class,
                ModelResourcePermissionFactory.create(
                        Guestbook.class, Guestbook::getGuestbookId,
                        _guestbookLocalService::getGuestbook, _portletResourcePermission,
                        (modelResourcePermission, consumer) ->
                                consumer.accept(new StagingPermissionCheck(_stagingPermission))
                ),
                properties);

    }

    private ServiceRegistration<ModelResourcePermission> _serviceRegistration;

    @Deactivate
    public void deactivate() {
        _serviceRegistration.unregister();
    }

    @Reference
    private GuestbookLocalService _guestbookLocalService;

    @Reference(target = "(resource.name=" + GuestbookConstants.RESOURCE_NAME + ")")
    private PortletResourcePermission _portletResourcePermission;

    @Reference
    private StagingPermission _stagingPermission;

    private static class StagingPermissionCheck implements ModelResourcePermissionLogic<Guestbook> {

        @Override
        public Boolean contains(PermissionChecker permissionChecker, String name, Guestbook guestbook, String actionId) throws PortalException {

            return _stagingPermission.hasPermission(
                    permissionChecker, guestbook.getGroupId(),
                    Guestbook.class.getName(), guestbook.getGuestbookId(),
                    GuestbookAdminPortletKeys.GUESTBOOK_ADMIN_PORTLET, actionId
            );

        }

        private StagingPermissionCheck(StagingPermission stagingPermission) {
            _stagingPermission = stagingPermission;
        }

        private final StagingPermission _stagingPermission;

    }

}