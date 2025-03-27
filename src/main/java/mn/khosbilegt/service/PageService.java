package mn.khosbilegt.service;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import mn.khosbilegt.jooq.generated.tables.records.PfBlockRecord;
import mn.khosbilegt.jooq.generated.tables.records.PfPageRecord;
import mn.khosbilegt.jooq.generated.tables.records.PfTagRecord;
import mn.khosbilegt.service.page.Block;
import mn.khosbilegt.service.page.Page;
import mn.khosbilegt.service.page.PageCreateDateComparator;
import mn.khosbilegt.service.page.Tag;
import org.jboss.logging.Logger;
import org.jooq.DSLContext;
import org.jooq.Record;

import java.util.*;

import static mn.khosbilegt.jooq.generated.Tables.*;

@ApplicationScoped
public class PageService {
    private final Logger LOG = Logger.getLogger("PortfolioManager");
    private final Map<Integer, Tag> TAGS = new HashMap<>();
    private final Map<Integer, Block> BLOCKS = new HashMap<>();
    @Inject
    DSLContext context;

    public void init(@Observes StartupEvent ignored) {
        cacheTags();
        cacheBlocks();
        LOG.infov("Completed initializing [PageService]");
    }

    public Collection<Page> fetchPages(String includedTags) {
        Map<Integer, Page> pages = new HashMap<>();
        if (includedTags.isEmpty()) {
            context.selectFrom(PF_PAGE)
                    .fetch()
                    .forEach(pageRecord -> {
                        Page page = new Page();
                        page.update(pageRecord);
                        pages.put(page.getId(), page);
                    });
            context.selectFrom(PF_PAGE_TAG)
                    .fetch()
                    .forEach(pageTagRecord -> {
                        if (pages.containsKey(pageTagRecord.getPageId())) {
                            Page page = pages.get(pageTagRecord.getPageId());
                            if (TAGS.containsKey(pageTagRecord.getTagId())) {
                                Tag tag = TAGS.get(pageTagRecord.getTagId());
                                page.addTag(tag);
                            }
                        }
                    });
        } else {
            String[] tagList = includedTags.split(",");
            context.select().from(PF_PAGE)
                    .leftJoin(PF_PAGE_TAG).on(PF_PAGE.PAGE_ID.eq(PF_PAGE_TAG.PAGE_ID))
                    .leftJoin(PF_TAG).on(PF_PAGE_TAG.TAG_ID.eq(PF_TAG.TAG_ID))
                    .where(PF_TAG.TAG_NAME.in(tagList))
                    .fetch()
                    .forEach(record -> {
                        if (!pages.containsKey(record.into(PF_PAGE).getPageId())) {
                            Page page = new Page();
                            page.update(record.into(PF_PAGE));
                            pages.put(page.getId(), page);
                        } else {
                            Page page = pages.get(record.into(PF_PAGE).getPageId());
                            Tag tag = new Tag();
                            tag.update(record.into(PF_TAG));
                            page.addTag(tag);
                        }
                    });
        }
        // Order by create_date
        PriorityQueue<Page> orderedPages = new PriorityQueue<>(new PageCreateDateComparator());
        orderedPages.addAll(pages.values());
        return orderedPages;

    }

    public Page fetchPage(int id) {
        PfPageRecord pageRecord = context.selectFrom(PF_PAGE)
                .where(PF_PAGE.PAGE_ID.eq(id))
                .orderBy(PF_PAGE.CREATE_DATE.desc())
                .fetchOne();
        if (pageRecord != null) {
            Page page = new Page();
            page.update(pageRecord);
            context.selectFrom(PF_PAGE_TAG)
                    .where(PF_PAGE_TAG.PAGE_ID.eq(id))
                    .orderBy(PF_PAGE.CREATE_DATE.desc())
                    .fetch()
                    .forEach(pageTagRecord -> {
                        if (TAGS.containsKey(pageTagRecord.getTagId())) {
                            Tag tag = TAGS.get(pageTagRecord.getTagId());
                            page.addTag(tag);
                        }
                    });
            return page;
        }
        throw new NotFoundException("Page not found");
    }

