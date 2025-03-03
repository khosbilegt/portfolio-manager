package mn.khosbilegt.service.page;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import mn.khosbilegt.jooq.generated.tables.records.PfPageRecord;
import org.jboss.logging.Logger;
import org.jooq.JSONB;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Page {
    private final Logger LOG = Logger.getLogger("PortfolioManager");
    private int id;
    private String key;
    private String name;
    private String title;
    private String subtitle;
    private String thumbnail;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    private List<Tag> tags = new ArrayList<>();
    private List<PageContent> contents = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public List<PageContent> getContents() {
        return contents;
    }

    public void setContents(List<PageContent> contents) {
        this.contents = contents;
    }

    public void addContent(PageContent content) {
        this.contents.add(content);
    }

    public void updateContent(int index, PageContent content) {
        this.contents.set(index, content);
    }

    public void removeContent(int index) {
        this.contents.remove(index);
    }

    public void swapContentIndexes(int index1, int index2) {
        PageContent content1 = contents.get(index1);
        PageContent content2 = contents.get(index2);
        contents.set(index1, content2);
        contents.set(index2, content1);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeTag(int id) {
        for (int i = 0; i < tags.size(); i++) {
            if (tags.get(i).getId() == id) {
                tags.remove(i);
                break;
            }
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public PfPageRecord toNewRecord() {
        PfPageRecord record = new PfPageRecord();
        record.setPageName(name);
        record.setPageKey(key);
        record.setPageTitle(title);
        record.setPageSubtitle(subtitle);
        record.setCreateDate(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toOffsetDateTime());
        record.setLastModifiedDate(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toOffsetDateTime());
        record.setPageContents(JSONB.valueOf(new JsonObject().encode()));
        return record;
    }

    public PfPageRecord toUpdateRecord() {
        PfPageRecord record = new PfPageRecord();
        record.setPageId(id);
        record.setPageName(name);
        record.setPageKey(key);
        record.setPageTitle(title);
        record.setPageSubtitle(subtitle);
        record.setPageThumbnail(thumbnail);
        record.setLastModifiedDate(LocalDateTime.now().atZone(ZoneOffset.systemDefault()).toOffsetDateTime());
        JsonArray contentsArray = new JsonArray();
        for (PageContent content : contents) {
            contentsArray.add(content.toJsonObject());
        }
        record.setPageContents(JSONB.valueOf(contentsArray.encode()));
        return record;
    }

    public void update(PfPageRecord record) {
        this.id = record.getPageId();
        this.name = record.getPageName();
        this.key = record.getPageKey();
        this.title = record.getPageTitle();
        this.subtitle = record.getPageSubtitle();
        this.thumbnail = record.getPageThumbnail();
        this.createDate = record.getCreateDate().toLocalDateTime();
        this.lastModifiedDate = record.getLastModifiedDate().toLocalDateTime();
        if (record.getPageContents() != null) {
            try {
                JsonArray jsonArray = new JsonArray(record.getPageContents().data());
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject jsonObject = jsonArray.getJsonObject(i);
                    PageContent content = new PageContent();
                    content.setContentType(PageContent.ContentType.valueOf(jsonObject.getString("contentType")));
                    content.setDefinition(jsonObject.getJsonObject("definition"));
                    this.contents.add(content);
                }
            } catch (Exception e) {
                LOG.error("Failed to parse page contents", e);
            }
        }
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", createDate=" + createDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", contents=" + contents +
                '}';
    }
}
