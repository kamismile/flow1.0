package com.shziyuan.flow.global.domain.flow;

import java.sql.Timestamp;


//渠道信息
public class InfoDistributor extends BaseDomain{
	private static final long serialVersionUID = -499268092800153304L;

	//渠道id
    private int id;
    //是否启用
    private boolean enabled;
    //名称
    private String name;
    //渠道代码
    private String code;
    //联系信息 公司名
    private String info_company_name;
    //联系信息 联系人
    private String info_contact;
    //联系信息 电话
    private String info_tel;
    //联系信息 邮件
    private String info_email;
    //创建时间
    private Timestamp create_time;
    //更新时间
    private Timestamp update_time;
    //更新用户
    private String update_user;
    //状态报告推送地址
    private String report_url;
    //是否启用主动查询接口
    private boolean need_actively;
        
    //key
    private String key;
    private int verify;
    private int from_cityid;
    private int from_channel;
    private String source;

    
	public int getVerify() {
		return verify;
	}

	public void setVerify(int verify) {
		this.verify = verify;
	}

	public int getFrom_cityid() {
		return from_cityid;
	}

	public void setFrom_cityid(int from_cityid) {
		this.from_cityid = from_cityid;
	}

	public int getFrom_channel() {
		return from_channel;
	}

	public void setFrom_channel(int from_channel) {
		this.from_channel = from_channel;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo_company_name() {
		return info_company_name;
	}

	public void setInfo_company_name(String info_company_name) {
		this.info_company_name = info_company_name;
	}

	public String getInfo_contact() {
		return info_contact;
	}

	public void setInfo_contact(String info_contact) {
		this.info_contact = info_contact;
	}

	public String getInfo_tel() {
		return info_tel;
	}

	public void setInfo_tel(String info_tel) {
		this.info_tel = info_tel;
	}

	public String getInfo_email() {
		return info_email;
	}

	public void setInfo_email(String info_email) {
		this.info_email = info_email;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

	public Timestamp getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}

	public String getReport_url() {
		return report_url;
	}

	public void setReport_url(String report_url) {
		this.report_url = report_url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isNeed_actively() {
		return need_actively;
	}

	public void setNeed_actively(boolean need_actively) {
		this.need_actively = need_actively;
	}
    
    @Override
    public String showname() {
    	return name;
    }
}


