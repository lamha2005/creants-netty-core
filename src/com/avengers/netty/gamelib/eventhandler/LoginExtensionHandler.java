/**
 * 
 */
package com.avengers.netty.gamelib.eventhandler;

import com.avengers.netty.core.event.CoreEventParam;
import com.avengers.netty.core.event.ICoreEvent;
import com.avengers.netty.core.event.SystemNetworkConstant;
import com.avengers.netty.core.extensions.BaseServerEventHandler;
import com.avengers.netty.core.om.IRoom;
import com.avengers.netty.core.util.CoreTracer;
import com.avengers.netty.core.util.DefaultMessageFactory;
import com.avengers.netty.gamelib.GameExtension;
import com.avengers.netty.gamelib.service.CacheService;
import com.avengers.netty.socket.gate.wood.Message;
import com.avengers.netty.socket.gate.wood.User;

/**
 * @author LamHa
 *
 */
public class LoginExtensionHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ICoreEvent event) {
		// CoreExtension parentExtension = getParentExtension();
		User user = (User) event.getParameter(CoreEventParam.USER);
		Message response = DefaultMessageFactory.createMessage(SystemNetworkConstant.COMMAND_USER_LOGIN);
		response.putInt(SystemNetworkConstant.KEYI_USER_ID, user.getCreantUserId());
		response.putString(SystemNetworkConstant.KEYS_USERNAME, user.getUserName());
		response.putLong(SystemNetworkConstant.KEYL_MONEY, user.getMoney());
		response.putString(SystemNetworkConstant.KEYS_AVATAR, user.getAvatar());
		send(response, user);
		IRoom lastRoomByUser = CacheService.getInstace().getLastRoomByUser(user.getCreantUserId());
		if (lastRoomByUser != null && lastRoomByUser.isGame() && lastRoomByUser.getPlayerSize() > 1) {
			User disconnectUser = lastRoomByUser.getUserByName(user.getName());
			user.setUserId(disconnectUser.getUserId());
			user.setLastJoinedRoom(lastRoomByUser);
			CoreTracer.debug(this.getClass(), "Reconnect " + user.getName());
			GameExtension gameExtension = (GameExtension) lastRoomByUser.getExtension();
			gameExtension.gameController.getGameInterface().joinRoom(user, lastRoomByUser);
		}
	}

}
