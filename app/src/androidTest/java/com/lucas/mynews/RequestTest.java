package com.lucas.mynews;

import android.support.test.runner.AndroidJUnit4;

import com.lucas.mynews.Models.MostPopular.MostPopularResponse;
import com.lucas.mynews.Models.MovieReviews.MovieReviewsResponse;
import com.lucas.mynews.Models.Search.SearchResponse;
import com.lucas.mynews.Models.TopStories.TopStoriesResponse;
import com.lucas.mynews.Utils.NyTimeStreams;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class RequestTest {

    @Test
    public void fetchTopStoriesArticles() throws Exception {

        Observable<TopStoriesResponse> observableArticles = NyTimeStreams.streamFetchTopStoriesArticles("home", "CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST");
        //2 - Create a new TestObserver
        TestObserver<TopStoriesResponse> testObserver = new TestObserver<>();
        //3 - Launch observable
        observableArticles.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue
    }

    @Test
    public void fetchMostPopularArticles() throws Exception {

        Observable<MostPopularResponse> observableArticles = NyTimeStreams.streamFetchMostPopularArticles(1, "CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST");
        //2 - Create a new TestObserver
        TestObserver<MostPopularResponse> testObserver = new TestObserver<>();
        //3 - Launch observable
        observableArticles.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue
    }

    @Test
    public void fetchMovieReviewsArticles() throws Exception {

        Observable<MovieReviewsResponse> observableArticles = NyTimeStreams.streamFetchMovieReviewsArticles("all", "CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST");
        //2 - Create a new TestObserver
        TestObserver<MovieReviewsResponse> testObserver = new TestObserver<>();
        //3 - Launch observable
        observableArticles.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue
    }

    @Test
    public void fetchSearchArticles() throws Exception {

        Observable<SearchResponse> observableArticles = NyTimeStreams.streamFetchSearchArticles("election","20160101", "","CMCk9Nz5BAjNKu5cF8nkDmoMzd3EOJST");
        //2 - Create a new TestObserver
        TestObserver<SearchResponse> testObserver = new TestObserver<>();
        //3 - Launch observable
        observableArticles.subscribeWith(testObserver)
                .assertNoErrors() // 3.1 - Check if no errors
                .assertNoTimeout() // 3.2 - Check if no Timeout
                .awaitTerminalEvent(); // 3.3 - Await the stream terminated before continue
    }
}