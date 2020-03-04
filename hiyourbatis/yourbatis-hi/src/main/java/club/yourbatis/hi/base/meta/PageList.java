package club.yourbatis.hi.base.meta;

import lombok.Data;
import org.apache.ibatis.cursor.Cursor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Data
public class PageList<T> implements Cursor<T> {
    private int count;
    List<T> list;

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public boolean isConsumed() {
        return false;
    }

    @Override
    public int getCurrentIndex() {
        return 0;
    }

    @Override
    public void close() throws IOException {
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
