package mn.khosbilegt.service.page;

import io.vertx.core.json.JsonObject;

public class Block {
    private int id;
    private String blockName;
    private JsonObject blockDefinition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public JsonObject getBlockDefinition() {
        return blockDefinition;
    }

    public void setBlockDefinition(JsonObject blockDefinition) {
        this.blockDefinition = blockDefinition;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", blockName='" + blockName + '\'' +
                ", blockDefinition=" + blockDefinition +
                '}';
    }
}
