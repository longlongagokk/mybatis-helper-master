package club.yourbatis.hi.base.meta;

import club.yourbatis.hi.base.Field;
import lombok.Getter;

import java.util.Map;

@Getter
public class TableMetaInfo {
    private String tableName;
    /**
     * the primary info
     */
    private Field primary;
    /**
     * total columns
     */
    private String[]columns;
    private Map<String, String> fieldWithColumns;
    private Map<String, Class<?>> fieldWithTypes;
    public TableMetaInfo(String tableName, Field primary, String[] columns
            , Map<String, String> fieldWithColumns
            , Map<String, Class<?>> fieldWithTypes
    ){
        this.tableName = tableName;
        this.primary = primary;
        this.columns = columns;
        this.fieldWithColumns = fieldWithColumns;
        this.fieldWithTypes = fieldWithTypes;
    }
}
