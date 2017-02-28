package com.ekoyulianto.qr_gen_read;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    Button buttonGen;
    Button buttonClr;
    private EditText editText;
    public String hasil;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Context context = this;
        editText = (EditText) this.findViewById(R.id.inputText);
        buttonClr = (Button) findViewById(R.id.buttonClr);
        buttonGen = (Button) findViewById(R.id.buttonGen);

        buttonClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                editText.setText("");
            }
        });

        buttonGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    hasil = editText.getText().toString();
                    BitMatrix bitMatrix = multiFormatWriter.encode(hasil, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    Intent intent = new Intent(context, GenerateActivity.class);
                    intent.putExtra("pic", bitmap);
                    startActivity(intent);
                } catch (WriterException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;

        menu.add(0, 1, 0, "Scan QR Code");
        menu.add(0, 2, 0, "Exit");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent i = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(i);
            case 2:
                finish();
                return true;
        }
        return false;
    }
}
