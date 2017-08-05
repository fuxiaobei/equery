package com.asiainfo.billing.drquery.client.distributed;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class DistributedLock{
	
	public static final Log LOG = LogFactory.getLog(DistributedLock.class);
	
	private String myNode;
	private String lockName;
	private ZooKeeper zk;
	private String root;
	boolean locked = false;
	
	
	public DistributedLock(ZooKeeper zk, String root, String lockName){
		this.zk = zk;
		this.root = root;
		this.lockName = lockName;
		createRootNode(root);
	}
	
	
	public void createRootNode(String root) {
		try{
			Stat stat = zk.exists("/" + root, false);
			if(stat == null){
				// 创建根节点
				zk.create("/" + root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT); 
				
			}
		} catch(KeeperException e) {
			throw new ZookeeperException(e);
		} catch(InterruptedException e) {
			throw new ZookeeperException(e);
		}
		
	}
	

	public boolean tryLock() {
		try{
			if(myNode == null) {
				myNode = zk.create("/" + root + "/" + lockName, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
				LOG.debug("create znode: " + myNode);
			}
			List<String> nodes = zk.getChildren("/" + root, false);
			if(nodes.size() == 1) {
				locked = true;
				return true;
			}

			Collections.sort(nodes);
			String firstNode = nodes.get(0);
			
			if(("/" + root + "/" + firstNode).equals(myNode)) {
				locked = true;
				return true;
			}
			
		} catch(KeeperException e) {
			throw new ZookeeperException(e);
		} catch(InterruptedException e) {
			throw new ZookeeperException(e);
		}
		return false;
	}
	

	public void unlock() {
		try {
			if(locked) {
				locked = false;
				zk.delete(myNode, -1);
				LOG.debug("delete znode: " + myNode);
				myNode = null;
			}
		} catch(KeeperException e) {
			throw new ZookeeperException(e);
		} catch(InterruptedException e) {
			throw new ZookeeperException(e);
		}
	}
	
	
	public String getNodeName() {
		return myNode;
	}
	
}

