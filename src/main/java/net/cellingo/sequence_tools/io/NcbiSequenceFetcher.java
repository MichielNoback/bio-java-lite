/**
 * 
 */
package net.cellingo.sequence_tools.io;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import net.cellingo.sequence_tools.annotation.SequenceObject;
import net.cellingo.sequence_tools.annotation.SequenceObjectType;
import net.cellingo.sequence_tools.sequences.Sequence;
import net.cellingo.sequence_tools.sequences.SequenceCreationException;
import net.cellingo.sequence_tools.sequences.SequenceFactory;

/**
 * This implementation of the SequenceFetcher interface fetches sequences 
 * from the NCBI eUtils site, conforming to their constraints
 * @author Michiel Noback (www.cellingo.net, michiel@cellingo.net)
 * @version 1.0
 */
public class NcbiSequenceFetcher implements SequenceFetcher {
	/*supported databases*/
	private static HashSet<SequenceDatabase> supportedDatabases;
	/*supported sequence formats*/
	private static HashSet<SequenceObjectType> supportedFormats;
	/*the database to fetch from (e.g "nucleotide", "protein")*/
	private SequenceDatabase database;
	/*the sequence format to retrieve*/
	private SequenceObjectType sequenceFormat;
	/*the list of fetched sequences (remains empty when processing streaming)*/
	private ArrayList<SequenceObject> sequences;
	/*the accessions of the sequences to be fetched*/
	private ArrayList<String> accessions;
	/*the listener object; when not null, process streaming*/
	private SequenceFetcherListener listener;
	
	static{
		supportedDatabases = new HashSet<SequenceDatabase>();
		supportedDatabases.add( SequenceDatabase.NCBI_PROTEIN );
		supportedDatabases.add( SequenceDatabase.NCBI_NUCLEOTIDE );
		supportedDatabases.add( SequenceDatabase.NCBI_GENOME );
		
		supportedFormats = new HashSet<SequenceObjectType>();
		supportedFormats.add( SequenceObjectType.SIMPLE_SEQUENCE );
		supportedFormats.add( SequenceObjectType.GENBANK_SEQUENCE );
	}
	
