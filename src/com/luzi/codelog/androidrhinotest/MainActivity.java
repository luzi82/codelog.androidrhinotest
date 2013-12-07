package com.luzi.codelog.androidrhinotest;

import org.mozilla.javascript.Callable;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		good0();
		// bad0();
		// bad1();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void good0() {
		Context cx = Context.enter();
		cx.setOptimizationLevel(-1);
		Scriptable scope = cx.initStandardObjects();
		cx.evaluateString(scope, "x=42;", "<cmd>", 0, null);
		Object result = cx.evaluateString(scope, "x", "<cmd>", 0, null);
		Log.v("androidrhinotest", result.toString());
		Context.exit();
	}

	public void bad0() {
		Context cx = Context.enter();
		// cx.setOptimizationLevel(-1);
		Scriptable scope = cx.initStandardObjects();
		cx.evaluateString(scope, "x=42;", "<cmd>", 0, null);
		cx.evaluateString(scope, "x", "<cmd>", 0, null); // die
		Context.exit();
	}

	public void bad1() {
		Context cx = Context.enter();
		cx.setOptimizationLevel(-1);
		Scriptable scope = cx.initStandardObjects();
		cx.evaluateString(scope, "x=function(){return 42;};", "<cmd>", 0, null);
		Callable c = (Callable) cx.evaluateString(scope, "x", "<cmd>", 0, null);
		c.call(cx, scope, null, null); // die
		Context.exit();
	}

}
