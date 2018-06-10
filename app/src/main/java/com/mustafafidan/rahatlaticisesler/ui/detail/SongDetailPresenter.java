package com.mustafafidan.rahatlaticisesler.ui.detail;

import com.mustafafidan.rahatlaticisesler.base.BasePresenter;
import com.mustafafidan.rahatlaticisesler.model.Sound;

import java.util.ArrayList;
import java.util.List;

public class SongDetailPresenter  extends BasePresenter<SongDetailView> {
    public SongDetailPresenter(SongDetailView view) {
        super(view);
    }



    public void getFavouritesByUserId(int userId){


        view.showLoading();


        List<Sound> items = new ArrayList<>();

        items.add(new Sound(){{
            setCategoryId(1);
            setSoundId(1);
            setSoundImageUrl("http://sansebastianregion.com/documents/1515268/1564963/san-sebastian-holidays-parque-aralar.jpg/987c0e27-16b8-4d5e-bfe6-3dd66a8a7425?t=1484001899000");
            setSoundName("Doğa");
            setSoundUrl("https://www.sample-videos.com/audio/mp3/crowd-cheering.mp3");
            setTotalSecond(27);
        }});

        items.add(new Sound(){{
            setCategoryId(2);
            setSoundId(2);
            setSoundImageUrl("http://sansebastianregion.com/documents/1515268/1564963/san-sebastian-holidays-parque-aralar.jpg/987c0e27-16b8-4d5e-bfe6-3dd66a8a7425?t=1484001899000");
            setSoundName("Ninni");
            setSoundUrl("https://www.sample-videos.com/audio/mp3/wave.mp3");
            setTotalSecond(45);
        }});

        view.hideLoading();
        view.updateItems(items);

    }
}
