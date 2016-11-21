/**
 * 
 */
package com.avengers.netty.gamelib.eventhandler;

import com.avengers.netty.core.event.CoreEventParam;
import com.avengers.netty.core.event.ICoreEvent;
import com.avengers.netty.core.extensions.BaseServerEventHandler;
import com.avengers.netty.core.om.IRoom;
import com.avengers.netty.gamelib.GameExtension;
import com.avengers.netty.gamelib.service.CacheService;
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
		IRoom lastRoomByUser = CacheService.getInstace().getLastRoomByUser(user.getCreantUserId());
		if (lastRoomByUser != null && lastRoomByUser.isGame()) {
			GameExtension gameExtension = (GameExtension) lastRoomByUser.getExtension();
			gameExtension.gameController.getGameInterface().joinRoom(user, lastRoomByUser);
		}
	}

}
