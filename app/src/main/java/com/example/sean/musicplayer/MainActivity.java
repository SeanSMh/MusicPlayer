package com.example.sean.musicplayer;

        import android.Manifest;
        import android.content.pm.PackageManager;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Environment;
        import android.support.v4.app.ActivityCompat;
        import android.support.v4.content.ContextCompat;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileInputStream;
        import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button play;
    private Button pause;
    private Button stop;
    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        play = (Button) findViewById(R.id.play_music);
        pause = (Button) findViewById(R.id.pause_music);
        stop = (Button) findViewById(R.id.stop_music);

        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            initMusicPlayer();  //初始化音乐播放器
        }
    }


    private void initMusicPlayer() {
        try {
            //File file = new File(Environment.getExternalStorageDirectory(), "Virtual Riot - Energy Drink.mp3");
            //FileInputStream fis = new FileInputStream(file);
            //String sdPath=Environment.getExternalStorageDirectory().getAbsolutePath();

            //mediaPlayer.setDataSource("/netease/cloudmusic/Music/法苏Lighting.mp3");  //指定音频文件的路径
            //mediaPlayer.setDataSource(this, Uri.parse("file://netease/cloudmusic/Music/法苏Lighting.mp3"));

            /*
            * 应用内部音乐播放
            * */
            /*mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer = MediaPlayer.create(this,R.raw.rocketgirls);
            mediaPlayer.prepare();  //让mediaplay进入到准备状态*/

            /*
            * 播放手机内部音乐
            * */
            File file = new File(Environment.getExternalStorageDirectory(),"Lighting.mp3");
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMusicPlayer();
                } else {
                    Toast.makeText(this, "You denied the request", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_music:
                mediaPlayer.start();  //开始播放
                break;
            case R.id.pause_music:
                mediaPlayer.pause();  //暂停播放
                break;
            case R.id.stop_music:
                mediaPlayer.reset();   //停止播放
                initMusicPlayer();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
