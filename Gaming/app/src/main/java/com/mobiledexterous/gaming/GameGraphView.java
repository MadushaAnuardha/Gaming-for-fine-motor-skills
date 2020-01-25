package com.mobiledexterous.gaming;

    import android.content.Intent;
    import android.support.annotation.NonNull;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;

    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
    import com.jjoe64.graphview.GraphView;
    import com.jjoe64.graphview.series.DataPoint;
    import com.jjoe64.graphview.series.LineGraphSeries;

public class GameGraphView extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;

    DatabaseReference databaseGameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_graph_view);

        String ref = "User/Gaming/"+GameUtilities.patientId;

        databaseGameData = FirebaseDatabase.getInstance().getReference(ref);

        databaseGameData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void showData( DataSnapshot dataSnapshot){

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<DataPoint>();

        double x = 0;
        double y = 0;

        for ( DataSnapshot ds : dataSnapshot.getChildren()){

            GameData data = new GameData();
            data.setScore(ds.getValue(GameData.class).getScore());
            data.setAttemptNo(ds.getValue(GameData.class).getAttemptNo());

            y = Double.parseDouble(data.getScore());

            series.appendData(new DataPoint(x,y), true, 500);
            ++x;

        }

        graph.addSeries(series);
    }

    public void onBackPressed(){
        super.onBackPressed();
//        GameUtilities.stopPlaying();
//        GameUtilities.mediaPlayer=null;
//        handler.removeCallbacksAndMessages(null);
        Intent intent = new Intent(this, GameMainActivity.class);
        startActivity(intent);
        finish();
    }

}
