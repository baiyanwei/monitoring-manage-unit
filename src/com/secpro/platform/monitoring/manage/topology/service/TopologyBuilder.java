package com.secpro.platform.monitoring.manage.topology.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import y.base.DataMap;
import y.base.Node;

import com.secpro.platform.monitoring.manage.entity.SysResObj;
import com.secpro.platform.monitoring.manage.util.Assert;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;
import com.yworks.yfiles.server.graphml.flexio.data.StyledLayoutGraph;

/**
 * <p>
 * Description: 拓扑构建器
 * </p>
 * 
 * @author baiyanwei
 * @version
 * 
 *          <p>
 *          History:
 * 
 *          Date Author Version Description
 *          --------------------------------------
 *          ------------------------------------------- 2012-12-28 下午3:46:48
 *          baiyanwei 3.4 To create
 *          </p>
 * 
 * @since
 * @see
 */
public class TopologyBuilder {
	final static private PlatformLogger logger = PlatformLogger.getLogger(TopologyAdapter.class);
	private static TopologyBuilder topologyBuilder = null;
	private ResourceProvider resourceProvider = null;
	private TopologyPainter topologyPainter = null;

	public static TopologyBuilder getInstance() {
		if (topologyBuilder == null) {
			topologyBuilder = new TopologyBuilder();
			topologyBuilder.resourceProvider = ResourceProvider.getInstance();
			topologyBuilder.topologyPainter = TopologyPainter.getInstance();
		}
		return topologyBuilder;
	}

	private TopologyBuilder() {

	}

	/**
	 * 创建一个基于实体节点的拓扑图
	 * 
	 * @param graph
	 * @param referentMap
	 */
	public void buildNodeTopology(StyledLayoutGraph graph, HashMap<String, String> referentMap) {
		logger.info("start build the hole topology graph ");
		// 实体节点出拓扑图时，按实现节点的所有关系都出来
		if (graph == null || referentMap == null) {
			return;
		}
		// 找到基于当前资源的所有关系
		HashMap<String, JSONObject> cityDataMap = resourceProvider.getALLCityResourceMap();
		if (cityDataMap == null) {
			return;
		}
		// 找到FW资源
		HashMap<String, List<JSONObject>> fwDataMap = resourceProvider.getFirewallAllResourceMap();
		// 全国CITY_CODE从1开始
		JSONObject currentResource = resourceProvider.getCityObjByNodeID("1");
		if (currentResource == null) {
			return;
		}
		// 构建当前全国节点到图上
		Node currentNode = topologyPainter.createNodeByResource(graph, currentResource);
		if (currentNode == null) {
			return;
		}
		// NODE与资源的对照
		HashMap<JSONObject, Node> graphNodeMap = new HashMap<JSONObject, Node>();
		// 当前资源
		graphNodeMap.put(currentResource, currentNode);
		// 构建全国节点的下层节点.
		buildSonNodeOnTopology(10000, graph, currentResource, currentNode, graphNodeMap, cityDataMap, fwDataMap);
		//
	}

	/**
	 * 构建一个基于类别节点的拓扑图
	 * 
	 * @param graph
	 * @param referentMap
	 */
	private void buildSonNodeOnTopology(int maxLoop, StyledLayoutGraph graph, JSONObject currentResource, Node currentNode, HashMap<JSONObject, Node> graphNodeMap,
			HashMap<String, JSONObject> cityDataMap, HashMap<String, List<JSONObject>> fwDataMap) {
		// #1取得当前资源的一层子城市资源,
		// safe call.
		if (maxLoop <= 0) {
			return;
		}
		List<JSONObject> sonCityList = getSonCityList(currentResource, cityDataMap);

		try {
			// #2构建当前资源下的FW资源.
			List<JSONObject> fwList = fwDataMap.get(currentResource.getString("CITY_CODE"));
			topologyPainter.paintNodeOnGraph(graph, currentNode, "to", currentResource, fwList, RelationConstants.FILIATION, graphNodeMap);
		} catch (JSONException e) {
			logger.exception(e);
		}
		//
		if (Assert.isEmptyCollection(sonCityList) == true) {
			return;
		}
		// 3构建当前资源的子城市资源.
		topologyPainter.paintNodeOnGraph(graph, currentNode, "to", currentResource, sonCityList, RelationConstants.FILIATION, graphNodeMap);
		//
		for (int n = 0; n < sonCityList.size(); n++) {
			Node sonCityNode = graphNodeMap.get(sonCityList.get(n));
			// #4 构建子资源下面的城市资源
			buildSonNodeOnTopology(maxLoop--, graph, sonCityList.get(n), sonCityNode, graphNodeMap, cityDataMap, fwDataMap);
		}

	}

