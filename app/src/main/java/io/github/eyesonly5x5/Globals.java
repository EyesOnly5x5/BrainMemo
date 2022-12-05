package io.github.eyesonly5x5;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.eyesonly5x5.brainmemo.R;

public class Globals  extends ListActivity {
    @SuppressLint("StaticFieldLeak")
    private static final Globals instance = new Globals();
    private static MyDisplay metrics;

    // Beispiele für Daten...
    private byte[][] Tast = new byte[100][9];
    private int maxFelder = 0;
    private final boolean[] Flg = new boolean[100];
    private final int[][] Colors = new int[11][5];
    List<Integer> Color = new ArrayList<>();
    int[] BUTTON_IDS;
    int[] TEXT_IDS;
    private TextView Ausgabe;
    List<Button> buttons = new ArrayList<>();
    List<ImageView> imgButtons = new ArrayList<>();
    List<TextView> TextV = new ArrayList<>();
    private int Zuege = 0;
    private int Anzahl = 0;
    private int Activity=-1;
    private Context myContext;
    private Resources myRes;
    private boolean gewonnen = true;
    private SoundBib SoundW;
    private SoundBib SoundF;
    private int Buty = 90;
    private int Buty2 = 90;
    private int[][] NonoG;
    private int[][] SudoK;
    List<String> piTron = new ArrayList<>();
    private GridLayout myBlock;
    private boolean geloest;
    private boolean dashEnde;
    private int istGedrueckt = 0;
    private int MemoryPic = 0;
    private ArrayList<Integer> MemoryPics = new ArrayList<>();
    private String woMischen = "Zauber";
    private boolean geMischt = false;
    List<RelativeLayout>viewButIDs = new ArrayList<>();

    // private Globals() { }

    public static Globals getInstance() {
        return instance;
    }

    public static void setMetrics( Resources hier ){
        metrics = new MyDisplay( hier );
    }
    public static MyDisplay getMetrics( ){
        return( metrics );
    }

    public byte[][] getTast() {
        return Tast;
    }

    public int getZuege() {
        return Zuege;
    }
    public int decZuege( int Anzahl ) {
        Zuege -= Anzahl;
        return( Zuege );
    }
    public int decZuege() {
        return --Zuege;
    }
    public int incZuege() { return ++Zuege; }
    public int incZuege( int Anzahl ) {
        Zuege += Anzahl;
        return( Zuege );
    }

    public TextView getAusgabe() {
        return Ausgabe;
    }
    public void setAusgabe(TextView wert) {
        Ausgabe = wert;
    }

    public SoundBib getSoundBib(boolean s) {
        return( (s)?SoundW:SoundF );
    }
    public void setSoundBib(boolean s, SoundBib wert) {
        if( s ) SoundW = wert;
        else SoundF = wert;
    }

    public boolean getGewonnen() {
        return gewonnen;
    }
    public void setGewonnen( boolean wert) {
        gewonnen = wert;
    }


    public int getActivity(){
        return Activity;
    }
    public void setActivity(int act){
        Activity = act;
    }
    public void setMyContext( MainActivity c) {
        myContext = c;
        myRes = myContext.getResources();
    }
    public Context getMyContext( ) { return( myContext ); }

    public void addImgButton(ImageView button) {
        imgButtons.add(button);
    }

    public void setNonoG( int vec, int pos, int wert ){ NonoG[vec][pos] = wert; }
    public int getNonoG( int vec, int pos ){ return( NonoG[vec][pos] ); }

    public int getMemoryPic(){ return( MemoryPic ); }
    public int getMemoryPicsLEN(){ return( MemoryPics.size() ); }
    public int getMemoryPicDraw(){
        int ret = 0;
        switch( MemoryPic ){
            case 0:
                ret = R.drawable.pics_01_00;
                break;
            case 1:
                ret = R.drawable.pics_02_00;
                break;
            case 2:
                ret = R.drawable.pics_03_00;
                break;
        }
        return( ret );
    }

    public void setWoMischen( String wert ){
        woMischen = wert;
        metrics.setWoMischen(wert);
    }
    public String getWoMischen( ){ return( woMischen ); }
    public boolean getGeMischt(){ return( geMischt ); }
    public void setGeMischt( boolean wert ) { geMischt = wert; }

    public List<Integer> getColor(){ return( Color ); }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void Mischer() {
        int id, id1, id2, tmp;
        Random r = new Random();
        Zuege = 0;
        gewonnen = false;
        Ausgabe.setText("Züge: " + Zuege);
        NonoG = new int[2][Anzahl*Anzahl];
        geMischt = true;
        piTron = null;
        piTron = new ArrayList<>();
        Ausgabe.setText( myContext.getString( R.string.Punkte ) + Zuege );
        for( int i = 0; i < MemoryPics.size(); i++ ){
            NonoG[0][i] = MemoryPics.get(i);
            NonoG[0][(13+i)] = MemoryPics.get(i);
            NonoG[1][i] = 0;
            NonoG[1][(13+i)] = 0;
        }
        NonoG[0][12] = 0;
        NonoG[1][12] = 2;
        for (int i = 0; i < 55; i++) {
            id1 = r.nextInt(maxFelder);
            id2 = r.nextInt(maxFelder);
            tmp = NonoG[0][id1];
            NonoG[0][id1] = NonoG[0][id2];
            NonoG[0][id2] = tmp;
            tmp = NonoG[1][id1];
            NonoG[1][id1] = NonoG[1][id2];
            NonoG[1][id2] = tmp;
        }
        for (id = 0; id < maxFelder; id++) {
            ImageView button = imgButtons.get(id);
            button.setBackground( myRes.getDrawable( getMemoryPicDraw() ) );
        }
    }


