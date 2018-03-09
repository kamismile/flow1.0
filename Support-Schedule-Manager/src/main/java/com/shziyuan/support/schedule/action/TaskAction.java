package com.shziyuan.support.schedule.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.jeasyui.JEasyuiData;
import com.shziyuan.support.schedule.service.ScheduleManager;

/**
 * 后端控制接口
 * @author james.hu
 *
 */
@RestController
@RequestMapping("/api")
public class TaskAction {
	// 业务控制器
	@Autowired
	private ScheduleManager scheduleManager;
	
	/**
	 * 列出当前有效任务
	 * @return
	 */
	@GetMapping("/schedule/task")
	@ResponseBody
	public JEasyuiData getAllTask() {
		return new JEasyuiData(scheduleManager.getGroovyTasks());
	}
	
	/**
	 * 查询当前所有计划信息
	 * @return
	 */
	@GetMapping("/schedule/info")
	@ResponseBody
	public JEasyuiData getAll() {
		return new JEasyuiData(scheduleManager.getAll());
	}
	
	/**
	 * 更新指定计划
	 * @param id
	 * @param name 名称
	 * @param cronStr cron字符串
	 * @param tags 标签 逗号分割
	 * @param override 是否完成后启动
	 * @return
	 */
	@PutMapping("/schedule/info/{id}")
	@ResponseBody
	public JEasyuiData updateTask(@PathVariable("id") int id,String name,String cronStr,String tags,boolean override) {
		return new JEasyuiData(scheduleManager.update(id, name, cronStr, tags,override));
	}
	
	/**
	 * 新建计划
	 * @param name 名称
	 * @param cronStr cron字符串
	 * @param taskClass 处理任务类名
	 * @param tags 标签 逗号分割
	 * @param override 是否完成后启动
	 * @return
	 */
	@PostMapping("/schedule/info/create")
	@ResponseBody
	public JEasyuiData insertTask(String name,String cronStr,String taskClass,String tags,boolean override) {
		return new JEasyuiData(scheduleManager.insert(name,cronStr,taskClass,tags,override));
	}
	
	/**
	 * 删除计划
	 * @param id
	 * @return
	 */
	@DeleteMapping("/schedule/info/{id}")
	@ResponseBody
	public JEasyuiData deleteTask(@PathVariable("id") int id) {
		return new JEasyuiData(scheduleManager.delete(id));
	}

	
	/**
	 * 启动指定计划
	 * @param id
	 * @param override 是否强制重启
	 * @return
	 */
	@PutMapping("/schedule/ctrl/{id}/start")
	@ResponseBody
	public JEasyuiData start(@PathVariable("id") int id,@RequestParam(name="override",defaultValue="false") boolean override) {
		return new JEasyuiData(scheduleManager.start(id, override));
	}
	
	/**
	 * 启动所有计划
	 * @param override 是否强制重启
	 * @return
	 */
	@PutMapping("/schedule/ctrl/all/start")
	@ResponseBody
	public JEasyuiData start(@RequestParam(name="override",defaultValue="false") boolean override) {
		scheduleManager.startAll(override);
		return new JEasyuiData(true);
	}
	
	/**
	 * 停止指定计划
	 * @param id
	 * @return
	 */
	@PutMapping("/schedule/ctrl/{id}/stop")
	@ResponseBody
	public JEasyuiData stop(@PathVariable("id") int id) {
		return new JEasyuiData(scheduleManager.stop(id));
	}
	
	/**
	 * 停止所有计划
	 * @return
	 */
	@PutMapping("/schedule/ctrl/all/stop")
	@ResponseBody
	public JEasyuiData stop() {
		scheduleManager.stopAll();
		return new JEasyuiData(true);
	}
}
