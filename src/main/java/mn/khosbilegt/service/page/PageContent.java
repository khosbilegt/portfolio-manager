package mn.khosbilegt.service.page;

import io.vertx.core.json.JsonObject;

public class PageContent {
    public enum ContentType {
        TEXT, IMAGE, IFRAME, CODE
    }
    private ContentType contentType;
    private JsonObject definition;

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

    public JsonObject toJsonObject() {
        return new JsonObject()
                .put("contentType", contentType.name())
                .put("definition", definition);
    }
}
