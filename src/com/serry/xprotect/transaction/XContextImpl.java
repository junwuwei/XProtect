package com.serry.xprotect.transaction;

import java.util.ArrayList;
import java.util.List;

import com.serry.xprotect.XHook;
import com.serry.xprotect.XPrivacy;

import android.content.pm.PackageManager;
import android.util.Log;

public class XContextImpl extends XHook {
	private Methods mMethod;

	private XContextImpl(Methods method, String restrictionName) {
		super(restrictionName, method.name(), null);
		mMethod = method;
	}

	public String getClassName() {
		return "android.app.ContextImpl";
	}

	// @formatter:off

	// public PackageManager getPackageManager()
	// public Object getSystemService(String name)
	// frameworks/base/core/java/android/app/ContextImpl.java

	// @formatter:on

	private enum Methods {
		getPackageManager, getSystemService, checkCallingPermission
	};

	public static List<XHook> getInstances() {
		List<XHook> listHook = new ArrayList<XHook>();
		listHook.add(new XContextImpl(Methods.getPackageManager, null));
		listHook.add(new XContextImpl(Methods.getSystemService, null));
		// sai.pan begin
		listHook.add(new XContextImpl(Methods.checkCallingPermission, null));
		// sai.pan end
		return listHook;
	}

	@Override
	protected void before(XParam param) throws Throwable {
		// Do nothing
	}

	@Override
	protected void after(XParam param) throws Throwable {
		switch (mMethod) {
		case getPackageManager:
			if (param.getResult() != null)
				XPrivacy.handleGetSystemService("PackageManager", param.getResult().getClass().getName(), getSecret());
			break;

		case getSystemService:
			if (param.args.length > 0 && param.args[0] instanceof String && param.getResult() != null) {
				String name = (String) param.args[0];
				Object instance = param.getResult();
				XPrivacy.handleGetSystemService(name, instance.getClass().getName(), getSecret());
			}
			break;

		/*case checkCallingPermission:
			if (param.args.length > 0 && param.args[0] instanceof String && param.getResult() != null) {
				String name = (String) param.args[0];
				Object instance = param.getResult();
				Log.e("sai", "checkCallingPermission");
				if ("android.permission.FORCE_STOP_PACKAGES".equals(name)) {
					Log.e("sai", "checkCallingPermission PERMISSION_GRANTED");
					instance = PackageManager.PERMISSION_GRANTED;
				}
				// XPrivacy.handleGetSystemService(name,
				// instance.getClass().getName(), getSecret());
			}
			break;*/
		}
	}
}
