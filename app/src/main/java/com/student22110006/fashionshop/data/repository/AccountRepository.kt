package com.student22110006.fashionshop.data.repository

import com.student22110006.fashionshop.data.model.account.AccountChangePasswordRequest
import com.student22110006.fashionshop.data.model.account.AccountGenerateOtpRequest
import com.student22110006.fashionshop.data.model.account.AccountLoginRequest
import com.student22110006.fashionshop.data.model.account.AccountRegisterRequest
import com.student22110006.fashionshop.data.model.account.AccountVerifyOtpRequest
import com.student22110006.fashionshop.data.remote.ApiClient

class AccountRepository {

    private val api = ApiClient.accountService

    suspend fun login(loginRequest: AccountLoginRequest) = api.login(loginRequest)

    suspend fun register(registerRequest: AccountRegisterRequest) = api.register(registerRequest)

    suspend fun generateOtp(generateOtpRequest: AccountGenerateOtpRequest) = api.generateOtp(generateOtpRequest)

    suspend fun verifyOtp(verifyOtpRequest: AccountVerifyOtpRequest) = api.verifyOtp(verifyOtpRequest)

    suspend fun changePassword(changePasswordRequest: AccountChangePasswordRequest) = api.changePassword(changePasswordRequest)
}