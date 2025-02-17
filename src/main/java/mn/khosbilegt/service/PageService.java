package mn.khosbilegt.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mn.khosbilegt.jooq.generated.tables.records.PfPageRecord;
import mn.khosbilegt.service.page.Block;
import mn.khosbilegt.service.page.Page;
import mn.khosbilegt.service.page.Tag;
import org.jooq.DSLContext;

import java.util.HashMap;
import java.util.Map;

import static mn.khosbilegt.jooq.generated.Tables.*;

@ApplicationScoped
public class PageService {
    @Inject
    DSLContext context;
    private Map<Integer, Page> PAGES = new HashMap<>();
    private Map<Integer, Tag> TAGS = new HashMap<>();
    private Map<Integer, Block> BLOCKS = new HashMap<>();

    public void createPage(Page page) {
        PfPageRecord pageRecord = context.insertInto(PF_PAGE)
                .set(page.toNewRecord())
                .returning()
                .fetchOne();
        if (pageRecord != null) {
            page.update(pageRecord);
            PAGES.put(page.getId(), page);
        } else {
            throw new RuntimeException("Failed to create page");
        }
    }

    public void updatePage(Page page) {
        PfPageRecord pageRecord = context.update(PF_PAGE)
                .set(page.toUpdateRecord())
                .where(PF_PAGE.PAGE_ID.eq(page.getId()))
                .returning()
                .fetchOne();
        if (pageRecord != null) {
            page.update(pageRecord);
            PAGES.put(page.getId(), page);
        } else {
            throw new RuntimeException("Failed to update page");
        }
    }

    public void deletePage(int id) {
        context.deleteFrom(PF_PAGE)
                .where(PF_PAGE.PAGE_ID.eq(id))
                .execute();
        PAGES.remove(id);
    }
}
