package com.yufei.infoExtractor.pfw;

import com.yufei.dataget.entity.ProxyServer;
import com.yufei.infoExtractor.entity.Seedsite;
import com.yufei.infoExtractor.entity.Task;
import com.yufei.infoExtractor.entity.TaskFingerprint;
import java.io.File;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.yufei.pfw.entity.Entity;
import com.yufei.pfw.service.MongodbInterface;
import java.util.regex.Pattern;

public interface InfoExtractorDao extends MongodbInterface{
public  void removeEntity(Entity entity);
public void addTask(Task task);
public List<Seedsite> getSeedSiteByStatus(final Integer status);
public List<Seedsite> getSeedSiteByTaskName(final String taskName);
public void updateSdStatus(final Integer status,final Long seedsiteid); 
public List<Task> getTasks();
public List<Task> getTaskByStatus(final Integer status);
//设置爬虫任务相关时间
public void setTaskTime(final Long taskid,Timestamp startedtime,Timestamp endTime);
//public List<Regularex> getRegularexBySeedsiteId(final String seedsiteid);
//获取某个种子网址已经抓取的链接的指纹
public Set<String> getLinkFingerprints(String taskName,Class entity);
public void addPattern(Pattern pattern);
public List<Pattern> getPatternByTaskName(String taskName);
public void setTaskEndTime(Date date,Long taskId);
public void setTaskStartedTime(Date date,Long taskId);
public String saveMediaFile(File file);
public File queryMediaFileById(String mediaFileId);
public void generateXmlForTaskCrawlerResult(Class cla);
/**used to save or update the object(if it has saved in database)
 * @param links
 */
public void saveEntity(Entity entity);
public Entity saveEntityWithReturnValue(Entity entity);

/**
 * @param entitys
 * 调用此方法之前请确保entitys不为空，否则会抛出异常
 */
public void batchInsertEntity(Collection<Entity> entitys);
/**
 * @param links
 */
void batchInsertLinks(Set links);
public List queryAllValuesOfField(String fieldName,Class entity,Map queryMap);
public <T> List<T> queryListEntity(Map queryMap,Class<T> cla);

/**
 * @param proxyServer
 * save or update the proxyServer
 */
public void saveProxyServer(ProxyServer proxyServer);
public ProxyServer getProxyServerByIdentify(String proxyIdentify);

public  <T> T queryEntity(String queryParameter,Object queryValue,Class<T> cla);
void updateTaskStatus(Integer status, Long id);

    public TaskFingerprint getTaskFingerprintByTaskName(String taskName);

    public void saveTaskFingerprint(TaskFingerprint taskFingerprint);

}
