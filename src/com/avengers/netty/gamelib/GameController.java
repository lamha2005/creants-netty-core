/**
 * 
 */
package com.avengers.netty.gamelib;

import com.avengers.netty.core.log.BoardLog;
import com.avengers.netty.core.om.IRoom;
import com.avengers.netty.gamelib.om.PlayerIdGenerator;
import com.avengers.netty.gamelib.result.IPlayMoveResult;
import com.avengers.netty.gamelib.result.StartGameResult;
import com.avengers.netty.gamelib.scheduler.ITimeoutListener;
import com.avengers.netty.socket.gate.wood.Message;
import com.avengers.netty.socket.gate.wood.User;
import com.google.gson.JsonObject;

/**
 * Đối tượng điều phối workflow khi user chơi game. <br>
 * Mỗi room sẽ có một GameController tương ứng
 * 
 * @author LamHa
 *
 */
public class GameController implements ITimeoutListener {

	public static enum STATUS {
		WAIT_USER_READY, WAIT_START_REQUEST, GAME_STARTING, GAME_PLAYING;
	}

	private final IRoom room;
	private final GameInterface iGame;
	private final GameAPI gameApi;
	private STATUS status;
	private final PlayerIdGenerator playerIdGenerator;
	private BoardLog boardLog;

	public GameController(IRoom room, GameInterface gamgeInterface) {
		this.room = room;
		iGame = gamgeInterface;
		gameApi = new GameAPI(this);
		iGame.setApi(gameApi);
		status = STATUS.WAIT_USER_READY;
		playerIdGenerator = new PlayerIdGenerator(room.getMaxUsers());

		// Đăng ký trận đấu với timeout scheduler
		// (hiện tại chỉ có kiểu đếm ngược đến timeout)
		// TimeoutScheduler timeoutScheduler = new
		// TimeoutScheduler(TimeoutScheduler.generateId(room));
		// timeoutScheduler.registerListener(this);
		// SchedulerManager.getInstance().registerScheduler(timeoutScheduler);
	}

	public IRoom getRoom() {
		return room;
	}

	public GameAPI getApi() {
		return gameApi;
	}

	public GameInterface getGameInterface() {
		return iGame;
	}

	public STATUS getStatus() {
		return status;
	}

	public void dispatchEvent(short commandId, User user, Message message) {
		iGame.dispatchEvent(commandId, user, message);
	}

	public void onPlayMove(User sender, Message data) {
		IPlayMoveResult result = iGame.onPlayMoveHandle(sender, data);
		if (result != null) {
			// Xử lý kết quả sau khi thực hiện 1 nước đi trong game
			result.handleResult(this);
		}
	}

	public JsonObject getGameData() {
		return iGame.getGameData();
	}

	public void onStartGame(User sender, Message message) {
		status = STATUS.GAME_STARTING;

		StartGameResult result = iGame.onStartGame(room.getOwner(), room.getPlayersList(), message);
		if (result != null) {
			result.handleResult(this);

			if (result.isSuccess()) {
				status = STATUS.GAME_PLAYING;
			}
		}
	}

	public void startCountdownStartGame(long timeOut) {
		iGame.startCountdownStartGame(timeOut);
	}

	/**
	 * Xử lý khi hết thời gian của 1 lượt đi
	 */
	public void timeout() {
		IPlayMoveResult result = iGame.timeout();
		if (result != null) {
			// Xử lý kết quả sau khi thực hiện 1 nước đi trong game
			result.handleResult(this);
		}
	}

	public void leaveRoom(User user, IRoom room) {
		iGame.leaveRoom(user, room);
	}

	/**
	 * Gửi thông báo cho Client - Ván đấu không thể Bắt đầu
	 * 
	 * @param errorCode
	 */
	public void notifyStartMatchError(int errorCode) {

	}

	public BoardLog getBoardLog() {
		return boardLog;
	}

	public PlayerIdGenerator getPlayerIdGenerator() {
		return playerIdGenerator;
	}

}
