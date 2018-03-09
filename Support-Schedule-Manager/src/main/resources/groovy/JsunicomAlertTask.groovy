import com.shziyuan.support.schedule.service.task.GroovyTask;

public class JsunicomAlertTask extends GroovyTask{

	private String sql = "select order_no from `order` order by create_time desc limit 1";
	
	public JsunicomAlertTask() {
		super("jsReadJdbcTemplate");
	}

	@Override
	protected void doRun() {
		String order_no = jdbcTemplate.queryForObject(sql, String.class);
		System.out.println(order_no);
	}

}