package com.secpro.platform.monitoring.manage.actions;

import java.net.URI;
import java.util.HashMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;

import com.secpro.platform.monitoring.manage.dao.TaskScheduleActionDao1;
import com.secpro.platform.monitoring.manage.entity.MsuTask;
import com.secpro.platform.monitoring.manage.entity.SysResAuth;
import com.secpro.platform.monitoring.manage.services.TaskScheduleService;
import com.secpro.platform.monitoring.manage.util.httpclient.HttpClient;
import com.secpro.platform.monitoring.manage.util.httpclient.IClientResponseListener;
import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

@Controller("TaskScheduleAction")
public class TaskScheduleAction {
	/**
	 * 
	 */
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

	PlatformLogger logger = PlatformLogger.getLogger(TaskScheduleAction.class);
	private String returnMsg;
	private String backUrl;
	private MsuTask msuTask;
	private TaskScheduleService taskScheduleService;

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public MsuTask getMsuTask() {
		return msuTask;
	}

	public void setMsuTask(MsuTask msuTask) {
		this.msuTask = msuTask;
	}

	public TaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	@Resource(name = "TaskScheduleServiceImpl")
	public void setTaskScheduleService(TaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	//
	public String create() throws Exception {
		System.out.println(">>>>>>>>>>>TaskScheduleAction.create");
		System.out.println(">>>>>>>>>>>TaskScheduleAction.taskScheduleService:" + taskScheduleService.getSessionFactory());
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			MsuTask task = getBuildMSUTaskByRequest(request, true);
			if (task == null) {
				request.setAttribute("exception", new Exception("Build MSUTask instance Exception. invalid bean."));
				return "error";
			}
			//
			taskScheduleService.save(task);
			//
			request.setAttribute("resourceBean", task);
			String callBackUrl = request.getParameter("callBackUrl");
			request.setAttribute("previousURL", callBackUrl);
			request.setAttribute("WEB_MSG", "创建成功！");
			// publish the task change into MSU
			System.out.println(formatMSUTask(task));
			publishMUSTaskToMSU(formatMSUTask(task), "TOPIC-TASK-ADD");
		} catch (Exception e) {
			request.setAttribute("exception", e);
			e.printStackTrace();
			return "error";
		}

		return "success";
	}

	public String update() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String callBackUrl = request.getParameter("callBackUrl");
		try {
			MsuTask task = getBuildMSUTaskByRequest(request, false);
			if (task == null) {
				request.setAttribute("exception", new Exception("Build MSUTask instance Exception. invalid bean."));
				return "error";
			}
			//
			TaskScheduleActionDao1.update(task);
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

	public String remove() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String callBackUrl = request.getParameter("callBackUrl");
		try {
			String id = request.getParameter("id");
			if (id == null || id.trim().length() == 0) {
				throw new Exception("invalid id");
			}
			// exist
			MsuTask task = TaskScheduleActionDao1.findById(id);
			if (task == null) {
				throw new Exception("doesn't exist instance ," + id);
			}
			TaskScheduleActionDao1.delete(task);
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
	private MsuTask getBuildMSUTaskByRequest(HttpServletRequest request, boolean isNew) throws Exception {
		MsuTask task = new MsuTask();
		//
		task.setRegion(request.getParameter("region"));
		task.setOperation(request.getParameter("operation"));
		task.setSchedule(request.getParameter("schedule"));
		task.setCreateAt(System.currentTimeMillis());
		// build the meateData from the ResAuth.
		String metaDataValue = formatSysResAuth(getBuildSysResAuthByRequest(request, isNew));
		task.setMetaData(metaDataValue);
		//
		task.setContent(request.getParameter("content").trim());
		task.setResId(Long.parseLong(request.getParameter("resId")));
		task.setTargetIp(request.getParameter("targetIp").trim());
		task.setTargetPort(Integer.parseInt(request.getParameter("targetPort")));
		task.setIsRealtime(Boolean.parseBoolean(request.getParameter("isRealtime")));
		if (isNew == true) {
			// create a UUID.
			task.setId(createMSUTaskID(task.getRegion(), task.getOperation(), task.getIsRealtime()));
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
		resAuth.setUsername(request.getParameter("username"));
		resAuth.setPassword(request.getParameter("password"));
		resAuth.setUserPrompt(request.getParameter("userPrompt"));
		resAuth.setPassPrompt(request.getParameter("passPrompt"));
		resAuth.setPrompt(request.getParameter("prompt"));
		resAuth.setExecPrompt(request.getParameter("execPrompt"));
		resAuth.setNextPrompt(request.getParameter("nextPrompt"));
		resAuth.setSepaWord(request.getParameter("sepaWord"));
		resAuth.setCommunity(request.getParameter("community"));
		resAuth.setSnmpv3User(request.getParameter("snmpv3User"));
		resAuth.setSnmpv3Auth(request.getParameter("snmpv3Auth"));
		resAuth.setSnmpv3Authpass(request.getParameter("snmpv3Authpass"));
		resAuth.setSnmpv3Priv(request.getParameter("snmpv3Priv"));
		resAuth.setSnmpv3Privpass(request.getParameter("snmpv3Privpass"));
		resAuth.setResId(Long.valueOf(request.getParameter("resId")));

		//
		return resAuth;
	}

	private void publishMUSTaskToMSU(String publishTask, String opeationType) {
		if (publishTask == null || publishTask.length() == 0 || opeationType == null || opeationType.length() == 0) {
			return;
		}
		try {
			URI targetURI = new URI("http://localhost:8092/msu/manage");
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
			// String
			// testContent={"isrt":false,"mda":"{\"username\":\"baiyanwei\"}","con":"ls","cat":1386066691274,"rid":1,"reg":"0311","ope":"ssh","sch":"0 10 * * * ?","tid":"0311-0-SSH-513B4E8CFEB2418895DD972C54580467"};
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

	public String formatMSUTask(MsuTask task) {
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
				messageObj.put(IS_REALTIME_TITLE, task.getIsRealtime());
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

	public static void main(String[] args) {
		try {
			URI targetURI = new URI("http://localhost:8092/msu/manage");
			HashMap<String, String> headerParameterMap = new HashMap<String, String>();
			headerParameterMap.put(OPERATION_TYPE, "TOPIC-TASK-ADD");
			IClientResponseListener responseListener = new IClientResponseListener() {

				public void fireSucceed(Object messageObj) throws Exception {
					System.out.println(messageObj);
				}

				public void fireError(Object messageObj) throws Exception {
					System.out.println(messageObj);
				}

			};
			String publishTask="{\"isrt\":false,\"mda\":\"{\"username\":\"baiyanwei\"}\",\"con\":\"ls\",\"cat\":1386066691274,\"rid\":1,\"reg\":\"0311\",\"ope\":\"ssh\",\"sch\":\"0 10 * * * ?\",\"tid\":\"0311-0-SSH-513B4E8CFEB2418895DD972C54580467\"}";
			HttpClient httpClient = new HttpClient(targetURI, HttpMethod.POST, headerParameterMap, publishTask, responseListener);
			httpClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
