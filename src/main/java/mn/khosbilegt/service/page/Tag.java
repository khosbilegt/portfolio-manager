package mn.khosbilegt.service.page;

import mn.khosbilegt.jooq.generated.tables.records.PfTagRecord;

public class Tag {
    private int id;
    private String name;
    private String type;
    private String color;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void update(PfTagRecord record) {
        this.id = record.getTagId();
        this.name = record.getTagName();
        this.type = record.getTagType();
        this.color = record.getTagColor();
    }

    public PfTagRecord toNewRecord() {
        PfTagRecord record = new PfTagRecord();
        record.setTagName(name);
        record.setTagType(type);
        record.setTagColor(color);
        return record;
    }

    public PfTagRecord toUpdateRecord() {
        PfTagRecord record = new PfTagRecord();
        record.setTagId(id);
        record.setTagName(name);
        record.setTagType(type);
        record.setTagColor(color);
        return record;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
