package datawerks.common.storage.parsers.processors;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NameCellProcessorTests {

	private NameCellProcessor processor = new NameCellProcessor();
	
	@Test
	public void shouldChangeNameToLower() throws Exception {
		String lower = processor.execute("NiCHOlas", null);
		assertEquals(lower, "nicholas");
	}
}
