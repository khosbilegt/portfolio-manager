package mn.khosbilegt.service.blog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Blog {
    private String id;
    private String title;
    private String subTitle;
    private String type;
    private String thumbnail;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
    private List<BlogContent> contents = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<BlogContent> getContents() {
        return contents;
    }

    public void setContents(List<BlogContent> contents) {
        this.contents = contents;
    }

    public void addContent(BlogContent content) {
        this.contents.add(content);
    }

    public void removeContent(int index) {
        this.contents.remove(index);
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", type='" + type + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", createDate=" + createDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", contents=" + contents +
                '}';
    }
}
