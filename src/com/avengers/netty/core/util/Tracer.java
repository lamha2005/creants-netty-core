package com.avengers.netty.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lamhm
 *
 */
public class Tracer {
	private static final Logger MESSAGE_LOG = LoggerFactory.getLogger("CoreLogger");
	private static final Logger ERROR_LOG = LoggerFactory.getLogger("ErrorLogger");
	private static final Logger GAME_LIB_LOG = LoggerFactory.getLogger("GamelibLogger");
	private static final Logger PLUTO_GAME_LOG = LoggerFactory.getLogger("PlutoGameLogger");
	private static final Logger ROOM_LOG = LoggerFactory.getLogger("RoomLogger");

	/**
	 * Log thông tin lỗi
	 * 
	 * @param clazz
	 *            class nào xảy ra lỗi
	 * @param msgs
	 *            thông tin kèm theo lỗi - nên kèm theo tên hàm
	 */
	public static void error(Class<?> clazz, Object... msgs) {
		ERROR_LOG.error(getTraceMessage(clazz, msgs));
	}

	public static void warn(Class<?> clazz, Object... msgs) {
		MESSAGE_LOG.warn(getTraceMessage(clazz, msgs));
	}

	/**
	 * log tất cả thông tin nào liên quan gameLib
	 * 
	 * @param clazz
	 *            class đang xử lý
	 * @param msgs
	 *            thông tin kèm theo - nên kèm theo tên hàm xử lý
	 */
	public static void debugGameLib(Class<?> clazz, Object... msgs) {
		if (GAME_LIB_LOG.isDebugEnabled()) {
			GAME_LIB_LOG.debug(getTraceMessage(clazz, msgs));
		}
	}

	public static void debugRoom(Class<?> clazz, Object... msgs) {
		if (ROOM_LOG.isDebugEnabled()) {
			ROOM_LOG.debug(getTraceMessage(clazz, msgs));
		}
	}

	public static void errorRoom(Class<?> clazz, Object... msgs) {
		ROOM_LOG.error(getTraceMessage(clazz, msgs));
	}

	/**
	 * log tất cả thông tin nào liên core
	 * 
	 * @param clazz
	 *            class đang xử lý
	 * @param msgs
	 *            thông tin kèm theo - nên kèm theo tên hàm xử lý
	 */
	public static void debug(Class<?> clazz, Object... msgs) {
		if (MESSAGE_LOG.isDebugEnabled()) {
			MESSAGE_LOG.debug(getTraceMessage(clazz, msgs));
		}
	}

	public static void info(Class<?> clazz, Object... msgs) {
		MESSAGE_LOG.info(getTraceMessage(clazz, msgs));
	}

	/**
	 * log mau binh
	 * 
	 * @param clazz
	 *            class xử lý
	 * @param msgs
	 *            thông tin kèm theo - nên kèm theo têm hàm xử lý
	 */
	public static void debugPlutoGame(Class<?> clazz, Object... msgs) {
		if (PLUTO_GAME_LOG.isDebugEnabled()) {
			PLUTO_GAME_LOG.debug(getTraceMessage(clazz, msgs));
		}
	}

	private static String getTraceMessage(Class<?> clazz, Object[] msgs) {
		StringBuilder traceMsg = new StringBuilder().append("{").append(clazz.getSimpleName()).append("}: ");
		Object[] arrayOfObject;
		int j = (arrayOfObject = msgs).length;
		for (int i = 0; i < j; i++) {
			traceMsg.append(arrayOfObject[i].toString()).append(" ");
		}

		return traceMsg.toString();
	}

}
