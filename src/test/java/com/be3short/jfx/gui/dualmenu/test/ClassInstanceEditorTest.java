
package com.be3short.jfx.gui.dualmenu.test;

import com.be3short.jfx.input.fieldeditors.ClassInstanceEditor;

public class ClassInstanceEditorTest {

	public static class RandomData {

		public String str = " bla bla ";

		public float ff = (float) 0.0;

		public double dd = 25.0;

		public Integer ii = 100;

		public RandomData() {

		}
	}

	public static void main(String args[]) {

		RandomData rd = new RandomData();
		ClassInstanceEditor ci = new ClassInstanceEditor(rd);
	}
}
