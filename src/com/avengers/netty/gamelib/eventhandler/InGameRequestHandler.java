/**
 * 
 */
package com.avengers.netty.gamelib.eventhandler;

import com.avengers.netty.core.extensions.BaseClientRequestHandler;
import com.avengers.netty.core.util.Tracer;
import com.avengers.netty.gamelib.GameController;
import com.avengers.netty.gamelib.GameExtension;
import com.avengers.netty.socket.gate.IMessage;
import com.avengers.netty.socket.gate.wood.Message;
import com.avengers.netty.socket.gate.wood.User;

/**
 * Class xử lý tất cả các request khi user đang trong bàn chơi Game
 * 
 * @author LamHa
 *
 */
public class InGameRequestHandler extends BaseClientRequestHandler {

	@Override
	protected void init() {

	}

	@Override
	public void handleClientRequest(User user, IMessage message) {
		Tracer.info(InGameRequestHandler.class, "[INFO] handleClientRequest",
				"- ProcessMessage: Id=" + message.getCommandId() + " ----Data=" + message.toString());

		GameExtension gameExt = (GameExtension) getParentExtension();
		GameController gameController = gameExt.gameController;
		Tracer.info(InGameRequestHandler.class, "[INFO] handleClientRequest",
				"- ProcessMessage: RoomId:" + gameController.getRoom().getId());

		gameController.onPlayMove(user, (Message) message);
	}
}
