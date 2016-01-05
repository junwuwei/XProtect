package com.serry.xprotect;

import com.serry.xprotect.transaction.PrivacyManager;
import com.serry.xprotect.transaction.XParam;
import com.serry.xprotect.utils.Util;

import android.os.Binder;
import android.util.Log;

public abstract class XHook {
	private String mRestrictionName;
	private String mMethodName;
	private String mSpecifier;
	private String mSecret;

	protected XHook(String restrictionName, String methodName, String specifier) {
		mRestrictionName = restrictionName;
		mMethodName = methodName;
		mSpecifier = specifier;
	}

	abstract public String getClassName();

	public boolean isVisible() {
		return true;
	}

	public String getRestrictionName() {
		return mRestrictionName;
	}

	public String getMethodName() {
		return mMethodName;
	}

	public String getSpecifier() {
		return (mSpecifier == null ? mMethodName : mSpecifier);
	}

	public void setSecret(String secret) {
		mSecret = secret;
	}

	protected String getSecret() {
		return mSecret;
	}

	abstract protected void before(XParam param) throws Throwable;

	abstract protected void after(XParam param) throws Throwable;

	protected boolean isRestricted(XParam param) throws Throwable {
		return isRestricted(param, getSpecifier());
	}

	protected boolean isRestrictedExtra(XParam param, String extra) throws Throwable {
		int uid = Binder.getCallingUid();
		Util.log(null, Log.ERROR, "sai XHook isRestrictedExtra(XParam param, String extra)");
		return PrivacyManager.getRestrictionExtra(this, uid, mRestrictionName, getSpecifier(), extra, mSecret);
	}

	protected boolean isRestrictedExtra(XParam param, String methodName, String extra) throws Throwable {
		int uid = Binder.getCallingUid();
		Util.log(null, Log.ERROR, "sai XHook isRestrictedExtra(XParam param, String methodName, String extra");
		return PrivacyManager.getRestrictionExtra(this, uid, mRestrictionName, methodName, extra, mSecret);
	}

	protected boolean isRestrictedExtra(XParam param, String restrictionName, String methodName, String extra)
			throws Throwable {
		int uid = Binder.getCallingUid();
		Util.log(null, Log.ERROR, "sai XHook isRestrictedExtra(XParam param, String restrictionName, String methodName, String extra)");
		return PrivacyManager.getRestrictionExtra(this, uid, restrictionName, methodName, extra, mSecret);
	}

	protected boolean isRestrictedExtra(int uid, String restrictionName, String methodName, String extra)
			throws Throwable {
		Util.log(null, Log.ERROR, "sai XHook isRestrictedExtra(int uid, String restrictionName, String methodName, String extra)");
		return PrivacyManager.getRestrictionExtra(this, uid, restrictionName, methodName, extra, mSecret);
	}

	protected boolean isRestricted(XParam param, String methodName) throws Throwable {
		int uid = Binder.getCallingUid();
		return PrivacyManager.getRestriction(this, uid, mRestrictionName, methodName, mSecret);
	}

	protected boolean isRestricted(XParam param, String restrictionName, String methodName) throws Throwable {
		int uid = Binder.getCallingUid();
		return PrivacyManager.getRestriction(this, uid, restrictionName, methodName, mSecret);
	}

	protected boolean getRestricted(int uid) throws Throwable {
		return PrivacyManager.getRestriction(this, uid, mRestrictionName, getSpecifier(), mSecret);
	}

	protected boolean getRestricted(int uid, String methodName) throws Throwable {
		return PrivacyManager.getRestriction(this, uid, mRestrictionName, methodName, mSecret);
	}

	protected boolean getRestricted(int uid, String restrictionName, String methodName) throws Throwable {
		return PrivacyManager.getRestriction(this, uid, restrictionName, methodName, mSecret);
	}

	@Override
	public String toString() {
		return getRestrictionName() + "/" + getSpecifier() + " (" + getClassName() + ")";
	}
}
