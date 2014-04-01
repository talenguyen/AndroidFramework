package com.talenguyen.androidframework.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;

import com.talenguyen.androidframework.module.database.SQLiteHelper;

public abstract class MultipleSelectionCursorAdapter<T> extends CursorAdapter {

	private List<Integer> selectedIndexs; // The array which will contain the position of the selections.
	private Class<? extends T> clazz;
	
	public MultipleSelectionCursorAdapter(Context context, Class<? extends T> clazz) {
		super(context, null, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		this.clazz = clazz;
	}
	
	/**
	 * @return Number of selected items
	 */
	public int getSelectionCount() {
		if (selectedIndexs != null) {
			return selectedIndexs.size();
		}
		return 0;
	}
	
	/**
	 * Select/De-select the item at position. 
	 * @param position The position where item is toggleSelect
	 */
	public void toggleSelect(int position) {
		if (selectedIndexs == null) {
			selectedIndexs = new ArrayList<Integer>();
		}
		
		if (isSelected(position)) {
			selectedIndexs.remove(Integer.valueOf(position));
		} else {
			selectedIndexs.add(position);
		}
	}
	
	/**
	 * Get selected items.
	 * @return The selected items or null.
	 */
	public List<T> getSelectedItems() {
		if (selectedIndexs == null || selectedIndexs.size() == 0) {
			return null;
		}
		
		final Cursor cursor = getCursor();
		if (cursor == null || cursor.getCount() == 0) {
			return null;
		}
		
		final int length = selectedIndexs.size();
		final List<T> result = new ArrayList<T>(length);
		for (int i = 0; i < length; i++) {
			final int index = selectedIndexs.get(i);
			if (cursor.moveToPosition(index)) {
				final T item = SQLiteHelper.fromCursor(cursor, clazz);
				result.add(item);	
			}
			
		}
		
		return result;
	}
	
	/**
	 * Select all items.
	 */
	public void selectAll() {
		int length = getCount();
		if (length == 0) {
			return;
		}
		
		if (selectedIndexs == null) {
			selectedIndexs = new ArrayList<Integer>();
		} else {
			selectedIndexs.clear();
		}
		
		for (int i = 0; i < length; i++) {
			selectedIndexs.add(i);
		}
	}
	
	/**
	 * Deselect all items.
	 */
	public void deselectAll() {
		if (selectedIndexs != null) {
			selectedIndexs.clear();
			selectedIndexs = null;
		}
	}
	
	/**
	 * Check if the position is selected or not.
	 * @param position The position to check.
	 * @return <b>true</b> if the item at position is selected or <b>false</b> otherwise.
	 */
	public boolean isSelected(int position) {
		if (selectedIndexs != null && selectedIndexs.contains(Integer.valueOf(position))) {
			return true;
		}
		return false;
	}
}
