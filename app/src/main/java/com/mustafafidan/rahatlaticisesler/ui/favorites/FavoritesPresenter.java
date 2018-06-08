package com.mustafafidan.rahatlaticisesler.ui.favorites;

import com.mustafafidan.rahatlaticisesler.base.BasePresenter;
import com.mustafafidan.rahatlaticisesler.model.Sound;
import com.mustafafidan.rahatlaticisesler.network.FavoritesApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class FavoritesPresenter extends BasePresenter<FavoritesView> {

    @Inject
    FavoritesApi favoritesApi;

    public FavoritesPresenter(FavoritesView view) {
        super(view);
    }

    public void getFavouritesByUserId(int userId){
        List<Sound> favorites = new ArrayList<>();

        favorites.add(new Sound(){{
            setCategoryId(1);
            setSoundId(1);
            setSoundImageUrl("http://sansebastianregion.com/documents/1515268/1564963/san-sebastian-holidays-parque-aralar.jpg/987c0e27-16b8-4d5e-bfe6-3dd66a8a7425?t=1484001899000");
            setSoundName("Doğa");
            setSoundUrl("https://www.sample-videos.com/audio/mp3/crowd-cheering.mp3");
            setTotalSecond(27);
        }});

        favorites.add(new Sound(){{
            setCategoryId(2);
            setSoundId(2);
            setSoundImageUrl("http://sansebastianregion.com/documents/1515268/1564963/san-sebastian-holidays-parque-aralar.jpg/987c0e27-16b8-4d5e-bfe6-3dd66a8a7425?t=1484001899000");
            setSoundName("Ninni");
            setSoundUrl("https://www.sample-videos.com/audio/mp3/wave.mp3");
            setTotalSecond(45);
        }});



        view.updateFavorites(favorites);





    }




}
