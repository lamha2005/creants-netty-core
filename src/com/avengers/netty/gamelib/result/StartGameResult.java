/**
 * 
 */
package com.avengers.netty.gamelib.result;

import java.util.List;

import com.avengers.netty.gamelib.GameController;

/**
 * Xử lý kết quả khi StartGame
 * 
 * @author LamHa
 *
 */
public class StartGameResult implements IPlayMoveResult {
	private boolean isSuccess;
	private short errorCode;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public short getErrorString() {
		return errorCode;
	}

	public void setErrorString(short errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Start game ERROR with CODE
	 */
	public StartGameResult(short errorCode) {
		this.isSuccess = false;
		this.errorCode = errorCode;
	}

	/**
	 * Start game SUCCESS
	 */
	public StartGameResult() {
		this.isSuccess = true;
	}

	@Override
	public void handleResult(GameController gameController) {
		if (!this.isSuccess) {
			gameController.notifyStartMatchError(this.errorCode);
		}
	}

	@Override
	public void addChildResult(IPlayMoveResult result) {

	}

	@Override
	public List<IPlayMoveResult> getChildrenResult() {
		return null;
	}
}
