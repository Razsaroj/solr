package apollo;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.lucene.index.TermVectorOffsetInfo;

import java.util.ArrayList;

public class WindowTermVectorMapper {

	int start;
	  int end;
	  LinkedHashMap entries = new LinkedHashMap();

	  public void map(String term, int frequency, TermVectorOffsetInfo[] offsets, int[] positions) {
	    for (int i = 0; i < positions.length; i++) {//unfortunately, we still have to loop over the positions //we'll make this inclusive of the boundaries if (positions[i] >= start && positions[i] < end){
	        WindowEntry entry = (WindowEntry) entries.get(term);
	        if (entry == null) {
	          entry = new WindowEntry(term);
	          entries.put(term, entry);
	        }
	        entry.positions.add(positions[i]);
	      }
	    }
	  

	  public void setExpectations(String field, int numTerms, boolean storeOffsets, boolean storePositions) {
	    // do nothing for this example
	    //See also the PositionBasedTermVectorMapper.
	  }

	}
