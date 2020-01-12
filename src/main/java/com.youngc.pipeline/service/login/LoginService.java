package com.youngc.pipeline.service.login;

import java.util.Map;

public interface LoginService {
    Map login(String studentNumber, String password, int identity);

}
