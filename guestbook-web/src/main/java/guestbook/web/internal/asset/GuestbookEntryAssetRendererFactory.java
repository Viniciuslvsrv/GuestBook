package guestbook.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import guestbook.model.GuestbookEntry;
import guestbook.service.GuestbookEntryLocalService;
import guestbook.web.internal.security.permission.resource.GuestbookEntryPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import guestbook.constants.GuestbookConstants;

@Component(
    immediate = true,
    property = {"javax.portlet.name=" + GuestbookConstants.GUESTBOOKWEB},
    service = AssetRendererFactory.class
)
public class GuestbookEntryAssetRendererFactory extends BaseAssetRendererFactory<GuestbookEntry> {

    public GuestbookEntryAssetRendererFactory() {
        setClassName(CLASS_NAME);
        setLinkable(_LINKABLE);
        setPortletId(GuestbookConstants.GUESTBOOKWEB);
        setSearchable(true);
        setSelectable(true);
    }

    @Override
    public AssetRenderer<GuestbookEntry> getAssetRenderer(long classPK, int type)
            throws PortalException {

        GuestbookEntry entry = _guestbookEntryLocalService.getGuestbookEntry(classPK);

        GuestbookEntryAssetRenderer guestbookEntryAssetRenderer = new GuestbookEntryAssetRenderer(entry, _guestbookEntryModelResourcePermission);

        guestbookEntryAssetRenderer.setAssetRendererType(type);
        guestbookEntryAssetRenderer.setServletContext(_servletContext);

        return guestbookEntryAssetRenderer;
    }

    @Override
    public String getClassName() {
        return CLASS_NAME;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean hasPermission(PermissionChecker permissionChecker,
            long classPK, String actionId) throws Exception {

        GuestbookEntry entry = _guestbookEntryLocalService.getGuestbookEntry(classPK);
        
        return GuestbookEntryPermission.contains(permissionChecker, entry, actionId);
    }

    @Override
    public PortletURL getURLAdd(LiferayPortletRequest liferayPortletRequest,
            LiferayPortletResponse liferayPortletResponse, long classTypeId) {

        PortletURL portletURL = null;

        try {
            ThemeDisplay themeDisplay = (ThemeDisplay) liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);

            portletURL = liferayPortletResponse.createLiferayPortletURL(getControlPanelPlid(themeDisplay),
            		GuestbookConstants.GUESTBOOKWEB, PortletRequest.RENDER_PHASE);
           
            portletURL.setParameter("mvcPath", "/guestbook/edit_entry.jsp");
            portletURL.setParameter("showback", Boolean.FALSE.toString());
        
        } catch (PortalException e) {
        }

        return portletURL;
    }

    @Override
    public PortletURL getURLView(LiferayPortletResponse liferayPortletResponse, WindowState windowState) {

        LiferayPortletURL liferayPortletURL = liferayPortletResponse.createLiferayPortletURL(
        	GuestbookConstants.GUESTBOOKWEB, PortletRequest.RENDER_PHASE);

        try {
            liferayPortletURL.setWindowState(windowState);
        } catch (WindowStateException wse) {

        }
        
        return liferayPortletURL;
    }

    @Override
    public boolean isLinkable() {
        return _LINKABLE;
    }

    @Override
    public String getIconCssClass() {
        return "pencil";
    }

    @Reference(target = "(osgi.web.symbolicname=guestbook.web)", unbind = "-")
    public void setServletContext (ServletContext servletContext) {
        _servletContext = servletContext;
    }

    @Reference(unbind = "-")
    protected void setGuestbookEntryLocalService(GuestbookEntryLocalService guestbookEntryLocalService) {
        _guestbookEntryLocalService = guestbookEntryLocalService;
    }


    private GuestbookEntryLocalService _guestbookEntryLocalService;
    private ServletContext _servletContext;
    private ModelResourcePermission<GuestbookEntry> _guestbookEntryModelResourcePermission;
    
    private static final boolean _LINKABLE = true;
    
    public static final String TYPE = "entry";
    public static final String CLASS_NAME = GuestbookEntry.class.getName();
}
