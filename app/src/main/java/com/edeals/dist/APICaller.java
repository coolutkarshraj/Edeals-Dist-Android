package com.edeals.dist;

import android.app.Activity;
import android.app.ProgressDialog;

import com.edeals.dist.models.BrandsModel;
import com.edeals.dist.models.DistributorProfile;
import com.edeals.dist.models.InboxModel;
import com.edeals.dist.models.LoginModel;
import com.edeals.dist.models.NotificationModel;
import com.edeals.dist.models.OrdersModel;
import com.edeals.dist.models.BrandAndProductsModel;
import com.edeals.dist.models.RegistrationModel;
import com.edeals.dist.utils.LocalStorage;
import com.edeals.dist.utils.UrlLocator;
import com.edeals.dist.utils.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import java.util.ArrayList;

/**
 * Created by team edeals on 19-02-2018.
 */

public class APICaller {

    public static ProgressDialog progressDialog;

    public static void getProducts(Activity activity,
                                   final FutureCallback<BrandAndProductsModel> apiCallBack) {
        final DistributorProfile profile = LocalStorage.getInstance(activity).getDistributorProfile();
        JsonObject json = new JsonObject();
        json.addProperty("groupBy", "brand");
        json.addProperty("distributorId", profile.id);
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.products))
                .noCache().setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        System.out.println("result " + result);
                        BrandAndProductsModel brandAndProducts = gson.fromJson(result, BrandAndProductsModel.class);
                        apiCallBack.onCompleted(e, brandAndProducts);
                    }
                });
    }

    public static void registerUser(final RegistrationModel registrationModel,
                                    final Activity activity,
                                    final FutureCallback<RegistrationModel> apiCallBack) {
        progressDialog = Utils.getProgressDialog(activity, "Registering you please wait...");
        final Gson gson = new Gson();
        String requestString = gson.toJson(registrationModel);
        JsonObject json = gson.fromJson(requestString, JsonObject.class);
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.registerUser))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        progressDialog.dismiss();
                        RegistrationModel registrationModel = gson.fromJson(result, RegistrationModel.class);
                        apiCallBack.onCompleted(e, registrationModel);
                    }
                });
    }

    public static void loginUser(final LoginModel loginModel,
                                 final Activity activity,
                                 final FutureCallback<Response<JsonObject>> apiCallBack) {
        progressDialog = Utils.getProgressDialog(activity, "Logging in please wait...");
        final Gson gson = new Gson();
        final String requestString = gson.toJson(loginModel);
        JsonObject json = gson.fromJson(requestString, JsonObject.class);
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.login))
                .noCache()
                .setJsonObjectBody(json)
                .asJsonObject()
                .withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {

                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        progressDialog.dismiss();
                        apiCallBack.onCompleted(e, result);
                    }
                });
    }

    public static void logoutUser(final Activity activity, final FutureCallback apiCallBack) {
        progressDialog = Utils.getProgressDialog(activity, "Logging out please wait...");
        DistributorProfile profile = LocalStorage.getInstance(activity).getDistributorProfile();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.logout, profile.id))
                .noCache()
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {

                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        progressDialog.dismiss();
                        apiCallBack.onCompleted(e, result);
                    }
                });
    }

    public static void updateOnlineStatus(final Activity activity, final int onlineStatus, final FutureCallback apiCallBack) {
        progressDialog = Utils.getProgressDialog(activity, "Updating status please wait...");
        final DistributorProfile profile = LocalStorage.getInstance(activity).getDistributorProfile();
        JsonObject json = new JsonObject();
        json.addProperty("userId", profile.id);
        json.addProperty("onlineStatus", onlineStatus);
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.updateOnlineStatus))
                .noCache().setJsonObjectBody(json)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {

                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        progressDialog.dismiss();
                        apiCallBack.onCompleted(e, result);
                    }
                });
    }

    public static void updateDeviceToken(final Activity activity, final FutureCallback apiCallBack) {
        DistributorProfile profile = LocalStorage.getInstance(activity).getDistributorProfile();
        JsonObject json = new JsonObject();
        json.addProperty("userId", profile.id);
        json.addProperty("token", FirebaseInstanceId.getInstance().getToken());
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.updateDeviceToken))
                .noCache().setJsonObjectBody(json)
                .asString()
                .withResponse()
                .setCallback(new FutureCallback<Response<String>>() {

                    @Override
                    public void onCompleted(Exception e, Response<String> result) {
                        apiCallBack.onCompleted(e, result);
                    }
                });
    }

    public static void getAllBrands(Activity activity,
                                    final FutureCallback<BrandsModel> apiCallBack) {
        final Gson gson = new Gson();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.defaultBrands))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        BrandsModel brands = gson.fromJson(result, BrandsModel.class);
                        apiCallBack.onCompleted(e, brands);
                    }
                });
    }

    public static void getAllOrders(Activity activity,
                                    final FutureCallback<OrdersModel> apiCallBack) {
        final Gson gson = new Gson();
        DistributorProfile profile = LocalStorage.getInstance(activity).getDistributorProfile();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.orders, profile.id))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        OrdersModel orders = null;
                        try {
                            orders = gson.fromJson(result, OrdersModel.class);
                        } catch (JsonSyntaxException e1) {
                            e1.printStackTrace();
                        }
                        apiCallBack.onCompleted(e, orders);
                    }
                });
    }

    public static void getInbox(Activity activity, final FutureCallback<InboxModel> apiCallBack) {
        final Gson gson = new Gson();
        DistributorProfile profile = LocalStorage.getInstance(activity).getDistributorProfile();
        Ion.with(activity)
                .load(UrlLocator.getFinalUrl(Config.Url.inbox, profile.id))
                .noCache()
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        InboxModel inbox = null;
                        try {
                            inbox = gson.fromJson(result, InboxModel.class);
                        } catch (JsonSyntaxException e1) {
                            e1.printStackTrace();
                        }
                        apiCallBack.onCompleted(e, inbox);
                    }
                });
    }
}
