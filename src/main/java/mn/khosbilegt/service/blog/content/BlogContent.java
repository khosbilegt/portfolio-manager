package mn.khosbilegt.service.blog.content;

public abstract class BlogContent {
    public enum ContentType {
        TEXT, IMAGE, IFRAME
    }
    protected ContentType type;

    public ContentType getType() {
        return type;
    }

    public void setType(ContentType type) {
        this.type = type;
    }
}
