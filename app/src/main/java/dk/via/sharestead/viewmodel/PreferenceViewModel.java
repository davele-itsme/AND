package dk.via.sharestead.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.ArrayList;
import java.util.List;

import dk.via.sharestead.R;
import dk.via.sharestead.model.SliderItem;

public class PreferenceViewModel extends AndroidViewModel {
    private List<SliderItem> sliderItems;

    public PreferenceViewModel(@NonNull Application application) {
        super(application);
        sliderItems = new ArrayList<>();
        setImages();
    }

    private void setImages() {
        sliderItems.add(new SliderItem("COMPUTER", R.drawable.img_computer));
        sliderItems.add(new SliderItem("CONSOLE", R.drawable.img_console));
        sliderItems.add(new SliderItem("MOBILE", R.drawable.img_mobile));
        sliderItems.add(new SliderItem("VIRTUAL REALITY", R.drawable.img_vr));
    }

    public List<SliderItem> getSliderItems() {
        return sliderItems;
    }

    public void selectPlatform(int clickedItemIndex) {
        SliderItem sliderItem = sliderItems.get(clickedItemIndex);
        if(sliderItem.isSelected())
        {
            sliderItem.setSelected(false);
        }
        else {
            sliderItem.setSelected(true);
        }
    }

    public boolean verifyPlatforms() {
        boolean oneTrue = false;
        for(SliderItem sliderItem: sliderItems)
        {
            if(sliderItem.isSelected())
            {
                if(oneTrue)
                {
                    return false;
                }
                else {
                    oneTrue = true;
                }
            }
        }
        return oneTrue;
    }

    public String getSelectedPlatform() {
        String platform = "";
        for(SliderItem sliderItem: sliderItems)
        {
            if(sliderItem.isSelected())
            {
                platform = sliderItem.getPlatformText();
                break;
            }
        }
        return platform;
    }
}
