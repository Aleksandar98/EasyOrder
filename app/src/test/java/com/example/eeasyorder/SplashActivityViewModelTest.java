package com.example.eeasyorder;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.eeasyorder.domain.use_case.token_usecases.GetTokenUseCase;
import com.example.eeasyorder.domain.use_case.user_usecases.GetUserNameUseCase;
import com.example.eeasyorder.ui.SplashActivityViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;


@RunWith(MockitoJUnitRunner.class)
public class SplashActivityViewModelTest {

    @Rule // -> allows liveData to work on different thread besides main, must be public!
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private GetTokenUseCase getTokenUseCase ;
    private GetUserNameUseCase getUserNameUseCase;
    private SplashActivityViewModel viewModel ;
    private Observer<String> mockObserver;

    @Before
    public void setUp(){
        getUserNameUseCase = Mockito.mock(GetUserNameUseCase.class);
        getTokenUseCase = Mockito.mock(GetTokenUseCase.class);
        viewModel = new SplashActivityViewModel(getTokenUseCase,getUserNameUseCase);

        mockObserver = Mockito.mock(Observer.class);
        viewModel.getTokenResult.observeForever(mockObserver);
    }

    @Test
    public void testGetToken() throws InterruptedException {
        Flowable<String> expectedRes = Flowable.just("asdsadsad");

        Mockito.when(getTokenUseCase.invoke()).thenReturn(expectedRes);

        viewModel.getToken();

        String token = LiveDataTestUtil.getValue(viewModel.getTokenResult);
        assertEquals(token,"asdsadsad");
    }

    @Test
    public void testGetUserName() throws InterruptedException {
        Flowable<String> expectedRes = Flowable.just("UserName1");

        Mockito.when(getUserNameUseCase.invoke()).thenReturn(expectedRes);

        viewModel.getUserName();

        String token = LiveDataTestUtil.getValue(viewModel.getUserNameResult);
        assertEquals(token,"UserName1");
    }
}