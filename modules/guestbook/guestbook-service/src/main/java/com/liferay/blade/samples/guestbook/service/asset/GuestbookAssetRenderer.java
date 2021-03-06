package com.liferay.blade.samples.guestbook.service.asset;

import static com.liferay.blade.samples.guestbook.constants.GuestbookAdminPortletKeys.GUESTBOOK_ADMIN_PORTLET;
import static com.liferay.blade.samples.guestbook.constants.GuestbookAdminPortletKeys.MVC_PATH_EDIT;
import static com.liferay.blade.samples.guestbook.constants.GuestbookWebPortletKeys.GUESTBOOK_WEB_PORTLET;

import static javax.portlet.PortletRequest.RENDER_PHASE;

import com.liferay.asset.kernel.model.BaseJSPAssetRenderer;
import com.liferay.blade.samples.guestbook.model.Guestbook;
import com.liferay.blade.samples.guestbook.service.permission.GuestbookPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GuestbookAssetRenderer extends BaseJSPAssetRenderer<Guestbook> {

	public GuestbookAssetRenderer(Guestbook guestbook) {
		_guestbook = guestbook;
	}

	@Override
	public Guestbook getAssetObject() {
		return _guestbook;
	}

	@Override
	public String getClassName() {
		return Guestbook.class.getName();
	}

	@Override
	public long getClassPK() {
		return _guestbook.getGuestbookId();
	}

	@Override
	public long getGroupId() {
		return _guestbook.getGroupId();
	}

	@Override
	public String getJspPath(HttpServletRequest request, String template) {
		if (template.equals(TEMPLATE_FULL_CONTENT)) {
			request.setAttribute("guestbook", _guestbook);

			return "/asset/guestbook/" + template + ".jsp";
		}

		return null;
	}

	@Override
	public String getSummary(PortletRequest request, PortletResponse response) {
		return "Name: " + _guestbook.getName();
	}

	@Override
	public String getTitle(Locale locale) {
		return _guestbook.getName();
	}

	@Override
	public PortletURL getURLEdit(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws Exception {

		PortletURL portletURL = liferayPortletResponse.createLiferayPortletURL(
	getControlPanelPlid(liferayPortletRequest), GUESTBOOK_WEB_PORTLET,
	PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", MVC_PATH_EDIT);
		portletURL.setParameter(
	"guestbookId", String.valueOf(_guestbook.getGuestbookId()));
		portletURL.setParameter("showback", Boolean.FALSE.toString());

		return portletURL;
	}

	@Override
	public String getURLView(
			LiferayPortletResponse liferayPortletResponse,
			WindowState windowState)
		throws Exception {

		return super.getURLView(liferayPortletResponse, windowState);
	}

	@Override
	public String getURLViewInContext(
			LiferayPortletRequest request, LiferayPortletResponse response,
			String noSuchEntryRedirect)
		throws Exception {

		try {
			long plid = PortalUtil.getPlidFromPortletId(
	_guestbook.getGroupId(), GUESTBOOK_ADMIN_PORTLET);

			PortletURL portletURL;

			if (plid == LayoutConstants.DEFAULT_PLID) {
				portletURL = response.createLiferayPortletURL(
	getControlPanelPlid(request), GUESTBOOK_ADMIN_PORTLET, RENDER_PHASE);
			} else {
				portletURL = PortletURLFactoryUtil.create(
	request, GUESTBOOK_ADMIN_PORTLET, plid, RENDER_PHASE);
			}

			portletURL.setParameter("mvcPath", MVC_PATH_EDIT);
			portletURL.setParameter(
	"guestbookId", String.valueOf(_guestbook.getGuestbookId()));

			String currentUrl = PortalUtil.getCurrentURL(request);

			portletURL.setParameter("redirect", currentUrl);

			return portletURL.toString();

		} catch (PortalException | SystemException e) {
			Logger.getLogger(GuestbookAssetRenderer.class.getName())
				.log(Level.SEVERE, null, e);
		}

		return noSuchEntryRedirect;
	}

	@Override
	public long getUserId() {
		return _guestbook.getUserId();
	}

	@Override
	public String getUserName() {
		return _guestbook.getUserName();
	}

	@Override
	public String getUuid() {
		return _guestbook.getUuid();
	}

	@Override
	public boolean hasEditPermission(PermissionChecker permissionChecker)
		throws PortalException {

		long guestbookId = _guestbook.getGuestbookId();

		return GuestbookPermission.contains(
	permissionChecker, guestbookId, ActionKeys.UPDATE);
	}

	@Override
	public boolean hasViewPermission(PermissionChecker permissionChecker)
		throws PortalException {

		long guestbookId = _guestbook.getGuestbookId();

		return GuestbookPermission.contains(
	permissionChecker, guestbookId, ActionKeys.VIEW);
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		request.setAttribute("GUESTBOOK", _guestbook);
		request.setAttribute("HtmlUtil", HtmlUtil.getHtml());
		request.setAttribute("StringUtil", new StringUtil());

		return super.include(request, response, template);
	}

	private Guestbook _guestbook;

}