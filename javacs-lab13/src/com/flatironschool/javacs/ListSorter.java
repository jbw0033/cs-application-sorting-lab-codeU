/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        if(list.size() < 2) {
        	return list;
        }
    
        List<T> left = mergeSort(list.subList(0, list.size()/2), comparator);
        List<T> right = mergeSort(list.subList(list.size()/2, list.size()), comparator);
        
        return merge(left, right, comparator);
	}
	
	private List<T> merge(List<T> left, List<T> right, Comparator<T> comparator) {
		List<T> result = new  ArrayList<T>();
		int j = 0, i = 0;
		while(i < left.size() && j < right.size()) {
			if (comparator.compare(left.get(i),right.get(j)) >= 0) {
				result.add(right.get(j));
				j++;
			}
			else {
				result.add(left.get(i));
				i++;
			}
		}
		if(i >= left.size()) {
			while(j < right.size()) {
				result.add(right.get(j));
				j++;
			}
		}
		else if(j >= right.size()) {
			while(i < left.size()) {
				result.add(left.get(i));
				i++;
			}
		}
		return result;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> heap = new PriorityQueue<T>();
        heap.addAll(list);
        list.clear();
        while(!heap.isEmpty()) {
        	list.add(heap.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> heap = new PriorityQueue<T>();
        List<T> result = new ArrayList<T>();
        for(T element: list) {
        	if(k > heap.size()) {
        		heap.add(element);
        	}
        	else {
        		if(comparator.compare(element, heap.peek()) > 0) {
        			heap.poll();
        			heap.add(element);
        		}
        	}
        }
        while(!heap.isEmpty()) {
        	result.add(heap.poll());
        }
        return result;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
