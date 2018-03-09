package com.shziyuan.flow.oauth2.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.util.Assert;

import com.shziyuan.flow.global.domain.ldap.LDAPAccount;
import com.shziyuan.flow.global.util.EncryptUtil;
import com.shziyuan.flow.oauth2.conf.LDAPProperties;

public class LDAPNativeService implements LDAPService {
	
	private static final String[] DEFAULT_USER_RET_ATTS = {"uid","cn","olcDisallows","mobile","mail","userPassword"};		// 默认获取ldap参数名
	private static final String[] DEFAULT_ROLE_ATTS = {"cn"};		// 权限参数名 / 权限由ldap组实现,每个特殊ldap组代表一个role,一个组包含N个用户
	
	private LDAPProperties ldapProperties;		// ldap参数
	
	private Hashtable<String, String> env;		// LDAP环境参数
	
	/**
	 * 初始化LDAP环境参数
	 */
	public LDAPNativeService(LDAPProperties properties) {
		this.ldapProperties = properties;
		
		env = new Hashtable<>();
	    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");	// LDAP环境上下文支持工厂
	    env.put(Context.PROVIDER_URL, properties.getServer());			
	    env.put(Context.SECURITY_AUTHENTICATION, "simple");								// 验证方式

	    env.put(Context.SECURITY_PRINCIPAL, properties.getBinder());					
        env.put(Context.SECURITY_CREDENTIALS, properties.getPassword());
	}
	
	/**
	 * 使用管理账户连接LDAP服务器
	 * @return
	 * @throws NamingException
	 */
	public DirContext connectServer() throws LDAPConnectionException {
		try {
			return new InitialDirContext(env);
		} catch (NamingException e) {
			throw new LDAPConnectionException("连接LDAP服务器失败", e);
		}
	}
	
	/**
	 * 构建search查询
	 * @param ctx
	 * @param filter
	 * @param param
	 * @param returnAttributes
	 * @param baseSearchDN
	 * @return
	 * @throws NamingException
	 */
	private NamingEnumeration<SearchResult> buildSearch(DirContext ctx,String filter,String[] param, String[] returnAttributes,String baseSearchDN) throws NamingException {
        SearchControls ctls = new SearchControls();
        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        ctls.setReturningAttributes(returnAttributes);
        ctls.setReturningObjFlag(true);
        return ctx.search(baseSearchDN, filter, param, ctls);
	}
	
	/**
	 * 根据返回构建ldapaccount
	 * @param searchResult
	 * @return
	 * @throws NamingException
	 */
	private LDAPAccount buildAccount(SearchResult searchResult) throws NamingException {
		LDAPAccount account = new LDAPAccount();
        
        
		Attributes atts = searchResult.getAttributes();
		Attribute tmp = atts.get("uid");
		Assert.notNull(tmp,"uid mast not null");
		account.setUid(tmp.get().toString());
        tmp = atts.get("mail");
        account.setEmail(tmp != null ? tmp.get().toString() : "");
        tmp = atts.get("mobile");
        account.setPhone(tmp != null ? tmp.get().toString() : "");
        tmp = atts.get("olcDisallows");
        account.setEnabled(tmp != null ? !tmp.get().toString().equalsIgnoreCase("true") : true);
        tmp = atts.get("userPassword");
        if(tmp != null) {
        	byte[] bytes=(byte[])tmp.get(0);
            account.setPassword(new String(bytes));
        } else
        	account.setPassword("");
        tmp = atts.get("role");
        account.setRole(tmp != null ? tmp.get().toString() : "");
        
        return account;
	}
	
	/* (non-Javadoc)
	 * @see com.shziyuan.flow.ldap.service.LDAPService#authentication(java.lang.String, java.lang.String)
	 */
	@Override
	public LDAPAccount authentication(String username,String password) throws LDAPAuthenticationException{
		// Step 1: Bind anonymously            
        DirContext ctx = connectServer();
        
		try {
	        NamingEnumeration<SearchResult> enm = buildSearch(
	        		ctx, "(&(uid={0}))", new String[]{username}, DEFAULT_USER_RET_ATTS,ldapProperties.getUserBaseDN());

	        String dn = null;
   
	        LDAPAccount account = null;
	        
	        if (enm.hasMore()) {
	            SearchResult result = enm.next();
	            dn = result.getNameInNamespace();
	            if (dn == null) {
		            // uid not found or not unique
		            throw new LDAPAuthenticationException("账户不存在");
		        }

	            account = buildAccount(result);
	        } else {
	        	throw new LDAPAuthenticationException("账户不存在");
	        }

	        // Step 3: Bind with found DN and given password
	        ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, dn);
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);
	        // Perform a lookup in order to force a bind operation with JNDI
	        ctx.lookup(dn);
	        
	        enm.close();
	        
