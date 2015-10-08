import java.util.*;
interface Algorithm1 {
	void replace(Block x[], Block newBlock, ArrayList<Integer> history);
}

class Block {
	int[] elem=new int[8];
}

class LRU1 implements Algorithm1 {
	public void replace(Block x[], Block newBlock, ArrayList<Integer> history) {
		int index=(int)history.get(0);
		//x[index]=
	}
}

