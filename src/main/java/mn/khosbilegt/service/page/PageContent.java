package mn.khosbilegt.service.page;

import io.vertx.core.json.JsonObject;

public class PageContent {
    public enum ContentType {
        TEXT, IMAGE, IFRAME, CODE
    }
    private String id;
    private ContentType contentType;
    private JsonObject definition;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public JsonObject getDefinition() {
        return definition;
    }

    public void setDefinition(JsonObject definition) {
        this.definition = definition;
    }
}
