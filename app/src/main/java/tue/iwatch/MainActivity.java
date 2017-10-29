package tue.iwatch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.button;

public class MainActivity extends AppCompatActivity {

    private Button[] button;
    private Button refresh;
    private LinearLayout screen;
    private TextView output,percent,info;
    private EditText scrsize, icnsize;
    private SeekBar watch,icon;
    private int clk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        final int dmwidth = dm.widthPixels,dmheight=dm.heightPixels;
        Toast.makeText(this,dmwidth + " " + dmheight, Toast.LENGTH_SHORT).show();
        button = new Button[9];
        button[0] = (Button) findViewById(R.id.b1);
        button[1] = (Button) findViewById(R.id.b2);
        button[2] = (Button) findViewById(R.id.b3);
        button[3] = (Button) findViewById(R.id.b4);
        button[4] = (Button) findViewById(R.id.b5);
        button[5] = (Button) findViewById(R.id.b6);
        button[6] = (Button) findViewById(R.id.b7);
        button[7] = (Button) findViewById(R.id.b8);
        button[8] = (Button) findViewById(R.id.b9);
        screen = (LinearLayout) findViewById(R.id.screen);
        output = (TextView) findViewById(R.id.output);
        scrsize = (EditText) findViewById(R.id.watch_size);
        icnsize = (EditText) findViewById(R.id.icon_size);
        percent = (TextView) findViewById(R.id.per_cent);
        watch = (SeekBar) findViewById(R.id.watch_seek);
        icon = (SeekBar) findViewById(R.id.icon_seek);
        info = (TextView) findViewById(R.id.info);
        icon.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!b)
                    return;
                setIcon(i * watch.getProgress() * dmwidth / 100 / 300);

                updateProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        watch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!b)
                    return;
                int icons = icon.getProgress();
                int targetscrsize = i * dmwidth / 100;
                setIcon(targetscrsize * icons / 300);
                setWatch(targetscrsize);

                updateProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        refresh = (Button) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int watchsize = Integer.parseInt(scrsize.getText().toString());
                int perwat = watchsize * 100 / dmwidth;
                int iconsize = Integer.parseInt(icnsize.getText().toString());
                int pericn = iconsize * 300 / watchsize;
                setIcon(iconsize);
                setWatch(watchsize);
                icon.setProgress(pericn);
                watch.setProgress(perwat);

                updateProgress();
            }
        });
        clk = 0;
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clk ++;
                info.setText(""+clk);
            }
        });
        screen.post(new Runnable() {
            @Override
            public void run() {
                int width = screen.getMeasuredWidth(), height = screen.getMeasuredHeight();
                scrsize.setText(""+width);
                watch.setProgress(width * 100 / dmwidth);
            }
        });
        button[0].post(new Runnable() {
            @Override
            public void run() {
                int width = button[0].getMeasuredWidth(), height = button[0].getMeasuredHeight();
                icnsize.setText("" + width);
                icon.setProgress(width * 300 / (dmwidth * watch.getProgress() / 100));
                updateProgress();
            }
        });

        for (int i = 0; i < 9; i ++)
        {
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    output.setText(((Button)view).getText());
                }
            });
        }
    }
    void setWatch(int scr)
    {
        ViewGroup.LayoutParams sclp = screen.getLayoutParams();
        sclp.width = scr;
        sclp.height = scr;
        screen.setLayoutParams(sclp);
        scrsize.setText(""+scr);
    }
    void setIcon(int icon)
    {
        for (int i = 0; i < 9; i ++)
        {
            ViewGroup.LayoutParams sc = button[i].getLayoutParams();
            sc.width = icon;
            sc.height = icon;
            button[i].setLayoutParams(sc);
        }
        icnsize.setText(""+icon);
    }
    void updateProgress()
    {
        percent.setText(icon.getProgress()+"%");
    }
}
