package com.asiainfo.billing.drquery.client.distributed;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

/**
 * 基于zookeeper分布式共享锁
 * 
 * DistributedClient client = new DistributedClient("ubuntu:2185", "locks", "seq_", 60 * 1000);
 * try{
 *     if(client.tryLock()) {
 *     		client.getData("/DRQuery/cmod/lastProcessTime");
 *     		//do something
 *     		
 *     		client.setDataForPersistentZNode("/DRQuery/cmod/lastProcessTime", "20131216152030");
 *     }
 * } finally{
 *     client.unlock();
 * }
 * 
 * @author wangkai8
 *
 */
public class DistributedClient {
	
	private ZooKeeper zk;
	private DistributedLock lock;
	private CountDownLatch latch = new CountDownLatch(1); 
	
	public DistributedClient(String hosts, String root, String lockName, int session_timeout) throws IOException{
		connect(hosts, session_timeout);
		lock = new DistributedLock(zk, root, lockName);
	}
	
	public void connect(String hosts, int session_timeout) throws IOException {
		
		zk = new ZooKeeper(hosts, session_timeout, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				if(event.getState() == KeeperState.SyncConnected) {
					latch.countDown();
				}
			} 
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public DistributedLock getLock() {
		return this.lock;
	}

	
	public boolean tryLock() {
		return lock.tryLock();
	}
	
	
	public void unlock() {
		lock.unlock();
	}
	
	
	public void setDataForPersistentZNode(String path, byte[] data) {
		try{
			if(zk.exists(path, false) == null) {
				ensurePathExists(path);
			}
			zk.setData(path, data, -1);
		} catch(KeeperException e) {
			throw new ZookeeperException(e);
		} catch(InterruptedException e) {
			throw new ZookeeperException(e);
		}
	}
	
	
	public byte[] getData(String path){
		try{
			return zk.getData(path, false, null);
		} catch(KeeperException e) {
			throw new ZookeeperException(e);
		} catch(InterruptedException e) {
			throw new ZookeeperException(e);
		}
	}
	
	
	public void ensurePathExists(String path) {
		try{
			String[] paths = path.split("/");
			String temp = "";
			for(int i = 1; i < paths.length; i++) {
				temp += "/" + paths[i];
				if(zk.exists(temp, false) == null) {
					zk.create(temp, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
				}
			}
		} catch(KeeperException e) {
			throw new ZookeeperException(e);
		} catch(InterruptedException e) {
			throw new ZookeeperException(e);
		}
	}
	
	
	public ZooKeeper getZK(){
		return this.zk;
	}
	
	
	public boolean exist(String path) {
		try {
			return zk.exists(path, false) == null ? false : true;
		} catch (KeeperException e) {
			throw new ZookeeperException(e);
		} catch (InterruptedException e) {
			throw new ZookeeperException(e);
		}
	}
	

	/**
	 * TEST
	 * @param args
	 * @throws Exception
	 */
	public  static void main(String[] args) throws Exception {
		String hosts = "ocdata08,ocdata09:2485";
		String root = "lock";
		String lockName = "seq_";
		DistributedClient client = new DistributedClient(hosts, root, lockName, 60*1000);
		client.setDataForPersistentZNode("/DRQuery/cmod/processTime", "TEST".getBytes());
		System.out.println(new String(client.getData("/DRQuery/cmod/processTime")));
		client.tryLock();
		client.unlock();
	}
	
	
}