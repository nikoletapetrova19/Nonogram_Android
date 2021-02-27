package petrova.nonogram;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;

import static petrova.nonogram.GameActivity.MAX_NUM_HINT;
import static petrova.nonogram.GameActivity.numHint;
import static petrova.nonogram.GameActivity.ticketButton;

public class HintNotifyThread extends Thread{
    private static final int CHARGE_TIME = 3000;

    private HintNotifyService.ServiceHandler mHandler;
    private boolean mIsRun = true;
    private boolean isMsgSent = false;
    private Context context;

    public HintNotifyThread(HintNotifyService.ServiceHandler handler, Context context) {
        mHandler = handler;
        this.context = context;
    }

    public void stopForever() {
        synchronized (this) {
            mIsRun = false;
        }
    }

    @SuppressLint("SetTextI18n")
    public void run() {
        while (mIsRun) {

            if(numHint >= MAX_NUM_HINT){
                if(shouldShowNotification(context) && !isMsgSent){
                    isMsgSent = true;
                    mHandler.sendEmptyMessage(0);
                }
            }
            else {
                try {
                    Thread.sleep(CHARGE_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                numHint++;
                isMsgSent = false;
                ticketButton.setText(Integer.toString(numHint));
                ticketButton.setAlpha(1.0f);
            }

        }
    }

    static boolean shouldShowNotification(Context context) {
        ActivityManager.RunningAppProcessInfo myProcess = new ActivityManager.RunningAppProcessInfo();
        ActivityManager.getMyMemoryState(myProcess);
        if (myProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
            return true;

        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

        return km.inKeyguardRestrictedInputMode();
    }
}
