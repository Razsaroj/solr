package apollo;

import java.util.ArrayList;
import java.util.List;

public class WindowEntry {
	  String term;
	  List positions = new ArrayList();//a term could appear more than once w/in a position

	  WindowEntry(String term) {
	    this.term = term;
	  }

	  @Override
	  public String toString() {
	    return "WindowEntry{" +
	            "term='" + term + "'" +
	            ", positions=" + positions +
	            '}';
	  }
	}
