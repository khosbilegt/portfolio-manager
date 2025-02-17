package mn.khosbilegt.service.page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Page {
    private int id;
    private String key;
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
