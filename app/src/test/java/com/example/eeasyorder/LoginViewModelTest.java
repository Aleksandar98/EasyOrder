package com.example.eeasyorder;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.example.eeasyorder.data.Resource;
import com.example.eeasyorder.data.remote.dto.ResponseDto;
import com.example.eeasyorder.data.remote.dto.UserDto;
import com.example.eeasyorder.domain.model.User;
import com.example.eeasyorder.domain.use_case.login.LoginUserUseCase;
import com.example.eeasyorder.domain.use_case.token_usecases.SetTokenUseCase;
import com.example.eeasyorder.domain.use_case.user_usecases.SetUserNameUseCase;
import com.example.eeasyorder.ui.LoginScreen.LoginViewModel;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Single;

@RunWith(MockitoJUnitRunner.class)
public class LoginViewModelTest {

    @Rule // -> allows liveData to work on different thread besides main, must be public!
    public InstantTaskExecutorRule executorRule = new InstantTaskExecutorRule();

    private  LoginUserUseCase loginUserUseCase;
    private  SetTokenUseCase setTokenUseCase;
    private  SetUserNameUseCase setUserNameUseCase;
    private LoginViewModel viewModel ;
    private Observer<Resource<User>> mockObserver;
    @Before
    public void setUp(){
        loginUserUseCase = Mockito.mock(LoginUserUseCase.class);
        setTokenUseCase = Mockito.mock(SetTokenUseCase.class);
        setUserNameUseCase = Mockito.mock(SetUserNameUseCase.class);
        viewModel = new LoginViewModel(loginUserUseCase,setTokenUseCase,setUserNameUseCase);

        mockObserver = Mockito.mock(Observer.class);
        viewModel.loginResult.observeForever(mockObserver);
    }

    @Test
    public void testLoginUser() throws InterruptedException {


        UserDto userDto = new Gson().fromJson("{\"status\":\"OK\",\"code\":200,\"data\":{\"customer_account\":{\"id\":7794,\"type_id\":1,\"reference_type\":\"CustomerAccount\",\"customer_id\":7772,\"brand_id\":1,\"first_name\":\"Test\",\"last_name\":\"Test\",\"email\":\"test@testmenu.app\",\"confirmed\":false,\"phone_number\":\"\",\"locale\":\"en\",\"state\":1,\"demographics\":[],\"optin_status_email\":3,\"optin_status_pn\":3,\"updated_at\":\"2022-02-11 13:33:22\",\"created_at\":\"2019-07-05 06:44:37\"},\"token\":{\"value\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvYXBpLXBsYXlncm91bmQubWVudS5hcHBcL2FwaVwvY3VzdG9tZXJzXC9sb2dpbiIsImlhdCI6MTY2MTE2MTg4NiwiZXhwIjoxNjYxMTY1NDg2LCJuYmYiOjE2NjExNjE4ODYsImp0aSI6Inl2NFdSZ0p2OTljMzJKbkoiLCJzdWIiOjc3OTQsInBydiI6ImNjMzI5MjFhMTU0ODBhMTE3ZDliYmM3MmMwZTEyNTZhNjg1MjQ1OGIiLCJhcHBsaWNhdGlvbl9pZCI6Mywic2Vzc2lvbl9pZCI6OTc5NDB9.gIohrDg9JrTkudfXD_B85pzGCFSD-9vSJ_OS0QUrH-8\",\"ttl\":60,\"refresh_ttl\":2628000}}}",UserDto.class);
        ResponseDto responseDto = new ResponseDto("OK",200,userDto);
        Single<ResponseDto> expectedRes = Single.just(responseDto);
        String email = "test@testmenu.app";
        String pass = "test1234";

        HashMap<String,String> body = new HashMap();
        body.put("email",email);
        body.put("password",pass);

        Mockito.when(loginUserUseCase.invoke(body)).thenReturn(expectedRes);

        viewModel.loginUser(email,pass);

        //Resource<User> resource = LiveDataTestUtil.getValue(viewModel.loginResult);

        Mockito.verify(mockObserver,Mockito.times(1)).onChanged(Mockito.isA(Resource.Loading.class));

        Mockito.verify(mockObserver,Mockito.times(1)).onChanged(Mockito.isA(Resource.Error.class));

        verifyNoMoreInteractions(mockObserver);
        //assertThat(resource).isInstanceOf(Resource.Success.class);
    }

}