	        return account;
	    } catch (NamingException e) {
	        throw new LDAPAuthenticationException("账户认证失败",e);
	    } finally {
	        try {
				ctx.close();
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.shziyuan.flow.ldap.service.LDAPService#getAccount(java.lang.String)
	 */
	@Override
	public LDAPAccount getAccount(String username) throws LDAPAuthenticationException{
		// Step 1: Bind anonymously            
        DirContext ctx = connectServer();
        
		try {
	        NamingEnumeration<SearchResult> enm = buildSearch(
	        		ctx, "(&(uid={0}))", new String[]{username}, DEFAULT_USER_RET_ATTS,ldapProperties.getUserBaseDN());

	        String dn = null;
   
	        LDAPAccount account = null;

	        if (enm.hasMore()) {
	            SearchResult result = enm.next();
	            dn = result.getNameInNamespace();

	            if (dn == null) {
		            // uid not found or not unique
		            throw new LDAPAuthenticationException("账户不存在");
		        }

	            account = buildAccount(result);
	        } else {
	        	throw new LDAPAuthenticationException("账户不存在");
	        }

	        enm.close();
	        
	        return account;
	    } catch (NamingException e) {
	        throw new LDAPAuthenticationException("账户认证失败",e);
	    } finally {
	        try {
				ctx.close();
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.shziyuan.flow.ldap.service.LDAPService#getRole(java.lang.String)
	 */
	public List<String> getRole(String username) throws LDAPAuthenticationException{
		// Step 1: Bind anonymously            
        DirContext ctx = connectServer();
        
		try {
	        NamingEnumeration<SearchResult> enm = buildSearch(
	        		ctx, "(&(memberUid={0}))", new String[]{username}, DEFAULT_ROLE_ATTS,ldapProperties.getAuthGroupBaseDN());

	        String dn = null;
   
	        List<String> roles = new ArrayList<>();

	        while (enm.hasMore()) {
	            SearchResult result = enm.next();
	            dn = result.getNameInNamespace();

	            if (dn == null) {
		            // uid not found or not unique
		            throw new LDAPAuthenticationException("账户不存在");
		        }

	            Attributes atts = result.getAttributes();
	    		Attribute tmp = atts.get("cn");
	    		roles.add(tmp.get().toString());
	        }
	        
	        enm.close();
	        
	        return roles;
	    } catch (NamingException e) {
	        throw new LDAPAuthenticationException("账户认证失败",e);
	    } finally {
	        try {
				ctx.close();
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.shziyuan.flow.ldap.service.LDAPService#listUser()
	 */
	@Override
	public List<LDAPAccount> listUser() throws LDAPAuthenticationException {
		return listUser("objectClass=inetOrgPerson");
	}
	/* (non-Javadoc)
	 * @see com.shziyuan.flow.ldap.service.LDAPService#listUser(java.lang.String)
	 */
	@Override
	public List<LDAPAccount> listUser(String search) throws LDAPAuthenticationException{
		// Step 1: Bind anonymously            
        DirContext ctx = connectServer();
        
		try {
	        NamingEnumeration<SearchResult> enm = buildSearch(ctx, "(&(" + search + "))", null, DEFAULT_USER_RET_ATTS,ldapProperties.getUserBaseDN());
   
	        List<LDAPAccount> list = new ArrayList<>();
	        while(enm.hasMore()) {
	        	SearchResult result = enm.next();
	        	LDAPAccount account = buildAccount(result);
	        	list.add(account);
	        }
	        enm.close();
	        return list;
	    } catch (NamingException e) {
	        throw new LDAPAuthenticationException("LDAP获取数据异常",e);
	    } finally {
	        try {
				ctx.close();
			} catch (NamingException e) {
			}
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.shziyuan.flow.ldap.service.LDAPService#updatePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void updatePassword(String username,String newPassword) {
		// Step 1: Bind anonymously            
        DirContext ctx = connectServer();
        
		try {            
			ModificationItem[] mods = new ModificationItem[1];
			String encryptPassword = EncryptUtil.sha1(newPassword);
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					new BasicAttribute("userPassword", encryptPassword));

			ctx.modifyAttributes("uid="+username+",ou=dc=shziyuan,dc=com", mods);
	    } catch (NamingException e) {
	        throw new LDAPAuthenticationException("修改密码失败",e);
	    } finally {
	        try {
				ctx.close();
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
	    }
	}
	
	/* (non-Javadoc)
	 * @see com.shziyuan.flow.ldap.service.LDAPService#getLDAPData(java.lang.String)
	 */
	@Override
	public Map<String,Object> getLDAPData(String dn) throws LDAPAuthenticationException{
		// Step 1: Bind anonymously            
        DirContext ctx = connectServer();
        
		try {            
	        // Step 2: Search the directory
	        String filter = "(objectclass=*)";           
	        SearchControls ctls = new SearchControls();
	        ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//	        String[] ats = {"uid","cn","mobile","mail"};
	        ctls.setReturningAttributes(null);
	        ctls.setReturningObjFlag(true);
	        NamingEnumeration<SearchResult> enm = ctx.search(dn, filter, ctls);
   	        
	        if (enm.hasMore()) {
	            SearchResult result = enm.next();
	            
	            if (result.getNameInNamespace() == null) {
		            throw new LDAPAuthenticationException("DN目标不存在");
		        }

	            Attributes atts = result.getAttributes();
	            
	            Map<String,Object> data = new HashMap<>();
	            NamingEnumeration<? extends Attribute> ne = atts.getAll();
	            while(ne.hasMore()) {
	            	Attribute at = ne.next();
	            	data.put(at.getID(), at.get());
	            }
	            ne.close();
	            return data;
	        }

	        enm.close();
	        
	        return null;
	    } catch (NamingException e) {
	        throw new LDAPAuthenticationException("DN目标异常",e);
	    } finally {
	        try {
				ctx.close();
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
	    }
	}
	
}
