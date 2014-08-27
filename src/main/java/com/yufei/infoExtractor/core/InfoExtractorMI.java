package com.yufei.infoExtractor.core;




import com.yufei.dataget.entity.ProxyServer;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.yufei.infoExtractor.entity.Task;
import com.yufei.pfw.entity.Entity;

/**
 * @author zhaoyufei zhao-0244@qq.com
 *系统管理以及辅助使用接口，对系统的一些业务进行配置和修改以及提供一些方便的工具供相关业务人员使用本系统,
 * created at 2012-9-2 上午11:33:44
 */
public interface InfoExtractorMI {
	public void exportEntityToXml(String calssName,String path);
	public void exportTaskToXml(String taskName,String path);
	public List<Task> createTask(String taskFilesPath);
	/**
	 * @param inputStream
	 * 根据配置文件的输入流来对数据库中的task信息进行更新
	 * @throws JAXBException 
	 */
	public void updateTask(InputStream  inputStream ) throws JAXBException;
	 
    /**根据查询条件返回查询实体信息
     * @return
     * @throws ClassNotFoundException 
     */
    public List<? extends Entity > queryEntity(String collectionName) throws ClassNotFoundException;
    /**统计某个集合的总数
     * @param collectionName
     * @return
     */
    public long count(String collectionName);
    /**获取所有类全名
     * @return
     */
    public List<String> getAllEntityFullClassName();
    
    /**通过系统内置的网页访问工具来获取网页源码
     * @param url
     * @param isRequireJS TODO
     * @return
     * @throws IOException
     */
    public String getHtmlContentByDataRetriver(String url, Boolean isRequireJS) throws IOException;
    /**用于测试系统中的正则表达式正确与否
     * @param htmlContent
     * @param regex
     * @return
     */
    public List<String> matchStrByRegex(String htmlContent, String regex);
    public void saveObject(Entity object);
    public void deleteObject(Entity entity);
  
    //修改baidusearch.xml文件然后重新导入
    //启动数据库中的任务（所有）
    
    
	/* (non-Javadoc)
	 * @see com.yufei.infoExtractor.core.InfoExtractorMI#startTask()
	 * just to used by demo
	 */
    public void startTask(String keyWords);
    public void dropTable(String tableName);
    public List<Task> getTasksByStatus(Integer status);
	/**
	 * @param taskName
	 * @param keyWords：多个查询关键字，每个element表示一个查询关键字组合，里面可以包含多个关键字之间以空格隔开
	 * @return 
	 */
	Task createTask(String taskName, List<String> keyWords);
	void updateTaskStatus(Integer taskStatus, String taskid);
	 Task queryEntityByPropertyValue(String queryParameter,Object queryValue,Class cla);
	
	public void updateBaiduSearchConfig(String pages);

	/**
	 * @param proxyServer
	 * update or save the proxyServer
	 */
	public void saveProxyServer(ProxyServer proxyServer);

    public ProxyServer getProxyServer(String proxyServerIdentity);
    /**
     * @param taskName
     * 如果任务完成之后，相关的采集结果已经导出，那么要及时清楚此任务在mongodb中保留的相关数据防止相关内容浪费磁盘空间
     */
    public void cleanDataBaseRelatedWithTask(String taskName);
}
