
/**************************************************************************
	Copyright (c) 2001-2003 Geir Landr?(drop@destroydrop.com)
	JavaScript Tree - www.destroydrop.com/hjavascripts/tree/
	Version 0.96	

	This script can be used freely as long as all copyright messages are
	intact.
**************************************************************************/

// Arrays for nodes and icons
var nodes = new Array();
var openNodes = new Array();
var icons = new Array(6);
var selectNodeId = 1;

// Loads all icons that are used in the tree
function preloadIcons() {
	icons[0] = new Image();
	icons[0].src = "../style/app/images/plus.gif";
	icons[1] = new Image();
	icons[1].src = "../style/app/images/plusbottom.gif";
	icons[2] = new Image();
	icons[2].src = "../style/app/images/minus.gif";
	icons[3] = new Image();
	icons[3].src = "../style/app/images/minusbottom.gif";
	icons[4] = new Image();
	icons[4].src = "../style/app/images/folder.gif";
	icons[5] = new Image();
	icons[5].src = "../style/app/images/folderopen.gif";
}
// Create the tree
function createTree(arrName, startNode, openNode) {
	nodes = arrName;
	if (nodes.length > 0) {
		preloadIcons();
		if (startNode == null) {
			startNode = 0;
		}
		if (openNode != 0 || openNode != null) {
			setOpenNodes(openNode);
		}
		//make tree root node
		if (startNode != 0) {
			var nodeValues = nodes[getArrayId(startNode)].split("|");
			document.write("<a onmouseover=\"window.status='" + nodeValues[2] + "';return true;\" onmouseout=\"window.status=' ';return true;\"><img src=\"../style/app/images/" + (nodeValues[4] == "null" || nodeValues[4] == "" ? "folderopen.gif" : nodeValues[4]) + "\" align=\"absbottom\" alt=\"\" /><span id=\"span_node_" + nodeValues[0] + "\">" + nodeValues[2] + "</span></a><br />");
		} else {
			document.write("<img src=\"../style/app/images/base.gif\" align=\"absbottom\" alt=\"\" /><span id=\"span_node_1\">Website</span><br />");
		}
		var recursedNodes = new Array();
		addNode(startNode, recursedNodes);
	}
}
// Returns the position of a node in the array
function getArrayId(node) {
	for (i = 0; i < nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[0] == node) {
			return i;
		}
	}
}
// Puts in array nodes that will be open
function setOpenNodes(openNode) {
	for (i = 0; i < nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[0] == openNode) {
			openNodes.push(nodeValues[0]);
			setOpenNodes(nodeValues[1]);
		}
	}
}
// Checks if a node is open
function isNodeOpen(node) {
	for (i = 0; i < openNodes.length; i++) {
		if (openNodes[i] == node) {
			return true;
		}
	}
	return false;
}
// Checks if a node has any children
function hasChildNode(parentNode) {
	for (i = 0; i < nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[1] == parentNode) {
			return true;
		}
	}
	return false;
}
// Checks if a node is the last sibling
function lastSibling(node, parentNode) {
	var lastChild = 0;
	for (i = 0; i < nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[1] == parentNode) {
			lastChild = nodeValues[0];
		}
	}
	if (lastChild == node) {
		return true;
	}
	return false;
}
// Adds a new node to the tree
function addNode(parentNode, recursedNodes) {
	for (var i = 0; i < nodes.length; i++) {
		var nodeValues = nodes[i].split("|");
		if (nodeValues[1] == parentNode) {
			var ls = lastSibling(nodeValues[0], nodeValues[1]);
			var hcn = hasChildNode(nodeValues[0]);
			var ino = isNodeOpen(nodeValues[0]);

			// Write out line & empty icons
			for (g = 0; g < recursedNodes.length; g++) {
				if (recursedNodes[g] == 1) {
					document.write("<img src=\"../style/app/images/line.gif\" align=\"absbottom\" alt=\"\" />");
				} else {
					document.write("<img src=\"../style/app/images/empty.gif\" align=\"absbottom\" alt=\"\" />");
				}
			}

			// put in array line & empty icons
			if (ls) {
				recursedNodes.push(0);
			} else {
				recursedNodes.push(1);
			}

			// Write out join icons
			if (hcn) {
				if (ls) {
					document.write("<a href=\"javascript: oc(" + nodeValues[0] + ", 1);\"><img id=\"join" + nodeValues[0] + "\" src=\"../style/app/images/");
					if (ino) {
						document.write("minus");
					} else {
						document.write("plus");
					}
					document.write("bottom.gif\" align=\"absbottom\" alt=\"Open/Close node\" /></a>");
				} else {
					document.write("<a href=\"javascript: oc(" + nodeValues[0] + ", 0);\"><img id=\"join" + nodeValues[0] + "\" src=\"../style/app/images/");
					if (ino) {
						document.write("minus");
					} else {
						document.write("plus");
					}
					document.write(".gif\" align=\"absbottom\" alt=\"Open/Close node\" /></a>");
				}
			} else {
				if (ls) {
					document.write("<img src=\"../style/app/images/joinbottom.gif\" align=\"absbottom\" alt=\"\" />");
				} else {
					document.write("<img src=\"../style/app/images/join.gif\" align=\"absbottom\" alt=\"\" />");
				}
			}

			// Start link
			var actionTreeStr=nodeValues[3].split("#@#");
			document.write("<a onmouseover=\"window.status='" + nodeValues[2] + "';return true;\" onmouseout=\"window.status=' ';return true;\" onclick=\"click_node_set('" + nodeValues[0] + "');tree_click_event('"+actionTreeStr[0]+"','"+actionTreeStr[1]+"')\">");
			
			// Write out folder & page icons
			if (hcn) {
				document.write("<img id=\"icon" + nodeValues[0] + "\" src=\"../style/app/images/" + (nodeValues[4] == "null" || nodeValues[4] == "" ? (ino ? "folderopen.gif" : "folder.gif") : nodeValues[4]));
				//document.write("<img id="icon" + nodeValues[0] + "" src="../style/app/images/folder.gif");
					//if (ino) document.write("open");
				document.write("\" align=\"absbottom\" alt=\"\" />");
			} else {
				document.write("<img id=\"icon" + nodeValues[0] + "\" src=\"../style/app/images/" + (nodeValues[4] == "null" || nodeValues[4] == "" ? "page.gif" : nodeValues[4]) + "\" align=\"absbottom\" alt=\"\" />");
			}
			
			// Write out node name
			document.write("<span id=\"span_node_" + nodeValues[0] + "\" style=\"cursor: pointer\">" + nodeValues[2]);

			// End link
			document.write("</span></a><br />");
			
			// If node has children write out divs and go deeper
			if (hcn) {
				document.write("<div id=\"div" + nodeValues[0] + "\"");
				if (!ino) {
					document.write(" style=\"display: none;\"");
				}
				document.write(">");
				addNode(nodeValues[0], recursedNodes);
				document.write("</div>");
			}
			
			// remove last line or empty icon 
			recursedNodes.pop();
		}
	}
}
//
function click_node_set(nodeId) {
	//选择背景控制
	var bf_node_div = document.getElementById("span_node_" + selectNodeId);
	bf_node_div.style.backgroundColor = "#FFFFFF";
	var span_node_div = document.getElementById("span_node_" + nodeId);
	span_node_div.style.backgroundColor = "#e0e9f9";
	selectNodeId = nodeId;
}
// Opens or closes a node
function oc(node, bottom) {
	var theDiv = document.getElementById("div" + node);
	var theJoin = document.getElementById("join" + node);
	var theIcon = document.getElementById("icon" + node);
	if (theDiv.style.display == "none") {
		if (bottom == 1) {
			theJoin.src = icons[3].src;
		} else {
			theJoin.src = icons[2].src;
		}
		//if default ico change ioc where open or close 
		if (theIcon.src == icons[4].src) {
			theIcon.src = icons[5].src;
		}
		theDiv.style.display = "";
	} else {
		if (bottom == 1) {
			theJoin.src = icons[1].src;
		} else {
			theJoin.src = icons[0].src;
		}
		//if default ico change ioc where open or close 
		if (theIcon.src == icons[5].src) {
			theIcon.src = icons[4].src;
		}
		theDiv.style.display = "none";
	}
}
// Push and pop not implemented in IE
if (!Array.prototype.push) {
	function array_push() {
		for (var i = 0; i < arguments.length; i++) {
			this[this.length] = arguments[i];
		}
		return this.length;
	}
	Array.prototype.push = array_push;
}
if (!Array.prototype.pop) {
	function array_pop() {
		lastElement = this[this.length - 1];
		this.length = Math.max(this.length - 1, 0);
		return lastElement;
	}
	Array.prototype.pop = array_pop;
}

