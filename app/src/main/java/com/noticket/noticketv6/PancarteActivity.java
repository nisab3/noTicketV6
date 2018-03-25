package com.noticket.noticketv6;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class PancarteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pancarte);

        // listenner pour les seekbars
        SeekBar barHeure = findViewById(R.id.seekBarHeure);
        SeekBar barJour = findViewById(R.id.seekBarJour);
        SeekBar barMois = findViewById(R.id.seekBarMois);
        barHeure.setOnSeekBarChangeListener(barChangement);
        barJour.setOnSeekBarChangeListener(barChangement);
        barMois.setOnSeekBarChangeListener(barChangement);

        //listener pour les boutons
        Button bh1 = findViewById(R.id.boutHeure1);
        Button bh2 = findViewById(R.id.boutHeure2);
        Button bh3 = findViewById(R.id.boutHeure3);
        Button bj1 = findViewById(R.id.boutJour1);
        Button bj2 = findViewById(R.id.boutJour2);
        Button bj3 = findViewById(R.id.boutJour3);
        Button bm1 = findViewById(R.id.boutMois1);
        bh1.setOnClickListener(b);
        bh2.setOnClickListener(b);
        bh3.setOnClickListener(b);
        bj1.setOnClickListener(b);
        bj2.setOnClickListener(b);
        bj3.setOnClickListener(b);
        bm1.setOnClickListener(b);
    }

    // ecoute si il y a un changement sur les SeekBars et change les dessins de numero
    private SeekBar.OnSeekBarChangeListener barChangement = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (i == 0) {
                seekBar.setThumb(getDrawable(R.drawable.number_0));
                barChangeVisible(seekBar, i);
            } else {
                if (i == 1) {
                    seekBar.setThumb(getDrawable(R.drawable.number_1));
                    barChangeVisible(seekBar, i);
                } else {
                    if (i == 2) {
                        seekBar.setThumb(getDrawable(R.drawable.number_2));
                        barChangeVisible(seekBar, i);
                    } else {
                        seekBar.setThumb(getDrawable(R.drawable.number_3));
                        barChangeVisible(seekBar, i);
                    }
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    //fonction pour faire apparaitre les boutons heure, jour, mois selon le
    //nombre dans le seekBar fournie
    //in : SeekBar, int état u seekbar
    //out: rien
    public void barChangeVisible(SeekBar seekbar, int i){
        int barId = seekbar.getId();
        if (barId == R.id.seekBarHeure){
            for (int j = 0; j < 3; j++ ){
                if (j < i){
                    findViewById(R.id.boutHeure1 + j).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.boutHeure1 + j).setVisibility(View.GONE);
                }
            }
        }
        else {
            if (barId == R.id.seekBarJour) {
                for (int j = 0; j < 3; j++) {
                    if (j < i) {
                        findViewById(R.id.boutJour1 + j).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.boutJour1 + j).setVisibility(View.GONE);
                    }
                }
            }
            else {
                //(barId == R.id.barMois)

                    if (i == 1) {
                        findViewById(R.id.boutMois1).setVisibility(View.VISIBLE);
                    } else {
                        findViewById(R.id.boutMois1).setVisibility(View.GONE);
                    }
            }
        }
    }

        // écoute si on click sur un bouton de l'activité ou du fragment et décide de ce qu'il doit faire
    private Button.OnClickListener b = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // 1er ligne d'heure
            if (view.getId() == R.id.boutHeure1){
                clickAjoutPancarte(view, "heure", "1");
            }
            // 2e ligne d'heure
            if (view.getId() == R.id.boutHeure2){
                clickAjoutPancarte(view, "heure", "2");
            }
            // 3e ligne d'heure
            if (view.getId() == R.id.boutHeure3){
                clickAjoutPancarte(view, "heure", "3");
            }

            // 1er ligne jour
            if (view.getId() == R.id.boutJour1){
                clickAjoutPancarte(view, "jour", "1");
            }
            // 2e ligne jour
            if (view.getId() == R.id.boutJour2){
                clickAjoutPancarte(view, "jour", "2");
            }
            // 3e ligne jour
            if (view.getId() == R.id.boutJour3){
                clickAjoutPancarte(view, "jour", "3");
            }

            // 1er ligne mois
            if (view.getId() == R.id.boutMois1){
                clickAjoutPancarte(view, "mois", "1");
            }

            // cancel de tout fragment
            if (view.getId() == R.id.boutFragCancel){
                clickFermerFrag(view);
            }
        }
    };

            // mettre tout les infos nécessaires dans un bundle et lancer le fragment
            // in : View view = view
            //      string type = heure, jour, mois (type du bouton)
            //      String numero = 1, 2, 3 (ligne du bouton)
    public void clickAjoutPancarte(View view, String type, String numero){


        Bundle paquet = new Bundle();
        paquet.putString("type", type);
        paquet.putString("numero", numero);
        Fragment fragChoix = new ChoixFragment();
        fragChoix.setArguments(paquet);

        //desactive tout les boutons dans pancarte activity
        desactivationBouton();

        //creer et lancer le fragment
        lanceFragment(view, fragChoix);

    }

        //lancement du frangement
    public void lanceFragment(View view, Fragment fragChoix ){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layoutPourChoixFrag, fragChoix);
        fragmentTransaction.commit();
    }

    public void clickFermerFrag(View view){

        //reactivation des boutons de pancarte activity
        activationBouton();

        //enlever et détruire le fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment frag = fragmentManager.findFragmentById(R.id.layoutPourChoixFrag);
        fragmentTransaction.remove(frag);
        fragmentTransaction.commit();
    }

    // désactive tout les boutons de l'activity pancarte lorsque nous somme dans le fragment
    public void desactivationBouton(){
        findViewById(R.id.seekBarHeure).setEnabled(false);
        findViewById(R.id.seekBarJour).setEnabled(false);
        findViewById(R.id.seekBarMois).setEnabled(false);
        findViewById(R.id.seekBarJour).setEnabled(false);
        findViewById(R.id.seekBarMois).setEnabled(false);
        findViewById(R.id.boutHeure1).setEnabled(false);
        findViewById(R.id.boutHeure2).setEnabled(false);
        findViewById(R.id.boutHeure3).setEnabled(false);
        findViewById(R.id.boutJour1).setEnabled(false);
        findViewById(R.id.boutJour2).setEnabled(false);
        findViewById(R.id.boutJour3).setEnabled(false);
        findViewById(R.id.boutMois1).setEnabled(false);
    }

    // réactive tout les bouton de activity pancarte lorsque nous sortont du fragment
    public void activationBouton(){
        findViewById(R.id.seekBarHeure).setEnabled(true);
        findViewById(R.id.seekBarJour).setEnabled(true);
        findViewById(R.id.seekBarMois).setEnabled(true);
        findViewById(R.id.seekBarHeure).setEnabled(true);
        findViewById(R.id.seekBarJour).setEnabled(true);
        findViewById(R.id.seekBarMois).setEnabled(true);
        findViewById(R.id.boutHeure1).setEnabled(true);
        findViewById(R.id.boutHeure2).setEnabled(true);
        findViewById(R.id.boutHeure3).setEnabled(true);
        findViewById(R.id.boutJour1).setEnabled(true);
        findViewById(R.id.boutJour2).setEnabled(true);
        findViewById(R.id.boutJour3).setEnabled(true);
        findViewById(R.id.boutMois1).setEnabled(true);
    }
}
