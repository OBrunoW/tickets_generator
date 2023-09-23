package com.example.camaleovendas.fragment;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.camaleovendas.R;
import com.example.camaleovendas.controller.EventController;
import com.example.camaleovendas.model.Event;
import com.example.camaleovendas.util.Util;
import com.example.camaleovendas.view.MainActivity;

import java.io.IOException;
import java.util.List;
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

        editName = view.findViewById(R.id.edit_name);

        LinearLayout layout = view.findViewById(R.id.layout_canvas);
        layout.addView(new Preview(getContext()));

        LinearLayout previewLayout = view.findViewById(R.id.layout_canvas_border);
        previewLayout.setBackground(getContext().getDrawable(R.color.coil));

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

            paint.setColor(getContext().getColor(R.color.coil));
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
