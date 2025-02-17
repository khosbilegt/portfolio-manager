package mn.khosbilegt.service.page.content;

import java.util.HashSet;
import java.util.Set;

public class FontStyle {
    public enum TextDecoration {
        ITALIC, UNDERLINE
    }
    private int fontSize;
    private int fontWeight;
    private int opacity;
    private String color;
    private Set<TextDecoration> decorations = new HashSet<>();
}
