package com.talenguyen.androidframework;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GIANG on 3/7/14.
 */
public class Test {

    public static void main(String[] args) {
        String[] items = init(4);
        String[] pageListEx = new String[8];
        System.arraycopy(items, 0, pageListEx, 0, 4);
        System.arraycopy(items, 0, pageListEx, 4, 4);
        printList(pageListEx);
    }

    private static void printList(String[] pageListEx) {
        for (int i = 0; i < pageListEx.length; i++) {
            System.out.println(pageListEx[i]);
        }
    }

    private static String[] init(int count) {
        final String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = "Item " + i;
        }
        return result;
    }
}
