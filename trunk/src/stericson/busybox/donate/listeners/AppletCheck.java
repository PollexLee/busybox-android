package stericson.busybox.donate.listeners;

import java.io.File;
import java.util.List;

import stericson.busybox.donate.R;
import stericson.busybox.donate.Activity.BaseActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.stericson.RootTools.RootTools;

public class AppletCheck implements OnItemSelectedListener {

	private BaseActivity activity;
	
	public AppletCheck(BaseActivity activity)
	{
		this.activity = activity;
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
		if (arg2 == 0 || arg2 == 1)
		{
			if (arg2 == 1)
			{
				activity.initiatePopupWindow(activity.getString(R.string.whatisthis), false, activity);				
			}
		}
		else
		{
			String applet = arg0.getAdapter().getItem(arg2).toString();
			
			String appletInfo = "";

			if (RootTools.findBinary(applet))
			{
				appletInfo += activity.getString(R.string.foundhere) + " " + RootTools.lastFoundBinaryPaths.get(0);
				String symlink = RootTools.getSymlink(new File(RootTools.lastFoundBinaryPaths.get(0) + "/" + applet));
				if (symlink.equals(""))
				{
					appletInfo += "\n" + activity.getString(R.string.notsymlinked);
				}
				else
				{
					appletInfo += "\n" + activity.getString(R.string.symlinkedTo) + " " + symlink + "\n\n";
				}
			}
			else
			{
				appletInfo += activity.getString(R.string.notFound);
			}
			
			if (RootTools.isAppletAvailable(applet))
			{
				try
				{
					List<String> result = RootTools.sendShell("busybox " + applet + " --help", -1);
					
					for (String info : result)
					{
						if (!info.equals("1"))
						{
							appletInfo += info + "\n";
						}
					}
					
					activity.initiatePopupWindow(appletInfo, false, activity);
				}
				catch (Exception e)
				{
					appletInfo += "\n" + activity.getString(R.string.noInfo);
				}
			}
			else
			{
				appletInfo += "\n" + activity.getString(R.string.noInfo);
			}
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
