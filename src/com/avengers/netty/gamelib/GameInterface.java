package com.avengers.netty.gamelib;

import java.util.List;

import com.avengers.netty.core.om.IRoom;
import com.avengers.netty.gamelib.result.IPlayMoveResult;
import com.avengers.netty.gamelib.result.StartGameResult;
import com.avengers.netty.socket.gate.IMessage;
import com.avengers.netty.socket.gate.wood.Message;
import com.avengers.netty.socket.gate.wood.User;
import com.google.gson.JsonObject;

/**
 * FLOW: CORE -> GAME<br>
 * Class controller của GameLogic.<br>
 * Một Bàn cờ ứng với một GameExtension.<br>
 * Từ GameExtesion sẽ gọi các action của bàn cờ qua GameInterface<br>
 * 
 * @author LamHa
 *
 */
public interface GameInterface {

	/**
	 * Phát sự kiện cho game logic
	 * 
	 * @param commandId
	 * @param user
	 * @param message
	 */
	public void dispatchEvent(short commandId, User user, Message message);

	/**
	 * Xử lý start game
	 * 
	 * @param owner
	 *            Chủ bàn
	 * @param listPlayer
	 *            Danh sách người chơi
	 * @param extraData
	 *            Các Extra Data từ Client gửi lên tùy theo từng game muốn sử
	 *            dụng
	 */
	public StartGameResult onStartGame(User owner, List<User> listPlayer, Message extraData);

	/**
	 * Xử lý các nước đi trong game
	 * 
	 * @param sender
	 *            player thực hiện move
	 * @param data
	 *            dữ liệu thực hiện một nước move
	 */
	public IPlayMoveResult onPlayMoveHandle(User sender, Message data);

	/**
	 * Lấy dữ liệu GameData cho người xem. Đối tượng trả về tùy thuộc vào game
	 * tương ứng. Có thể là Json String, Map, hoặc List
	 */
	public Object getGameDataForViewer();

	/**
	 * Set người được quyền chia bài
	 * 
	 * @param userId
	 *            id của user có quyền chia bài
	 */
	public void setDealer(long userId);

	/**
	 * Set GameAPI cho game
	 * 
	 * @param api
	 */
	public void setApi(GameAPI api);

	/**
	 * Lấy thông tin gchoi7trong bàn đang chơi PlayerList - DataInGame....
	 * 
	 * @return
	 */
	public JsonObject getGameData();

	/**
	 * User rời phòng game
	 * 
	 * @param user
	 * @param room
	 */
	public void leaveRoom(User user, IRoom room);

	// FIXME nên throw exception
	public boolean joinRoom(User user, IRoom room);

	public void disconnect(User user);

	/**
	 * Xử lý khi hết thời gian của 1 lượt đi
	 */
	public IPlayMoveResult timeout();

	public void startCountdownStartGame(long timeOut);

	public void test(User user, IMessage message);

}
