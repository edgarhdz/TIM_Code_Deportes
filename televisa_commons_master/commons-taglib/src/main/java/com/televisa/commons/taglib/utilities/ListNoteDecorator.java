/*
 * ListNoteDecorator.java
 *
 * Access a note list with numeric indexes.
 *
 * Copyright (c) Televisa 2013.
 * All Rights Reserved.
 *
 * $Id$
 */

package com.televisa.commons.taglib.utilities;

import com.televisa.commons.services.datamodel.Note;

import java.util.ArrayList;
import java.util.List;

/**
 * List Note Decorator
 *
 * Access a note list with numeric indexes.
 *
 * Changes History:
 *
 *         2013-02-25 Initial Development
 *
 * @author gescobar@xumak.com
 * @version 1.0
 */
public class ListNoteDecorator<T extends Note> {

	private ListNoteDecoratorList list;

	public ListNoteDecorator(List<T> list) {
		this.list = new ListNoteDecoratorList(list);
	}

	public ListNoteDecoratorList getDecorator() {
		return list;
	}

	/**
	 * List Note Decorator List
	 *
	 * A container for a list of pair of notes, conveniently creating the list of pair of notes at construction.
	 *
	 * Changes History:
	 *
	 *         2013-02-25 Initial Development
	 *
	 * @author gescobar@xumak.com
	 * @version 1.0
	 */
	public class ListNoteDecoratorList {

		private List<ListNoteDecoratorNote> list;

		ListNoteDecoratorList(List<T> list) {
			this.list = new ArrayList<ListNoteDecoratorNote>();
			for (int i = 0; i < list.size(); i = i + 2) {

                T note1 = null;
                T note2 = null;
                if (i < list.size()) {
                    note1 = list.get(i);
                }
                if (i + 1 < list.size()) {
                    note2 = list.get(i + 1);
                }

				this.list.add(new ListNoteDecoratorNote(note1, note2));
			}
		}

		public List<ListNoteDecoratorNote> getList() {
			return list;
		}

		/**
		 * List Note Decorator Note
		 *
		 * A container for a pair of notes, conveniently return null for any note and a list for loops constructions.
		 *
		 * Changes History:
		 *
		 *         2013-02-25 Initial Development
		 *
		 * @author gescobar@xumak.com
		 * @version 1.0
		 */
		public class ListNoteDecoratorNote {

			private List<T> list;
			private T note1;
			private T note2;

			public ListNoteDecoratorNote(T note1, T note2) {
				this.list = new ArrayList<T>();
				this.list.add(note1);
				this.list.add(note2);
				this.note1 = note1;
				this.note2 = note2;
			}

			public List<T> getList() {
				return list;
			}

			public T getNote1() {
				return note1;
			}

			public T getNote2() {
				return note2;
			}

		}

	}

}
