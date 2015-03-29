package app;

import javax.batch.api.chunk.AbstractItemWriter;
import java.util.List;

public class StdOutItemWriter extends AbstractItemWriter {
    @Override
    public void writeItems(List<Object> items) throws Exception {
        System.out.println(items);
    }
}
