package mn.khosbilegt.service.page;

import mn.khosbilegt.service.page.content.CodeLanguage;

public class PageContent {
    public enum ContentType {
        TEXT, IMAGE, IFRAME, CODE
    }
    private String id;
    private ContentType contentType;
    private String content;
    // If ContentType is CODE
    private CodeLanguage codeLanguage;
}
