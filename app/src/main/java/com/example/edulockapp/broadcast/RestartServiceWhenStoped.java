package com.example.edulockapp.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.edulockapp.ui.quiz.Quiz;
import com.example.edulockapp.utils.Utils;

public class RestartServiceWhenStoped extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Utils utils = new Utils(context);
        String appRunning = utils.getLauncherTopApp();

        if(utils.isLock(appRunning)){

            if(!appRunning.equals(utils.getLastApp())){

                utils.clearLastApp();
                utils.setLastApp(appRunning);

                Intent i = new Intent(context, Quiz.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("Broadcast_receiver", "Broadcast_receiver");
                context.startActivity(i);
            }

        }

    }
}
