package mn.khosbilegt.service.blog.content;

public class ImageContent extends BlogContent {
    private String url;

    public ImageContent() {
        this.type = ContentType.IMAGE;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
