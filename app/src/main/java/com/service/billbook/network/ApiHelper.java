package com.service.billbook.network;

import com.service.billbook.servicemodels.AddItemModel;
import com.service.billbook.servicemodels.CreateInvoiceModel;
import com.service.billbook.servicemodels.CreatePartyModel;
import com.service.billbook.servicemodels.DeleteSettingsModel;
import com.service.billbook.servicemodels.GetAdditionalSettingsModel;
import com.service.billbook.servicemodels.ItemListResponseModel;
import com.service.billbook.servicemodels.PartyListResponseModel;
import com.service.billbook.servicemodels.SetAdditionalSettingsModel;
import com.service.billbook.servicemodels.CategoryListModel;
import com.service.billbook.servicemodels.CreateCategoryModel;
import com.service.billbook.servicemodels.DecodeModel;
import com.service.billbook.servicemodels.EncodeModel;
import com.service.billbook.servicemodels.GetAllDropDownModel;
import com.service.billbook.servicemodels.SendOtpModel;
import com.service.billbook.servicemodels.UpdatePartyModel;
import com.service.billbook.servicemodels.VerifyOTPModel;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiHelper {

    @Headers({"Content-Type:application/json"})
    @POST("otp")
    Call<SendOtpModel> getSendOtp(@Query("strMobileNo") String strMobileNo);

    @Headers({"Content-Type:application/json"})
    @POST("Decode")
    Call<DecodeModel> Decode(@Query("str") String str);

    @Headers({"Content-Type:application/json"})
    @POST("Encode")
    Call<EncodeModel> Encode(@Query("str") String str);

    @Headers({"Content-Type:application/json"})
    @POST("Login")
    Call<VerifyOTPModel> VerifyOTP(@Query("strotp") String strotp, @Query("strMobileNo") String strMobileNo);

    @Headers({"Content-Type:application/json"})
    @GET("All-Category")
    Call<CategoryListModel> getCategoryList(@Query("Uid") String Uid);

    @Headers({"Content-Type:application/json"})
    @GET("All-Dropdown")
    Call<GetAllDropDownModel> getAllDropDownList(@Query("Uid") String Uid);

    @Multipart
    @POST("Add-item")
    Call<AddItemModel> addItem(@Query("uid") String uid, @PartMap Map<String, RequestBody> bodyMap,
                               @Part MultipartBody.Part ProfileImageA, @Part MultipartBody.Part ProfileImageB, @Part MultipartBody.Part ProfileImageC);

    @FormUrlEncoded
    @POST("Create-Category")
    Call<CreateCategoryModel> CreateCategory(@Query("Uid") String Uid, @Field("strCategoryName") String strCategoryName);

    @Headers({"Content-Type:application/json"})
    @POST("Additional-Setting")
    Call<SetAdditionalSettingsModel> AdditionalSetting(@Query("Uid") String Uid, @Query("xml") String xml);

    @Headers({"Content-Type:application/json"})
    @POST("Fetch-Setting")
    Call<GetAdditionalSettingsModel> getAdditionalSetting(@Query("Uid") String Uid);

    @Headers({"Content-Type:application/json"})
    @GET("Fetch-item")
    Call<ItemListResponseModel> getItemList(@Query("Uid") String Uid);

    @Headers({"Content-Type:application/json"})
    @POST("Delete-Setting")
    Call<DeleteSettingsModel> DeleteSettings(@Query("uid") String uid, @Query("fieldValue") String fieldValue);

    @FormUrlEncoded
    @POST("create-invoice")
    Call<CreateInvoiceModel> CreateInvoice(@Query("Uid") String Uid,
                                           @Field("strPartyId") String strPartyId,
                                           @Field("strPartyName") String strPartyName,
                                           @Field("bigintSaleInvoiceNo") String bigintSaleInvoiceNo,
                                           @Field("dtSaleInvoiceDate") String dtSaleInvoiceDate,
                                           @Field("strTax") String strTax,
                                           @Field("dcTotalAmount") String dcTotalAmount,
                                           @Field("dcCashRecived") String dcCashRecived,
                                           @Field("dcBalanceAmount") String dcBalanceAmount,
                                           @Field("dcPreviousPartyBalance") String dcPreviousPartyBalance
//                                            @Field("StrUserName") String StrUserName,
//                                            @Field("intIndustryTypeId") String intIndustryTypeId,
//                                            @Field("strBussinessTypeId") String strBussinessTypeId,
//                                            @Field("IntBillType") String IntBillType,
//                                            @Field("strBussinessName") String strBussinessName,
//                                            @Field("strUPIID") String strUPIID,
//                                            @Field("strAccountNumber") String strNotes,
    );

    @FormUrlEncoded
    @POST("Create-Party")
    Call<CreatePartyModel> CreateParty(@Query("Uid") String Uid,
                                       @Field("strPartyName") String strPartyName,
                                       @Field("MobileNo") String MobileNo,
                                       @Field("strAddress") String strAddress,
                                       @Field("bitIsAddressSame") String bitIsAddressSame,
                                       @Field("strShippingAddress") String strShippingAddress,
                                       @Field("strGSTNo") String strGSTNo,
                                       @Field("intPartyType") String intPartyType,
                                       @Field("intCreditPeriod") String intCreditPeriod,
                                       @Field("dcCreditLimit") String dcCreditLimit
    );

    @FormUrlEncoded
    @POST("Update-Party")
    Call<UpdatePartyModel> UpdateParty(@Query("Uid") String Uid,
                                       @Field("bigintPartyId") String bigintPartyId,
                                       @Field("strPartyName") String strPartyName,
                                       @Field("MobileNo") String MobileNo,
                                       @Field("strAddress") String strAddress,
                                       @Field("bitIsAddressSame") String bitIsAddressSame,
                                       @Field("strShippingAddress") String strShippingAddress,
                                       @Field("strGSTNo") String strGSTNo,
                                       @Field("intPartyType") String intPartyType,
                                       @Field("intCreditPeriod") String intCreditPeriod,
                                       @Field("dcCreditLimit") String dcCreditLimit
    );

    @Headers({"Content-Type:application/json"})
    @GET("Fetch-Party")
    Call<PartyListResponseModel> getPartyList(@Query("Uid") String Uid);
}
