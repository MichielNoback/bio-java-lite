/**
 * 
 */
package net.cellingo.sequence_tools.io;

import java.util.Iterator;
import java.util.List;

import net.cellingo.sequence_tools.annotation.SequenceObject;
import net.cellingo.sequence_tools.annotation.SequenceObjectType;

/**
 * This interface specifies the methods an internet sequence
 * fetcher should implement 
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public interface SequenceFetcher {
	/**
	 * adds this list to the list of sequence accession numbers that should be fetched
	 * @param accessions
	 */
	public void addAccessions( List<String> accessions );
	/**
	 * sets the format in which to download the sequence.
	 * Not all implementations may support all formats! In that case,
	 * a UnsupportedFormatException will be thrown
	 * @param sequenceFormat
	 * @throws UnsupportedFormatException
	 */
	public void setFormat( SequenceObjectType sequenceFormat ) throws UnsupportedFormatException;
	/**
	 * returns the supported sequence formats
	 * @return iterator of supported sequence formats
	 */
	public Iterator<SequenceObjectType> getSupportedSequenceFormats();
	/**
	 * returns whether the format is supported for this sequence fetcher
	 * @param format
	 * @return format is supported
	 */
	public boolean isSupportedSequenceFormat( SequenceObjectType format );
	/**
	 * sets the database to fetch sequence form with this object. Not all databases are 
	 * supported for each host. In that case, an UnsupportedDatabaseException will be thrown
	 * @param db
	 * @throws UnsupportedDatabaseException
	 */
	public void setDatabase( SequenceDatabase db) throws UnsupportedDatabaseException;
	/**
	 * returns the supported sequence databases
	 * @return iterator of supported sequence databases
	 */
	public Iterator<SequenceDatabase> getSupportedSequenceDatabases();
	/**
	 * returns whether the database is supported for this fetcher implementation
	 * @param db
	 * @return database is supported
	 */
	public boolean isSupportedSequenceDatabase( SequenceDatabase db );
	/**
	 * specifies the listener to report back to. When a listener has been set on 
	 * the sequence fetcher object, it should process fetched sequences streaming.
	 * @param listener
	 */
	public void setListener( SequenceFetcherListener listener );
	/**
	 * returns a list of fetched sequences, but ONLY if no listener was set.
	 * If a listener was set, the sequences were processed in a streaming fashion and
	 * the list will be empty.	
	 * @return list of sequence objects
	 */
	public List<SequenceObject> getSequences();
	/**
	 * the actual fetch function
	 * @throws Exception
	 */
	public void fetch() throws Exception;
	
}
