package com.secpro.platform.monitoring.manage.actions;

import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.json.JSONException;
import org.json.JSONObject;

import com.opensymphony.xwork2.ActionSupport;
import com.secpro.platform.monitoring.manage.dao.TaskScheduleActionDao;
import com.secpro.platform.monitoring.manage.dao.impl.SysResObjDaoImpl;
import com.secpro.platform.monitoring.manage.entity.MSUTask;
import com.secpro.platform.monitoring.manage.entity.SysResAuth;
import com.secpro.platform.monitoring.manage.util.httpclient.HttpClient;
import com.secpro.platform.monitoring.manage.util.httpclient.IClientResponseListener;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

public class TaskScheduleAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7815398525744691861L;
	final public static String ID_TITLE = "tid";
	final public static String REGION_TITLE = "reg";
	final public static String OPERATION_TITLE = "ope";
	final public static String CREATE_AT_TITLE = "cat";
	final public static String SCHEDULE_TITLE = "sch";
	final public static String CONTENT_TITLE = "con";
	final public static String META_DATA_TITLE = "mda";
	final public static String RES_ID_TITLE = "rid";
	final public static String IS_REALTIME_TITLE = "isrt";
	private final static PlatformLogger theLogger = PlatformLogger.getLogger(TaskScheduleAction.class);
	final public static String[] MANAGEMENT_OPERATION_TYPE = new String[] { "TOPIC-TASK-ADD", "TOPIC-TASK-UPDATE", "TOPIC-TASK-REMOVE", "TOPIC-SYSLOG-STANDARD-RULE-ADD",
			"TOPIC-SYSLOG-STANDARD-RULE-UPDATE", "TOPIC-SYSLOG-STANDARD-RULE-REMOVE" };
	final public static String OPERATION_TYPE = "operationType";

	//
	public String createMSUTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {

			MSUTask task = getBuildMSUTaskByRequest(request, true);
			if (task == null) {
				request.setAttribute("exception", new Exception("Build MSUTask instance Exception. invalid bean."));
				return "error";
			}
			//
			TaskScheduleActionDao.save(task);
			//
			request.setAttribute("resourceBean", task);
			String callBackUrl = request.getParameter("callBackUrl");
			request.setAttribute("previousURL", callBackUrl);
			request.setAttribute("WEB_MSG", "创建成功！");
			// publish the task change into MSU
			publishMUSTaskToMSU(formatMSUTask(task), "TOPIC-TASK-ADD");
		} catch (Exception e) {
			request.setAttribute("exception", e);
			e.printStackTrace();
			return "error";
		}

		return "success";
	}

	public String updateMSUTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String callBackUrl = request.getParameter("callBackUrl");
		try {
			MSUTask task = getBuildMSUTaskByRequest(request, false);
			if (task == null) {
				request.setAttribute("exception", new Exception("Build MSUTask instance Exception. invalid bean."));
				return "error";
			}
			//
			TaskScheduleActionDao.update(task);
			//
			request.setAttribute("resourceBean", task);
			request.setAttribute("previousURL", callBackUrl);
			request.setAttribute("WEB_MSG", "操作成功！");
			// publish the task change into MSU
			publishMUSTaskToMSU(formatMSUTask(task), "TOPIC-TASK-UPDATE");
		} catch (Exception e) {
			request.setAttribute("exception", e);
			e.printStackTrace();
			return "error";
		}

		return "success";
	}

	public String removeMSUTask() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String callBackUrl = request.getParameter("callBackUrl");
		try {
			String id = request.getParameter("id");
			if (id == null || id.trim().length() == 0) {
				throw new Exception("invalid id");
			}
			// exist
			MSUTask task = TaskScheduleActionDao.findById(id);
			if (task == null) {
				throw new Exception("doesn't exist instance ," + id);
			}
			TaskScheduleActionDao.delete(task);
			request.setAttribute("resourceBean", task);
			request.setAttribute("previousURL", callBackUrl);
			request.setAttribute("WEB_MSG", "操作成功！");
			// publish the task change into MSU
			publishMUSTaskToMSU(formatMSUTask(task), "TOPIC-TASK-REMOVE");
		} catch (Exception e) {
			request.setAttribute("exception", e);
			e.printStackTrace();
			return "error";
		}

		return "success";
	}

	//
	// public String removeListMSUTask() throws Exception {
	// HttpServletRequest request = ServletActionContext.getRequest();
	// String callBackUrl = request.getParameter("callBackUrl");
	// try {
	// request.setAttribute("previousURL", callBackUrl);
	// String type = request.getParameter("type");
	// String[] ids = request.getParameterValues("ids");
	// if (type == null || type.trim().length() == 0) {
	// throw new Exception("TYPE未定义");
	// }
	// if (ids == null || ids.length == 0) {
	// throw new Exception("ids未定义");
	// }
	// DataViewService dataViewService =
	// ServiceHelper.findService(DataViewService.class);
	// Class<?> clazz = dataViewService.getBeanClass(type);
	// if (clazz == null) {
	// throw new Exception("TYPE未定义");
	// }
	// for (int i = 0; i < ids.length; i++) {
	// TaskScheduleActionDao.removeBean(clazz, Long.parseLong(ids[i]));
	// }
	// request.setAttribute("WEB_MSG", "操作成功！");
	// } catch (Exception e) {
	// request.setAttribute("exception", e);
	// e.printStackTrace();
	// return "error";
	// }
	//
	// return "success";
	// }

	/**
	 * @param request
	 * @param isNew
	 * @return
	 * @throws Exception
	 * 
	 *             build bean from form.
	 */
	private MSUTask getBuildMSUTaskByRequest(HttpServletRequest request, boolean isNew) throws Exception {
		MSUTask task = new MSUTask();
		//
		task.setRegion(request.getParameter("region").trim());
		task.setOperation(request.getParameter("operation").trim());
		task.setSchedule(request.getParameter("schedule").trim());
		task.setCreateAt(System.currentTimeMillis());
		//build the meateData from the ResAuth.
		String metaDataValue=formatSysResAuth(getBuildSysResAuthByRequest(request, isNew));
		task.setMetaData(metaDataValue);
		//
		task.setContent(request.getParameter("content").trim());
		task.setResId(Long.parseLong(request.getParameter("resId")));
		task.setTargetIp(request.getParameter("targetIp").trim());
		task.setTargetPort(Integer.parseInt(request.getParameter("targetPort")));
		task.setRealtime(Boolean.parseBoolean(request.getParameter("isRealtime")));
		if (isNew == true) {
			// create a UUID.
			task.setId(createMSUTaskID(task.getRegion(), task.getOperation(), task.isRealtime()));
		} else {
			task.setId(request.getParameter("id").trim());
		}
		//
		return task;
	}

	public static SysResAuth getBuildSysResAuthByRequest(HttpServletRequest request, boolean isNew) throws Exception {
		SysResAuth resAuth = new SysResAuth();
		// if (isNew == false) {
		// resAuth.setId(Long.valueOf(request.getParameter("resAuthId").trim()));
		// }
		resAuth.setUsername(request.getParameter("username").trim());
		resAuth.setPassword(request.getParameter("password").trim());
		resAuth.setUserPrompt(request.getParameter("userPrompt").trim());
		resAuth.setPassPrompt(request.getParameter("passPrompt").trim());
		resAuth.setPrompt(request.getParameter("prompt").trim());
		resAuth.setExecPrompt(request.getParameter("execPrompt").trim());
		resAuth.setNextPrompt(request.getParameter("nextPrompt").trim());
		resAuth.setSepaWord(request.getParameter("sepaWord").trim());
		resAuth.setCommunity(request.getParameter("community").trim());
		resAuth.setSnmpv3User(request.getParameter("snmpv3User").trim());
		resAuth.setSnmpv3Auth(request.getParameter("snmpv3Auth").trim());
		resAuth.setSnmpv3Authpass(request.getParameter("snmpv3Authpass").trim());
		resAuth.setSnmpv3Priv(request.getParameter("snmpv3Priv").trim());
		resAuth.setSnmpv3Privpass(request.getParameter("snmpv3Privpass").trim());
		resAuth.setResId(Long.valueOf(request.getParameter("resId").trim()));

		//
		return resAuth;
	}

	private void publishMUSTaskToMSU(String publishTask, String opeationType) {
		if (publishTask == null || publishTask.length() == 0 || opeationType == null || opeationType.length() == 0) {
			return;
		}
		try {
			URI targetURI = new URI("http://localhost:8092//msu/manage");
			HashMap<String, String> headerParameterMap = new HashMap<String, String>();
			headerParameterMap.put(OPERATION_TYPE, opeationType);
			IClientResponseListener responseListener = new IClientResponseListener() {

				public void fireSucceed(Object messageObj) throws Exception {
					System.out.println(messageObj);
				}

				public void fireError(Object messageObj) throws Exception {
					System.out.println(messageObj);
				}

			};
			HttpClient httpClient = new HttpClient(targetURI, HttpMethod.POST, headerParameterMap, publishTask, responseListener);
			httpClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * create a SN or ID for task , UUID
	 * 
	 * @param region
	 * @param operation
	 * @param isRealTime
	 * @param createAt
	 * @return
	 * @throws Exception
	 */
	public final static String createMSUTaskID(String region, String operation, boolean isRealTime) throws Exception {
		if (region == null || region.length() == 0) {
			throw new Exception("invalid parameter region.");
		}
		if (operation == null || operation.length() == 0) {
			throw new Exception("invalid parameter operation.");
		}
		String regionShort = region;
		if (region.length() > 6) {
			regionShort = region.substring(0, 6);
		}
		String operationShort = operation;
		if (region.length() > 8) {
			operationShort = operation.substring(0, 8);
		}
		String sn = UUID.randomUUID().toString();
		String sortSN = sn.substring(0, 8) + sn.substring(9, 13) + sn.substring(14, 18) + sn.substring(19, 23) + sn.substring(24);
		// region(6)-isRealTime(1)-operation(8)-UUID(32) size=6+1+8+32+3<=50;
		return regionShort.toUpperCase() + "-" + (isRealTime ? 1 : 0) + "-" + operationShort.toUpperCase() + "-" + sortSN.toUpperCase();
	}

	public String formatMSUTask(MSUTask task) {
		JSONObject messageObj = new JSONObject();
		if (task != null) {
			try {
				messageObj.put(ID_TITLE, task.getId());
				messageObj.put(REGION_TITLE, task.getRegion());
				messageObj.put(OPERATION_TITLE, task.getOperation());
				messageObj.put(CREATE_AT_TITLE, task.getCreateAt());
				messageObj.put(SCHEDULE_TITLE, task.getSchedule());
				messageObj.put(CONTENT_TITLE, task.getContent());
				messageObj.put(META_DATA_TITLE, task.getMetaData());
				messageObj.put(RES_ID_TITLE, task.getResId());
				messageObj.put(IS_REALTIME_TITLE, task.isRealtime());
			} catch (JSONException e) {
				theLogger.exception(e);
			}

		}
		return messageObj.toString();
	}

	public String formatSysResAuth(SysResAuth resAuth) {
		JSONObject messageObj = new JSONObject();
		if (resAuth != null) {
			try {
				//
				if (resAuth.getUsername() != null && resAuth.getUsername().length() > 0) {
					messageObj.put("username", resAuth.getUsername());
				}
				//
				if (resAuth.getPassword() != null && resAuth.getPassword().length() > 0) {
					messageObj.put("password", resAuth.getPassword());
				}
				if (resAuth.getUserPrompt() != null && resAuth.getUserPrompt().length() > 0) {
					messageObj.put("userPrompt", resAuth.getUserPrompt());
				}
				if (resAuth.getPassPrompt() != null && resAuth.getPassPrompt().length() > 0) {
					messageObj.put("passPrompt", resAuth.getPassPrompt());
				}
				if (resAuth.getPrompt() != null && resAuth.getPrompt().length() > 0) {
					messageObj.put("prompt", resAuth.getPrompt());
				}
				if (resAuth.getExecPrompt() != null && resAuth.getExecPrompt().length() > 0) {
					messageObj.put("execPrompt", resAuth.getExecPrompt());
				}
				if (resAuth.getNextPrompt() != null && resAuth.getNextPrompt().length() > 0) {
					messageObj.put("nextPrompt", resAuth.getNextPrompt());
				}
				if (resAuth.getSepaWord() != null && resAuth.getSepaWord().length() > 0) {
					messageObj.put("sepaWord", resAuth.getSepaWord());
				}
				if (resAuth.getCommunity() != null && resAuth.getCommunity().length() > 0) {
					messageObj.put("community", resAuth.getCommunity());
				}
				if (resAuth.getSnmpv3User() != null && resAuth.getSnmpv3User().length() > 0) {
					messageObj.put("snmpv3User", resAuth.getSnmpv3User());
				}
				if (resAuth.getSnmpv3Auth() != null && resAuth.getSnmpv3Auth().length() > 0) {
					messageObj.put("snmpv3Auth", resAuth.getSnmpv3Auth());
				}
				if (resAuth.getSnmpv3Authpass() != null && resAuth.getSnmpv3Authpass().length() > 0) {
					messageObj.put("snmpv3Authpass", resAuth.getSnmpv3Authpass());
				}
				if (resAuth.getSnmpv3Priv() != null && resAuth.getSnmpv3Priv().length() > 0) {
					messageObj.put("snmpv3Priv", resAuth.getSnmpv3Priv());
				}
				if (resAuth.getSnmpv3Privpass() != null && resAuth.getSnmpv3Privpass().length() > 0) {
					messageObj.put("snmpv3Privpass", resAuth.getSnmpv3Privpass());
				}

			} catch (JSONException e) {
				theLogger.exception(e);
			}

		}
		return messageObj.toString();
	}
}
