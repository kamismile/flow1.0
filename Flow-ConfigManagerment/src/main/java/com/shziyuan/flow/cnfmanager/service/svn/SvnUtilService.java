package com.shziyuan.flow.cnfmanager.service.svn;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.springframework.core.io.Resource;
import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.shziyuan.flow.global.util.LoggerUtil;

public class SvnUtilService {
	
	    private Logger logger = LoggerUtil.sys;
	    
	    private String svnRoot;
	    private String username;
	    private String password;
	    
	    private Resource resWorkspace;
	    private File docRootWorkspace;

	    public SvnUtilService(String svnRoot, String username, String password) {
			super();
			this.svnRoot = svnRoot;
			this.username = username;
			this.password = password;
		}
	    
	    private SVNURL svnRootUrl;
	    private SVNClientManager clientManager;
	    
	    /** 
	     * 通过不同的协议初始化版本库 
	     */  
	    private void setupLibrary() {  
	        DAVRepositoryFactory.setup();		// HTTP协议代理的实现  
//	        SVNRepositoryFactoryImpl.setup();  	// svn协议代理的实现
//	        FSRepositoryFactory.setup();  		// file文件代理的实现
	    }

		/** 
	     * 验证登录svn 
	     */  
	    public void authSvn() {
	    	if(clientManager == null) {
		        // 初始化版本库  
		        setupLibrary();  
		  
		        // 创建库连接  
		        SVNRepository repository = null;
		        try {
		        	svnRootUrl = SVNURL.parseURIEncoded(svnRoot);
		            repository = SVNRepositoryFactory.create(svnRootUrl);  
		        } catch (SVNException e) {  
		            logger.error(e.getMessage(),e);    
		        }  
		  
		        // 身份验证  
		        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password.toCharArray());
		        // 创建身份验证管理器  
		        repository.setAuthenticationManager(authManager);  
		  
		        DefaultSVNOptions options = SVNWCUtil.createDefaultOptions(true);  
		        SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);  
		        this.clientManager = clientManager;
	    	}
	    }
	    	      
	    /** 
	     * Make directory in svn repository 
	     * @param clientManager 
	     * @param url  
	     *          eg: http://svn.ambow.com/wlpt/bsp/trunk  
	     * @param commitMessage 
	     * @return 
	     * @throws SVNException 
	     */  
	    public SVNCommitInfo makeDirectory(SVNClientManager clientManager,  
	            SVNURL url, String commitMessage) {  
	        try {  
	            return clientManager.getCommitClient().doMkDir(  
	                    new SVNURL[] { url }, commitMessage);  
	        } catch (SVNException e) {  
	            logger.error(e.getMessage(), e);  
	        }  
	        return null;  
	    }  
	      
	    /** 
	     * Imports an unversioned directory into a repository location denoted by a 
	     *  destination URL 
	     * @param clientManager 
	     * @param localPath 
	     *          a local unversioned directory or singal file that will be imported into a  
	     *          repository; 
	     * @param dstURL 
	     *          a repository location where the local unversioned directory/file will be  
	     *          imported into 
	     * @param commitMessage 
	     * @param isRecursive 递归 
	     * @return 
	     */  
	    public SVNCommitInfo importDirectory(SVNClientManager clientManager,  
	            File localPath, SVNURL dstURL, String commitMessage,  
	            boolean isRecursive) {  
	        try {  
	            return clientManager.getCommitClient().doImport(localPath, dstURL,  
	                    commitMessage, null, true, true,  
	                    SVNDepth.fromRecurse(isRecursive));  
	        } catch (SVNException e) {  
	            logger.error(e.getMessage(), e);  
	        }  
	        return null;  
	    }  
	      
	    /** 
	     * Puts directories and files under version control 
	     * @param clientManager 
	     *          SVNClientManager 
	     * @param wcPath  
	     *          work copy path 
	     */  
	    public void addEntry(SVNClientManager clientManager, File wcPath) {  
	        try {  
	            clientManager.getWCClient().doAdd(new File[] { wcPath }, true,  
	                    false, false, SVNDepth.INFINITY, false, false,  
	                    true);  
	        } catch (SVNException e) {  
	            logger.error(e.getMessage(), e);  
	        }  
	    }  
	         
	    /** 
	     * Collects status information on a single Working Copy item 
	     * @param clientManager 
	     * @param wcPath 
	     *          local item's path 
	     * @param remote 
	     *          true to check up the status of the item in the repository,  
	     *          that will tell if the local item is out-of-date (like '-u' option in the SVN client's  
	     *          'svn status' command), otherwise false 
	     * @return 
	     * @throws SVNException 
	     */  
	    public SVNStatus showStatus(SVNClientManager clientManager,  File wcPath, boolean remote) {  
	        SVNStatus status = null;  
	        try {  
	            status = clientManager.getStatusClient().doStatus(wcPath, remote);  
	        } catch (SVNException e) {  
	            logger.error(e.getMessage(), e);  
	        }  
	        return status;  
	    }  
	      
	    /** 
	     * Commit work copy's change to svn 
	     * @param clientManager 
	     * @param wcPath  
	     *          working copy paths which changes are to be committed 
	     * @param keepLocks 
	     *          whether to unlock or not files in the repository 
	     * @param commitMessage 
	     *          commit log message 
	     * @return 
	     * @throws SVNException 
	     */  
	    public SVNCommitInfo commit(SVNClientManager clientManager,  
	            File wcPath, boolean keepLocks, String commitMessage) {  
	        try {  
	            return clientManager.getCommitClient().doCommit(  
	                    new File[] { wcPath }, keepLocks, commitMessage, null,  
	                    null, false, false, SVNDepth.INFINITY);  
	        } catch (SVNException e) {  
	            logger.error(e.getMessage(), e);  
	        }  
	        return null;  
	    }  
	      
	    /** 
	     * Updates a working copy (brings changes from the repository into the working copy). 
	     * @param clientManager 
	     * @param wcPath 
	     *          working copy path 
	     * @param updateToRevision 
	     *          revision to update to 
	     * @param depth 
	     *          update的深度：目录、子目录、文件 
	     * @return 
	     * @throws SVNException 
	     */  
	    public long update(SVNRevision updateToRevision, SVNDepth depth) {  
	        SVNUpdateClient updateClient = clientManager.getUpdateClient();  
	  
	        /* 
	         * sets externals not to be ignored during the update 
	         */  
	        updateClient.setIgnoreExternals(false);  
	  
	        /* 
	         * returns the number of the revision wcPath was updated to 
	         */  
	        try {  
	            return updateClient.doUpdate(docRootWorkspace, updateToRevision,depth, false, false);  
	        } catch (SVNException e) {  
	            logger.error(e.getMessage(), e);  
	        }  
	        return 0;  
	    }  
	      
	    /** 
	     * recursively checks out a working copy from url into wcDir 
	     * @param clientManager 
	     * @param url 
	     *          a repository location from where a Working Copy will be checked out 
	     * @param revision 
	     *          检出版本号 @see SVNRevision.HEAD
	     * @param destPath 
	     *          替换本地工作路径
	     * @param depth 
	     *          checkout的深度，目录、子目录、文件  @see SVNDepth.INFINITY
	     * @param allowUnversionedObstructions
	     * 			是否允许没有版本的障碍物，true的话允许，false不允许，false在checkout的时候如果有障碍物就会停止检出，所以一般是true
	     * @return 
	     * @throws SVNException 
	     */  
	    public long checkout(SVNURL url,  SVNRevision revision, File destPath, SVNDepth depth,boolean allowUnversionedObstructions) {  
	  
	        SVNUpdateClient updateClient = clientManager.getUpdateClient();
	        /* 
	         * sets externals not to be ignored during the checkout 
	         */  
	        updateClient.setIgnoreExternals(false);  
	        /* 
	         * returns the number of the revision at which the working copy is 
	         */  
	        try {  
	            return updateClient.doCheckout(url, destPath, revision, revision,depth, allowUnversionedObstructions);  
	        } catch (SVNException e) {  
	            logger.error(e.getMessage(), e);  
	        }  
	        return 0;  
	    }  
	      
	    /** 
	     * 确定path是否是一个工作空间 
	     * @param path 
	     * @return 
	     */  
	    public boolean isWorkingCopy(File path){  
	        if(!path.exists()){  
	            logger.warn("'" + path + "' not exist!");  
	            return false;  
	        }  
	        try {  
	            if(null == SVNWCUtil.getWorkingCopyRoot(path, false)){  
	                return false;  
	            }  
	        } catch (SVNException e) {  
	            logger.error(e.getMessage(), e);  
	        }  
	        return true;  
	    }  
	      
	    /** 
	     * 确定一个URL在SVN上是否存在 
	     * @param url 
	     * @return 
	     */  
	    public boolean isURLExist(SVNURL url,String username,String password){  
	        try {  
	            SVNRepository svnRepository = SVNRepositoryFactory.create(url);  
	            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password.toCharArray());  
	            svnRepository.setAuthenticationManager(authManager);  
	            SVNNodeKind nodeKind = svnRepository.checkPath("", -1);  
	            return nodeKind == SVNNodeKind.NONE ? false : true;   
	        } catch (SVNException e) {  
	            e.printStackTrace();  
	        }  
	        return false;  
	    }

		public String getSvnRoot() {
			return svnRoot;
		}

		public void setSvnRoot(String svnRoot) {
			this.svnRoot = svnRoot;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public SVNURL getSvnRootUrl() {
			return svnRootUrl;
		}

		public Resource getResWorkspace() {
			return resWorkspace;
		}

		public void setResWorkspace(Resource resWorkspace) {
			this.resWorkspace = resWorkspace;
			try {
				this.docRootWorkspace = resWorkspace.getFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public File getDocRootWorkspace() {
			return docRootWorkspace;
		}
		
		public void setDocRootWorkspace(File docRootWorkspace) {
			this.docRootWorkspace = docRootWorkspace;
		}
	  
	
}
