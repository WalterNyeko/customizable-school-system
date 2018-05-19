package school.ui.multiline;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

import school.ui.timetable.TimeTableLesson;

public class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
	
	TimeTableLesson timeTableLesson;
	
	public MultiLineTableCellRenderer(){
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(true);
	}
	
	
	
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    
    	setText((value==null)? "" : value.toString());
    	adjustRowHeight(table,row,column);
    	
        return this;
    }

	private void adjustRowHeight(JTable table, int row, int column) {
		
	}
}