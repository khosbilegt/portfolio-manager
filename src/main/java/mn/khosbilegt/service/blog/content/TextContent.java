package mn.khosbilegt.service.blog.content;

public class TextContent extends BlogContent {
    private String text;
    private String fontSize;
    private int fontWeight;
    private boolean isItalicized;

    public TextContent() {
        this.type = ContentType.TEXT;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public int getFontWeight() {
        return fontWeight;
    }

    public void setFontWeight(int fontWeight) {
        this.fontWeight = fontWeight;
    }

    public boolean isItalicized() {
        return isItalicized;
    }

    public void setItalicized(boolean italicized) {
        isItalicized = italicized;
    }

    @Override
    public String toString() {
        return "TextContent{" +
                "type=" + type +
                ", isItalicized=" + isItalicized +
                ", fontWeight=" + fontWeight +
                ", fontSize='" + fontSize + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
