package gui_system;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

class IntFilter extends DocumentFilter {
	
	private int maxLength = 0;
	
	@Override
	public void insertString(FilterBypass fb, int offset, String string,
			AttributeSet attr) throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.insert(offset, string);

		if (test(sb.toString())) {
			super.insertString(fb, offset, string, attr);
		} else {
			// warn the user and don't allow the insert
		}
	}

	private boolean test(String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text,
			AttributeSet attrs) throws BadLocationException {

		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.replace(offset, offset + length, text);
		
		if (text == "")
			doc.remove(0, doc.getLength());

		if (test(sb.toString()) && sb.length() <= maxLength) {
			super.replace(fb, offset, length, text, attrs);
		} else {
			// warn the user and don't allow the insert
		}

	}

	@Override
	public void remove(FilterBypass fb, int offset, int length)
			throws BadLocationException {
		
		Document doc = fb.getDocument();
		StringBuilder sb = new StringBuilder();
		sb.append(doc.getText(0, doc.getLength()));
		sb.delete(offset, offset + length);
		
		super.remove(fb, offset, length);
	}
	
	public void setMaxLength(int max) {
		maxLength = max;
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	
	public void clear() {
		
	}
}
