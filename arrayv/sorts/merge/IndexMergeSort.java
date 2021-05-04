package sorts.merge;

import main.ArrayVisualizer;
import sorts.templates.Sort;

/*
 * 
The MIT License (MIT)

Copyright (c) 2021 aphitorite

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

final public class IndexMergeSort extends Sort {
    public IndexMergeSort(ArrayVisualizer arrayVisualizer) {
        super(arrayVisualizer);
        
        this.setSortListName("Index Merge");
        this.setRunAllSortsName("Index Merge Sort");
        this.setRunSortName("Index Mergesort");
        this.setCategory("Merge Sorts");
        this.setComparisonBased(true);
        this.setBucketSort(false);
        this.setRadixSort(false);
        this.setUnreasonablySlow(false);
        this.setUnreasonableLimit(0);
        this.setBogoSort(false);
    }
	
	private void indexSort(int[] array, int[] idx, int a, int b) {
		while(a < b) {
			int nxt = idx[a];
			while(Reads.compareOriginalValues(a, nxt) != 0) {
				Writes.swap(idx, a, nxt, 0, true, false);
				Writes.swap(array, a, nxt, 0.5, true, false);
				nxt = idx[a];
			}
			a++;
		}
	}
	
	private void merge(int[] array, int[] idx, int a, int m, int b) {
		int i = a, j = m, c = a;
		
		while(i < m && j < b) {
			if(Reads.compareValues(array[i], array[j]) <= 0)
				Writes.write(idx, i++, c++, 0.5, true, true);
			
			else {
				Highlights.markArray(2, j);
				Writes.write(idx, j++, c++, 0.5, false, true);
			}
		}
		
		while(i < m) 
			Writes.write(idx, i++, c++, 0.5, true, true);
		
		while(j < b) {
			Highlights.markArray(2, j);
			Writes.write(idx, j++, c++, 0.5, false, true);
		}
		
		this.indexSort(array, idx, a, b);
	}
	
	private void sort(int[] array, int[] idx, int a, int b) {
		if(b-a < 2) return;
		
		int m = (a+b)/2;
		this.sort(array, idx, a, m);
		this.sort(array, idx, m, b);
		this.merge(array, idx, a, m, b);
	}
    
    @Override
    public void runSort(int[] array, int length, int bucketCount) {
		int[] idx = Writes.createExternalArray(length);
		this.sort(array, idx, 0, length);
		Writes.deleteExternalArray(idx);
    }
}