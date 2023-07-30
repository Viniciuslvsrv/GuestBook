package guestbook.web.internal.asset;

import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.BaseAssetRendererFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import guestbook.constants.GuestbookConstants;

import guestbook.model.Guestbook;
import guestbook.service.GuestbookLocalService;
import guestbook.web.internal.security.permission.resource.GuestbookPermission;

@Component(
	immediate = true, 
	property = {"javax.portlet.name=" + GuestbookConstants.GUESTBOOKWEB}, 
	service = AssetRendererFactory.class
)
public class GuestbookAssetRendererFactory extends BaseAssetRendererFactory<Guestbook> {

	  public GuestbookAssetRendererFactory() {
	      setClassName(CLASS_NAME);
	      setLinkable(_LINKABLE);
	      setPortletId(GuestbookConstants.GUESTBOOKWEB); 
	      setSearchable(true);
	      setSelectable(true); 
	  }         

	  @Override
   	  public AssetRenderer<Guestbook> getAssetRenderer(long classPK, int type) 
			     throws PortalException {

	      Guestbook guestbook = _guestbookLocalService.getGuestbook(classPK);

	      GuestbookAssetRenderer guestbookAssetRenderer = 
	      new GuestbookAssetRenderer(guestbook, _guestbookModelResourcePermission);

	      guestbookAssetRenderer.setAssetRendererType(type);
	      guestbookAssetRenderer.setServletContext(_servletContext);

	      return guestbookAssetRenderer;
	  }
	  
	  @Override
	  public boolean hasPermission(PermissionChecker permissionChecker, 
	             long classPK, String actionId) throws Exception {

	      Guestbook guestbook = _guestbookLocalService.getGuestbook(classPK);
	      
	      long groupId = guestbook.getGroupId();
	      
	      return GuestbookPermission.contains(permissionChecker, groupId, actionId);
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
	  public PortletURL getURLAdd(LiferayPortletRequest liferayPortletRequest,
	             LiferayPortletResponse liferayPortletResponse, long classTypeId) {
	      
		  PortletURL portletURL = null;

	      try {
	    	  
	          ThemeDisplay themeDisplay = (ThemeDisplay) 
	          liferayPortletRequest.getAttribute(WebKeys.THEME_DISPLAY);

	          portletURL = liferayPortletResponse.createLiferayPortletURL(getControlPanelPlid(themeDisplay),
	        	  GuestbookConstants.GUESTBOOKWEB, PortletRequest.RENDER_PHASE);
	          
	          portletURL.setParameter("mvcPath", "/guestbook/edit_entry.jsp");
	          portletURL.setParameter("showback", Boolean.FALSE.toString());

	      } 
	      catch (PortalException e) {
	          logger.log(Level.SEVERE, e.getMessage()); 
	      }

	      return portletURL;
	  }

	  @Override
	  public boolean isLinkable() {
	      return _LINKABLE;
	  }

	  @Override
	  public String getIconCssClass() {
	    return "bookmarks";
	  }

	  @Reference(target = "(osgi.web.symbolicname=guestbook.web)", unbind = "-")
	  public void setServletContext(ServletContext servletContext) {
	      _servletContext = servletContext;
	  }

	  @Reference(unbind = "-")
	  protected void setGuestbookLocalService(GuestbookLocalService guestbookLocalService) {
	      _guestbookLocalService = guestbookLocalService; 
	  }

	  private ServletContext _servletContext;
	  private GuestbookLocalService _guestbookLocalService;
	  private Logger logger = Logger.getLogger(this.getClass().getName());
	  private ModelResourcePermission<Guestbook> _guestbookModelResourcePermission;
	  
	  private static final boolean _LINKABLE = true;
	  
	  public static final String TYPE = "guestbook";
	  public static final String CLASS_NAME = Guestbook.class.getName();
}
