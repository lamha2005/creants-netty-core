/**
 * 
 */
package com.avengers.netty.gamelib.eventhandler;

import com.avengers.netty.core.event.ICoreEvent;
import com.avengers.netty.core.extensions.BaseServerEventHandler;
import com.avengers.netty.core.extensions.CoreExtension;

/**
 * @author LamHa
 *
 */
public class LoginExtensionHandler extends BaseServerEventHandler {

	@Override
	public void handleServerEvent(ICoreEvent event) {
		CoreExtension parentExtension = getParentExtension();
		// User user = (User) event.getParameter(CoreEventParam.USER);

	}

}
