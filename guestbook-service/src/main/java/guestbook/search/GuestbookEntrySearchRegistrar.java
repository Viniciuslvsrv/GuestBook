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

import guestbook.model.GuestbookEntry;

@Component(immediate = true)
public class GuestbookEntrySearchRegistrar {
	
    @Activate
    protected void activate(BundleContext bundleContext) {

        _serviceRegistration = modelSearchRegistrarHelper.register(
            GuestbookEntry.class, bundleContext, modelSearchDefinition -> {
                modelSearchDefinition.setDefaultSelectedFieldNames(
                    Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
                    Field.ENTRY_CLASS_PK, Field.UID, 
                    Field.SCOPE_GROUP_ID, Field.GROUP_ID);

                modelSearchDefinition.setDefaultSelectedLocalizedFieldNames(
                    Field.TITLE, Field.CONTENT);

                modelSearchDefinition.setModelIndexWriteContributor(
                    modelIndexWriterContributor);
                
                modelSearchDefinition.setModelSummaryContributor(
                    modelSummaryContributor);
                
                modelSearchDefinition.setSelectAllLocales(true);
            }
        );
    }

    @Deactivate
    protected void deactivate() {
        _serviceRegistration.unregister();
    }
    
    @Reference(target = "(indexer.class.name=guestbook.model.GuestbookEntry)")
    protected ModelIndexerWriterContributor<GuestbookEntry> modelIndexWriterContributor;
    @Reference(target = "(indexer.class.name=guestbook.model.GuestbookEntry)")
    protected ModelSummaryContributor modelSummaryContributor;
    
    @Reference
    protected ModelSearchRegistrarHelper modelSearchRegistrarHelper;

    private ServiceRegistration<?> _serviceRegistration;
}
