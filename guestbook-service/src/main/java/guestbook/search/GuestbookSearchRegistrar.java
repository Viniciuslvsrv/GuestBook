package guestbook.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.spi.model.index.contributor.ModelIndexerWriterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchRegistrarHelper;
import com.liferay.portal.search.spi.model.result.contributor.ModelSummaryContributor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import guestbook.model.Guestbook;

@Component(immediate = true)
public class GuestbookSearchRegistrar {
	
    @Activate
    protected void activate(BundleContext bundleContext) {

        _serviceRegistration = modelSearchRegistrarHelper.register(
            Guestbook.class, bundleContext, modelSearchDefinition -> {
                modelSearchDefinition.setDefaultSelectedFieldNames(
                    Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.CONTENT,
                    Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK,
                    Field.GROUP_ID, Field.MODIFIED_DATE, 
                    Field.SCOPE_GROUP_ID, Field.TITLE, 
                    Field.UID);

                modelSearchDefinition.setModelIndexWriteContributor(
                    modelIndexWriterContributor);
                
                modelSearchDefinition.setModelSummaryContributor(
                    modelSummaryContributor);
            }
        );
    }

    @Deactivate
    protected void deactivate() {
        _serviceRegistration.unregister();
    }
    
    @Reference(target = "(indexer.class.name=guestbook.model.Guestbook)")
    protected ModelIndexerWriterContributor<Guestbook> modelIndexWriterContributor;
    @Reference(target = "(indexer.class.name=guestbook.model.Guestbook)")
    protected ModelSummaryContributor modelSummaryContributor;
    
    @Reference
    protected ModelSearchRegistrarHelper modelSearchRegistrarHelper;

    private ServiceRegistration<?> _serviceRegistration;
}
