package mn.khosbilegt.service.blog.content;

public class TextContent extends BlogContent {
    private String text;

    public TextContent() {
        this.type = ContentType.TEXT;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
