package apollo;

import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.TermVectorMapper;
import org.apache.lucene.index.TermVectorOffsetInfo;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.search.spans.Spans;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;


public class TermVectorFun {

	public static String[] DOCS = {
	          "The quick red fox jumped over the lazy brown dogs.",
	          "Mary had a little lamb whose fleece was white as snow.",
	          "Moby Dick is a story of a whale and a man obsessed.",
	          "The robber wore a black fleece jacket and a baseball cap.",
	          "The English Springer Spaniel is the best of all dogs."
	  };
	
	  public static void main(String[] args) throws IOException {
		    RAMDirectory ramDir = new RAMDirectory();
		    //Index some made up content
		    IndexWriter writer = new IndexWriter(ramDir, new StandardAnalyzer(Version.LUCENE_36), true, IndexWriter.MaxFieldLength.UNLIMITED);
		    for (int i = 0; i < DOCS.length; i++) {
		      Document doc = new Document();
		      Field id = new Field("id", "doc_" + i, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS);
		      doc.add(id);
		      //Store both position and offset information
		      Field text = new Field("content", DOCS[i], Field.Store.NO, Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS);
		      doc.add(text);
		      writer.addDocument(doc);
		    }
		    writer.close();
		    
		    IndexSearcher searcher = new IndexSearcher(ramDir);
		    // Do a search using SpanQuery
		    SpanTermQuery fleeceQ = new SpanTermQuery(new Term("content", "fleece"));
		    TopDocs results = searcher.search(fleeceQ, 10);
		    for (int i = 0; i < results.scoreDocs.length; i++) {
		      ScoreDoc scoreDoc = results.scoreDocs[i];
		      System.out.println("Score Doc: " + scoreDoc);
		    }
		    
		    Spans spans = fleeceQ.getSpans(searcher.getIndexReader());
		    while (spans.next() == true){
		      System.out.println("Doc: " + spans.doc() + " Start: " + spans.start() + " End: " + spans.end());
		    }
}
}