	public NcbiSequenceFetcher(){
		this.database = SequenceDatabase.NCBI_PROTEIN;
		this.sequenceFormat = SequenceObjectType.SIMPLE_SEQUENCE;
		this.sequences = new ArrayList<SequenceObject>(); 
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#getSequences()
	 */
	public List<SequenceObject> getSequences() {
		if(sequences == null) return new ArrayList<SequenceObject>();
		return sequences;
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#getSupportedSequenceDatabases()
	 */
	public Iterator<SequenceDatabase> getSupportedSequenceDatabases() {
		return supportedDatabases.iterator();
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#getSupportedSequenceFormats()
	 */
	public Iterator<SequenceObjectType> getSupportedSequenceFormats() {
		return supportedFormats.iterator();
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#isSupportedSequenceDatabase(net.cellingo.sequence_tools.io.SequenceDatabase)
	 */
	public boolean isSupportedSequenceDatabase(SequenceDatabase db) {
		return supportedDatabases.contains(db);
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#isSupportedSequenceFormat(net.cellingo.sequence_tools.annotation.SequenceObjectType)
	 */
	public boolean isSupportedSequenceFormat(SequenceObjectType format) {
		return supportedFormats.contains(sequenceFormat);
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#setAccessions(java.util.List)
	 */
	public void addAccessions(List<String> accessions) {
		if(this.accessions == null) this.accessions = new ArrayList<String>();
		this.accessions.addAll( accessions );
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#setDatabase(net.cellingo.sequence_tools.io.SequenceDatabase)
	 */
	public void setDatabase(SequenceDatabase db) throws UnsupportedDatabaseException {
		if(supportedDatabases.contains(db)) this.database = db;
		else throw new UnsupportedDatabaseException("this sequence fetcher does not support database: " + db);
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#setFormat(net.cellingo.sequence_tools.annotation.SequenceObjectType)
	 */
	public void setFormat(SequenceObjectType sequenceFormat) throws UnsupportedFormatException {
		if( supportedFormats.contains(sequenceFormat) ) this.sequenceFormat = sequenceFormat;
		else throw new UnsupportedFormatException( "sequence format " + sequenceFormat + " is not supported" );
	}

	/* (non-Javadoc)
	 * @see net.cellingo.sequence_tools.io.SequenceFetcher#setListener(net.cellingo.sequence_tools.io.SequenceFetcherListener)
	 */
	public void setListener(SequenceFetcherListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Organizes batches for batch-wise retrieval for a list of protein ids.
	 * 
	 * @throws Exception 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void fetch() throws Exception {
		int maxNcbiBatchSize = 5;
		
		for(int current=0; current<accessions.size(); current+=maxNcbiBatchSize ){
			List<String> idBatch;
			if( current+maxNcbiBatchSize < accessions.size() ){
				idBatch = accessions.subList( current, current+maxNcbiBatchSize );
				Thread.sleep(3000);
			}
			else{
				idBatch = accessions.subList( current, accessions.size() );
			}
			fetchSequenceBatch(idBatch);
		}
	}
	
	/**
	 * fetches a single sequence batch
	 * @param accessionsBatch
	 * @throws Exception
	 */
	private void fetchSequenceBatch( List<String> accessionsBatch ) throws Exception{
		String protein_id_string = "";

		for(int i=0; i<accessionsBatch.size(); i++){
			String protein_id = accessionsBatch.get(i);
			if(i == accessionsBatch.size() - 1){
				protein_id_string += protein_id;
			}
			else{
				protein_id_string += protein_id + ",";
			}
		}
		
		String url = "http://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=" + database + "&dopt=" + sequenceFormat + "&id=" + protein_id_string;
	
		//for testing only
		//String result = streamToString(new URL(url).openStream());
		
		try {
			if( this.sequenceFormat == SequenceObjectType.SIMPLE_SEQUENCE ){
				streamToFasta( new URL(url).openStream(), accessionsBatch );
			}
			else if( this.sequenceFormat == SequenceObjectType.GENBANK_SEQUENCE ){
				streamToGenbank( new URL(url).openStream(), accessionsBatch );
			}

		} catch (MalformedURLException e) {
			throw new Exception("caught MalformedURLException");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("caught IOException");
		}
	}


	/**
	 * parse the input stream to simple Sequence objects
	 * @param is
	 * @return List<FastaSequences>
	 * @throws IOException
	 */
	private void streamToFasta( InputStream is, List<String> accessionsBatch ) throws IOException{
		
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuilder sequence = new StringBuilder();
		String description = "";
		
		try {
			String line;
			while( (line = in.readLine()) != null ){
				//System.out.println( line );
				if(line.startsWith(">") ){
					/*new FASTA sequence starts; process the sequence of last Fasta*/
					if( sequence.length() != 0 ){
						Sequence newSeq = SequenceFactory.createSequence(sequence);
						newSeq.setSequenceName( description );
						for( String accno : accessionsBatch ){
							if( description.contains(accno) ){
								newSeq.setAccessionNumber( accno );
							}
						}
						
						if( listener != null ){
							listener.sequenceFetched( newSeq );
						}
						else{
							sequences.add( newSeq );
						}
					}
					//get the (new) description
					description = line.substring(1);
					sequence = new StringBuilder();
				}
				else{	// no FASTA description: concatenate
					sequence.append(line);
				}
			}
			/*process last sequence*/
			Sequence newSeq;
			newSeq = SequenceFactory.createSequence(sequence);
			newSeq.setSequenceName( description );
			for( String accno : accessionsBatch ){
				if( description.contains(accno) ){
					newSeq.setAccessionNumber( accno );
				}
			}
			if( listener != null ){
				listener.sequenceFetched( newSeq );
			}
			else{
				sequences.add( newSeq );
			}
		} catch (SequenceCreationException e) {
			System.out.println( " a sequence creation error occurred. This one is skipped: " + description );
			e.printStackTrace();
		}
		is.close();
	}

	private void streamToGenbank( InputStream is, List<String> accessionsBatch ) throws IOException{
		System.out.println(" this option has not been mplemented yet");
		//TODO unimplemented method
	}


}
