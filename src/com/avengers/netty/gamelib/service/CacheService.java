package com.avengers.netty.gamelib.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.avengers.netty.core.om.Room;

/**
 * @author LamHa
 *
 */
public class CacheService {
	private static CacheService instance;
	private Map<Integer, Room> userInRoomMap;

	public static CacheService getInstace() {
		if (instance == null)
			instance = new CacheService();
		return instance;
	}

	private CacheService() {
		userInRoomMap = new ConcurrentHashMap<Integer, Room>();
	}

	public Room getLastRoomByUser(int creantUserId) {
		return userInRoomMap.get(creantUserId);
	}

	public void joinRoom(int creantUserId, Room room) {
		userInRoomMap.put(creantUserId, room);
	}

	public void freeLastRoom(int creantUserId) {
		userInRoomMap.remove(creantUserId);
	}
}
