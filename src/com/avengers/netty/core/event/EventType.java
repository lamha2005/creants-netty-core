package com.avengers.netty.core.event;

/**
 * @author LamHa
 *
 */
public enum EventType {
	SERVER_READY, USER_LOGIN, USER_JOIN_ZONE, USER_LOGOUT, USER_JOIN_ROOM, USER_LEAVE_ROOM, USER_DISCONNECT, USER_RECONNECTION_TRY, USER_RECONNECTION_SUCCESS, ROOM_ADDED, ROOM_REMOVED, PUBLIC_MESSAGE, PRIVATE_MESSAGE, ROOM_VARIABLES_UPDATE, USER_VARIABLES_UPDATE, SPECTATOR_TO_PLAYER, PLAYER_TO_SPECTATOR, BUDDY_ADD, BUDDY_REMOVE, BUDDY_BLOCK, BUDDY_VARIABLES_UPDATE, BUDDY_ONLINE_STATE_UPDATE, BUDDY_MESSAGE, BUDDY_LIST_INIT, GAME_INVITATION_SUCCESS, GAME_INVITATION_FAILURE, FILE_UPLOAD, __TRACE_MESSAGE;
}