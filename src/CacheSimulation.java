import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

class Architecture {
	int memMB, cacheB, procb, addb, blockb, offsetb, tagb, numBlocks;
	void setArchitecture(int mem, int num, int proc) {
		memMB = mem;
		numBlocks = num;
		procb = proc;
		addb = 20+log2(mem);
		offsetb = log2(procb/8);
		tagb = addb-offsetb;
		blockb = 1+tagb+procb;
		cacheB = numBlocks*blockb/8;
	}
	static int log2(int x) {
		return (int)(Math.round(Math.log(x)/Math.log(2)));
	}
	public String toString() {
		return 
				"Architecture:\n"+
				"System type = "+procb+"-bit\n" +
				"Memory = "+memMB+" MB\n"+
				"Cache = "+cacheB+" B\n"+
				"Number of Cache Blocks = "+numBlocks+"\n" +
				"Cache Block Size = "+blockb+" b\n"+
				"Address Size = "+addb+" b\n" +
				"Tag = "+tagb+" b\n" +
				"Offset = "+offsetb+" b";
	}
}

class InvalidAddressException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}

class Entry {
	long tag, offset;
	Entry(long address, Architecture arch) {
		if(address>((long)arch.memMB*1024*1024)) throw new InvalidAddressException();
		long off = (long)Math.round(Math.pow(2, arch.offsetb));
		offset = address%off;
		tag = address/off;
	}
}

class CacheBlock {
	boolean valid; long tag;
	CacheBlock() {
		valid = false;
		tag = 0;
	}
	CacheBlock(Entry e) {
		tag = e.tag;
		valid = true;
	}
	static void printCache(CacheBlock Cache[], Architecture arch) {
		int i=0;
		long off = (long)Math.round(Math.pow(2, arch.offsetb));
		while(i<Cache.length) {
			System.out.print((Cache[i].valid?
				("V "+Cache[i].tag*off+"-"+((Cache[i].tag+1)*off-1)):"I")+" | ");
			if(++i%8==0) System.out.println();
		}
		System.out.println();
	}
	int indexIn(CacheBlock[] Cache) {
		int index=0;
		try {
			while(!Cache[index].valid || Cache[index].tag!=tag) index++;
			return index;
		} catch(ArrayIndexOutOfBoundsException aibe) {
			return -1;
		}
	}
	boolean hitOrMiss(CacheBlock Cache[], Algorithm alg, Architecture arch) {
		int index = indexIn(Cache);		
		if(index>=0) {
			System.out.println("Hit");
			alg.hit(index);
		} else {
			System.out.println("Miss");
			alg.miss(Cache,this);
		}
		System.out.print("Register: ");
		for(int i = 0; i<alg.indexQueue.size(); i++)
			System.out.print(alg.indexQueue.get(i)+" ");
		System.out.println();
		CacheBlock.printCache(Cache,arch);
		return index>=0?true:false;
	}
	static CacheBlock[] newCache(Architecture arch) {
		CacheBlock Cache[] = new CacheBlock[arch.numBlocks];
		int index = 0;
		while (index<Cache.length) Cache[index++] = new CacheBlock();
		return Cache;
	}
}

abstract class Algorithm {
	ArrayList<Integer> indexQueue =  new ArrayList<Integer>();
	abstract void miss(CacheBlock Cache[], CacheBlock newBlock);
	abstract void hit(int index);
}

class LRU extends Algorithm {
	void miss(CacheBlock Cache[], CacheBlock newBlock) {
		int index = 0;
		if(indexQueue.size()==Cache.length) {
			index = indexQueue.get(0);
			indexQueue.remove(0);  //removing from LRU side
		} else while(Cache[index].valid) index++;
		indexQueue.add(new Integer(index)); //adding to MRU side
		Cache[index] = newBlock;
	}
	void hit(int index) {
		indexQueue.remove(indexQueue.indexOf(index));
		indexQueue.add(index);
	}
}

class MRU extends Algorithm {
	void miss(CacheBlock Cache[], CacheBlock newBlock) {
		int index = 0;
		if(indexQueue.size()==Cache.length) {
			index = indexQueue.get(indexQueue.size()-1);
		} else { //Invalid blocks in cache
			while(Cache[index].valid) index++;
			indexQueue.add(new Integer(index)); 
		}
		Cache[index] = newBlock;
	}
	void hit(int index) {
		indexQueue.remove(indexQueue.indexOf(index));
		indexQueue.add(index);
	}
}

class LFU extends Algorithm {
	void miss(CacheBlock Cache[], CacheBlock newBlock) {
		int index = 0;
		if(indexQueue.size()==Cache.length) {
			for(int i = 1, min = indexQueue.get(0); i<indexQueue.size(); i++) {
				if(indexQueue.get(i)<min) {
					min = indexQueue.get(i);
					index = i;
				}
			}
			indexQueue.set(index, new Integer(1));
		} else {
			while(Cache[index].valid) index++;
			indexQueue.add(new Integer(1));
		}
		Cache[index] = newBlock;
	}
	void hit(int index) {
		indexQueue.set(index, new Integer(indexQueue.get(index)+1));
	}
}