    public Page fetchPageByKey(String key) {
        PfPageRecord pageRecord = context.selectFrom(PF_PAGE)
                .where(PF_PAGE.PAGE_KEY.eq(key))
                .fetchOne();
        if (pageRecord != null) {
            Page page = new Page();
            page.update(pageRecord);
            context.selectFrom(PF_PAGE_TAG)
                    .where(PF_PAGE_TAG.PAGE_ID.eq(page.getId()))
                    .fetch()
                    .forEach(pageTagRecord -> {
                        if (TAGS.containsKey(pageTagRecord.getTagId())) {
                            Tag tag = TAGS.get(pageTagRecord.getTagId());
                            page.addTag(tag);
                        }
                    });
            return page;
        }
        throw new NotFoundException("Page not found");
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
            LOG.infov("Created page: {0}", page.getId());
            return page;
        } else {
            throw new RuntimeException("Failed to create page");
        }
    }

    public Page updatePage(int id, Page page) {
        page.setId(id);
        PfPageRecord pageRecord = context.update(PF_PAGE)
                .set(page.toUpdateRecord())
                .where(PF_PAGE.PAGE_ID.eq(id))
                .returning()
                .fetchOne();
        if (pageRecord != null) {
            page.update(pageRecord);
            LOG.infov("Updated page: {0}", page.getId());
            return page;
        } else {
            throw new RuntimeException("Failed to update page");
        }
    }

    public Tag addTagToPage(int pageId, int tagId) {
            if (TAGS.containsKey(tagId)) {
                Tag tag = TAGS.get(tagId);
                context.insertInto(PF_PAGE_TAG)
                        .set(PF_PAGE_TAG.PAGE_ID, pageId)
                        .set(PF_PAGE_TAG.TAG_ID, tagId)
                        .execute();
                return tag;
            } else {
                throw new NotFoundException("Tag not found");
            }
    }

    public Tag removeTagFromPage(int pageId, int tagId) {
            if (TAGS.containsKey(tagId)) {
                Tag tag = TAGS.get(tagId);
                context.deleteFrom(PF_PAGE_TAG)
                        .where(PF_PAGE_TAG.PAGE_ID.eq(pageId))
                        .and(PF_PAGE_TAG.TAG_ID.eq(tagId))
                        .execute();
                return tag;
            } else {
                throw new NotFoundException("Tag not found");
            }
    }

    public Page setPageContents(int id, String contents) {
        PfPageRecord pageRecord = context.update(PF_PAGE)
                .set(PF_PAGE.PAGE_CONTENTS, contents)
                .where(PF_PAGE.PAGE_ID.eq(id))
                .returning()
                .fetchOne();
        if (pageRecord != null) {
            Page page = new Page();
            page.update(pageRecord);
            return page;
        } else {
            throw new RuntimeException("Failed to update page contents");
        }
    }

    public void deletePage(int id) {
        context.deleteFrom(PF_PAGE)
                .where(PF_PAGE.PAGE_ID.eq(id))
                .execute();
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

    public Collection<Tag> fetchTags(String name, String type) {
        List<Tag> queriedTags = new ArrayList<>();
        if (name.isEmpty()) {
            queriedTags.addAll(TAGS.values());
        } else {
            for (Tag tag : TAGS.values()) {
                if (tag.getName().contains(name)) {
                    queriedTags.add(tag);
                }
            }
        }
        if (!type.isEmpty() && !queriedTags.isEmpty()) {
            queriedTags.removeIf(tag -> !tag.getType().equals(type));
        }
        return queriedTags;
    }

    public Tag fetchTag(int id) {
        if (!TAGS.containsKey(id)) {
            throw new NotFoundException("Tag not found");
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
        if (!TAGS.containsKey(id)) {
            throw new NotFoundException("Tag not found");
        }
        tag.setId(id);
        PfTagRecord tagRecord = context.update(PF_TAG)
                .set(tag.toUpdateRecord())
                .where(PF_TAG.TAG_ID.eq(id))
                .returning()
                .fetchOne();
        if (tagRecord != null) {
            tag.update(tagRecord);
            TAGS.put(id, tag);
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
            throw new NotFoundException("Block not found");
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
        if (!BLOCKS.containsKey(id)) {
            throw new NotFoundException("Block not found");
        }
        block.setId(id);
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
