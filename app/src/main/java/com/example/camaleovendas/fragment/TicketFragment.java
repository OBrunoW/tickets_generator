package com.example.camaleovendas.fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.camaleovendas.R;
import com.example.camaleovendas.adapter.ConsolidatedAdapterList;
import com.example.camaleovendas.adapter.ProductAdapterList;
import com.example.camaleovendas.controller.ConsolidatedController;
import com.example.camaleovendas.controller.EventController;
import com.example.camaleovendas.controller.ProductController;
import com.example.camaleovendas.model.Consolidated;
import com.example.camaleovendas.model.Event;
import com.example.camaleovendas.model.Product;
import com.example.camaleovendas.util.Util;
import com.example.camaleovendas.view.MainActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class TicketFragment extends Fragment {

    private MainActivity mainActivity;
    private Bitmap mBitmapDraw, bitmap;
    private Canvas mCanvasDraw;
    private EditText editName;
    private ImageView selectImage;

    private static final String TAG_FRAGMENT = "fragment_main_menu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ticket, container, false);

        mainActivity = (MainActivity) getActivity();

        assert mainActivity != null;

        Objects.requireNonNull(mainActivity.getSupportActionBar()).setTitle("Camaleões vendas");
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mainActivity.getSupportActionBar().setElevation(0);
        mainActivity.getSupportActionBar().show();

        editName = view.findViewById(R.id.edit_name);

        LinearLayout layout = view.findViewById(R.id.layout_canvas);
        layout.addView(new Preview(getContext()));

        LinearLayout previewLayout = view.findViewById(R.id.layout_canvas_border);
        previewLayout.setBackground(getContext().getDrawable(R.color.color_coil_termic_printer));

        ImageView selectImage = view.findViewById(R.id.select_image);
        selectImage.setOnClickListener(v -> startGallery());

        List <Event> list = new EventController(getContext()).getEvent();
        if(list.size() > 0) bitmap = Util.getImage(list.get(0).getImage());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1000) {

                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images
                            .Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    setBitmap(bitmapImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startGallery() {

        Intent cameraIntent = new Intent(Intent.ACTION_PICK);
        cameraIntent.setType("image/*");
        startActivityForResult(cameraIntent, 1000);

    }

    private void setBitmap(Bitmap bitmapFileSelected) {
        bitmap = bitmapFileSelected;
        EventController controller = new EventController(getContext());
        Event event = new Event();
        event.setName(null);
        event.setImage(Util.getBytes(bitmap));

        if(controller.getEvent().size() > 0) {
            event.setId(1);
            controller.update(event);
        } else controller.save(event);
    }

    private class Preview extends View {
        Paint paint = new Paint();

        public Preview(Context context) {
            super(context);
        }

        @SuppressLint("DrawAllocation")
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec/2 - heightMeasureSpec/4 - heightMeasureSpec/13);
        }

        @SuppressLint("DrawAllocation")
        @Override
        public void onDraw(Canvas canvas) {
            mBitmapDraw = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

            mCanvasDraw = new Canvas(mBitmapDraw);

            paint.setColor(getContext().getColor(R.color.color_coil_termic_printer));
            mCanvasDraw.drawPaint(paint);

            paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.BLACK);
            paint.setTextSize(36);

            int xPos = getWidth()/4;
            int yPos = getHeight()/4;

            String[] date = Util.getDateTimeNow().split(" ");
            mCanvasDraw.drawText("Válido até " + date[0], xPos, yPos, paint);

            paint.setTextSize(96);
            xPos = getWidth()/5;
            yPos = getHeight() - getHeight()/8;

            mCanvasDraw.drawText("Produto", xPos, yPos, paint);

            xPos = getRight() - 250;
            yPos = 0;

            if(bitmap != null) {
                Bitmap mBitmap = Util.setImageSizeColor(bitmap, 250, 250);
                mCanvasDraw.drawBitmap(Util.replaceColor(mBitmap), xPos, yPos, paint);
            }

            canvas.drawBitmap(mBitmapDraw, getLeft(), getTop(), paint);
            //invalidate();
        }

    }
}