	/**
	 * 取得指定资源的子城市
	 * 
	 * @param currentResource
	 * @param cityDataMap
	 * @return
	 */
	private List<JSONObject> getSonCityList(JSONObject currentResource, HashMap<String, JSONObject> cityDataMap) {
		ArrayList<JSONObject> sonCityList = new ArrayList<JSONObject>();
		try {
			String currentID = currentResource.getString("CITY_CODE");
			JSONObject sonResouce = null;
			for (Iterator<String> keyIter = cityDataMap.keySet().iterator(); keyIter.hasNext();) {
				sonResouce = cityDataMap.get(keyIter.next());
				if (sonResouce.getString("PARENT_CODE").equalsIgnoreCase(currentID) == true) {
					sonCityList.add(sonResouce);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sonCityList;
	}

	/**
	 * 为NODE TOOLTIP加入默认信息
	 * 
	 * @param nodeTipArray
	 * @param currentResource
	 */
	public void buildDefaultNodeTip(ArrayList<ArrayList<String>> nodeTipDataList, JSONObject currentResource) {
		if (nodeTipDataList == null || currentResource == null) {
			return;
		}
		ArrayList<String> defaultTipData = new ArrayList<String>();
		nodeTipDataList.add(defaultTipData);
		// 资源名称，资源IP，采集器，健康度，监控状态
		// 标题
		JSONArray titleObject = new JSONArray();
		titleObject.put("default");
		titleObject.put("默认");
		defaultTipData.add(titleObject.toString());
		// 内容
		defaultTipData.add("资源名称:" + "AA");
		defaultTipData.add("资源标识:");
		defaultTipData.add("采集器:");
		defaultTipData.add("健康度:");
		defaultTipData.add("监控状态:");
	}

	/**
	 * 为NODE TOOLTIP加入事件信息
	 * 
	 * @param nodeTipDataList
	 * @param currentResource
	 */
	@SuppressWarnings("unchecked")
	public void buildNodeTipEvent(ArrayList<ArrayList<String>> nodeTipDataList, JSONObject currentResource) {
		if (nodeTipDataList == null || currentResource == null) {
			return;
		}
		ArrayList<String> eventTipData = new ArrayList<String>();
		//
		int lastActionIndex = -1;
		for (int i = 0; i < nodeTipDataList.size(); i++) {
			ArrayList<String> colList = nodeTipDataList.get(i);
			if (colList == null || colList.isEmpty()) {
				continue;
			}
			if (colList.get(0).startsWith("[\"action\",")) {
				lastActionIndex = i;
			}
		}
		if (lastActionIndex == -1) {
			nodeTipDataList.add(eventTipData);
		} else {
			nodeTipDataList.add(lastActionIndex, eventTipData);
		}
		// 查询最新的事件
		//
		//HashMap<String, Object> eventMap = resourceProvider.getEventList(currentResource);
		// 标题
		// JSONArray titleObject = new JSONArray();
		// titleObject.add("event");
		// if (eventMap.containsKey("total") == false) {
		// titleObject.add("事件(0)");
		// } else {
		// titleObject.add("事件(" + eventMap.get("total") + ")");
		// }
		// eventTipData.add(titleObject.toString());
		// if (eventMap == null || eventMap.isEmpty()) {
		// return;
		// }
		// List<EventView> eventList = (List<EventView>) eventMap.get("event");
		// if (eventList == null || eventList.isEmpty()) {
		// return;
		// }
		// for (int i = 0; i < eventList.size(); i++) {
		// if (eventList.get(i) == null) {
		// continue;
		// }
		// EventView view = eventList.get(i);
		// JSONArray eventLevelArray = new JSONArray();
		// eventLevelArray.add("(" +
		// resourceProvider.getUsableStatusDescr(view.getLevel()) + "," +
		// view.getTime() + ")");
		// eventLevelArray.add(String.valueOf(view.getLevel()));
		// eventTipData.add(eventLevelArray.toString());
		// eventLevelArray.clear();
		// eventLevelArray.add("\t" + view.getMsg());
		// eventLevelArray.add(String.valueOf(view.getLevel()));
		// eventTipData.add(eventLevelArray.toString());
		// }

	}

	/**
	 * 设置拓扑图的附加数据
	 * 
	 * @param graph
	 * @param dataObj
	 */
	public void setGraphAdditionalData(StyledLayoutGraph graph, JSONObject dataObj) {
		if (graph == null || dataObj == null) {
			return;
		}
		DataMap graphDp = (DataMap) graph.getDataProvider("GraphAdditionalDataMap");
		if (graphDp == null) {
			return;
		}
		graphDp.set(graph, dataObj.toString());
	}

	/**
	 * 设置拓扑图的附加数据
	 * 
	 * @param graph
	 * @param dataObj
	 */
	public String getGraphAdditionalData(StyledLayoutGraph graph) {
		if (graph == null) {
			return null;
		}
		DataMap graphDp = (DataMap) graph.getDataProvider("GraphAdditionalDataMap");
		if (graphDp == null) {
			return null;
		}
		Object dataObj = graphDp.get(graph);
		if (dataObj == null) {
			return null;
		}
		return String.valueOf(dataObj);
	}

	/**
	 * 设置拓扑图的背景 图片URL可以有三种 #1 xxx 取默认配置的背景目录下的同名图片 #2 /unionmon/xx/xx/xxx
	 * 应该下的相对路径同名图片 #3 http://x.x.x.x/xx/xxx 全URL路径
	 * 
	 * @param graph
	 * @param imageURL
	 */
	public void setGraphBackGroup(StyledLayoutGraph graph, String imageURL, String width, String height) {
		JSONObject dataObj = new JSONObject();
		JSONObject backGroundObj = new JSONObject();
		// backGroundObj.put("imageURL", imageURL);
		// backGroundObj.put("width", width);
		// backGroundObj.put("height", height);
		// dataObj.put("graphBackGround", backGroundObj);
		setGraphAdditionalData(graph, dataObj);

	}

	/**
	 * 只设置背景图
	 * 
	 * @param graph
	 * @param imageURL
	 */
	public void setGraphBackGroup(StyledLayoutGraph graph, String imageURL) {
		// JSONObject dataObj = new JSONObject();
		// JSONObject backGroundObj = new JSONObject();
		// backGroundObj.put("imageURL", imageURL);
		// dataObj.put("graphBackGround", backGroundObj);
		// setGraphAdditionalData(graph, dataObj);

	}

	/**
	 * 判断一个JS拓扑图是否存在
	 * 
	 * @param nodeID
	 * @param currentTopologyName
	 * @return
	 */
	public boolean hasExistJSTopology(String nodeID, String currentTopologyName) {
		if (nodeID == null || nodeID.length() == 0) {
			return false;
		}
		if (currentTopologyName == null || currentTopologyName.length() == 0) {
			return false;
		}
		// List<TopuConfValue> tpList =
		// FactoryRegistry.getTopuConfFactory().getAllTopuConf();
		// for (TopuConfValue tcv : tpList) {
		// if (tcv.getNodeId().equals(nodeID) &&
		// tcv.getName().equals(currentTopologyName)) {
		// return true;
		// }
		// }
		return false;
	}
}