public class CacheSimulation {
	public static void main(String args[]) throws IOException {
		Architecture arch = new Architecture();
		FrameArch first = new FrameArch();
		while(!first.submitted) {
			try {
			    Thread.sleep(100);
			} catch(InterruptedException ie) {
			    Thread.currentThread().interrupt();
			}
		}
		arch.setArchitecture(
			Integer.parseInt(first.memText.getText()),
			Integer.parseInt(first.cacheText.getText()),
			Integer.parseInt(first.procText.getText())
		);
		System.out.println(arch);
		
		int totalCount = 0;
		CacheBlock LRUCache[] = CacheBlock.newCache(arch);
		Algorithm algLRU = new LRU();
		int hitCountLRU = 0;
		CacheBlock MRUCache[] = CacheBlock.newCache(arch);
		Algorithm algMRU = new MRU();
		int hitCountMRU = 0;
		CacheBlock LFUCache[] = CacheBlock.newCache(arch);
		Algorithm algLFU = new LFU();
		int hitCountLFU = 0;
		boolean exit = false, readFile = false;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do {
			System.out.print("\nAddress/Command: ");
			String input = br.readLine();
			try{
				if(readFile && input!=null) System.out.println(input);
				Entry newEntry = new Entry(Integer.parseInt(input),arch);
				CacheBlock newBlock = new CacheBlock(newEntry);
				System.out.print("LRU: ");
				if(newBlock.hitOrMiss(LRUCache,algLRU,arch)) hitCountLRU++;
				System.out.print("MRU: ");
				if(newBlock.hitOrMiss(MRUCache,algMRU,arch)) hitCountMRU++;
				System.out.print("LFU: ");
				if(newBlock.hitOrMiss(LFUCache,algLFU,arch)) hitCountLFU++;
				totalCount++;
				System.out.println("Hit Ratios:");
				System.out.println("LRU: "+(float)hitCountLRU/totalCount+
								" | MRU: "+(float)hitCountMRU/totalCount+
								" | LFU: "+(float)hitCountLFU/totalCount);
				/*if(readFile) {
					try {
					    Thread.sleep(500);
					} catch(InterruptedException ie) {
					    Thread.currentThread().interrupt();
					}
				}*/
			} catch(NumberFormatException nfe) {
				if(input==null) {
					System.out.println("End of file");
					br = new BufferedReader(new InputStreamReader(System.in));
					readFile = false;
				}
				else if(input.charAt(0)=='r') {
					try{
						br = new BufferedReader(
								new FileReader(input.substring(1).trim()));
						readFile = true;
					} catch (FileNotFoundException fnfe) {
						System.out.println(input.substring(1).trim()+" File Not Found");
						br = new BufferedReader(new InputStreamReader(System.in));
					}
				}
				else if(input.equals("q")) exit = true;
				else System.out.println("Invalid Entry");
			} catch(InvalidAddressException iae) {
				System.out.println("Address Limit is "+arch.memMB*1024*1024);
			}
		} while(!exit);
	}
}

class FrameArch extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JTextField memText, cacheText, procText;
	boolean submitted;
	FrameArch() {
		super("Algorithm Predictor 2");
		setSize(500,500);  
		
		JPanel fin = new JPanel(null);
		
		JLabel memLabel = new JLabel("Memory Size (MB): ");
		memText = new JTextField();
		memLabel.setLocation(100,50);
		memText.setLocation(250,50);
		memLabel.setSize(150,50);
		memText.setSize(150,50);
		
		JLabel cacheLabel = new JLabel("No. of Cache Blocks: ");
		cacheText = new JTextField();
		cacheLabel.setLocation(100,150);
		cacheText.setLocation(250,150);
		cacheLabel.setSize(150,50);
		cacheText.setSize(150,50);
		
		JLabel procLabel = new JLabel("CPU (32-bit/64-bit):");
		procText = new JTextField();
		procLabel.setLocation(100,250);
		procText.setLocation(250,250);
		procLabel.setSize(150,50);
		procText.setSize(150,50);
		
		JButton subm = new JButton("Submit");
		subm.setLocation(175,350);
		subm.setSize(150,50);
		
		fin.add(memLabel);
		fin.add(memText);
		fin.add(cacheLabel);
		fin.add(cacheText);
		fin.add(procLabel);
		fin.add(procText);
		fin.add(subm);
		
		add(fin);
		
		subm.addActionListener(this);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void actionPerformed(ActionEvent ae) {
		submitted = true;
	}
}