package mn.khosbilegt.service.page;

import mn.khosbilegt.jooq.generated.tables.records.PfPageRecord;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class Page {
    private int id;
    private String key;
    private String name;
    private String title;
    private String subtitle;
    private String thumbnail;
    private String contents;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    private List<Tag> tags = new ArrayList<>();

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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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
        record.setPageContents(contents);
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
        record.setPageContents(contents);
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
        this.contents = record.getPageContents();
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
