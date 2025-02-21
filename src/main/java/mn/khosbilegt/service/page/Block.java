package mn.khosbilegt.service.page;

import io.vertx.core.json.JsonObject;
import mn.khosbilegt.jooq.generated.tables.records.PfBlockRecord;
import org.jooq.JSONB;

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

    public void update(PfBlockRecord record) {
        this.id = record.getBlockId();
        this.blockName = record.getBlockName();
        this.blockDefinition = new JsonObject(record.getDefinition().data());
    }

    public PfBlockRecord toNewRecord() {
        PfBlockRecord record = new PfBlockRecord();
        record.setBlockName(blockName);
        record.setDefinition(JSONB.valueOf(blockDefinition.encode()));
        return record;
    }

    public PfBlockRecord toUpdateRecord() {
        PfBlockRecord record = new PfBlockRecord();
        record.setBlockId(id);
        record.setBlockName(blockName);
        record.setDefinition(JSONB.valueOf(blockDefinition.encode()));
        return record;
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
