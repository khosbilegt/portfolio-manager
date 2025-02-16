package mn.khosbilegt.service.blog.content;

public class IFrameContent extends BlogContent {
    private String url;

    public IFrameContent() {
        this.type = ContentType.IFRAME;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
