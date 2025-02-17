package mn.khosbilegt.service.page;

import io.vertx.core.json.JsonObject;
import mn.khosbilegt.jooq.generated.tables.records.PfPageRecord;
import org.jooq.JSONB;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class Page {
    private int id;
    private String key;
    private String name;
    private String title;
    private String subtitle;
    private String thumbnail;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
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

    public void removeContent(int index) {
        this.contents.remove(index);
    }

    public PfPageRecord toNewRecord() {
        PfPageRecord record = new PfPageRecord();
        record.setPageName(name);
        record.setPageKey(key);
        record.setPageTitle(title);
        record.setPageSubtitle(subtitle);
        record.setCreateDate(OffsetDateTime.from(LocalDateTime.now()));
        record.setLastModifiedDate(OffsetDateTime.from(LocalDateTime.now()));
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
        record.setLastModifiedDate(OffsetDateTime.from(LocalDateTime.now()));
        record.setPageContents(JSONB.valueOf(new JsonObject().encode()));
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