    @SuppressLint("WrongConstant")
    private int anzahlButtons(){
        int AnzBut = (((metrics.getMaxPixels()) / (int)(this.Buty*metrics.getFaktor()))-3);
        // int dimenX = (int) metrics.getMinPixels() / (column+1);
        if( AnzBut > 11 ) AnzBut = 11;
        AnzBut *= Anzahl;
        return( AnzBut );
    }

    public int[] getButtonIDs() {
        int wer = getWerWoWas();
        int Buttys = (wer==0)?9:(wer==1)?16:(wer==2)?25:(wer==3)?anzahlButtons():(wer==4)?100:(wer>=5)?Anzahl*Anzahl:0;
        int[] ret = new int[Buttys];

        if( wer < 3 ){
            for (int i = 0; i < ret.length; i++) {
                ret[i] = myRes.getIdentifier("b"+(i+1), "id", myContext.getPackageName());
            }
        } else {
            for (int i = 0; i < ret.length; i++) {
                ret[i] = (i + 1);
            }
            if( wer == 5 ){
                NonoG = new int[2][Anzahl*Anzahl];
            } else if( wer == 6 ){
                SudoK = new int[3][Anzahl*Anzahl];
            }
        }
        BUTTON_IDS = ret;
        maxFelder = BUTTON_IDS.length;
        return (BUTTON_IDS);
    }

    @SuppressLint("NonConstantResourceId")
    private int getWerWoWas(){
        int ret = -1;
        switch( Activity ){
            case R.layout.activity_memory:
                ret = 2;
                break;
        }
        return( ret );
    }

    public void setGameData( String txt ) {
        Random r = new Random();
        Zuege = 0;
        gewonnen = true;
        buttons = null;
        buttons = new ArrayList<>();
        TextV = null;
        TextV = new ArrayList<>();
        imgButtons = null;
        imgButtons = new ArrayList<>();
        viewButIDs = null;
        viewButIDs = new ArrayList<>();
        istGedrueckt = 0;
        dashEnde = false;
        MemoryPics = new ArrayList<>();
        Anzahl = 5;
        MemoryPic = r.nextInt(3 );
        String dat = "pics_" + StrZ(MemoryPic+1, 2) + "_";
        for (int i = 1; i <= 12; i++) {
            MemoryPics.add(myRes.getIdentifier(dat + StrZ(i, 2), "drawable", "de.eyesonly5x5.brainmemo"));
        }
        maxFelder = Anzahl*Anzahl;
        NonoG = new int[2][Anzahl*Anzahl];
    }

    private byte beRechneFeld( byte id, byte i ){
        byte[] arr = {(byte) (id-9), (byte) (id-10), (byte) (id-11), (byte) (id-1), id, (byte) (id+1), (byte) (id+9), (byte) (id+10), (byte) (id+11)};
        if( (id%10)==1 ) { arr[2] = 0; arr[3] = 0; arr[6] = 0; }
        else if( (id%10)==0 ) { arr[0] = 0; arr[5] = 0; arr[8] = 0; }

        if( arr[i] < 0 ) arr[i] = 0;
        if( arr[i] > maxFelder ) arr[i] = 0;
        return( arr[i] );
    }

    static class SoundBib extends AppCompatActivity {
        private SoundPool soundPool;
        List<Integer> sound = new ArrayList<>();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.activity_main);
        }

        public SoundBib(boolean s, Context context) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();

            if( s ) {
                sound.add(soundPool.load(context, R.raw.won1, 1));
                sound.add(soundPool.load(context, R.raw.won2, 1));
                sound.add(soundPool.load(context, R.raw.won3, 1));
                sound.add(soundPool.load(context, R.raw.won4, 1));
                sound.add(soundPool.load(context, R.raw.won5, 1));
            } else {
                sound.add(soundPool.load(context, R.raw.fail1, 1));
                sound.add(soundPool.load(context, R.raw.fail2, 1));
                sound.add(soundPool.load(context, R.raw.fail3, 1));
                sound.add(soundPool.load(context, R.raw.fail4, 1));
            }
        }

        // When users click on the button "Gun"
        public void playSound() {
            soundPool.autoPause();
            Random r = new Random();
            int id = r.nextInt(sound.size());
            soundPool.play(sound.get(id), 0.25F, 0.25F, 0, 0, 1);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            soundPool.release();
            soundPool = null;
        }
    }
    private String StrZ( int wert, int len ){
        StringBuilder ret;
        ret = new StringBuilder(new StringBuilder().append("").append(wert).toString());
        while( ret.length() < len ) ret.insert(0, "0");
        return(ret.toString());
    }

    public void Anleitung( Context dasDA, int Wat ) {
        Dialog customDialog = new Dialog( dasDA );
        customDialog.setContentView(R.layout.anleitung);
        TextView oView = customDialog.findViewById( R.id.Anleitung );
        String str = myRes.getString( Wat, myRes.getString( R.string.Wunschliste ) );
        oView.setText( str );
        Button bView = customDialog.findViewById( R.id.Warte );
        bView.setOnClickListener(view -> customDialog.dismiss());
        customDialog.setCancelable(false);
        customDialog.show();
    }

}