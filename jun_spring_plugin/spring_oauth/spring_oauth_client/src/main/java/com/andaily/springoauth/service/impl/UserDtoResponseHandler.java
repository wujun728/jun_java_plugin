package com.andaily.springoauth.service.impl;

import com.andaily.springoauth.infrastructure.httpclient.MkkHttpResponse;
import com.andaily.springoauth.service.dto.UserDto;

/**
 * 15-5-18
 *
 * @author Wujun
 */
public class UserDtoResponseHandler extends AbstractResponseHandler<UserDto> {


    private UserDto userDto;

    public UserDtoResponseHandler() {
    }

    /*
    * Response is JSON or  XML (failed)
    *
    *  Error data:
    *  <oauth><error_description>Invalid access token: 3420d0e0-ed77-45e1-8370-2b55af0a62e8</error_description><error>invalid_token</error></oauth>
    *
    * */
    @Override
    public void handleResponse(MkkHttpResponse response) {
        if (response.isResponse200()) {
            this.userDto = responseToDto(response, new UserDto());
        } else {
            this.userDto = responseToErrorDto(response, new UserDto());
        }
    }


    public UserDto getUserDto() {
        return userDto;
    }


}
