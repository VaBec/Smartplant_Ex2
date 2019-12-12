package de.htwg.smartplant.main.recycler.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import de.htwg.smartplant.R;
import de.htwg.smartplant.Utils;
import de.htwg.smartplant.rest.jsonmodels.Plant;

public class YourPlantsAdapter extends RecyclerView.Adapter<YourPlantsAdapter.PlantsViewHolder> {

    private String userName;
    private List<Plant> plants;

    public static class PlantsViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView imageView;
        public ProgressBar waterValue;
        public TextView macLabel;
        public TextView dateLabel;
        public LinearLayout plantContainer;
        public String userName;

        public PlantsViewHolder(View v, String name) {
            super(v);
            view = v ;
            userName = name;
            imageView = view.findViewById(R.id.plant_row_image);
            waterValue = view.findViewById(R.id.plant_row_watervalue);
            plantContainer = view.findViewById(R.id.plant_container);
            macLabel = view.findViewById(R.id.macLabel);
            dateLabel = view.findViewById(R.id.dateLabel);
        }
    }

    public YourPlantsAdapter(List<Plant> plants, String userName) {
        this.plants = plants;
        this.userName = userName;
    }

    @NonNull
    @Override
    public PlantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.plant_row, parent, false);
        return new PlantsViewHolder(v, this.userName);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsViewHolder plantsViewHolder, int i) {
        int image = Utils.getImageOfPlant(plants.get(i).getPlantType());
        plantsViewHolder.imageView.setImageResource(image);
        plantsViewHolder.dateLabel.setText("Stand vom: " + this.formatTimeStamp(
                plants.get(i).getTimeStamp()
        ));
        plantsViewHolder.macLabel.setText("MAC: " + plants.get(i).getMac());
        styleProgressBar(plants.get(i).getWaterValue(), plantsViewHolder.waterValue);
    }

    private String formatTimeStamp(String timeStamp) {
        String date = "";
        String time = "";

        for(int i=0 ; i<timeStamp.length() ; i++) {
            if(timeStamp.charAt(i) == ' ') {
                date = timeStamp.substring(0, i);

                break;
            }
        }

        boolean firstFound = false;
        int index = -1;

        for(int i=0 ; i<timeStamp.length() ; i++) {
            if(timeStamp.charAt(i) == ' ') {
                if(firstFound) {
                    time = timeStamp.substring(index, i);
                    break;
                } else {
                    firstFound = true;
                    index = i;
                }
            }
        }

        String day = "";
        String month = "";
        String year = "";

        int ctr = 0;
        int prevIndex = 0;

        for(int i=0 ; i<date.length() ; i++) {
            if(date.charAt(i) == '/') {
                if(ctr == 0) {
                    month = date.substring(0, i);
                } else if(ctr == 1) {
                    day = date.substring(prevIndex, i);
                }

                prevIndex = i+1;
                ctr++;
            }
        }

        year = date.substring(prevIndex);

        boolean isAm = time.toLowerCase().contains("am");

        int hours = -1;
        int minutes = -1;
        int seconds = -1;

        int hourIndex = -1;
        int minuteIndex = -1;
        time = time.replace(" ", "");
        for(int i=0 ; i<time.length() ; i++) {
            if(time.charAt(i) == ':') {
                hours = Integer.valueOf(time.substring(0, i));
                if(isAm) {
                    if(hours == 12) {
                        hours = 0;
                    }
                } else {
                    if(hours != 12) {
                        hours = hours + 12;
                    }
                }

                hourIndex = i+1;
                break;
            }
        }

        for(int i=hourIndex ; i<time.length() ; i++) {
            if(time.charAt(i) == ':') {
                minutes = Integer.valueOf(time.substring(hourIndex, i));
                minuteIndex = i+1;
                break;
            }
        }

        seconds = Integer.valueOf(time.substring(minuteIndex));

        String correctDate = day + "." + month + "." + year;

        String stringHours;
        String stringMinutes;
        String stringSeconds;

        if(hours < 10) {
            stringHours = "0" + hours;
        } else {
            stringHours = String.valueOf(hours);
        }

        if(minutes < 10) {
            stringMinutes = "0" + minutes;
        } else {
            stringMinutes = String.valueOf(minutes);
        }

        if(seconds < 10) {
            stringSeconds = "0" + seconds;
        } else {
            stringSeconds = String.valueOf(seconds);
        }

        String correctTime = stringHours + ":" + stringMinutes + ":" + stringSeconds;

        return "" + correctDate + ", " + correctTime + "";
    }

    @Override
    public int getItemCount() {
        return plants.size();
    }

    private void styleProgressBar(Integer waterValue, ProgressBar waterValueProgressBar) {
        String colorString;
        if(waterValue >= 682) {
            colorString = "#2389da";    // Water blue
        } else if(waterValue >= 341) {
            colorString = "#ffae42";    // Warning orange
        } else {
            colorString = "#ff5042";   // Error red
        }

        waterValueProgressBar.getProgressDrawable().setColorFilter(
                Color.parseColor(colorString), android.graphics.PorterDuff.Mode.SRC_IN);

        waterValueProgressBar.setProgress(waterValue);
    }
}
