package gui.editor_panel.project_panel;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class ProjectTreeModel extends DefaultTreeModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5217791057700379327L;

	public ProjectTreeModel(TreeNode RootNode) {
		super(RootNode);
	}
}