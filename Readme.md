# BioJavaLite

## A bioinformatics base library 

Can be used for modelling and manipulating  
- sequences
- probes
- taxonomy
- alignments

## Example use cases

### Sequence creation from scratch
Sequences can be created in several ways. Here are a few.

Simply pass a String or StringBuilder object to an appropriate method of SequenceFactory.
The factory method will determine the sequence3 type based on the raw sequence.

```java
String raw = "atggatctttctttcactctttcggtcgtgtcggccatcctcgccatcactgctgtgattgctgtatttattgtgat";
Sequence sequence = SequenceFactory.createSequence(raw);
System.out.println("created sequence = " + sequence);
```

will output
```
created sequence = UNNAMED SEQUENCE
ATGGATCTTTCTTTCACTCTTTCGGTCGTGTCGGCCATCCTCGCCATCACTGCTGTGATTGCTGTATTTATTGTGAT
```

Or if you know the sequence type, use the overloaded method

```java
String raw = "atggatctttctttcactctttcggtcgtgtcggccatcctcgccatcactgctgtgattgctgtatttattgtgat";
Sequence sequence = SequenceFactory.createSequence(testSeqDNAone, SequenceType.DNA);
sequence.setSequenceName("New Sequence");
System.out.println(sequence);
```

will output
```
New Sequence
ATGGATCTTTCTTTCACTCTTTCGGTCGTGTCGGCCATCCTCGCCATCACTGCTGTGATTGCTGTATTTATTGTGAT
```

Of course, you can also use the type specific constructors, but then you'll have to make sure you
have legal (e.a. correct alphabet), uppercase String variables:

```java
DnaSequence dnaSeq = new DnaSequence(testSeqDNA);
RnaSequence rnaSeq = new RnaSequence(testSeqRNA);
```

Sequences are immutable for their sequence property, so you can only change sequences by creating
a modified copy. Here are two ways to get a subsequence.

```java
sequence.setSequenceName("Mighty big sequence");
Sequence subSeq = sequence.getSubSequence(0, 10);
//or, with a coordinates object
Sequence subSeq2 = sequence.getSubSequence(new SequenceCoordinates(0, 10));

System.out.println(subSeq);
```

will output

```
Mighty big sequence [FROM 0 TO 10]
ATGGATCTTT
```

Note that subsequencing follows Java substringing conventions: zero-based and
from--to-but-not-including.


### Reading sequences from file

Reading from file always involves the `SequenceReader` class. It can determine
the file type (sequence format) dynamically, or -preferred- you specify the sequence type
through the constructor (which is less error-prone of course).
Reading sequences from file will always return SequenceObject instances.

Reading can be done in two ways: streaming and batch (whole file).
When registering a `SequenceReaderListener`, streaming will be used.
When no listener has been registered, bacth-processing is assumed.

The general form is this

```java
SequenceReader reader = new SequenceReader(SequenceObjectType.YOUR_FORMAT, YOUR_FILE(S));
reader.read();
```

Currently, only Fasta, GenBank and GFF3 are supported.

#### Fasta streaming

All sequence formats support streaming processing. Simply register a listener and get notified whenever a new
sequence is ready.

```java
SequenceReader reader = new SequenceReader(fastaFile);
reader.addSequenceReaderListener(new SequenceReaderListener() {
    @Override
    public void sequenceRead(SequenceObject sequenceObject) {
        System.out.println("sequence = " + (Sequence)sequenceObject);
    }
    @Override
    public void sequenceReadingFinished() {
        System.out.println("finished");
    }
});
reader.read();
}
```

#### Fasta in batch

Reading Fasta sequences will return `SequenceObject` instances that can be directly cast to `Sequence` instances.

```java
File fastaFile = new File("sample_data/dna_sequences_4.fa");
assertTrue(fastaFile.exists() && fastaFile.canRead());
SequenceReader reader = new SequenceReader(fastaFile);
reader.read();
ArrayList<SequenceObject> sequenceObjects = reader.getSequenceList();
for (SequenceObject seq : sequenceObjects) {
    //since Fasta has been read, a direct cast to sequence is possible
    System.out.println((Sequence)seq);
}
```

#### Processing the Fasta description line into an Annotations object

The Fasta description line, which has this form: `gi|15215093|gb|AAH12662.1| Fhit protein [Mus musculus]`
can be processed into an `Annotations` object, using a static method of the Annotations class:

```java
String line = ">gi|15215093|gb|AAH12662.1|TAXID|123456| Fhit protein [Mus musculus]";
Annotations a = Annotations.fromFastaDescriptionLine(line);
System.out.println(a);
```

will give

```
Annotations{annotations={description=[Fhit protein], organism=[Mus musculus], accession number=[gi|15215093, gb|AAH12662.1, TAXID|123456]}}
```

