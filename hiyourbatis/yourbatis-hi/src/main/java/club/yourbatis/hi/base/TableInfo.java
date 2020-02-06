package club.yourbatis.hi.base;

public interface TableInfo {
    String getAlias();
    String getTableName();
    String getTableNameWithAlias();
    Class<?> getTableClass();
}
