package datawerks.common.storage.parsers;

import java.io.IOException;
import java.util.List;

public interface FileParser<ParsableObject> {

	public List<ParsableObject> parse(String url) throws IOException;
}