The annotations can be processed while reading from file by setting this flag on the SequenceReader object: `reader.setParseFastaHeader(true);`


#### GenBank

GenBank entries are also read

```java
SequenceReader reader = new SequenceReader(SequenceObjectType.GENBANK_SEQUENCE, genbankFile);
reader.read();
//I know there is only one GenBank entry in the file
GenbankEntry gbEntry = (GenbankEntry)(reader.getSequenceList().get(0));
System.out.println("GB Sequence entry:\n" + gbEntry);
System.out.println("\nnumber of genes = " + gbEntry.getElementList(SequenceElementType.GENE).size());
System.out.println("\nnumber of CDSs = " + gbEntry.getElementList(SequenceElementType.CDS).size());
System.out.println("\nnumber of rRNAs = " + gbEntry.getElementList(SequenceElementType.RRNA).size());
//Iterate the features
for (SequenceElementType type : gbEntry.getSequenceElementTypeList()) {
    for (SequenceElement element : gbEntry.getElementList(type)) {
        System.out.println("TYPE=" + type + "; CLASS=" + element.getClass().getSimpleName() + " ELEMENT=" + element);
    }
}
```


### Manipulating nucleic acid sequences

Nucleic acid sequences can be complemented and revers complemented. Ambiguous nucleotides are supported.

```java
NucleicAcidSequence dnaSeq = new DnaSequence("atggatctttcttaaa".toUpperCase());
System.out.println("dnaSeq = " + dnaSeq);
NucleicAcidSequence compl = dnaSeq.complement();
System.out.println("compl = " + compl);

DnaSequence ambiguousDna = new DnaSequence("ARWYGCTAN");
System.out.println("ambiguousDna = " + ambiguousDna);
DnaSequence ambRevCompl = (DnaSequence)ambiguousDna.reverseComplement();
System.out.println("ambRevCompl = " + ambRevCompl);
```

will output

```
dnaSeq = UNNAMED SEQUENCE
ATGGATCTTTCTTAAA

compl = UNNAMED SEQUENCE
TACCTAGAAAGAATTT

ambiguousDna = UNNAMED SEQUENCE
ARWYGCTAN

ambRevCompl = UNNAMED SEQUENCE
NTAGCRWYT
```


### ORF finding and translation
With nucleotides you can either find Open Reading Frames (ORFs) or translate them.

To do ORF finding, you need to pass a GeneAnalysisOptions object together with the sequence to be analysed.
Call `GeneAnalysisOptions.getDefaults()` to get an object with default values.

```java
GeneAnalysisOptions geneAnalysisOptions = new GeneAnalysisOptions();
geneAnalysisOptions.setMinimumOrfSize(20);
geneAnalysisOptions.setStrandSelection(SequenceStrand.BOTH);
geneAnalysisOptions.setOrfDefinition(OrfDefinition.STOP_TO_STOP);
OrfFinder orfFinder = new OrfFinder(dnaOne, geneAnalysisOptions);
orfFinder.start();
List<OpenReadingFrame> orfs = orfFinder.getOrfList();
for (OpenReadingFrame orf : orfs) {
    System.out.println(orf);
}
```

To translate a single sequence between two positions:

```java
StringBuilder translation = OrfFinder.doTranslation(1, dnaOne, 8, 56, "");
System.out.println("translation = " + translation);
```

To get a 3- or 6-frame translation, follow this use case

```java
String[] translation = OrfFinder.doSixFrameTranslation(dnaOne);
for (String line : translation) {
    System.out.println(line);
}
```

```
M  D  L  S  F  T  L  S  V  V  S  A  I  L  A  I  T  A  V  I  A  V  F  I  *  K  D
 W  I  F  L  S  L  F  R  S  C  R  P  S  S  P  S  L  L  *  L  L  Y  L  F  K  K
  G  S  F  F  H  S  F  G  R  V  G  H  P  R  H  H  C  C  D  C  C  I  Y  L  K  R
ATGGATCTTTCTTTCACTCTTTCGGTCGTGTCGGCCATCCTCGCCATCACTGCTGTGATTGCTGTATTTATTTAAAAAGAT
TACCTAGAAAGAAAGTGAGAAAGCCAGCACAGCCGGTAGGAGCGGTAGTGACGACACTAACGACATAAATAAATTTTTCTA
  H  I  K  R  E  S  K  R  D  H  R  G  D  E  G  D  S  S  H  N  S  Y  K  N  L  F  I
    S  R  E  K  V  R  E  T  T  D  A  M  R  A  M  V  A  T  I  A  T  N  I  *  F  S
   P  D  K  K  *  E  K  P  R  T  P  W  G  R  W  *  Q  Q  S  Q  Q  I  *  K  F  L
```

