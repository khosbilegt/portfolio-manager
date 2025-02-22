package mn.khosbilegt.service.page;

import io.vertx.core.json.JsonObject;
import mn.khosbilegt.jooq.generated.tables.records.PfBlockRecord;
import org.jooq.JSONB;

public class Block {
    private int id;
    private String name;
    private JsonObject definition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JsonObject getDefinition() {
        return definition;
    }

    public void setDefinition(JsonObject definition) {
        this.definition = definition;
    }

    public void update(PfBlockRecord record) {
        this.id = record.getBlockId();
        this.name = record.getBlockName();
        this.definition = new JsonObject(record.getDefinition().data());
    }

    public PfBlockRecord toNewRecord() {
        PfBlockRecord record = new PfBlockRecord();
        record.setBlockName(name);
        record.setDefinition(JSONB.valueOf(definition.encode()));
        return record;
    }

    public PfBlockRecord toUpdateRecord() {
        PfBlockRecord record = new PfBlockRecord();
        record.setBlockId(id);
        record.setBlockName(name);
        record.setDefinition(JSONB.valueOf(definition.encode()));
        return record;
    }

    @Override
    public String toString() {
        return "Block{" +
                "id=" + id +
                ", blockName='" + name + '\'' +
                ", blockDefinition=" + definition +
                '}';
    }
}
