package com.serry.xprotect.ui;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.serry.xprotect.R;
import com.serry.xprotect.R.attr;
import com.serry.xprotect.R.id;
import com.serry.xprotect.R.layout;
import com.serry.xprotect.R.string;
import com.serry.xprotect.R.style;
import com.serry.xprotect.service.UpdateService;
import com.serry.xprotect.transaction.ApplicationInfoEx;
import com.serry.xprotect.transaction.PrivacyManager;
import com.serry.xprotect.utils.Util;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsDialog {

	public static void edit(final ActivityBase context, ApplicationInfoEx appInfo) {
		final int userId = Util.getUserId(Process.myUid());
		final int uid = (appInfo == null ? userId : appInfo.getUid());

		// Build dialog
		String themeName = PrivacyManager.getSetting(userId, PrivacyManager.cSettingTheme, "");
		int themeId = (themeName.equals("Dark") ? R.style.CustomTheme_Dialog : R.style.CustomTheme_Light_Dialog);
		final Dialog dlgSettings = new Dialog(context, themeId);
		dlgSettings.requestWindowFeature(Window.FEATURE_LEFT_ICON);
		dlgSettings.setTitle(R.string.menu_settings);
		dlgSettings.setContentView(R.layout.settings);
		dlgSettings.setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, context.getThemed(R.attr.icon_launcher));

		// Reference controls
		TextView tvAppName = (TextView) dlgSettings.findViewById(R.id.tvAppName);
		View vwAppNameBorder = (View) dlgSettings.findViewById(R.id.vwAppNameBorder);

		final CheckBox cbNotify = (CheckBox) dlgSettings.findViewById(R.id.cbNotify);
		final CheckBox cbOnDemand = (CheckBox) dlgSettings.findViewById(R.id.cbOnDemand);
		final CheckBox cbBlacklist = (CheckBox) dlgSettings.findViewById(R.id.cbBlacklist);
		final CheckBox cbUsage = (CheckBox) dlgSettings.findViewById(R.id.cbUsage);
		final CheckBox cbParameters = (CheckBox) dlgSettings.findViewById(R.id.cbParameters);
		final CheckBox cbLog = (CheckBox) dlgSettings.findViewById(R.id.cbLog);

		final CheckBox cbExpert = (CheckBox) dlgSettings.findViewById(R.id.cbExpert);
		final CheckBox cbSystem = (CheckBox) dlgSettings.findViewById(R.id.cbSystem);
		final CheckBox cbExperimental = (CheckBox) dlgSettings.findViewById(R.id.cbExperimental);
		final CheckBox cbHttps = (CheckBox) dlgSettings.findViewById(R.id.cbHttps);
		final CheckBox cbAOSP = (CheckBox) dlgSettings.findViewById(R.id.cbAOSP);
		final LinearLayout llConfidence = (LinearLayout) dlgSettings.findViewById(R.id.llConfidence);
		final EditText etConfidence = (EditText) dlgSettings.findViewById(R.id.etConfidence);
		final EditText etQuirks = (EditText) dlgSettings.findViewById(R.id.etQuirks);
		final Button btnClearDb = (Button) dlgSettings.findViewById(R.id.btnClearDb);

		final CheckBox cbRandom = (CheckBox) dlgSettings.findViewById(R.id.cbRandom);
		final Button btnRandom = (Button) dlgSettings.findViewById(R.id.btnRandom);
		final Button btnClear = (Button) dlgSettings.findViewById(R.id.btnClear);
		final Button btnFlush = (Button) dlgSettings.findViewById(R.id.btnFlush);

		final EditText etSerial = (EditText) dlgSettings.findViewById(R.id.etSerial);
		final EditText etLat = (EditText) dlgSettings.findViewById(R.id.etLat);
		final EditText etLon = (EditText) dlgSettings.findViewById(R.id.etLon);
		final EditText etAlt = (EditText) dlgSettings.findViewById(R.id.etAlt);
		final EditText etSearch = (EditText) dlgSettings.findViewById(R.id.etSearch);
		final Button btnSearch = (Button) dlgSettings.findViewById(R.id.btnSearch);
		final EditText etMac = (EditText) dlgSettings.findViewById(R.id.etMac);
		final EditText etIP = (EditText) dlgSettings.findViewById(R.id.etIP);
		final EditText etImei = (EditText) dlgSettings.findViewById(R.id.etImei);
		final EditText etPhone = (EditText) dlgSettings.findViewById(R.id.etPhone);
		final EditText etId = (EditText) dlgSettings.findViewById(R.id.etId);
		final EditText etGsfId = (EditText) dlgSettings.findViewById(R.id.etGsfId);
		final EditText etAdId = (EditText) dlgSettings.findViewById(R.id.etAdId);
		final EditText etMcc = (EditText) dlgSettings.findViewById(R.id.etMcc);
		final EditText etMnc = (EditText) dlgSettings.findViewById(R.id.etMnc);
		final EditText etCountry = (EditText) dlgSettings.findViewById(R.id.etCountry);
		final EditText etOperator = (EditText) dlgSettings.findViewById(R.id.etOperator);
		final EditText etIccId = (EditText) dlgSettings.findViewById(R.id.etIccId);
		final EditText etCid = (EditText) dlgSettings.findViewById(R.id.etCid);
		final EditText etLac = (EditText) dlgSettings.findViewById(R.id.etLac);
		final EditText etSubscriber = (EditText) dlgSettings.findViewById(R.id.etSubscriber);
		final EditText etSSID = (EditText) dlgSettings.findViewById(R.id.etSSID);
		final EditText etUa = (EditText) dlgSettings.findViewById(R.id.etUa);

		final CheckBox cbSerial = (CheckBox) dlgSettings.findViewById(R.id.cbSerial);
		final CheckBox cbLat = (CheckBox) dlgSettings.findViewById(R.id.cbLat);
		final CheckBox cbLon = (CheckBox) dlgSettings.findViewById(R.id.cbLon);
		final CheckBox cbAlt = (CheckBox) dlgSettings.findViewById(R.id.cbAlt);
		final CheckBox cbMac = (CheckBox) dlgSettings.findViewById(R.id.cbMac);
		final CheckBox cbImei = (CheckBox) dlgSettings.findViewById(R.id.cbImei);
		final CheckBox cbPhone = (CheckBox) dlgSettings.findViewById(R.id.cbPhone);
		final CheckBox cbId = (CheckBox) dlgSettings.findViewById(R.id.cbId);
		final CheckBox cbGsfId = (CheckBox) dlgSettings.findViewById(R.id.cbGsfId);
		final CheckBox cbAdId = (CheckBox) dlgSettings.findViewById(R.id.cbAdId);
		final CheckBox cbCountry = (CheckBox) dlgSettings.findViewById(R.id.cbCountry);
		final CheckBox cbSubscriber = (CheckBox) dlgSettings.findViewById(R.id.cbSubscriber);
		final CheckBox cbSSID = (CheckBox) dlgSettings.findViewById(R.id.cbSSID);

		Button btnOk = (Button) dlgSettings.findViewById(R.id.btnOk);
		Button btnCancel = (Button) dlgSettings.findViewById(R.id.btnCancel);

		final EditText[] edits = new EditText[] { etSerial, etLat, etLon, etAlt, etMac, etIP, etImei, etPhone, etId,
				etGsfId, etAdId, etMcc, etMnc, etCountry, etOperator, etIccId, etCid, etLac, etSubscriber, etSSID, etUa };

		final CheckBox[] boxes = new CheckBox[] { cbSerial, cbLat, cbLon, cbAlt, cbMac, cbImei, cbPhone, cbId, cbGsfId,
				cbAdId, cbCountry, cbSubscriber, cbSSID };

		// Listen for changes
		cbExpert.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				cbSystem.setEnabled(isChecked);
				cbExperimental.setEnabled(isChecked);
				cbHttps.setEnabled(isChecked);
				cbAOSP.setEnabled(isChecked);
				etConfidence.setEnabled(isChecked);
				etQuirks.setEnabled(isChecked);
				btnClearDb.setEnabled(isChecked);
				if (!isChecked) {
					cbSystem.setChecked(false);
					cbExperimental.setChecked(false);
					cbHttps.setChecked(true);
					cbAOSP.setChecked(false);
					etConfidence.setText("");
					etQuirks.setText("");
				}
			}
		});

		cbSerial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etSerial.setEnabled(!isChecked);
			}
		});

		cbLat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etLat.setEnabled(!isChecked);
			}
		});

		cbLon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etLon.setEnabled(!isChecked);
			}
		});

		cbAlt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etAlt.setEnabled(!isChecked);
			}
		});

		cbMac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etMac.setEnabled(!isChecked);
			}
		});

		cbImei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etImei.setEnabled(!isChecked);
			}
		});

		cbPhone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etPhone.setEnabled(!isChecked);
			}
		});

		cbId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etId.setEnabled(!isChecked);
			}
		});

		cbGsfId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etGsfId.setEnabled(!isChecked);
			}
		});

		cbAdId.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etAdId.setEnabled(!isChecked);
			}
		});

		cbCountry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etCountry.setEnabled(!isChecked);
			}
		});

		cbSubscriber.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etSubscriber.setEnabled(!isChecked);
			}
		});

		cbSSID.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				etSSID.setEnabled(!isChecked);
			}
		});

		// Display app name
		if (appInfo == null) {
			tvAppName.setVisibility(View.GONE);
			vwAppNameBorder.setVisibility(View.GONE);
		} else
			tvAppName.setText(TextUtils.join(", ", appInfo.getApplicationName()));

		// Get current values
		boolean usage = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingUsage, true);
		boolean parameters = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingParameters, false);
		boolean log = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingLog, false);

		boolean components = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingSystem, false);
		boolean experimental = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingExperimental, false);
		boolean https = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingHttps, true);
		boolean aosp = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingAOSPMode, false);
		String confidence = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingConfidence, "");

		// Get quirks
		boolean freeze = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingFreeze, false);
		boolean resolve = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingResolve, false);
		boolean noresolve = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingNoResolve, false);
		boolean permman = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingPermMan, false);
		boolean iwall = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingIntentWall, false);
		boolean safemode = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingSafeMode, false);
		boolean test = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingTestVersions, false);
		boolean odsystem = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingOnDemandSystem, false);
		List<String> listQuirks = new ArrayList<String>();
		if (freeze)
			listQuirks.add("freeze");
		if (resolve)
			listQuirks.add("resolve");
		if (noresolve)
			listQuirks.add("noresolve");
		if (permman)
			listQuirks.add("permman");
		if (iwall)
			listQuirks.add("iwall");
		if (safemode)
			listQuirks.add("safemode");
		if (test)
			listQuirks.add("test");
		if (odsystem)
			listQuirks.add("odsystem");
		Collections.sort(listQuirks);
		String quirks = TextUtils.join(",", listQuirks.toArray());

		final boolean expert = (components || experimental || !https || aosp || !"".equals(confidence) || listQuirks
				.size() > 0);

		// Application specific
		boolean notify = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingNotify, true);
		boolean ondemand = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingOnDemand, uid == userId);
		boolean blacklist = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingBlacklist, false);
		boolean enabled = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingRestricted, true);

		// Common
		boolean random = PrivacyManager.getSettingBool(-uid, PrivacyManager.cSettingRandom, false);

		String serial = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingSerial, "");
		String lat = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingLatitude, "");
		String lon = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingLongitude, "");
		String alt = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingAltitude, "");
		String mac = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingMac, "");
		String imei = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingImei, "");
		String phone = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingPhone, "");
		String id = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingId, "");
		String gsfid = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingGsfId, "");
		String adid = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingAdId, "");
		String country = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingCountry, "");
		String subscriber = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingSubscriber, "");
		String ssid = PrivacyManager.getSetting(-uid, PrivacyManager.cSettingSSID, "");

		// Set current values
		if (uid == userId) {
			// Global settings
			cbUsage.setChecked(usage);
			cbParameters.setChecked(parameters);
			cbParameters.setEnabled(Util.hasProLicense(context) != null);
			if (userId == 0)
				cbLog.setChecked(log);
			else {
				cbLog.setVisibility(View.GONE);
				btnClearDb.setVisibility(View.GONE);
			}
			cbExpert.setChecked(expert);

			if (PrivacyManager.cVersion3 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
				cbAOSP.setVisibility(View.VISIBLE);

			if (expert) {
				cbSystem.setChecked(components);
				cbExperimental.setChecked(experimental);
				cbHttps.setChecked(https);
				cbAOSP.setChecked(aosp);
				etConfidence.setText(confidence);
				etQuirks.setText(quirks);
			} else {
				cbSystem.setEnabled(false);
				cbExperimental.setEnabled(false);
				cbHttps.setEnabled(false);
				cbHttps.setChecked(true);
				cbAOSP.setEnabled(false);
				cbAOSP.setChecked(false);
				etConfidence.setEnabled(false);
				etQuirks.setEnabled(false);
				btnClearDb.setEnabled(false);
			}
		} else {
			// Disable global settings
			cbUsage.setVisibility(View.GONE);
			cbParameters.setVisibility(View.GONE);
			cbLog.setVisibility(View.GONE);
			cbSystem.setVisibility(View.GONE);
			cbExperimental.setVisibility(View.GONE);
			cbHttps.setVisibility(View.GONE);
			cbAOSP.setVisibility(View.GONE);
			llConfidence.setVisibility(View.GONE);
			btnClearDb.setVisibility(View.GONE);

			cbExpert.setChecked(expert);
			if (expert)
				etQuirks.setText(quirks);
			else
				etQuirks.setEnabled(false);
		}

		boolean gnotify = PrivacyManager.getSettingBool(userId, PrivacyManager.cSettingNotify, true);
		if (uid == userId || gnotify)
			cbNotify.setChecked(notify);
		else
			cbNotify.setVisibility(View.GONE);

		final boolean isApp = PrivacyManager.isApplication(uid);
		final boolean odSystem = PrivacyManager.getSettingBool(userId, PrivacyManager.cSettingOnDemandSystem, false);
		boolean gondemand = PrivacyManager.getSettingBool(userId, PrivacyManager.cSettingOnDemand, true);
		if (uid == userId || ((isApp || odSystem) && gondemand)) {
			cbOnDemand.setChecked(ondemand);
			cbOnDemand.setEnabled(enabled);
		} else
			cbOnDemand.setVisibility(View.GONE);

		String blFileName = Environment.getExternalStorageDirectory().getPath() + "/.xprivacy/blacklist";
		if (uid == userId || !new File(blFileName).exists())
			cbBlacklist.setVisibility(View.GONE);
		else
			cbBlacklist.setChecked(blacklist);

		// Common
		cbRandom.setChecked(random);

		// Set randomize on access check boxes
		cbSerial.setChecked(serial.equals(PrivacyManager.cValueRandom));
		cbLat.setChecked(lat.equals(PrivacyManager.cValueRandom));
		cbLon.setChecked(lon.equals(PrivacyManager.cValueRandom));
		cbAlt.setChecked(alt.equals(PrivacyManager.cValueRandom));
		cbMac.setChecked(mac.equals(PrivacyManager.cValueRandom));
		cbImei.setChecked(imei.equals(PrivacyManager.cValueRandom));
		cbPhone.setChecked(phone.equals(PrivacyManager.cValueRandom));
		cbId.setChecked(id.equals(PrivacyManager.cValueRandom));
		cbGsfId.setChecked(gsfid.equals(PrivacyManager.cValueRandom));
		cbAdId.setChecked(adid.equals(PrivacyManager.cValueRandom));
		cbCountry.setChecked(country.equals(PrivacyManager.cValueRandom));
		cbSubscriber.setChecked(subscriber.equals(PrivacyManager.cValueRandom));
		cbSSID.setChecked(ssid.equals(PrivacyManager.cValueRandom));

		// Set fake values
		etSerial.setText(cbSerial.isChecked() ? "" : serial);
		etLat.setText(cbLat.isChecked() ? "" : lat);
		etLon.setText(cbLon.isChecked() ? "" : lon);
		etAlt.setText(cbAlt.isChecked() ? "" : alt);
		etMac.setText(cbMac.isChecked() ? "" : mac);
		etImei.setText(cbImei.isChecked() ? "" : imei);
		etPhone.setText(cbPhone.isChecked() ? "" : phone);
		etId.setText(cbId.isChecked() ? "" : id);
		etGsfId.setText(cbGsfId.isChecked() ? "" : gsfid);
		etAdId.setText(cbAdId.isChecked() ? "" : adid);
		etCountry.setText(cbCountry.isChecked() ? "" : country);
		etSubscriber.setText(cbSubscriber.isChecked() ? "" : subscriber);
		etSSID.setText(cbSSID.isChecked() ? "" : ssid);

		etSerial.setEnabled(!cbSerial.isChecked());
		etLat.setEnabled(!cbLat.isChecked());
		etLon.setEnabled(!cbLon.isChecked());
		etAlt.setEnabled(!cbAlt.isChecked());

		etSearch.setEnabled(Geocoder.isPresent());
		btnSearch.setEnabled(Geocoder.isPresent());

		etMac.setEnabled(!cbMac.isChecked());
		etImei.setEnabled(!cbImei.isChecked());
		etPhone.setEnabled(!cbPhone.isChecked());
		etId.setEnabled(!cbId.isChecked());
		etGsfId.setEnabled(!cbGsfId.isChecked());
		etAdId.setEnabled(!cbAdId.isChecked());
		etCountry.setEnabled(!cbCountry.isChecked());
		etSubscriber.setEnabled(!cbSubscriber.isChecked());
		etSSID.setEnabled(!cbSSID.isChecked());

		etIP.setText(PrivacyManager.getSetting(-uid, PrivacyManager.cSettingIP, ""));
		etMcc.setText(PrivacyManager.getSetting(-uid, PrivacyManager.cSettingMcc, ""));
		etMnc.setText(PrivacyManager.getSetting(-uid, PrivacyManager.cSettingMnc, ""));
		etOperator.setText(PrivacyManager.getSetting(-uid, PrivacyManager.cSettingOperator, ""));
		etIccId.setText(PrivacyManager.getSetting(-uid, PrivacyManager.cSettingIccId, ""));
		etCid.setText(PrivacyManager.getSetting(-uid, PrivacyManager.cSettingCid, ""));
		etLac.setText(PrivacyManager.getSetting(-uid, PrivacyManager.cSettingLac, ""));
		etUa.setText(PrivacyManager.getSetting(-uid, PrivacyManager.cSettingUa, ""));

		btnClearDb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
				alertDialogBuilder.setTitle(R.string.menu_clear_db);
				alertDialogBuilder.setMessage(R.string.msg_sure);
				alertDialogBuilder.setIcon(context.getThemed(R.attr.icon_launcher));
				alertDialogBuilder.setPositiveButton(context.getString(android.R.string.ok),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								PrivacyManager.clear();
								((EditText) context.findViewById(R.id.etFilter)).setText("");
								context.recreate();
								Toast.makeText(context, context.getString(R.string.msg_reboot), Toast.LENGTH_LONG)
										.show();
								dlgSettings.dismiss();
							}
						});
				alertDialogBuilder.setNegativeButton(context.getString(android.R.string.cancel),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
			}
		});

		// Handle manual randomize
		btnRandom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				etSerial.setText(PrivacyManager.getRandomProp("SERIAL"));
				etLat.setText(PrivacyManager.getRandomProp("LAT"));
				etLon.setText(PrivacyManager.getRandomProp("LON"));
				etAlt.setText(PrivacyManager.getRandomProp("ALT"));
				etMac.setText(PrivacyManager.getRandomProp("MAC"));
				etImei.setText(PrivacyManager.getRandomProp("IMEI"));
				etPhone.setText(PrivacyManager.getRandomProp("PHONE"));
				etId.setText(PrivacyManager.getRandomProp("ANDROID_ID"));
				etGsfId.setText(PrivacyManager.getRandomProp("GSF_ID"));
				etAdId.setText(PrivacyManager.getRandomProp("AdvertisingId"));
				etCountry.setText(PrivacyManager.getRandomProp("ISO3166"));
				etSubscriber.setText(PrivacyManager.getRandomProp("SubscriberId"));
				etSSID.setText(PrivacyManager.getRandomProp("SSID"));
			}
		});

		// Handle clear
		btnClear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				for (EditText edit : edits)
					edit.setText("");
				etSearch.setText("");

				for (CheckBox box : boxes)
					box.setChecked(false);
			}
		});

		// Handle flush
		if (uid == 0)
			btnFlush.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent flushIntent = new Intent(UpdateService.cFlush);
					context.startService(flushIntent);
					Toast.makeText(context, context.getString(R.string.msg_done), Toast.LENGTH_LONG).show();
				}
			});
		else
			btnFlush.setVisibility(View.GONE);

		// Handle search
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try {
					String search = etSearch.getText().toString();
					final List<Address> listAddress = new Geocoder(context).getFromLocationName(search, 1);
					if (listAddress.size() > 0) {
						Address address = listAddress.get(0);

						// Get coordinates
						if (address.hasLatitude()) {
							cbLat.setChecked(false);
							etLat.setText(Double.toString(address.getLatitude()));
						}
						if (address.hasLongitude()) {
							cbLon.setChecked(false);
							etLon.setText(Double.toString(address.getLongitude()));
						}

						// Get address
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
							if (i != 0)
								sb.append(", ");
							sb.append(address.getAddressLine(i));
						}
						etSearch.setText(sb.toString());
					}
				} catch (Throwable ex) {
					Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});

		// Handle OK
		btnOk.setOnClickListener(new View.OnClickListener() {
			@Override
			@SuppressLint("DefaultLocale")
			public void onClick(View view) {
				if (uid == userId) {
					// Global settings
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingUsage, Boolean.toString(cbUsage.isChecked()));
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingParameters,
							Boolean.toString(cbParameters.isChecked()));
					if (userId == 0)
						PrivacyManager.setSetting(uid, PrivacyManager.cSettingLog, Boolean.toString(cbLog.isChecked()));
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingSystem,
							Boolean.toString(cbSystem.isChecked()));
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingExperimental,
							Boolean.toString(cbExperimental.isChecked()));
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingHttps, Boolean.toString(cbHttps.isChecked()));
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingAOSPMode,
							Boolean.toString(cbAOSP.isChecked()));
					PrivacyManager
							.setSetting(uid, PrivacyManager.cSettingConfidence, etConfidence.getText().toString());
				}

				// Quirks
				List<String> listQuirks = Arrays.asList(etQuirks.getText().toString().toLowerCase().replace(" ", "")
						.split(","));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingFreeze,
						Boolean.toString(listQuirks.contains("freeze")));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingResolve,
						Boolean.toString(listQuirks.contains("resolve")));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingNoResolve,
						Boolean.toString(listQuirks.contains("noresolve")));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingPermMan,
						Boolean.toString(listQuirks.contains("permman")));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingIntentWall,
						Boolean.toString(listQuirks.contains("iwall")));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingSafeMode,
						Boolean.toString(listQuirks.contains("safemode")));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingTestVersions,
						Boolean.toString(listQuirks.contains("test")));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingOnDemandSystem,
						Boolean.toString(listQuirks.contains("odsystem")));

				// Notifications
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingNotify, Boolean.toString(cbNotify.isChecked()));

				// On demand restricting
				if (uid == userId || (isApp || odSystem))
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingOnDemand,
							Boolean.toString(cbOnDemand.isChecked()));

				if (uid != userId)
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingBlacklist,
							Boolean.toString(cbBlacklist.isChecked()));

				// Random at boot
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingRandom,
						cbRandom.isChecked() ? Boolean.toString(true) : null);

				// Serial#
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingSerial, getValue(cbSerial, etSerial));

				// Set latitude
				if (cbLat.isChecked())
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingLatitude, PrivacyManager.cValueRandom);
				else
					try {
						float lat = Float.parseFloat(etLat.getText().toString().replace(',', '.'));
						if (lat < -90 || lat > 90)
							throw new InvalidParameterException();

						PrivacyManager.setSetting(uid, PrivacyManager.cSettingLatitude, Float.toString(lat));
					} catch (Throwable ignored) {
						PrivacyManager.setSetting(uid, PrivacyManager.cSettingLatitude, null);
					}

				// Set longitude
				if (cbLon.isChecked())
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingLongitude, PrivacyManager.cValueRandom);
				else
					try {
						float lon = Float.parseFloat(etLon.getText().toString().replace(',', '.'));
						if (lon < -180 || lon > 180)
							throw new InvalidParameterException();
						PrivacyManager.setSetting(uid, PrivacyManager.cSettingLongitude, Float.toString(lon));
					} catch (Throwable ignored) {
						PrivacyManager.setSetting(uid, PrivacyManager.cSettingLongitude, null);
					}

				// Set altitude
				if (cbAlt.isChecked())
					PrivacyManager.setSetting(uid, PrivacyManager.cSettingAltitude, PrivacyManager.cValueRandom);
				else
					try {
						float alt = Float.parseFloat(etAlt.getText().toString().replace(',', '.'));
						if (alt < -10000 || alt > 10000)
							throw new InvalidParameterException();
						PrivacyManager.setSetting(uid, PrivacyManager.cSettingAltitude, Float.toString(alt));
					} catch (Throwable ignored) {
						PrivacyManager.setSetting(uid, PrivacyManager.cSettingAltitude, null);
					}

				// Check Gsf ID
				try {
					String value = etGsfId.getText().toString();
					if (!"".equals(value))
						Long.parseLong(value.toLowerCase(), 16);
				} catch (NumberFormatException ignored) {
					etGsfId.setText("");
				}

				// Other settings
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingMac, getValue(cbMac, etMac));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingIP, getValue(null, etIP));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingImei, getValue(cbImei, etImei));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingPhone, getValue(cbPhone, etPhone));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingId, getValue(cbId, etId));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingGsfId, getValue(cbGsfId, etGsfId));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingAdId, getValue(cbAdId, etAdId));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingMcc, getValue(null, etMcc));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingMnc, getValue(null, etMnc));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingCountry, getValue(cbCountry, etCountry));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingOperator, getValue(null, etOperator));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingIccId, getValue(null, etIccId));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingCid, getValue(null, etCid));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingLac, getValue(null, etLac));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingSubscriber, getValue(cbSubscriber, etSubscriber));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingSSID, getValue(cbSSID, etSSID));
				PrivacyManager.setSetting(uid, PrivacyManager.cSettingUa, getValue(null, etUa));

				dlgSettings.dismiss();

				// Refresh view
				if (uid == userId) {
					Intent intent = new Intent(context, ActivityMain.class);
					context.startActivity(intent);
				} else {
					Intent intent = new Intent(context, ActivityApp.class);
					intent.putExtra(ActivityApp.cUid, uid);
					intent.putExtra(ActivityApp.cAction, ActivityApp.cActionRefresh);
					context.startActivity(intent);
				}
			}
		});

		// Handle Cancel
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dlgSettings.dismiss();
			}
		});

		// Show dialog
		dlgSettings.setCancelable(true);
		dlgSettings.show();
	}

	private static String getValue(CheckBox check, EditText edit) {
		if (check != null && check.isChecked())
			return PrivacyManager.cValueRandom;
		String value = edit.getText().toString().trim();
		return ("".equals(value) ? null : value);
	}
}
