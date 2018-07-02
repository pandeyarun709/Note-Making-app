package com.example.arunpandey.notemakingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> notes;
    Prefrences prefrences;
    SQLoperation sqLoperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        listView =  findViewById(R.id.listview);
        prefrences = Prefrences.getInstance(getApplicationContext());
        sqLoperation = SQLoperation.getInstances(getApplicationContext());


        if(!prefrences.isLogin()) {
            Intent intent = new Intent(NotesActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }


       displayNotes(prefrences.getUserName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addItem) {
            //Insert Note
            openDialogBox(prefrences.getUserName());
        } else if(id == R.id.logout) {
            //Logout
            prefrences.logOut();
            Intent i = new Intent(NotesActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        return true;

    }

    private void openDialogBox(final String tablename) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_box, null);
        dialogBuilder.setView(dialogView);

        final EditText add = (EditText) dialogView.findViewById(R.id.edit1);

        dialogBuilder.setTitle("Custom dialog");
        dialogBuilder.setMessage("Enter Note Name");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                sqLoperation.insert(tablename,add.getText().toString());
                displayNotes(tablename);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }
/*######################### Display Notes ##############################################################*/

    void displayNotes(String username) //to display the notes
    {
        notes = sqLoperation.getNotes(username);

        if(!notes.isEmpty())
        {
            arrayAdapter = new ArrayAdapter<>(NotesActivity.this,android.R.layout.simple_list_item_1,notes);
            listView.setAdapter(arrayAdapter);

        }else{
            Toast.makeText(getApplicationContext(),"Note is empty",Toast.LENGTH_LONG).show();
        }
    }

}
