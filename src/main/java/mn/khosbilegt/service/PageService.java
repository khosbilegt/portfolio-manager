package mn.khosbilegt.service;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import mn.khosbilegt.jooq.generated.tables.records.PfBlockRecord;
import mn.khosbilegt.jooq.generated.tables.records.PfPageRecord;
import mn.khosbilegt.jooq.generated.tables.records.PfTagRecord;
import mn.khosbilegt.service.page.Block;
import mn.khosbilegt.service.page.Page;
import mn.khosbilegt.service.page.Tag;
import org.jboss.logging.Logger;
import org.jooq.DSLContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static mn.khosbilegt.jooq.generated.Tables.*;

@ApplicationScoped
public class PageService {
    private final Logger LOG = Logger.getLogger("PortfolioManager");
    private final Map<Integer, Page> PAGES = new HashMap<>();
    private final Map<Integer, Tag> TAGS = new HashMap<>();
    private final Map<Integer, Block> BLOCKS = new HashMap<>();
    @Inject
    DSLContext context;

    public void init(@Observes StartupEvent ignored) {
        cachePages();
        cacheTags();
        cacheBlocks();
        LOG.infov("Completed initializing [PageService]");
    }

    public void cachePages() {
        context.selectFrom(PF_PAGE)
                .fetch()
                .forEach(pageRecord -> {
                    Page page = new Page();
                    page.update(pageRecord);
                    PAGES.put(page.getId(), page);
                });
        LOG.infov("Completed caching [Pages]: {0}", PAGES.size());
    }

    public Collection<Page> fetchPages() {
        return PAGES.values();
    }

    public Page fetchPage(int id) {
        if (!PAGES.containsKey(id)) {
            return null;
        }
        return PAGES.get(id);
    }

    public Collection<Page> fetchPagesByTag(int tagId) {
        return context.selectFrom(PF_PAGE)
                .where(PF_PAGE.PAGE_ID.in(
                        context.select(PF_PAGE_TAG.PAGE_ID)
                                .from(PF_PAGE_TAG)
                                .where(PF_PAGE_TAG.TAG_ID.eq(tagId))
                ))
                .fetch()
                .map(pageRecord -> {
                    Page page = new Page();
                    page.update(pageRecord);
                    return page;
                });
    }

    public Page createPage(Page page) {
        PfPageRecord pageRecord = context.insertInto(PF_PAGE)
                .set(page.toNewRecord())
                .returning()
                .fetchOne();
        if (pageRecord != null) {
            page.update(pageRecord);
            PAGES.put(page.getId(), page);
            LOG.infov("Created page: {0}", page.getId());
            return page;
        } else {
            throw new RuntimeException("Failed to create page");
        }
    }

    public Page updatePage(int id, Page page) {
        PfPageRecord pageRecord = context.update(PF_PAGE)
                .set(page.toUpdateRecord())
                .where(PF_PAGE.PAGE_ID.eq(id))
                .returning()
                .fetchOne();
        if (pageRecord != null) {
            page.update(pageRecord);
            PAGES.put(page.getId(), page);
            LOG.infov("Updated page: {0}", page.getId());
            return page;
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

    public void cacheTags() {
        context.selectFrom(PF_TAG)
                .fetch()
                .forEach(tagRecord -> {
                    Tag tag = new Tag();
                    tag.update(tagRecord);
                    TAGS.put(tag.getId(), tag);
                });
        LOG.infov("Completed caching [Tags]: {0}", TAGS.size());
    }

    public Collection<Tag> fetchTags() {
        return TAGS.values();
    }

    public Tag fetchTag(int id) {
        if (!TAGS.containsKey(id)) {
            return null;
        }
        return TAGS.get(id);
    }

    public Tag createTag(Tag tag) {
        PfTagRecord tagRecord = context.insertInto(PF_TAG)
                .set(tag.toNewRecord())
                .returning()
                .fetchOne();
        if (tagRecord != null) {
            tag.update(tagRecord);
            TAGS.put(tag.getId(), tag);
            LOG.infov("Created tag: {0}", tag.getId());
            return tag;
        } else {
            throw new RuntimeException("Failed to create tag");
        }
    }

    public Tag updateTag(int id, Tag tag) {
        PfTagRecord tagRecord = context.update(PF_TAG)
                .set(tag.toUpdateRecord())
                .where(PF_TAG.TAG_ID.eq(id))
                .returning()
                .fetchOne();
        if (tagRecord != null) {
            tag.update(tagRecord);
            TAGS.put(tag.getId(), tag);
            return tag;
        } else {
            throw new RuntimeException("Failed to update tag");
        }
    }

    public void deleteTag(int id) {
        context.deleteFrom(PF_TAG)
                .where(PF_TAG.TAG_ID.eq(id))
                .execute();
        TAGS.remove(id);
    }

    public void cacheBlocks() {
        context.selectFrom(PF_BLOCK)
                .fetch()
                .forEach(blockRecord -> {
                    Block block = new Block();
                    block.update(blockRecord);
                    BLOCKS.put(block.getId(), block);
                });
        LOG.infov("Completed caching [Blocks]: {0}", BLOCKS.size());
    }

    public Collection<Block> fetchBlocks() {
        return BLOCKS.values();
    }

    public Block fetchBlock(int id) {
        if (!BLOCKS.containsKey(id)) {
            return null;
        }
        return BLOCKS.get(id);
    }

    public Block createBlock(Block block) {
        PfBlockRecord blockRecord = context.insertInto(PF_BLOCK)
                .set(block.toNewRecord())
                .returning()
                .fetchOne();
        if (blockRecord != null) {
            block.update(blockRecord);
            BLOCKS.put(block.getId(), block);
            return block;
        } else {
            throw new RuntimeException("Failed to create block");
        }
    }

    public Block updateBlock(int id, Block block) {
        PfBlockRecord blockRecord = context.update(PF_BLOCK)
                .set(block.toUpdateRecord())
                .where(PF_BLOCK.BLOCK_ID.eq(id))
                .returning()
                .fetchOne();
        if (blockRecord != null) {
            block.update(blockRecord);
            BLOCKS.put(block.getId(), block);
            return block;
        } else {
            throw new RuntimeException("Failed to update block");
        }
    }

    public void deleteBlock(int id) {
        context.deleteFrom(PF_BLOCK)
                .where(PF_BLOCK.BLOCK_ID.eq(id))
                .execute();
        BLOCKS.remove(id);
    }
}
