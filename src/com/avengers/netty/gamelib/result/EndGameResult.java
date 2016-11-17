package com.avengers.netty.gamelib.result;

import java.util.ArrayList;
import java.util.List;

import com.avengers.netty.core.util.Tracer;
import com.avengers.netty.gamelib.GameController;
import com.avengers.netty.socket.gate.wood.Message;

/**
 * @author LamHa
 *
 */
public class EndGameResult implements IPlayMoveResult {

	// List kết quả của từng user
	private ArrayList<UserResult> userResults = new ArrayList<UserResult>();
	// Thông tin riêng của từng game
	private Message extraDataResult = new Message();

	@Override
	public void handleResult(GameController gameController) {
		// for (UserResult userResult : userResults) {
		// User user =
		// gameController.getRoom().getUserById(userResult.getUserId());
		// String desc = "Ket thuc game";
		// Reporter.debug("update user: " + userResult.getUserId());
		// }

		Tracer.info(EndGameResult.class, "-----> END_GAME: " + extraDataResult.toString());
		gameController.getApi().sendAllInRoom(extraDataResult);
		gameController.startCountdownStartGame(5000);
	}

	@Override
	public void addChildResult(IPlayMoveResult result) {
		throw new Error("Could not add child, because this is not ListPlayMoveResult");
	}

	@Override
	public List<IPlayMoveResult> getChildrenResult() {
		return null;
	}

	public void addUserResult(UserResult userResult) {
		userResults.add(userResult);
	}

	public Message getExtraDataResult() {
		return extraDataResult;
	}

	public void setExtraDataResult(Message extraDataResult) {
		this.extraDataResult = extraDataResult;
	}

}