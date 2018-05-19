package clarion.ui.dorm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import clarion.controller.dorm.DormController;

public class DormTree extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	private JTree tree;
	private DefaultMutableTreeNode root;
	private JScrollPane scrollPaneDormTree;

	private DormController controller;

	public DormTree() {

		setPreferredSize(new Dimension(160, 540));
		// setBorder(new LineBorder(Color.black, 3));

		
		List<Object> hierarchy = null;
		try {
			controller = new DormController();
			hierarchy = controller.fillDormTree();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		hierarchy.toArray();

		root = processHierarchy(hierarchy);
		tree = new JTree(root);
		scrollPaneDormTree = new JScrollPane(tree);
		add(scrollPaneDormTree, BorderLayout.CENTER);

	}

	public DefaultMutableTreeNode processHierarchy(List<Object> hierarchy) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy.get(0));
		DefaultMutableTreeNode child;
		for (int i = 1; i < hierarchy.size(); i++) {
			Object nodeSpecifier = hierarchy.get(i);
			if (nodeSpecifier instanceof List<?>) // Ie node with children
			{
				child = processHierarchy((List<Object>) nodeSpecifier);
			} else {
				child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
			}
			node.add(child);
		}
		return (node);
	}

	public JTree getTree() {
		return tree;
	}

	public JScrollPane getScrollPaneDormTree() {
		return scrollPaneDormTree;
	}

}