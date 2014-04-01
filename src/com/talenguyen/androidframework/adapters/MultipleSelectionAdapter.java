package com.talenguyen.androidframework.adapters;

import java.util.ArrayList;
import java.util.List;

public abstract class MultipleSelectionAdapter<T> extends CustomAdapter<T> {
	private List<T> selectedItems; // The array which will contain the position of the selections. 
	

	/**
	 * Select/De-select the item at position. 
	 * @param position The position where item is toggleSelect
	 */
	public void toggleSelect(int position) {
		if (position >= getCount()) {
			return;
		}
		
		if (selectedItems == null) {
			selectedItems = new ArrayList<T>();
		}
		
		final T item = getItem(position);
		
		if (selectedItems.contains(item)) {
			selectedItems.remove(item);
		} else {
			selectedItems.add(item);
		}
	}
	
	/**
	 * Get selected items.
	 * @return The selected items or null.
	 */
	public List<T> getSelectedItems() {
		return selectedItems;
	}
	
	public void selectAll() {
		int length = getCount();
		if (length == 0) {
			return;
		}
		
		if (selectedItems == null) {
			selectedItems = new ArrayList<T>();
		} else {
			selectedItems.clear();
		}
		
		for (int i = 0; i < length; i++) {
			final T item = getItem(i);
			selectedItems.add(item);
		}
	}
	
	public void deselectAll() {
		if (selectedItems != null) {
			selectedItems.clear();
			selectedItems = null;
		}
	}
//	private List<Integer> selectedIndexs; // The array which will contain the position of the selections. 
//	
//
//	/**
//	 * Select/De-select the item at position. 
//	 * @param position The position where item is toggleSelect
//	 */
//	public void toggleSelect(int position) {
//		if (selectedIndexs == null) {
//			selectedIndexs = new ArrayList<Integer>();
//		}
//		
//		if (selectedIndexs.contains(position)) {
//			selectedIndexs.remove(Integer.valueOf(position));
//		} else {
//			selectedIndexs.add(position);
//		}
//	}
//	
//	/**
//	 * Get selected items.
//	 * @return The selected items or null.
//	 */
//	public List<T> getSelectedItems() {
//		if (selectedIndexs == null || selectedIndexs.size() == 0) {
//			return null;
//		}
//		
//		final int length = selectedIndexs.size();
//		final List<T> result = new ArrayList<T>(length);
//		for (int i = 0; i < length; i++) {
//			final int index = selectedIndexs.get(i);
//			final T item = getItem(index);
//			result.add(item);
//		}
//		
//		return result;
//	}
//	
//	public void selectAll() {
//		int length = getCount();
//		if (length == 0) {
//			return;
//		}
//		
//		if (selectedIndexs == null) {
//			selectedIndexs = new ArrayList<Integer>();
//		} else {
//			selectedIndexs.clear();
//		}
//		
//		for (int i = 0; i < length; i++) {
//			selectedIndexs.add(i);
//		}
//	}
}
