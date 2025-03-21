package com.student22110006.fashionshop.data.remote

import com.student22110006.fashionshop.data.model.ApiResponse
import com.student22110006.fashionshop.data.model.account.AccountChangePasswordData
import com.student22110006.fashionshop.data.model.account.AccountChangePasswordRequest
import com.student22110006.fashionshop.data.model.account.AccountGenerateOtpData
import com.student22110006.fashionshop.data.model.account.AccountGenerateOtpRequest
import com.student22110006.fashionshop.data.model.account.AccountLoginData
import com.student22110006.fashionshop.data.model.account.AccountLoginRequest
import com.student22110006.fashionshop.data.model.account.AccountRegisterData
import com.student22110006.fashionshop.data.model.account.AccountRegisterRequest
import com.student22110006.fashionshop.data.model.account.AccountVerifyOtpData
import com.student22110006.fashionshop.data.model.account.AccountVerifyOtpRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountService {

    @POST("Auth/Login")
    suspend fun login(@Body loginRequest: AccountLoginRequest): ApiResponse<AccountLoginData>

    @POST("Account/Register")
    suspend fun register(@Body registerRequest: AccountRegisterRequest): ApiResponse<AccountRegisterData>

    @POST("Account/GenerateOtp")
    suspend fun generateOtp(@Body generateOtpRequest: AccountGenerateOtpRequest): ApiResponse<AccountGenerateOtpData>

    @POST("Account/VerifyOtp")
    suspend fun verifyOtp(@Body verifyOtpRequest: AccountVerifyOtpRequest): ApiResponse<AccountVerifyOtpData>

    @POST("Account/ChangePassword")
    suspend fun changePassword(@Body changePasswordRequest: AccountChangePasswordRequest): ApiResponse<AccountChangePasswordData>
}